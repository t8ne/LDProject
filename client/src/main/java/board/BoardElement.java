package board;

import game.board.field.FieldColor;

public interface BoardElement {
	boolean isField();
	boolean isPiece();
	FieldColor getColor();
}
