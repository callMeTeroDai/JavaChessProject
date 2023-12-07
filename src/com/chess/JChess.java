package com.chess;

import com.chess.engine.board.Board;
import com.chess.gui.Table;

/**
 * Created by G40 on 12/2/2023.
 */
public class JChess {
    public static void main(String[] args){
        Board board = Board.createStandardBoard();
        System.out.println(board);

        Table table = new Table();
    }
}
