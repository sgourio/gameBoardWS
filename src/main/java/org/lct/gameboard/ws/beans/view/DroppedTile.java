/*
 * Scrabble Helper Module 2015.
 * Written by Sylvain Gourio
 * sylvain.gourio@gmail.com
 */

package org.lct.gameboard.ws.beans.view;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.lct.gameboard.ws.beans.model.Tile;

/**
 * Created by sgourio on 21/03/15.
 */
public final class DroppedTile {

    private final Tile tile;
    private final String value;


    public DroppedTile(@JsonProperty("tile") Tile tile, @JsonProperty("value") String value) {
        this.tile = tile;
        this.value = value;
    }

    public Tile getTile() {
        return tile;
    }

    public String getValue() {
        return value;
    }

}