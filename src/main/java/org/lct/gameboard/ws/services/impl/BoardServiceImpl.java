/*
 * Scrabble Helper Module 2015. 
 * Written by Sylvain Gourio
 * sylvain.gourio@gmail.com
 */

package org.lct.gameboard.ws.services.impl;


import org.lct.gameboard.ws.beans.model.Tile;
import org.lct.gameboard.ws.beans.view.DroppedWord;
import org.lct.gameboard.ws.beans.view.BoardGame;
import org.lct.gameboard.ws.beans.view.DroppedTile;
import org.lct.gameboard.ws.beans.view.Square;
import org.lct.gameboard.ws.services.BoardService;
import org.lct.dictionary.beans.*;
import org.lct.dictionary.beans.Dictionary;
import org.lct.dictionary.services.DictionaryService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;

/**
 * Board game service
 * Created by sgourio on 30/03/15.
 */
public class BoardServiceImpl implements BoardService {

    private static final Logger logger = LoggerFactory.getLogger(BoardServiceImpl.class);

    @Override
    public Set<DroppedWord> findBestWord(DictionaryService dictionaryService, Dictionary dictionary, BoardGame boardGame, List<Tile> tileList){
        //logBoardGameWithSquare(boardGame);
        Set<DroppedWord> bestWordList = null;
        Set<DroppedWord> horizontalWordList = findHorizontalBestWord(dictionaryService, dictionary, boardGame, tileList);
        Set<DroppedWord> verticalWordList = findVerticalBestWord(dictionaryService, dictionary, boardGame, tileList);
        bestWordList = chooseBestSet(horizontalWordList, verticalWordList);
        return bestWordList;
    }

    @Override
    public int wordInternalScore(BoardGame boardGame, DroppedWord droppedWord){
        int score = droppedWord.getPoints() * 100000;
        if( !droppedWord.isContainWilcard() ){
            score += 10000;
        }
        int line = droppedWord.getRow();
        int column = droppedWord.getColumn();
        for( Square ignored : droppedWord.getSquareList()){
            if( isAnchorHorizontal(line, column, boardGame) ){
                score += 100;
            }
            if( droppedWord.isHorizontal() ){
                column++;
            }else{
                line++;
            }
        }
        score += boardGame.getSquares().length - droppedWord.getRow();
        score += boardGame.getSquares()[0].length - droppedWord.getColumn();
        return score;
    }

    /**
     * Find the best vertical words in this board game.
     * If board is empty, no word is allowed
     * @param dictionaryService
     * @param boardGame
     * @param tileList
     * @return
     */
    private Set<DroppedWord> findVerticalBestWord(DictionaryService dictionaryService, Dictionary dictionary, BoardGame boardGame, List<Tile> tileList) {
        Set<DroppedWord> result = new HashSet<DroppedWord>();
        if( !boardGame.getMiddleSquare().isEmpty() ) { // first turn is horizontal
            Set<DroppedWord> wordList = findHorizontalBestWord(dictionaryService, dictionary, boardGame.transpose(), tileList);
            for (DroppedWord word : wordList) {
                DroppedWord w = word.transpose();
                result.add(w);
            }
        }
        return result;
    }

    /**
     * Find best horizontal words for this board game
     * @param dictionaryService
     * @param boardGame
     * @param tileList
     * @return
     */
    private Set<DroppedWord> findHorizontalBestWord(DictionaryService dictionaryService, Dictionary dictionary, BoardGame boardGame, List<Tile> tileList) {
        Set<DroppedWord> wordList = new HashSet<DroppedWord>();
        for( int i = 0; i < boardGame.getSquares().length ; i++){
            for( int j = 0; j < boardGame.getSquares()[0].length; j++){
                if( isAnchorHorizontal(i, j, boardGame)){
                    int emptyLeftCount = getLeftEmtyCount(i, j, boardGame);
                    Set<DroppedWord> temp = findWordAtSquare(dictionaryService, dictionary,  i, j, emptyLeftCount, tileList, boardGame, 0);
                    wordList = chooseBestSet(wordList, temp);
                }
            }
        }
        return wordList;
    }


