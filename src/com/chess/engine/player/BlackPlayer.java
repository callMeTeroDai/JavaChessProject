package com.chess.engine.player;

import com.chess.engine.Alliance;
import com.chess.engine.board.Board;
import com.chess.engine.board.Move;
import com.chess.engine.board.Tile;
import com.chess.engine.pieces.Piece;
import com.chess.engine.pieces.Rook;
import com.google.common.collect.ImmutableList;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Created by G40 on 12/2/2023.
 */
public class BlackPlayer extends Player {
    public BlackPlayer(final Board board,
                       final  Collection<Move> whiteStandardLegalMoves,
                       final Collection<Move> blackStandardLegalMoves) {
        super(board,blackStandardLegalMoves,whiteStandardLegalMoves);
    }
    @Override
    public Collection<Piece> getActivePieces(){
        return this.board.getBlackPieces();
    }

    @Override
    public Alliance getAlliance() {
        return Alliance.BLACK;
    }

    @Override
    public Player getOpponent() {
        return this.board.getWhitePlayer();
    }

    @Override
    protected Collection<Move> calculateKingCastle(final Collection<Move> playerLegals,
                                                   final Collection<Move> opponentLegals) {
        final List<Move> kingCastle = new ArrayList<>();

        if(this.playerKing.isFirstMove() && !this.isInCheck()){
            //kingSide castle
            if(!this.board.getTile(5).isTileOccupied() && !this.board.getTile(6).isTileOccupied()){
                final Tile rookTile = this.board.getTile(7);
                if(rookTile.isTileOccupied() && rookTile.getPiece().isFirstMove()){
                    if(Player.calculateAttacksOnTile(6,opponentLegals).isEmpty() &&
                            Player.calculateAttacksOnTile(5,opponentLegals).isEmpty()&&
                            rookTile.getPiece().getPieceType().isRook()){
                        kingCastle.add(new Move.KingSideCastle(this.board,this.playerKing,6,(Rook) rookTile.getPiece(),rookTile.getTileCoordinate(), 5));
                    }
                }
            }

            //queen side castle
            if(!this.board.getTile(1).isTileOccupied() && !this.board.getTile(2).isTileOccupied()&&
                    !this.board.getTile(3).isTileOccupied()){
                final Tile rookTile = this.board.getTile(0);
                if(rookTile.isTileOccupied() && rookTile.getPiece().isFirstMove() &&
                        rookTile.getPiece().getPieceType().isRook() &&
                        Player.calculateAttacksOnTile(1,opponentLegals).isEmpty() &&
                        Player.calculateAttacksOnTile(2,opponentLegals).isEmpty() &&
                        Player.calculateAttacksOnTile(3,opponentLegals).isEmpty()){
                    kingCastle.add(new Move.QueenSideCastle(this.board,this.playerKing,2,(Rook) rookTile.getPiece(),rookTile.getTileCoordinate(),3));
                }
            }
        }
        return ImmutableList.copyOf(kingCastle);
    }
}
