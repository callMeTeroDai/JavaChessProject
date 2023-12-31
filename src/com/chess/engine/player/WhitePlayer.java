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
public class WhitePlayer extends Player {
    public WhitePlayer(final Board board,
                       final Collection<Move> whiteStandardLegalMoves,
                       final Collection<Move> blackStandardLegalMoves) {
        super(board,whiteStandardLegalMoves,blackStandardLegalMoves);
    }

    @Override
    public Collection<Piece> getActivePieces(){
        return this.board.getWhitePieces();
    }

    @Override
    public Alliance getAlliance() {
        return Alliance.WHITE;
    }

    @Override
    public Player getOpponent() {
        return this.board.getBlackPlayer();
    }

    @Override
    protected Collection<Move> calculateKingCastle(final Collection<Move> playerLegals,
                                                   final Collection<Move> opponentLegals) {
        final List<Move> kingCastle = new ArrayList<>();

        if(this.playerKing.isFirstMove() && !this.isInCheck()){
            //kingSide castle
            if(!this.board.getTile(61).isTileOccupied() && !this.board.getTile(62).isTileOccupied()){
                final Tile rookTile = this.board.getTile(63);
                if(rookTile.isTileOccupied() && rookTile.getPiece().isFirstMove()){
                    if(Player.calculateAttacksOnTile(61,opponentLegals).isEmpty() &&
                            Player.calculateAttacksOnTile(62,opponentLegals).isEmpty()&&
                            rookTile.getPiece().getPieceType().isRook()){
                        kingCastle.add(new Move.KingSideCastle(this.board,this.playerKing,62,(Rook)rookTile.getPiece(),rookTile.getTileCoordinate(),61));
                    }
                }
            }

            //queen side castle
            if(!this.board.getTile(59).isTileOccupied() && !this.board.getTile(58).isTileOccupied()&&
                    !this.board.getTile(57).isTileOccupied()){
                final Tile rookTile = this.board.getTile(56);
                if(rookTile.isTileOccupied() && rookTile.getPiece().isFirstMove() &&
                        rookTile.getPiece().getPieceType().isRook() &&
                        Player.calculateAttacksOnTile(59,opponentLegals).isEmpty() &&
                        Player.calculateAttacksOnTile(58,opponentLegals).isEmpty() &&
                        Player.calculateAttacksOnTile(57,opponentLegals).isEmpty()){
                    kingCastle.add(new Move.QueenSideCastle(this.board,this.playerKing,58,(Rook)rookTile.getPiece(),rookTile.getTileCoordinate(),59));
                }
            }
        }
        return ImmutableList.copyOf(kingCastle);
    }
}
