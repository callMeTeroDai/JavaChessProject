package com.chess.engine;

import com.chess.engine.board.Board;

/**
 * Created by G40 on 12/2/2023.
 */
public class JChess {
    public static void main(String[] args){
        Board board = Board.createStandardBoard();
        System.out.println(board);
    }
}
