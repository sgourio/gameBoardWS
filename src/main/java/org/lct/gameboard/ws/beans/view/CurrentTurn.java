///*
// * Scrabble Helper Module 2015.
// * Written by Sylvain Gourio
// * sylvain.gourio@gmail.com
// */
//
//package lct.boardgame.ws.beans.view;
//
//import Tile;
//import Draw;
//
//import java.util.ArrayList;
//import java.util.Collections;
//import java.util.List;
//
///**
// * Created by sgourio on 22/03/15.
// */
//public final class CurrentTurn {
//
//    private final int turnNumber;
//    private final Draw drawing;
//    private final List<Tile> deck;
//    private final DroppedWord bestWord;
//    private final int previousScore;
//    private final BoardGame boardGame;
//
//    public CurrentTurn(int turnNumber, Draw drawing, List<Tile> deck, DroppedWord bestWord, int previousScore, BoardGame boardGame) {
//        this.turnNumber = turnNumber;
//        this.drawing = drawing;
//        this.deck = Collections.unmodifiableList(new ArrayList<Tile>(deck));
//        this.bestWord = bestWord;
//        this.previousScore = previousScore;
//        this.boardGame = boardGame;
//    }
//
//    public int getTurnNumber() {
//        return turnNumber;
//    }
//
//    public Draw getDrawing() {
//        return drawing;
//    }
//
//    public List<Tile> getDeck() {
//        return deck;
//    }
//
//    public DroppedWord getBestWord() {
//        return bestWord;
//    }
//
//    public int getPreviousScore() {
//        return previousScore;
//    }
//
//    public BoardGame getBoardGame() {
//        return boardGame;
//    }
//}
