/*
 * Scrabble Helper Module 2015.
 * Written by Sylvain Gourio
 * sylvain.gourio@gmail.com
 */

package org.lct.gameboard.ws.services.impl;


import org.lct.gameboard.ws.beans.model.Tile;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by sgourio on 21/03/15.
 */
public enum DeckTemplateEnum {
    french, // french set of tiles
    english, // english set of tiles
    test;
    
    private final List<Tile> tileList;

    private DeckTemplateEnum() {
        switch (this.name()){
            case "english":
                this.tileList = createEnglishClassicDeck();
                break;
            case "test":
                this.tileList = createTestDeck();
                break;
            case "french":
            default:
                this.tileList = createFrenchClassicDeck();
                break;
        }
    }

    public List<Tile> getTileList() {
        return new ArrayList<>(tileList);
    }

    private void add(List<Tile> tileList, Tile tile, int nb){
        for( int i = 1 ; i <= nb; i++){
            tileList.add(tile);
        }
    }
    private List<Tile> createTestDeck() {
        List<Tile> tileList = new ArrayList<>();
        add(tileList,Tile._T, 7);
        add(tileList,Tile._E, 7);
//        add(tileList,Tile._T, 1);
//        add(tileList,Tile._O, 1);
//        add(tileList,Tile._U, 1);
//        add(tileList,Tile._R, 1);
//        add(tileList,Tile._N, 1);
//        add(tileList,Tile._A, 1);
//        add(tileList,Tile._S, 1);
        return tileList;
    }

    private List<Tile> createEnglishClassicDeck() {
        List<Tile> tileList = new ArrayList<>();
        add(tileList,Tile._A, 12);
        add(tileList,Tile._B, 2);
        add(tileList,Tile._C, 2);
        add(tileList,Tile._D, 4);
        add(tileList,Tile._E, 12);
        add(tileList,Tile._F, 2);
        add(tileList,Tile._G, 3);
        add(tileList,Tile._H, 2);
        add(tileList,Tile._I, 9);
        add(tileList,Tile._J, 1);
        add(tileList,Tile._K, 1);
        add(tileList,Tile._L, 4);
        add(tileList,Tile._M, 2);
        add(tileList,Tile._N, 6);
        add(tileList,Tile._O, 8);
        add(tileList,Tile._P, 2);
        add(tileList,Tile._Q, 1);
        add(tileList,Tile._R, 6);
        add(tileList,Tile._S, 4);
        add(tileList,Tile._T, 6);
        add(tileList,Tile._U, 4);
        add(tileList,Tile._V, 2);
        add(tileList,Tile._W, 2);
        add(tileList,Tile._X, 1);
        add(tileList,Tile._Y, 2);
        add(tileList,Tile._Z, 1);
        add(tileList,Tile._WILDCARD, 2);

        return tileList;
    }

    private List<Tile> createFrenchClassicDeck(){
        List<Tile> tileList = new ArrayList<>();
        add(tileList,Tile.A, 9);
        add(tileList,Tile.B, 2);
        add(tileList,Tile.C, 2);
        add(tileList,Tile.D, 3);
        add(tileList,Tile.E, 15);
        add(tileList,Tile.F, 2);
        add(tileList,Tile.G, 2);
        add(tileList,Tile.H, 2);
        add(tileList,Tile.I, 8);
        add(tileList,Tile.J, 1);
        add(tileList,Tile.K, 1);
        add(tileList,Tile.L, 5);
        add(tileList,Tile.M, 3);
        add(tileList,Tile.N, 6);
        add(tileList,Tile.O, 6);
        add(tileList,Tile.P, 2);
        add(tileList,Tile.Q, 1);
        add(tileList,Tile.R, 6);
        add(tileList,Tile.S, 6);
        add(tileList,Tile.T, 6);
        add(tileList,Tile.U, 6);
        add(tileList,Tile.V, 2);
        add(tileList,Tile.W, 1);
        add(tileList,Tile.X, 1);
        add(tileList,Tile.Y, 1);
        add(tileList,Tile.Z, 1);
        add(tileList,Tile.WILDCARD, 2);
        return tileList;
    }
}
