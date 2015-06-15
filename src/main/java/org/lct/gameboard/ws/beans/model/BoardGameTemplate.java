/*
 * Scrabble Helper Module 2015.
 * Written by Sylvain Gourio
 * sylvain.gourio@gmail.com
 */

package org.lct.gameboard.ws.beans.model;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created by sgourio on 21/03/15.
 */
public class BoardGameTemplate {

    private final SquareType[][] squares;

    public BoardGameTemplate(@JsonProperty("squares") SquareType[][] squares) {
        this.squares = squares;
    }

    public SquareType[][] getSquares() {
        return squares;
    }
}