    /**
     * Return true if the square [i] [j] is a candidate square to fix an horizontal word
     * @param boardGame
     * @return
     */
    private boolean isAnchorHorizontal(int row, int column, BoardGame boardGame){
        Square[][] squares = boardGame.getSquares();
        if( boardGame.getMiddleSquare().isEmpty() && row == (squares.length / 2) && column == (squares[0].length / 2)){
            return true;
        }

        Square square = squares[row][column];
        if( !square.isEmpty()){
            if( column == 0 ){
                return true;
            }else{
                Square previousSquare = squares[row][column - 1];
                return previousSquare.isEmpty();
            }
        }else{
            // The top square is candidate if it's empty and have no letter on top or on left
            if( row < squares.length - 1) {
                Square underSquare = squares[row + 1][column];
                if( !underSquare.isEmpty() ){
                    if( row == 0) {
                        if (column == 0) {
                            return true;
                        } else {
                            Square leftSquare = squares[row][column - 1];
                            return leftSquare.isEmpty();
                        }
                    }else if( column == 0){
                        Square overSquare = squares[row - 1][column];
                        return overSquare.isEmpty();
                    }else{
                        Square leftSquare = squares[row][column - 1];
                        Square overSquare = squares[row - 1][column];
                        return leftSquare.isEmpty() && overSquare.isEmpty();
                    }
                }
            }
            if ( row > 0){
                Square overSquare = squares[row - 1][column];
                if( !overSquare.isEmpty() ){
                    if( column == 0){
                        return true;
                    }else{
                        Square left = squares[row][column - 1];
                        return left.isEmpty();
                    }
                }
            }
        }

        return false;
    }

    /**
     * Return the nb of square candidate to allow a letter
     * If 2 squares are empty, return 1
     * If 2 squares are empty and then the board limit, return 2
     * @param boardGame
     * @return
     */
    private int getLeftEmtyCount(int line, int column, BoardGame boardGame){
        if( column == 0){
            return 0;
        }

        int limite = 0;
        int currentColumn = column - 1;
        Square current = boardGame.getSquares()[line][currentColumn];
        while( current.isEmpty() && currentColumn > 0 && limite < 7){
            current = boardGame.getSquares()[line][--currentColumn];
            if( current.isEmpty() ){
                limite++;
            }

        }
        if( current.isEmpty() && currentColumn == 0 && limite < 7){
            limite++;
        }
        return limite;
    }


    protected int calculate(List<Square> currentSquareList){

        int total = 0;
        if( currentSquareList.size() > 1){
            int wordMultiplier = 1;
            int letterDroppedCount = 0;
            for( Square currentSquare : currentSquareList){
                Tile tile = currentSquare.getDroppedTile().getTile();
                if( currentSquare.isJustDropped() ){
                    letterDroppedCount++;
                    switch (currentSquare.getSquareType()){
                        case normal: total += tile.getPoint(); break;
                        case doubleLetter: total = total + 2 * tile.getPoint(); break;
                        case tripleLetter: total = total + 3 * tile.getPoint(); break;
                        case doubleWord:
                            total += tile.getPoint();
                            wordMultiplier = wordMultiplier * 2;
                            break;
                        case tripleWord:
                            total += tile.getPoint();
                            wordMultiplier = wordMultiplier * 3;
                            break;
                    }
                }else{
                    total += tile.getPoint();
                }

            }
            total = total * wordMultiplier;
            if(letterDroppedCount == 7){
                total += 50;
            }
        }
        return total;
    }

