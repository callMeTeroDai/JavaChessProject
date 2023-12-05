package com.chess.engine.pieces;

import com.chess.engine.Alliance;
import com.chess.engine.board.Board;
import com.chess.engine.board.BoardUtils;
import com.chess.engine.board.Move;
import com.chess.engine.board.Tile;
import com.google.common.collect.ImmutableList;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import static com.chess.engine.board.Move.*;

/**
 * Created by G40 on 11/30/2023.
 */
public class King extends Piece {

    private static final int[] CANDIDATE_COORDINATE_MOVE = {-9,-8,-7,-1,1,7,8,9};
    public King( Alliance pieceAlliance,int piecePosition) {
        super( piecePosition,PieceType.KING, pieceAlliance);
    }

    @Override
    public Collection<Move> calculateLegalMoves(Board board) {
        final List<Move> legalMoves = new ArrayList<>();

        for(final int currentCandidateOffset : CANDIDATE_COORDINATE_MOVE){
            final int candidateDestinationCoordinate = currentCandidateOffset+this.piecePosition;

            if(isFirstColumnExclusion(this.piecePosition, currentCandidateOffset) || isEighthColumnExclusion(this.piecePosition, currentCandidateOffset)){
                continue;
            }

            if(BoardUtils.isValidCoordinate(candidateDestinationCoordinate)){
                final Tile candidateDestinationTile = board.getTile(candidateDestinationCoordinate);
                if(!candidateDestinationTile.isTileOccupied()){
                    legalMoves.add(new MajorMove(board,this,candidateDestinationCoordinate));
                }
                else{
                    final Piece pieceAtDestination = candidateDestinationTile.getPiece();
                    final Alliance pieceAlliance = pieceAtDestination.getPieceAlliance();
                    if(this.pieceAlliance != pieceAlliance){
                        legalMoves.add(new AttackMove(board, this, candidateDestinationCoordinate,pieceAtDestination));
                    }
                }

            }
        }
        return ImmutableList.copyOf(legalMoves);
    }
    @Override
    public Piece movePiece(Move move) {
        return new King(move.getMovedPiece().getPieceAlliance(),move.getDestinationCoordinate());
    }

    @Override
    public String toString(){
        return PieceType.KING.toString();
    }
    private static boolean isFirstColumnExclusion( final int currentPosition, final int candidateOffSet){
        return BoardUtils.FIRST_COLUMN[currentPosition] && (candidateOffSet == -9 || candidateOffSet == -7 ||
                candidateOffSet == -1);
    }
    private static boolean isEighthColumnExclusion (final int currentPosition, final int candidateOffSet){
        return BoardUtils.EIGHTH_COLUMN[currentPosition] && (candidateOffSet == -7 || candidateOffSet == 9  ||
        candidateOffSet == 1);
    }
}
