package game.board.field;

public abstract class Field {
    protected int row;
    protected int diagonal;
    protected FieldStatus status;
    protected FieldColor color;


    public boolean isFree() {
        return status == FieldStatus.FREE;
    }

    public boolean isHomeField() {
        return !color.equals(FieldColor.NONE);
    }

    public boolean isLegal() {
        return status != FieldStatus.ILLEGAL;
    }

    public FieldColor getColor() {
        return color;
    }

    public int getRow() {
        return row;
    }

    public int getDiagonal() {
        return diagonal;
    }

    public void setStatus(FieldStatus status) {
        this.status = status;
    }
    
    public String positionToString() {
    	return Integer.toString(row) + " " + Integer.toString(diagonal);
    }
}
