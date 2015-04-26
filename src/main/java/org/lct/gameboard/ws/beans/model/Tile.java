/*
 * Scrabble Helper Module 2015.
 * Written by Sylvain Gourio
 * sylvain.gourio@gmail.com
 */

/**
 * 
 */
package org.lct.gameboard.ws.beans.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * @author Sylvain Gourio
 *
 */
@JsonFormat(shape= JsonFormat.Shape.OBJECT)
public enum Tile {
	A("A", 1, TileType.vowel),
	B("B", 3, TileType.consonant),
	C("C", 3, TileType.consonant),
	D("D", 2, TileType.consonant),
	E("E", 1, TileType.vowel),
	F("F", 4, TileType.consonant),
	G("G", 2, TileType.consonant),
	H("H", 4, TileType.consonant),
	I("I", 1, TileType.vowel),
	J("J", 8, TileType.consonant),
	K("K", 10, TileType.consonant),
	L("L", 1, TileType.consonant),
	M("M", 2, TileType.consonant),
	N("N", 1, TileType.consonant),
	O("O", 1, TileType.vowel),
	P("P", 3, TileType.consonant),
	Q("Q", 8, TileType.consonant),
	R("R", 1, TileType.consonant),
	S("S", 1, TileType.consonant),
	T("T", 1, TileType.consonant),
	U("U", 1, TileType.vowel),
	V("V", 4, TileType.consonant),
	W("W", 10, TileType.consonant),
	X("X", 10, TileType.consonant),
	Y("Y", 10, TileType.Y),
	Z("Z", 10, TileType.consonant),
	WILDCARD("?", 0, TileType.wildcard),
	
	
	// lettre anglaise
	_A("A", 1, TileType.vowel),
	_B("B", 3, TileType.consonant),
	_C("C", 3, TileType.consonant),
	_D("D", 2, TileType.consonant),
	_E("E", 1, TileType.vowel),
	_F("F", 4, TileType.consonant),
	_G("G", 2, TileType.consonant),
	_H("H", 4, TileType.consonant),
	_I("I", 1, TileType.vowel),
	_J("J", 8, TileType.consonant),
	_K("K", 5, TileType.consonant),
	_L("L", 1, TileType.consonant),
	_M("M", 3, TileType.consonant),
	_N("N", 1, TileType.consonant),
	_O("O", 1, TileType.vowel),
	_P("P", 3, TileType.consonant),
	_Q("Q", 10, TileType.consonant),
	_R("R", 1, TileType.consonant),
	_S("S", 1, TileType.consonant),
	_T("T", 1, TileType.consonant),
	_U("U", 1, TileType.vowel),
	_V("V", 4, TileType.consonant),
	_W("W", 4, TileType.consonant),
	_X("X", 8, TileType.consonant),
	_Y("Y", 4, TileType.Y),
	_Z("Z", 10, TileType.consonant),
	_WILDCARD("?", 0, TileType.wildcard);
	

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

	public boolean isWildcard(){
		return this.tileType == TileType.wildcard;
	}
	
	public boolean isVoyelle(){
		return this.tileType == TileType.vowel || this.tileType == TileType.wildcard || this.tileType == TileType.Y;
	}
	
	public boolean isConsonne(){
		return this.tileType == TileType.consonant || this.tileType == TileType.wildcard || this.tileType == TileType.Y;
	}
	
	@Override
	public String toString() {
		return value + "("+point+")";
	}
}
