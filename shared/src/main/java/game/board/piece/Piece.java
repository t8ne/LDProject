package game.board.piece;

import game.board.field.Field;
import game.board.field.FieldColor;

public class Piece {
    private FieldColor pieceColor;
    private Field currentPosition;

    public Piece(Field currentPosition, FieldColor pieceColor) {
        this.pieceColor = pieceColor;
        this.currentPosition = currentPosition;
    }

    public FieldColor getPieceColor() {
        return pieceColor;
    }

    public Field getPosition() {
        return currentPosition;
    }

    public void setField(Field field) {
        currentPosition = field;
    }

    public String positionToString() {
    	return this.currentPosition.positionToString();
    }

}
