package server.validation;

import java.util.ArrayList;
import java.util.List;

import game.Game;
import game.board.field.Field;
import game.board.field.FieldColor;
import game.board.piece.Piece;
import game.player.Player;

public class GameAnalyzer {
    private Game game;

    public GameAnalyzer(Game game) {
        this.game = game;
    }

    public boolean isValid(MoveDetails details, boolean hasJumped, Piece lastMovedPiece) throws Exception {
        Field initialField = game.getFieldByCoordinates(details.getInitialRow(), details.getInitialDiagonal());
        Field destinationField = game.getFieldByCoordinates(details.getDestinationRow(), details.getDestinationDiagonal());

        Piece movingPiece = game.getPieceByField(initialField);
        FieldColor movingPieceColor = movingPiece.getPieceColor();
        FieldColor enemyColor = FieldColor.getEnemy(movingPieceColor);
        FieldColor initialfieldColor = initialField.getColor();
        if(initialfieldColor.equals(enemyColor)) {
        	FieldColor destinationFieldColor = destinationField.getColor();
        	if(!destinationFieldColor.equals(enemyColor)) {
        		return false;
        	}
        }
        
        if(!sourceOccupiedAndDestFree(initialField, destinationField))
            return false;
        Piece activePiece = game.getPieceByField(initialField);
        if (lastMovedPiece != null && activePiece != lastMovedPiece) {
            System.out.println("I was reached");
            return false;
        }
        if (!pieceColorMatches(activePiece, details.getPlayer()))
            return false;
        if (!hasJumped)
            return destinationIsLegal(initialField, destinationField);
        else
            return moveIsJump(initialField, destinationField);
    }

    private boolean sourceOccupiedAndDestFree(Field source, Field dest) {
        return !source.isFree() && dest.isFree();
    }

    private boolean pieceColorMatches(Piece piece, Player player) {
        return piece.getPieceColor() == player.getColor();
    }

    private boolean destinationIsLegal(Field source, Field dest) throws Exception {
        return moveIsStandard(source, dest) || moveIsJump(source, dest);
    }

    private boolean moveIsStandard(Field source, Field dest) {
        return     dest.getRow() == source.getRow() && dest.getDiagonal() == source.getDiagonal() + 1
                || dest.getRow() == source.getRow() && dest.getDiagonal() == source.getDiagonal() - 1
                || dest.getRow() == source.getRow() + 1 && dest.getDiagonal() == source.getDiagonal()
                || dest.getRow() == source.getRow() - 1 && dest.getDiagonal() == source.getDiagonal()
                || dest.getRow() == source.getRow() + 1 && dest.getDiagonal() == source.getDiagonal() - 1
                || dest.getRow() == source.getRow() - 1 && dest.getDiagonal() == source.getDiagonal() + 1;
    }

    public boolean moveIsJump(Field source, Field dest)  {
        int sr = source.getRow();
        int sd = source.getDiagonal();
        int dr = dest.getRow();
        int dd = dest.getDiagonal();

        return     (dr == sr) && (dd == sd + 2) && fieldOccupied(sr, sd +1)
                || (dr == sr) && (dd == sd -2) && fieldOccupied(sr, sd -1)
                || (dr == sr + 2) && dd == sd && fieldOccupied(sr + 1, sd)
                || (dr == sr - 2) && dd == sd && fieldOccupied(sr -1, sd)
                || (dr == sr +2) && dd == sd-2 && fieldOccupied(sr + 1, sd - 1)
                || (dr == sr - 2) && dd == sd + 2 && fieldOccupied(sr - 1, sd + 1);
    }

    private boolean fieldOccupied(int row, int diag) {
        Field dest = field(row, diag);
        return dest.isLegal() && !dest.isFree();
    }

    public boolean hasPossibleJumps(Piece targetPiece) {
        Field position = targetPiece.getPosition();
        int row = position.getRow();
        int diagonal = position.getDiagonal();

        return     fieldOccupied(row, diagonal + 1) && fieldFree(row, diagonal + 2)
                || fieldOccupied(row, diagonal - 1) && fieldFree(row, diagonal -2)
                || fieldOccupied(row + 1, diagonal) && fieldFree(row + 2, diagonal)
                || fieldOccupied(row - 1, diagonal) && fieldFree(row - 2, diagonal)
                || fieldOccupied(row -1, diagonal +1) && fieldFree(row -2, diagonal+2)
                || fieldOccupied(row + 1, diagonal - 1) && fieldFree(row + 2, diagonal - 2);
    }

    private boolean fieldFree(int row, int diag) {
        Field dest = field(row, diag);
        return dest.isLegal() && dest.isFree();
    }

    private Field field(int row, int diagonal) {
        return game.getFieldByCoordinates(row, diagonal);
    }
    
    public boolean hasFinished(Player player) {
    	Piece[] playerPieces = this.game.getPlayerPieces(player.getColor());
    	for(Piece piece: playerPieces) {
    		FieldColor fieldColor = piece.getPosition().getColor();
    		FieldColor pieceColor = piece.getPieceColor();
    		FieldColor enemyColor = FieldColor.getEnemy(pieceColor);
    		
    		if(fieldColor != enemyColor)
    			return false;
    	}
    	return true;
    }
}