    /**
     * Find the best horizontal word for a square
     * @param limiteLeft
     * @param tileList
     * @param boardGame
     * @return
     */
    private Set<DroppedWord> findWordAtSquare(DictionaryService dictionaryService, Dictionary dictionary, int line, int column, int limiteLeft, List<Tile> tileList, BoardGame boardGame, int pointToAdd){
        Set<DroppedWord> wordList = new HashSet<DroppedWord>();
        Square anchor = boardGame.getSquares()[line][column];
        int bestTotal = 0;
        // pour toutes les lettres
        // je selectionne une lettre a poser ( dépend si c'est un joker ou non)
        // je pose la lettre sur l'ancre
        // si le mot vertical créé est correct
        // j'ajoute a la liste le meilleur mot possible en complétant par la droite
        // Si il reste de la place a gauche
        // je rappelle la fonction sur la colonne de gauche avec la sous liste de lettre.

        if( !anchor.isEmpty() ) {
            wordList = findMeilleurePossibiliteDroite(dictionaryService, dictionary, dictionary.getDawg(), line, column, tileList, boardGame, pointToAdd);
            if (limiteLeft > 0) {
                Set<DroppedWord> tempLeftSet = findWordAtSquare(dictionaryService, dictionary, line, column - 1, limiteLeft - 1, tileList, boardGame, pointToAdd);
                wordList = chooseBestSet(wordList, tempLeftSet);
            }
        }else {

            for (int i = 0; i < tileList.size(); i++) {
                List<Tile> subTileList = tileList.subList(1, tileList.size());
                Tile tile = tileList.get(0);
                List<DroppedTile> currentTileList = new ArrayList<DroppedTile>();
                if (!tile.isWildcard()) {
                    currentTileList.add(new DroppedTile(tile, tile.getValue()));
                } else {
                    for (char c : "ABCDEFGHIJKLMNOPQRSTUVWXYZ".toCharArray()) {
                        currentTileList.add(new DroppedTile(tile, String.valueOf(c)));
                    }
                }

                for (DroppedTile droppedTile : currentTileList) {
                    // on pose la lettre sur le plateau
                    BoardGame activeBoard = boardGame.dropTile(line, column, droppedTile);
                    DroppedWord lettreMotCree = getVerticalWord(activeBoard, line, column);
                    if (lettreMotCree.getSquareList().size() == 1 || isValid(dictionaryService, dictionary, lettreMotCree.getSquareList())) {
                        Set<DroppedWord> bestRightWordSet = findMeilleurePossibiliteDroite(dictionaryService, dictionary, dictionary.getDawg(), line, column, subTileList, activeBoard, pointToAdd + lettreMotCree.getPoints());
                        wordList = chooseBestSet(wordList, bestRightWordSet);
                        if (limiteLeft > 0) {
                            Set<DroppedWord> tempLeftSet = findWordAtSquare(dictionaryService, dictionary, line, column - 1, limiteLeft - 1, subTileList, activeBoard, pointToAdd + lettreMotCree.getPoints());
                            wordList = chooseBestSet(wordList, tempLeftSet);
                        }
                    }
                }
                Collections.rotate(tileList, 1);
            }
        }

        return wordList;
    }

    private Set<DroppedWord> chooseBestSet(Set<DroppedWord> setA, Set<DroppedWord> setB){
        if( setA.isEmpty())return setB;
        if( setB.isEmpty())return setA;
        if( setA.iterator().next().getPoints() < setB.iterator().next().getPoints()) return setB;
        if( setA.iterator().next().getPoints() > setB.iterator().next().getPoints()) return setA;
        // else
        setA.addAll(setB);
        return setA;
    }

    private DroppedWord findDroppedHorizontalWord(BoardGame boardGame, int line, int column, int pointToAdd){
        DroppedWord horizontalWord = getHorizontalWord(boardGame, line, column, pointToAdd) ;
        if( horizontalWord.getSquareList().size() > 1){
            boolean posee = false; // on verifie qu'il y a quand meme une lettre posee
            for( Square c : horizontalWord.getSquareList()){
                if( c.isJustDropped() ){
                    posee = true;
                    break;
                }
            }
            if( posee ){
                return horizontalWord;
            }
        }
        return null;
    }

