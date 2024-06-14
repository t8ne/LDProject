package game;

import game.board.Board;
import game.board.field.Field;
import game.board.field.FieldColor;
import game.board.field.IllegalField;
import game.board.piece.Piece;
import game.gamebuilder.ConcreteGameBuilder;
import game.gamebuilder.GameBuilder;
import game.gamesettings.GameSettings;
import game.player.Player;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

public final class Game {
    private GameSettings gameSettings;
    private Player[] players;
    private Piece[] pieces;
    private List<Player> winners = new ArrayList<>();
    private Board board;
    private GameBuilder gameBuilder;

    public Game(GameSettings settings) {
        setSettings(settings);
        gameBuilder = new ConcreteGameBuilder();
    }

    public Player getPlayerByNumber(int number) {
        return players[number];
    }
    
    public GameSettings getSettings() {
    	return this.gameSettings;
    }

    public Board getBoard() {
        return board;
    }
    
    public Piece[] getPieces() {
    	return pieces;
    }

    public Piece[] getPlayerPieces(FieldColor playerColor) {
        Piece[] playerPieces = new Piece[10];
        int i = 0;
        for (Piece piece : pieces) {
            if (piece.getPieceColor() == playerColor) {
                playerPieces[i] = piece;
                i++;
            }
        }
        return playerPieces;
    }

    public void setBoard(Board board) {
        this.board = board;
    }

    public void setPlayers(Player[] players) {
        this.players = players;
    }

    public void setPieces(Piece[] pieces) {
        this.pieces = pieces;
    }

    public void setSettings(GameSettings settings) {
        gameSettings = settings;
    }

    public int getNumberOfPlayers() {
        return players.length;
    }

    public int getNumberOfHumanPlayers() {
        return gameSettings.getNumberOfHumanPlayers();
    }

    public int getNumberOfBots() {
        return gameSettings.getNumberOfBots();
    }

    public void setUp() {
        gameBuilder.buildGame(this);
    }

    public Field getFieldByCoordinates(int row, int diagonal) {
        Optional<Field> result =  Arrays.stream(board.getFields())
                .filter(f -> f.getRow() == row && f.getDiagonal() == diagonal)
                .findFirst();
        if(result.isPresent()) {
            return result.get();
        }
        else {
            return new IllegalField(0,0);
        }
    }

    public Piece getPieceByField(Field field) throws Exception {
        Optional<Piece> result = Arrays.stream(pieces).filter(p -> p.getPosition() == field).findFirst();
        if (result.isPresent()) {
            return result.get();
        }
        else {
            throw new Exception("Field is empty");
        }
    }

    public void addWinner(Player winner) {
    	this.winners.add(winner);
    }
    
    public boolean isWinner(Player player) {
    	return this.winners.contains(player);
    }
    
    public int getNumberOfWinners() {
    	return this.winners.size();
    }
}