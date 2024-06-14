package game.board.field;

public class NeutralField extends Field {
    public NeutralField(int row, int diagonal) {
        this.color = FieldColor.NONE;
        this.status = FieldStatus.FREE;
        this.row = row;
        this.diagonal = diagonal;
    }
}
