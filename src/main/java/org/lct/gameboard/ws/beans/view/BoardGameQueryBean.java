/*
 * Scrabble Helper Module 2015.
 * Written by Sylvain Gourio
 * sylvain.gourio@gmail.com
 */

package org.lct.gameboard.ws.beans.view;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.lct.gameboard.ws.beans.model.Tile;

import java.util.List;

/**
 * Created by sgourio on 25/04/15.
 */
public class BoardGameQueryBean {
    private final List<Tile> tileList;
    private final BoardGame boardGame;


    public BoardGameQueryBean(@JsonProperty("tileList") List<Tile> tileList, @JsonProperty("boardGame") BoardGame boardGame) {
        this.tileList = tileList;
        this.boardGame = boardGame;
    }

    public List<Tile> getTileList() {
        return tileList;
    }

    public BoardGame getBoardGame() {
        return boardGame;
    }
}