    /**
     * Retrouve le meilleur mot pouvant se placer dans les cases a droite de l'anchor
     * @param dawg
     * @param boardGame
     * @return
     */
    private Set<DroppedWord> findMeilleurePossibiliteDroite(DictionaryService dictionaryService, Dictionary dictionary, DAWG dawg, int line, int column, List<Tile> tileList, BoardGame boardGame, int pointToAdd){
        Set<DroppedWord> wordList = new HashSet<DroppedWord>();
        Square anchor = boardGame.getSquares()[line][column];

        if( dawg.getChild('|') != null && anchor.isEmpty() ){
            // on a la possibilite de terminer le mot ici ( si il existe un mot plus long, on prendra la mot plus long )
            DroppedWord word = findDroppedHorizontalWord(boardGame, line, column - 1, pointToAdd);
            if( word != null ){
                wordList.add(word);
            }
        }

        if( column == boardGame.getSquares()[0].length - 1){
            if( !anchor.isEmpty() ){
                DAWG child = dawg.getChild(anchor.getDroppedTile().getValue().charAt(0));
                if( child != null ){
                    if( child.getChild('|') != null){
                        DroppedWord word = findDroppedHorizontalWord(boardGame, line, column, pointToAdd);
                        if( word != null ){
                            wordList.add(word);
                        }
                    }
                }
            }else{
                // on doit piocher dans la reglette (qui n'est pas vide)
                for( Tile tile : tileList){
                    List<DroppedTile> currentTileList = new ArrayList<DroppedTile>();
                    if( !tile.isWildcard()){
                        currentTileList.add(new DroppedTile(tile, tile.getValue()) );
                    }else{
                        for( char c : "ABCDEFGHIJKLMNOPQRSTUVWXYZ".toCharArray()){
                            currentTileList.add(new DroppedTile(tile, String.valueOf(c)));
                        }
                    }

                    for( DroppedTile droppedTile : currentTileList){
                        DAWG child = dawg.getChild(droppedTile.getValue().charAt(0));
                        if( child != null ) {
                            BoardGame activeBoardGame = boardGame.dropTile(line, column, droppedTile);
                            DroppedWord verticalWord = getVerticalWord(activeBoardGame, line, column);
                            if (isValid(dictionaryService, dictionary, verticalWord.getSquareList())) {
                                DroppedWord horizontalWord = getHorizontalWord(activeBoardGame, line, column, pointToAdd + verticalWord.getPoints());
                                if (isValid(dictionaryService, dictionary, horizontalWord.getSquareList())) {
                                    if( wordList.isEmpty() || wordList.iterator().next().getPoints() == horizontalWord.getPoints()){
                                        wordList.add(horizontalWord);
                                    }else if( wordList.iterator().next().getPoints() < horizontalWord.getPoints()){
                                        wordList.clear();
                                        wordList.add(horizontalWord);
                                    }
                                }
                            }
                        }
                    }
                }
            }

            return wordList;
        }

        if( !anchor.isEmpty() ){
            DAWG child = dawg.getChild(anchor.getDroppedTile().getValue().charAt(0));
            if( child != null ){
                wordList = findMeilleurePossibiliteDroite(dictionaryService, dictionary, child, line, column + 1, tileList, boardGame, pointToAdd);
            }
            return wordList;
        }else{
            // on doit piocher dans la reglette (qui n'est pas vide)
            for( int i = 0; i < tileList.size(); i++){
                Tile tile = tileList.get(0);
                List<DroppedTile> currentTileList = new ArrayList<DroppedTile>();
                if( !tile.isWildcard()){
                    currentTileList.add(new DroppedTile(tile, tile.getValue()));
                }else{
                    for( char c : "ABCDEFGHIJKLMNOPQRSTUVWXYZ".toCharArray()){
                        currentTileList.add(new DroppedTile(tile, String.valueOf(c)));
                    }
                }
                for( DroppedTile droppedTile : currentTileList){
                    DAWG child = dawg.getChild(droppedTile.getValue().charAt(0));
                    if( child != null ){
                        BoardGame activeBoardGame = boardGame.dropTile(line, column, droppedTile);
                        DroppedWord verticalWord = getVerticalWord(activeBoardGame, line, column);
                        if( isValid(dictionaryService, dictionary, verticalWord.getSquareList())){
                            List<Tile> subTileList = tileList.subList(1, tileList.size());
                            Set<DroppedWord> horizontalWord = findMeilleurePossibiliteDroite(dictionaryService, dictionary, child, line, column + 1, subTileList, activeBoardGame, pointToAdd + verticalWord.getPoints());
                            if( !horizontalWord.isEmpty() ){
                                if( wordList.isEmpty() || wordList.iterator().next().getPoints() < horizontalWord.iterator().next().getPoints()){
                                    wordList = horizontalWord;
                                }else if(wordList.iterator().next().getPoints() == horizontalWord.iterator().next().getPoints()){
                                    wordList.addAll(horizontalWord);
                                }
                            }
                        }
                    }
                }

                Collections.rotate(tileList, 1);
            }
            return wordList;
        }
    }


