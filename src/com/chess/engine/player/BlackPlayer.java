package com.chess.engine.player;

import com.chess.engine.Alliance;
import com.chess.engine.board.Board;
import com.chess.engine.board.Move;
import com.chess.engine.board.Tile;
import com.chess.engine.pieces.Piece;
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
    protected Collection<Move> calculateKingCastle(Collection<Move> playerLegals, Collection<Move> opponentLegals) {
        final List<Move> kingCastle = new ArrayList<>();

        if(this.playerKing.isFirstMove() && !this.isInCheck()){
            //kingSide castle
            if(!this.board.getTile(61).isTileOccupied() && !this.board.getTile(62).isTileOccupied()){
                final Tile rookTile = this.board.getTile(63);
                if(rookTile.isTileOccupied() && rookTile.getPiece().isFirstMove()){
                    if(Player.calculateAttacksOnTile(61,opponentLegals).isEmpty() &&
                            Player.calculateAttacksOnTile(62,opponentLegals).isEmpty()&&
                            rookTile.getPiece().getPieceType().isRook()){
                        kingCastle.add(null);
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
                    kingCastle.add(null);
                }
            }
        }
        return ImmutableList.copyOf(kingCastle);
    }
}