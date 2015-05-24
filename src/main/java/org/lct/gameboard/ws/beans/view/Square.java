/*
 * Scrabble Helper Module 2015.
 * Written by Sylvain Gourio
 * sylvain.gourio@gmail.com
 */

package org.lct.gameboard.ws.beans.view;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.lct.gameboard.ws.beans.model.SquareType;

/**
 * Created by sgourio on 22/03/15.
 */
public class Square {

    private final SquareType squareType;
    private final DroppedTile droppedTile;
    private final boolean justDropped;

    public Square(@JsonProperty("squareType") SquareType squareType, @JsonProperty("droppedTile") DroppedTile droppedTile, @JsonProperty("justDropped") boolean justDropped) {
        this.squareType = squareType;
        this.droppedTile = droppedTile;
        this.justDropped = justDropped;
    }

    public SquareType getSquareType() {
        return squareType;
    }

    public DroppedTile getDroppedTile() {
        return droppedTile;
    }

    public boolean isJustDropped() {
        return justDropped;
    }

    public boolean isEmpty() {
        return droppedTile == null;
    }
}
