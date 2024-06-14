package game.board.field;

public class IllegalField extends Field {
    public IllegalField(int row, int diagonal) {
        this.color= FieldColor.NONE;
        this.status=FieldStatus.ILLEGAL;
        this.row = row;
        this.diagonal = diagonal;
    }
}