    /**
     * Get current vertical Word
     * @param boardGame
     * @param line
     * @param column
     * @return
     */
    private DroppedWord getVerticalWord(final BoardGame boardGame, final int line, final int column){
        return getHorizontalWord(boardGame.transpose(), column, line, 0).transpose();
    }

    /**
     * Get current horizontal Word
     * @param boardGame
     * @param line
     * @param column
     * @return
     */
    private DroppedWord getHorizontalWord(final BoardGame boardGame, final int line, final int column, final int pointToAdd){
        List<Square> currentSquareList = new ArrayList<Square>();

        int currentColumn = column;
        int firstColumn = currentColumn;
        while( currentColumn >= 0 ){
            Square currentSquare = boardGame.getSquares()[line][currentColumn];
            if( currentSquare.isEmpty() ){
                break;
            }
            firstColumn = currentColumn;
            currentSquareList.add(0, currentSquare);
            currentColumn--;
        }

        currentColumn = column + 1;
        while ( currentColumn < boardGame.getSquares()[0].length ) {
            Square currentSquare = boardGame.getSquares()[line][currentColumn];
            if( currentSquare.isEmpty() ){
                break;
            }
            currentSquareList.add(currentSquare);
            currentColumn++;
        }

        return new DroppedWord(currentSquareList, calculate(currentSquareList) + pointToAdd, true, line, firstColumn );
    }

    /**
     * Check if the droppedTileList represent a word
     * @param currentSquareList
     * @return
     */
    private boolean isValid(DictionaryService dictionaryService, Dictionary dictionary, List<Square> currentSquareList){
        if( currentSquareList.size() == 1){
            return true;
        }
        String s = "";
        for( Square c : currentSquareList){
            if( c.getDroppedTile() != null ) {
                s += c.getDroppedTile().getValue();
            }
        }
        return dictionaryService.exist(s, dictionary);
    }

    @Override
    public void logBoardGame(BoardGame boardGame){
        String result = "\n";
        for (int j = 0; j < boardGame.getSquares()[0].length; j++) {
            if( j == 0){
                result += " |";
            }
            result += (j+1)%10 + "|";
        }
        result += "\n";
        for( int i = 0 ; i < boardGame.getSquares().length; i++ ){
            for( int j = 0; j < boardGame.getSquares()[0].length; j++){
                if( j == 0){
                    result += getLineAsLetter(i) + "|";
                }
                if(boardGame.getSquares()[i][j].isEmpty()) {
                    result += " |";
                }else {
                    result += boardGame.getSquares()[i][j].getDroppedTile().getTile().getValue() + "|";
                }
                if(j == boardGame.getSquares()[0].length -1 ){
                    result += "\n";
                }
            }
        }
        logger.debug(result);

    }

    public void logBoardGameWithSquare(BoardGame boardGame){
        String result = "\n";
        for (int j = 0; j < boardGame.getSquares()[0].length; j++) {
            if( j == 0){
                result += " |";
            }
            result += (j+1)%10 + "|";
        }
        result += "\n";
        for( int i = 0 ; i < boardGame.getSquares().length; i++ ){
            for( int j = 0; j < boardGame.getSquares()[0].length; j++){
                if( j == 0){
                    result += getLineAsLetter(i) + "|";
                }
                if( isAnchorHorizontal(i, j, boardGame) ){
                    result += "o|";
                }else{
                    result += " |";
                }

                if(j == boardGame.getSquares()[0].length -1 ){
                    result += "\n";
                }
            }
        }
        logger.debug(result);

    }
    private String getLineAsLetter(int line){
        return String.valueOf("ABCDEFGHIJKLMNOPQRSTUVWXYZ".charAt(line));

    }
}
