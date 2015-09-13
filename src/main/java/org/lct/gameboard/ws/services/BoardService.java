/*
 * Scrabble Helper Module 2015.
 * Written by Sylvain Gourio
 * sylvain.gourio@gmail.com
 */

package org.lct.gameboard.ws.services;


import org.lct.gameboard.ws.beans.model.Tile;
import org.lct.gameboard.ws.beans.view.BoardGame;
import org.lct.gameboard.ws.beans.view.DroppedWord;
import org.lct.dictionary.beans.Dictionary;
import org.lct.dictionary.services.DictionaryService;
import org.lct.gameboard.ws.beans.view.Square;

import java.util.List;
import java.util.Set;

/**
 * Created by sgourio on 30/03/15.
 */
public interface BoardService {

    /**
     * Find the best possible word to drop in the current board game (based on dictionary DAWG )
     * @param dictionaryService
     * @param boardGame
     * @param tileList
     * @return
     */
    public Set<DroppedWord> findBestWord(DictionaryService dictionaryService, Dictionary dictionary, BoardGame boardGame, List<Tile> tileList);

    /**
     * Get a score to compare words. The score is calculated using word score, word anchor count, and word position
     * @param boardGame
     * @param droppedWord
     * @return
     */
    public int wordInternalScore(BoardGame boardGame, DroppedWord droppedWord);

    /**
     * Put in logger the current board game
     * @param boardGame
     */
    public void logBoardGame(BoardGame boardGame);

    public DroppedWord getVerticalWord(final BoardGame boardGame, final int line, final int column);

    public DroppedWord getHorizontalWord(final BoardGame boardGame, final int line, final int column, final int pointToAdd);

    public boolean isValid(DictionaryService dictionaryService, Dictionary dictionary, List<Square> currentSquareList);
}
