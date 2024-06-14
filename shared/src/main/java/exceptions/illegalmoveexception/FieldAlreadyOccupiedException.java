package exceptions.illegalmoveexception;

public class FieldAlreadyOccupiedException extends  IllegalMoveException {
    public FieldAlreadyOccupiedException() {
        super("This field is already occupied");
    }
}
