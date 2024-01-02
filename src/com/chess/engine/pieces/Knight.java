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


public class Knight extends Piece {

    private final static int[] CANDIDATE_MOVE_COORDINATES = {-17,-15,-10,-6,6,10,15,17};
    public Knight( Alliance pieceAlliance,int piecePosition) {
        super(piecePosition,PieceType.KNIGHT, pieceAlliance,true);
    }
    public Knight( Alliance pieceAlliance,int piecePosition, final boolean isFirstMove) {
        super(piecePosition,PieceType.KNIGHT, pieceAlliance,isFirstMove);
    }

    @Override
    public Collection<Move> calculateLegalMoves(final Board board) {
        int candidateDestiantionCoodrinate;
        final List<Move> legalMoves = new ArrayList<>();

        for(int currentCandidateOffSet : CANDIDATE_MOVE_COORDINATES) {

            candidateDestiantionCoodrinate = this.piecePosition + currentCandidateOffSet;

            if (BoardUtils.isValidCoordinate(candidateDestiantionCoodrinate)) {

                if(isFirstColumnExclusion(this.piecePosition, currentCandidateOffSet ) || isSecondColumnExclusion(this.piecePosition, currentCandidateOffSet)
                        || isSeventhColumnExclusion(this.piecePosition, currentCandidateOffSet) || isEighthColumnExclusion(this.piecePosition, currentCandidateOffSet) ){
                    continue;
                }

                    final Tile candidateDestinationTile = board.getTile(candidateDestiantionCoodrinate);
                if (!candidateDestinationTile.isTileOccupied()) {
                    legalMoves.add(new MajorMove(board, this, candidateDestiantionCoodrinate));
                } else {
                    final Piece pieceAtDestination = candidateDestinationTile.getPiece();
                    final Alliance pieceAlliance = pieceAtDestination.getPieceAlliance();

                    if (this.pieceAlliance != pieceAlliance) {
                        legalMoves.add(new MajorAttackMove(board,this,candidateDestiantionCoodrinate,pieceAtDestination));
                    }
                }
            }

        }
            return ImmutableList.copyOf(legalMoves);

    }
    @Override
    public Piece movePiece(Move move) {
        return new Knight(move.getMovedPiece().getPieceAlliance(),move.getDestinationCoordinate());
    }
    @Override
    public String toString(){
        return PieceType.KNIGHT.toString();
    }
    private static boolean isFirstColumnExclusion( final int currentPosition, final int candidateOffSet){
        return BoardUtils.FIRST_COLUMN[currentPosition] && (candidateOffSet == -17 || candidateOffSet == -10 ||
            candidateOffSet == 6 || candidateOffSet == 15);
    }
    private static boolean isSecondColumnExclusion (final int currentPosition, final int candidateOffSet){
        return BoardUtils.SECOND_COLUMN[currentPosition] && (candidateOffSet == -10 || candidateOffSet == 6 );
    }
    private static boolean isEighthColumnExclusion (final int currentPosition, final int candidateOffSet){
        return BoardUtils.EIGHTH_COLUMN[currentPosition] && (candidateOffSet == -15 || candidateOffSet == -6 ||
            candidateOffSet == 10 || candidateOffSet == 17);
    }
    private static boolean isSeventhColumnExclusion (final int currentPosition,final int candidateOffSet){
        return BoardUtils.SEVENTH_COLUMN[currentPosition] && (candidateOffSet == -6 || candidateOffSet == 10);
    }



    }
