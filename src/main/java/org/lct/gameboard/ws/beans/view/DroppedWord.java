/*
 * Scrabble Helper Module 2015.
 * Written by Sylvain Gourio
 * sylvain.gourio@gmail.com
 */

package org.lct.gameboard.ws.beans.view;

import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by sgourio on 21/03/15.
 */
public class DroppedWord {

    private final String value;
    private final List<Square> squareList;
    private final int points;
    private final String reference; //H1
    private final String serialized; // EX(E)MPLE
    private final boolean containWilcard;
    private final boolean horizontal;
    private final int row; // first letter row
    private final int column; // first letter column



    public DroppedWord(List<Square> squareList, int points, boolean horizontal, int row, int column) {
        this.squareList = Collections.unmodifiableList(new ArrayList<Square>(squareList));
        this.points = points;
        String val = "";
        String valSerialized = "";
        boolean wildcard = false;
        for( Square square : squareList){
            val += square.getDroppedTile().getValue();
            if( square.getDroppedTile().getTile().isWildcard()){
                wildcard = true;
                valSerialized += "(" + square.getDroppedTile().getValue() + ")";
            }else{
                valSerialized += square.getDroppedTile().getValue();
            }
        }
        this.value = val;
        this.containWilcard = wildcard;
        this.horizontal = horizontal;
        this.reference =  horizontal ? String.valueOf("ABCDEFGHIJKLMNOPQRSTUVWXYZ".charAt(row)) + StringUtils.leftPad(String.valueOf(column + 1), 2)  : StringUtils.leftPad(String.valueOf(column + 1), 2) + String.valueOf("ABCDEFGHIJKLMNOPQRSTUVWXYZ".charAt(row)) ;
        this.serialized = valSerialized;
        this.row = row;
        this.column = column;
    }

    public DroppedWord transpose(){
        return new DroppedWord(this.squareList, this.points, !horizontal, this.column, this.row);
    }

    public String getValue() {
        return value;
    }

    public int getPoints() {
        return points;
    }


    public String getReference() {
        return reference;
    }

    public String getSerialized() {
        return serialized;
    }

    public boolean isContainWilcard() {
        return containWilcard;
    }

    public List<Square> getSquareList() {
        return squareList;
    }

    public boolean isHorizontal() {
        return horizontal;
    }

    public int getRow() {
        return row;
    }

    public int getColumn() {
        return column;
    }

    @Override
    public String toString() {
        return "("+reference +") " + value +" - " + points +"pts";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        DroppedWord word = (DroppedWord) o;

        if (points != word.points) return false;
        if (reference != null ? !reference.equals(word.reference) : word.reference != null) return false;
        if (value != null ? !value.equals(word.value) : word.value != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = value != null ? value.hashCode() : 0;
        result = 31 * result + points;
        result = 31 * result + (reference != null ? reference.hashCode() : 0);
        return result;
    }
}
