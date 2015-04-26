///*
// * Scrabble Helper Module 2015.
// * Written by Sylvain Gourio
// * sylvain.gourio@gmail.com
// */
//
//package lct.boardgame.ws.beans.view;
//
//import BoardGameTemplate;
//import Tile;
//
//import java.util.ArrayList;
//import java.util.Collections;
//import java.util.List;
//
///**
// * Created by sgourio on 22/03/15.
// */
//public final class CurrentGame {
//
//    private final String id;
//    private final String name;
//    private final String lang;
//    private final BoardGameTemplate boardGameTemplate;
//    private final List<Tile> deck;
//    private final List<CurrentTurn> turnList;
//
//
//    public CurrentGame(String id, String name, String lang, BoardGameTemplate boardGameTemplate, List<Tile> deck, List<CurrentTurn> turnList) {
//        this.id = id;
//        this.name = name;
//        this.lang = lang;
//        this.boardGameTemplate = boardGameTemplate;
//        this.deck = deck;
//        this.turnList = Collections.unmodifiableList(new ArrayList<CurrentTurn>(turnList));
//    }
//
//    public String getId() {
//        return id;
//    }
//
//    public String getName() {
//        return name;
//    }
//
//    public String getLang() {
//        return lang;
//    }
//
//    public BoardGameTemplate getBoardGameTemplate() {
//        return boardGameTemplate;
//    }
//
//    public List<Tile> getDeck() {
//        return deck;
//    }
//
//    public List<CurrentTurn> getTurnList() {
//        return turnList;
//    }
//}
