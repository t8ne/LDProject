package game.board.field;

public class HomeField extends Field {
    public HomeField(FieldColor owner, int row, int diagonal) {
        this.color = owner;
        this.status = FieldStatus.OCCUPIED;
        this.row = row;
        this.diagonal = diagonal;
    }
}
