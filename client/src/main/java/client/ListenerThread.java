package client;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import board.BoardStage;
import game.Game;
import game.board.piece.Piece;
import game.gamesettings.GameSettings;
import game.player.Player;
import gui.InformationStage;
import gui.LobbyStage;
import gui.YourTurnStage;
import javafx.application.Platform;

public class ListenerThread extends Thread {
    private BufferedReader br;
    private Client client;
    private int playerNumber;
    private Game game;
    private BoardStage boardStage;
    private volatile List<String> gl_args;
    private boolean running = true; // Flag to control the thread's execution

    public ListenerThread(BufferedReader br, Client client) {
        this.br = br;
        this.client = client;
    }

    public void stopThread() {
        running = false;
        try {
            br.close(); // Close the BufferedReader to release resources
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void run() {
        while (running) {
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
                                this.boardStage.setLabel("Espera pela tua vez...");
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        });
                    } else if (currentLine.contains("É a tua vez!")) {
                        Platform.runLater(() -> {
                            boardStage.activate();
                            boardStage.setLabel("É a tua vez!");
                        });
                    } else if (currentLine.contains("winner")) {
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
            } catch (IOException e) {
                // Socket has been closed, handle it here
                System.err.println("Conexão fechada.");
                // Optionally, notify the user or perform cleanup actions
                Platform.runLater(() -> {
                });
                stopThread(); // Stop the thread
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private boolean isNumber(String line) {
        try {
            Integer.parseInt(line);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    private boolean isGameSettings(String line) {
        return isNumber(line.substring(0, 1)) && isNumber(line.substring(2, 3)) && line.charAt(1) == ' ';
    }
}
