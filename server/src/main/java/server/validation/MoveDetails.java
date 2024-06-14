package server.validation;

import game.Game;
import game.board.field.Field;
import game.board.piece.Piece;
import game.player.Player;

public class MoveDetails {

    private Player player;
    private int initialRow;
    private int initialDiagonal;
    private int destinationRow;
    private int destinationDiagonal;

    public MoveDetails(Player player, String moveLine) {
        this.player = player;
        String[] params = moveLine.split(" ");
        initialRow = Integer.parseInt(params[0]);
        initialDiagonal = Integer.parseInt(params[1]);
        destinationRow = Integer.parseInt(params[2]);
        destinationDiagonal = Integer.parseInt(params[3]);
    }

    public int getInitialRow() {
        return initialRow;
    }

    public int getInitialDiagonal() {
        return initialDiagonal;
    }

    public int getDestinationRow() {
        return destinationRow;
    }

    public int getDestinationDiagonal() {
        return destinationDiagonal;
    }

    public Player getPlayer() {
        return player;
    }
    
    public String moveToString() {
    	return Integer.toString(initialRow) + " " + Integer.toString(initialDiagonal) + " " +
    Integer.toString(destinationRow) + " " + Integer.toString(destinationDiagonal);
    }
}
