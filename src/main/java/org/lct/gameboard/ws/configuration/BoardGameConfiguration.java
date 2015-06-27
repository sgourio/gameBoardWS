/*
 * Dictionary REST module 2015.
 * Written by Sylvain Gourio
 * sylvain.gourio@gmail.com
 */

package org.lct.gameboard.ws.configuration;

import org.lct.gameboard.ws.services.impl.BoardServiceImpl;
import org.lct.gameboard.ws.services.BoardService;
import org.lct.gameboard.ws.services.impl.BoardServiceParalellizeImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Created by sgourio on 12/04/15.
 */
@Configuration
public class BoardGameConfiguration {

    @Bean
    BoardService boardService(){
        return new BoardServiceParalellizeImpl();
    }
}
