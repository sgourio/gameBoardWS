/*
 * Scrabble Helper Module 2015.
 * Written by Sylvain Gourio
 * sylvain.gourio@gmail.com
 */

package org.lct.gameboard.ws.beans.view;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.lct.gameboard.ws.beans.model.BoardGameTemplate;

/**
 * Created by sgourio on 22/03/15.
 */
public final class BoardGame {

    private final Square[][] squares;
    private final Square middleSquare;

    public BoardGame(@JsonProperty("squares") Square[][] squares) {

        this.squares = squares;
        this.middleSquare = squares[squares.length / 2][squares[0].length / 2];

    }

    public BoardGame(BoardGameTemplate template){
        Square[][] squareTab = new Square[template.getSquares().length][template.getSquares()[0].length];
        for(int i = 0; i < template.getSquares().length; i++){
            for(int j = 0; j < template.getSquares()[0].length; j++){
                squareTab[i][j] = new Square(template.getSquares()[i][j], null, false);
            }
        }
        this.squares = squareTab;
        this.middleSquare = squareTab[squareTab.length / 2][squareTab[0].length / 2];
    }


    /**
     * Put a word on board
     * @param word
     * @return
     */
    public BoardGame dropWord(DroppedWord word){
        Square[][] squareArray = new Square[this.squares.length][this.squares[0].length];
        for(int i=0; i< this.squares.length; i++) {
            for (int j = 0; j < this.squares[0].length; j++) {
                squareArray[i][j] = this.squares[i][j];
            }
        }

        int line = word.getRow();
        int column = word.getColumn();
        for( Square square : word.getSquareList()){
            squareArray[line][column] = new Square(this.squares[line][column].getSquareType(), square.getDroppedTile(), false);
            if( word.isHorizontal() ){
                column++;
            }else{
                line++;
            }
        }

        return new BoardGame(squareArray);
    }

    public BoardGame dropTile(int line, int column, DroppedTile droppedTile){
        Square[][] squareArray = new Square[this.squares.length][this.squares[0].length];
        for(int i=0; i< this.squares.length; i++) {
            if( i != line ) {
                squareArray[i] = this.squares[i];
            }else {
                for (int j = 0; j < this.squares[i].length; j++) {
                    if( j != column ){
                        squareArray[i][j] = this.squares[i][j];
                    }else{
                        squareArray[i][j] = new Square(this.squares[i][j].getSquareType(), droppedTile, true);
                    }
                }
            }
        }
        return new BoardGame(squareArray);
    }

    public BoardGame clearJustDropped(){
        Square[][] currentSquare = new Square[this.squares.length][this.squares[0].length];
        for(int i=0; i< this.squares.length; i++) {
            for (int j = 0; j < this.squares[i].length; j++) {
                Square square = this.squares[i][j];
                if( square.isJustDropped() ){
                    currentSquare[i][j] = new Square(square.getSquareType(), square.getDroppedTile(), false);
                }else{
                    currentSquare[i][j] = this.squares[i][j];
                }
            }
        }
        return new BoardGame(currentSquare);
    }

    /**
     * Inverse lines and columns
     * @return
     */
    public BoardGame transpose(){
        Square[][] square = new Square[this.squares[0].length][this.squares.length];
        for(int i=0; i< this.squares.length; i++) {
            for (int j = 0; j < this.squares[0].length; j++) {
                square[j][i] = this.squares[i][j];
            }
        }
        return new BoardGame(square);
    }

    public Square[][] getSquares() {
        return squares;
    }

    public Square getMiddleSquare() {
        return middleSquare;
    }
}
