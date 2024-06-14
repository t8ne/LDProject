package game.gamebuilder;

import game.Game;
import game.board.Board;
import game.board.field.Field;
import game.board.field.FieldColor;
import game.board.piece.Piece;
import game.player.BotPlayer;
import game.player.HumanPlayer;
import game.player.Player;

public class ConcreteGameBuilder implements GameBuilder {

    private Game game;
    private Board board;
    private Player[] players;
    private Piece[] pieces;

    private enum PlayerType {HUMAN, BOT}

    @Override
    public Game buildGame(Game game) {
        this.game = game;
        board = buildBoard();
        game.setBoard(board);
        pieces = buildPieces(game.getNumberOfHumanPlayers() + game.getNumberOfBots());
        game.setPieces(pieces);
        players = buildPlayers(game.getNumberOfHumanPlayers(), game.getNumberOfBots());
        game.setPlayers(players);

        return game;
    }

    private Board buildBoard() {
        Board board = new Board();
        return board;
    }

    private Piece[] buildPieces(final int numberOfPlayers) {
        Piece[] pieces = new Piece[10 * numberOfPlayers];
        int currentCounter = 0;
        for (int playerNumber = 0; playerNumber < numberOfPlayers; playerNumber++) {
            FieldColor currentPlayerColor = FieldColor.fromNumber(playerNumber);
            for (Field field : board.getFields(currentPlayerColor)) {
                pieces[currentCounter] = new Piece(field, currentPlayerColor);
                currentCounter++;
            }
        }
        return pieces;
    }

    private Player[] buildPlayers(final int numberOfHumanPlayers, final int numberOfBots) {
        final int numberOfPlayers = numberOfBots + numberOfHumanPlayers;
        Player[] players = new Player[numberOfPlayers];
        initializeHumanPlayers(numberOfHumanPlayers, players);
        initializeBotPlayers(numberOfBots, numberOfHumanPlayers, players);
        return players;
    }

    private void initializeHumanPlayers(final int numberOfHumanPlayers, Player[] players) {
        for (int i = 0; i < numberOfHumanPlayers; i++) {
            initializePlayer(PlayerType.HUMAN, i, numberOfHumanPlayers, players);
        }
    }

    private void initializeBotPlayers(final int numberOfBotPlayers, final int numberOfHumanPlayers, Player[] players) {
        for (int i = 0; i < numberOfBotPlayers; i++) {
            initializePlayer(PlayerType.BOT, i, numberOfHumanPlayers, players);
        }
    }

    /*private void initializePlayer(PlayerType type, int playerNumber, int numberOfHumanPlayers, Player[] players) {
        FieldColor newPlayerColor = FieldColor.fromNumber(playerNumber);
        Player newPlayer = null;
        switch (type) {
            case HUMAN:
                newPlayer = new HumanPlayer(newPlayerColor);
                players[playerNumber] = newPlayer;
                break;
            case BOT:
                newPlayer = new BotPlayer(newPlayerColor);
                players[playerNumber + numberOfHumanPlayers] = newPlayer;
                break;
        }
        Piece[] newPlayerPieces = game.getPlayerPieces(newPlayerColor);
        newPlayer.setPieces(newPlayerPieces);
    }*/
    
    //zmieniłam metodę initializePlayer, był niepoprawnie przypisywany kolor do bota
    private void initializePlayer(PlayerType type, int playerNumber, int numberOfHumanPlayers, Player[] players) {
        FieldColor newPlayerColor = null;
        Player newPlayer = null;
        switch (type) {
            case HUMAN:
            	newPlayerColor = FieldColor.fromNumber(playerNumber);
                newPlayer = new HumanPlayer(newPlayerColor);
                players[playerNumber] = newPlayer;
                break;
            case BOT:
            	newPlayerColor = FieldColor.fromNumber(playerNumber + numberOfHumanPlayers);
                newPlayer = new BotPlayer(newPlayerColor);
                players[playerNumber + numberOfHumanPlayers] = newPlayer;
                break;
        }
        Piece[] newPlayerPieces = game.getPlayerPieces(newPlayerColor);
        newPlayer.setPieces(newPlayerPieces);
    }
}