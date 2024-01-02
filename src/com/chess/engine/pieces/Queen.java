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


public class Queen extends Piece {
    public final static int[] CANDIDATE_MOVE_COORDINATES = {-9,-8,-7,-1,1,7,8,9};
    public Queen( Alliance pieceAlliance, int piecePosition) {
        super(piecePosition,PieceType.QUEEN, pieceAlliance,true);
    }
    public Queen( Alliance pieceAlliance, int piecePosition,final boolean isFirstMove) {
        super(piecePosition,PieceType.QUEEN, pieceAlliance,isFirstMove);
    }
    @Override
    public Collection<Move> calculateLegalMoves(final Board board) {
        final List<Move> legalMoves = new ArrayList<>();

        for(final int candidateCoordinateOffSet : CANDIDATE_MOVE_COORDINATES){
            int candidateDestinationCoordinate = this.piecePosition;
            while(BoardUtils.isValidCoordinate(candidateDestinationCoordinate)){

                if(isFirstColumnExclusion(candidateDestinationCoordinate, candidateCoordinateOffSet) ||
                        isEighthColumnExclusion(candidateDestinationCoordinate, candidateCoordinateOffSet)){
                    break;
                }


                candidateDestinationCoordinate += candidateCoordinateOffSet;
                if(BoardUtils.isValidCoordinate(candidateDestinationCoordinate)){
                    final Tile candidateDestinationTile = board.getTile(candidateDestinationCoordinate);
                    if(!candidateDestinationTile.isTileOccupied()){
                        legalMoves.add(new MajorMove(board,this, candidateDestinationCoordinate));
                    }
                    else{
                        final Piece pieceAtDestination = candidateDestinationTile.getPiece();
                        final Alliance pieceAlliance = pieceAtDestination.getPieceAlliance();
                        if(pieceAlliance != this.pieceAlliance){
                            legalMoves.add(new MajorAttackMove(board, this , candidateDestinationCoordinate, pieceAtDestination));
                        }
                        break;
                    }

                }
            }
        }

        return ImmutableList.copyOf(legalMoves);
    }
    @Override
    public Piece movePiece(Move move) {
        return new Queen(move.getMovedPiece().getPieceAlliance(),move.getDestinationCoordinate());
    }
    @Override
    public String toString(){
        return PieceType.QUEEN.toString();
    }
    private static boolean isFirstColumnExclusion(final int currentPosition, final int candidateOffSet){
        return BoardUtils.FIRST_COLUMN[currentPosition] && (candidateOffSet== -1 || candidateOffSet == -9 || candidateOffSet == 7);
    }
    private static boolean isEighthColumnExclusion(final int currentPosition, final int candidateOffSet){
        return BoardUtils.EIGHTH_COLUMN[currentPosition] && (candidateOffSet == 1 || candidateOffSet == -7 || candidateOffSet == 9);
    }
}
