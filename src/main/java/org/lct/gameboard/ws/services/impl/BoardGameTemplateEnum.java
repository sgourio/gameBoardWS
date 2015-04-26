/*
 * Scrabble Helper Module 2015.
 * Written by Sylvain Gourio
 * sylvain.gourio@gmail.com
 */

package org.lct.gameboard.ws.services.impl;

import org.lct.gameboard.ws.beans.model.SquareType;

/**
 * Created by sgourio on 21/03/15.
 */
public enum BoardGameTemplateEnum {
    classic; // classic scrabble board

    private final SquareType[][] squares;

    BoardGameTemplateEnum() {
        squares = createClassicBoardGame();
    }

    public SquareType[][] getSquares() {
        return squares;
    }

    private SquareType[][] createClassicBoardGame(){
        SquareType[][] squares = new SquareType[15][15];
        for( int j = 0 ; j < 15 ; j++ ){
            for( int i = 0 ; i< 15 ; i++ ){
                if( ((j == 0 || j == 14)  && (i == 0 || i == 7 || i == 14))
                        || (j == 7 && (i == 0 || i == 14))	){
                    squares[j][i] = SquareType.tripleWord;
                }else if(
                        ( (j == 1 || j == 13 ) && (i == 1 || i == 13) )
                                ||( (j == 2 || j == 12 ) && (i == 2 || i == 12) )
                                ||( (j == 3 || j == 11 ) && (i == 3 || i == 11) )
                                ||( (j == 4 || j == 10 ) && (i == 4 || i == 10) )
                                ||( j == 7 && i == 7)
                        ){
                    squares[j][i] = SquareType.doubleWord;
                }else if(
                        ( (j == 0 || j == 14 ) && (i == 3 || i == 11) )
                                ||( (j == 2 || j == 12 ) && (i == 6 || i == 8) )
                                ||( (j == 3 || j == 11 ) && (i == 0 || i == 7 || i == 14 ) )
                                ||( (j == 6 || j == 8 ) && (i == 2 || i == 12 || i == 6 || i == 8 ) )
                                ||( (j == 7) && (i == 3 || i == 11 ) )
                        ){
                    squares[j][i] = SquareType.doubleLetter;
                }else if(
                        ( (j == 1 || j == 13 ) && (i == 5 || i == 9) )
                                ||( (j == 5 || j == 9 ) && (i == 1 || i == 5 || i == 9 || i == 13) )
                        ){
                    squares[j][i] = SquareType.tripleLetter;
                }else{
                    squares[j][i] = SquareType.normal;
                }
            }
        }
        return squares;
    }
}