package game.player;

import game.board.field.FieldColor;
import game.board.piece.Piece;

public abstract class AbstractPlayer implements Player {
    protected FieldColor homeColor;
    protected FieldColor enemyColor;
    protected Piece[] pieces = new Piece[10];

    public void makeMove() {

    }
    public FieldColor getColor() {
        return homeColor;
    }
    public FieldColor getEnemyColor() {
        return enemyColor;
    }

    @Override
    public void setPieces(Piece[] pieces) {
        this.pieces = pieces;
    }
    
    public String toString() {
    	return "FieldColor: " + homeColor.toString() + " enemyColor: " + enemyColor.toString();
    }
}
