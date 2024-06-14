package exceptions.illegalmoveexception;

public class MovedToHomeFieldException extends IllegalMoveException {
    public MovedToHomeFieldException() {
        super("You cannot finish your turn on that field.");
    }
}
