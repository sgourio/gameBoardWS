/*
 * Dictionary REST module 2015.
 * Written by Sylvain Gourio
 * sylvain.gourio@gmail.com
 */

package org.lct.gameboard.ws.controllers;

import org.lct.dictionary.beans.Dictionary;
import org.lct.dictionary.services.DictionaryService;
import org.lct.gameboard.ws.beans.model.BoardGameTemplate;
import org.lct.gameboard.ws.beans.model.Tile;
import org.lct.gameboard.ws.beans.view.BoardGame;
import org.lct.gameboard.ws.beans.view.DroppedWord;
import org.lct.gameboard.ws.services.BoardService;
import org.lct.gameboard.ws.services.impl.BoardGameTemplateEnum;
import org.lct.gameboard.ws.services.impl.DeckTemplateEnum;
import org.lct.gameboard.ws.beans.view.BoardGameQueryBean;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Set;

/**
 * Created by sgourio on 12/04/15.
 */
@RestController
@RequestMapping(value="/board/{lang}")
public class BoardGameController {
    private static Logger logger = LoggerFactory.getLogger(BoardGameController.class);

    @Autowired
    private BoardService boardService;

    @Autowired
    private DictionaryService dictionaryService;

    @RequestMapping(value="/empty", method=RequestMethod.GET)
    @ResponseStatus(value=HttpStatus.OK)
    public @ResponseBody ResponseEntity<BoardGame> empty(@PathVariable("lang") String lang){
        BoardGameTemplate boardGameTemplate = new BoardGameTemplate(BoardGameTemplateEnum.classic.getSquares());
        BoardGame boardGame = new BoardGame(boardGameTemplate);
        return new ResponseEntity<BoardGame>(boardGame, HttpStatus.OK);
    }

    @RequestMapping(value="/deck/init", method=RequestMethod.GET)
    @ResponseStatus(value=HttpStatus.OK)
    public @ResponseBody ResponseEntity<List<Tile>> deck(@PathVariable("lang") String lang){
        List<Tile> result = null;
        if( "en".equals(lang) ){
            result = DeckTemplateEnum.english.getTileList();
        }else {
             result = DeckTemplateEnum.french.getTileList();
        }
        return new ResponseEntity<List<Tile>>(result, HttpStatus.OK);
    }

    @RequestMapping(value="/bestword", method=RequestMethod.POST)
    @ResponseStatus(value=HttpStatus.OK)
    public @ResponseBody ResponseEntity<Set<DroppedWord>> findBestWord(@PathVariable("lang") String lang, @RequestBody BoardGameQueryBean boardGameQueryBean){
        if( logger.isDebugEnabled() ) {
            logger.debug("Find best word " + boardGameQueryBean);
        }
        Set<DroppedWord> droppedWordSet = boardService.findBestWord(dictionaryService,  Dictionary.getByLang(lang), boardGameQueryBean.getBoardGame(), boardGameQueryBean.getTileList());
        StringBuilder sb = new StringBuilder();
        for( DroppedWord droppedWord : droppedWordSet){
            sb.append(droppedWord.toString() + " / ");
        }
        logger.info("Find best word " + boardGameQueryBean + " -> " + sb.toString());
        return  new ResponseEntity<Set<DroppedWord>>(droppedWordSet, HttpStatus.OK);
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    public void handleException(HttpMessageNotReadableException ex,
                                HttpServletResponse response) {

        logger.info("Handling " + ex.getClass().getSimpleName());
    }


}
