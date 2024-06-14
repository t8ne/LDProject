package client;

import java.io.BufferedReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import board.BoardStage;
//import com.sun.org.apache.xalan.internal.xsltc.compiler.Pattern;
import game.Game;
import game.board.piece.Piece;
import game.gamesettings.GameSettings;
import game.player.Player;
import gui.InformationStage;
import gui.LobbyStage;
import gui.YourTurnStage;
import javafx.application.Platform;
import javafx.stage.Stage;

public class ListenerThread extends Thread {
    private BufferedReader br;
    private Client client;
    private int playerNumber;
    private Game game;
    private BoardStage boardStage;
    private volatile List<String> gl_args;

    public ListenerThread(BufferedReader br, Client client) {
        this.br = br;
        this.client = client;
    }

    public void run() {
        while (true) {
            try {
                String currentLine;
                if (br.ready() && (currentLine = br.readLine()) != null) {
                    System.out.println(currentLine);

                    if (isNumber(currentLine)) {
                        this.playerNumber = Integer.parseInt(currentLine);
                        System.out.println("I got number " + playerNumber);
                    } else if (currentLine.contains("possible")) {

                        System.out.println("Right where u want me to be");
                        List<String> args = new ArrayList<>();

                        System.out.println(currentLine);

                        String[] split = currentLine.split("x");
                        args.addAll(Arrays.asList(split));
                        System.out.println("Size: " + args.size());
                        gl_args = args;
                        Platform.runLater(() -> {
                            LobbyStage stage = new LobbyStage(client, gl_args);
                            stage.showAndWait();
                        });

                    } else if (isGameSettings(currentLine)) {
                        System.out.println("I got settings " + currentLine);
                        this.game = new Game(new GameSettings(currentLine));
                        game.setUp();
                    } else if (currentLine.contains("Game started")) {
                        System.out.println("I got game started");
                        Platform.runLater(() -> {
                            client.closePreviousStage();
                            boardStage = new BoardStage(this.game, this.playerNumber, this.client);
                            boardStage.show();
                        });
                    } else if (currentLine.contains("No game found")) {
                        Platform.runLater(() -> {
                            InformationStage newStage = new InformationStage("Game not found");
                            newStage.show();
                        });
                    } else if (currentLine.contains("move")) {
                        Platform.runLater(() -> {
                            try {
                                this.boardStage.makeMove(currentLine);
                                this.boardStage.setLabel("Wait for your turn...");
                            }
                            catch (Exception e) {
                                e.printStackTrace();
                            }
                        });
                    } else if (currentLine.contains("Your turn.")) {
                        Platform.runLater(() -> {
                            boardStage.activate();
                            boardStage.setLabel("Your turn!");
                        });
                    } else if (currentLine.contains("winner")) { //winner 1
                        String[] string = currentLine.split(" ");
                        int number = Integer.parseInt(string[1]);

                        if (number == this.playerNumber) {
                            Platform.runLater(() -> {
                                InformationStage newStage = new InformationStage("You won!");
                                newStage.show();
                            });
                        } else {
                            Player player = this.game.getPlayerByNumber(number);
                            String color = player.getColor().toString();

                            Platform.runLater(() -> {
                                InformationStage newStage = new InformationStage(color + " won!");
                                newStage.show();
                            });
                        }
                    }
                }

            }
            catch (Exception e) {
            }
        }
    }

    private boolean isNumber(String line) {
        try {
            Integer.parseInt(line);
        }
        catch (NumberFormatException e) {
            return false;
        }
        return true;
    }

    private boolean isGameSettings(String line) {
        return isNumber(line.substring(0, 1)) && isNumber(line.substring(2, 3)) && line.charAt(1) == ' ';
    }
}
