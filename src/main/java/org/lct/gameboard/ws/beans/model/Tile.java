/*
 * Scrabble Helper Module 2015.
 * Written by Sylvain Gourio
 * sylvain.gourio@gmail.com
 */

/**
 * 
 */
package org.lct.gameboard.ws.beans.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * @author Sylvain Gourio
 *
 */
public class Tile {
	public static Tile A = new Tile("A", 1, TileType.vowel);
	public static Tile B = new Tile("B", 3, TileType.consonant);
	public static Tile C = new Tile("C", 3, TileType.consonant);
	public static Tile D = new Tile("D", 2, TileType.consonant);
	public static Tile E = new Tile("E", 1, TileType.vowel);
	public static Tile F = new Tile("F", 4, TileType.consonant);
	public static Tile G = new Tile("G", 2, TileType.consonant);
	public static Tile H = new Tile("H", 4, TileType.consonant);
	public static Tile I = new Tile("I", 1, TileType.vowel);
	public static Tile J = new Tile("J", 8, TileType.consonant);
	public static Tile K = new Tile("K", 10, TileType.consonant);
	public static Tile L = new Tile("L", 1, TileType.consonant);
	public static Tile M = new Tile("M", 2, TileType.consonant);
	public static Tile N = new Tile("N", 1, TileType.consonant);
	public static Tile O = new Tile("O", 1, TileType.vowel);
	public static Tile P = new Tile("P", 3, TileType.consonant);
	public static Tile Q = new Tile("Q", 8, TileType.consonant);
	public static Tile R = new Tile("R", 1, TileType.consonant);
	public static Tile S = new Tile("S", 1, TileType.consonant);
	public static Tile T = new Tile("T", 1, TileType.consonant);
	public static Tile U = new Tile("U", 1, TileType.vowel);
	public static Tile V = new Tile("V", 4, TileType.consonant);
	public static Tile W = new Tile("W", 10, TileType.consonant);
	public static Tile X = new Tile("X", 10, TileType.consonant);
	public static Tile Y = new Tile("Y", 10, TileType.Y);
	public static Tile Z = new Tile("Z", 10, TileType.consonant);
	public static Tile WILDCARD = new Tile("?", 0, TileType.wildcard);
	
	
	// lettre anglaise
	public static Tile _A = new Tile("A", 1, TileType.vowel);
	public static Tile _B = new Tile("B", 3, TileType.consonant);
	public static Tile _C = new Tile("C", 3, TileType.consonant);
	public static Tile _D = new Tile("D", 2, TileType.consonant);
	public static Tile _E = new Tile("E", 1, TileType.vowel);
	public static Tile _F = new Tile("F", 4, TileType.consonant);
	public static Tile _G = new Tile("G", 2, TileType.consonant);
	public static Tile _H = new Tile("H", 4, TileType.consonant);
	public static Tile _I = new Tile("I", 1, TileType.vowel);
	public static Tile _J = new Tile("J", 8, TileType.consonant);
	public static Tile _K = new Tile("K", 5, TileType.consonant);
	public static Tile _L = new Tile("L", 1, TileType.consonant);
	public static Tile _M = new Tile("M", 3, TileType.consonant);
	public static Tile _N = new Tile("N", 1, TileType.consonant);
	public static Tile _O = new Tile("O", 1, TileType.vowel);
	public static Tile _P = new Tile("P", 3, TileType.consonant);
	public static Tile _Q = new Tile("Q", 10, TileType.consonant);
	public static Tile _R = new Tile("R", 1, TileType.consonant);
	public static Tile _S = new Tile("S", 1, TileType.consonant);
	public static Tile _T = new Tile("T", 1, TileType.consonant);
	public static Tile _U = new Tile("U", 1, TileType.vowel);
	public static Tile _V = new Tile("V", 4, TileType.consonant);
	public static Tile _W = new Tile("W", 4, TileType.consonant);
	public static Tile _X = new Tile("X", 8, TileType.consonant);
	public static Tile _Y = new Tile("Y", 4, TileType.Y);
	public static Tile _Z = new Tile("Z", 10, TileType.consonant);
    public static Tile _WILDCARD = new Tile("?", 0, TileType.wildcard);
	

	private String value;
	private int point;
	private TileType tileType;
	
	/**
	 * @param value
	 * @param point
	 * @param tileType
	 */
	private Tile(@JsonProperty("value") String value,
                 @JsonProperty("point") int point,
                 @JsonProperty("tileType") TileType tileType) {
		this.value = value;
		this.point = point;
		this.tileType = tileType;
	}
	public String getValue() {
		return value;
	}
	public int getPoint() {
		return point;
	}
	public TileType getTileType() {
		return tileType;
	}

    @JsonIgnore
	public boolean isWildcard(){
		return this.tileType == TileType.wildcard;
	}

    @JsonIgnore
	public boolean isVowel(){
		return this.tileType == TileType.vowel || this.tileType == TileType.wildcard || this.tileType == TileType.Y;
	}

    @JsonIgnore
	public boolean isConsonant(){
		return this.tileType == TileType.consonant || this.tileType == TileType.wildcard || this.tileType == TileType.Y;
	}
	
	@Override
	public String toString() {
		return value + "("+point+")";
	}

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Tile tile = (Tile) o;

        if (point != tile.point) return false;
        if (tileType != tile.tileType) return false;
        if (!value.equals(tile.value)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = value.hashCode();
        result = 31 * result + point;
        result = 31 * result + tileType.hashCode();
        return result;
    }
}
