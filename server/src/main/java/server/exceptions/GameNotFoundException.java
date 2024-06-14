package server.exceptions;

public class GameNotFoundException extends Exception {
    public GameNotFoundException() {
        super("No game found");
    }
}
