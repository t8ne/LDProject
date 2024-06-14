package game.player;

import game.board.field.FieldColor;
import game.board.piece.Piece;

public interface Player {
    void makeMove();
    void setPieces(Piece[] pieces);
    boolean isBot();
    FieldColor getColor();
    FieldColor getEnemyColor();


}
