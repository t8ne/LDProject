package server;

import game.gamesettings.GameSettings;
import server.communication.CommunicationData;
import server.exceptions.GameNotFoundException;

import javax.naming.CommunicationException;
import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class Server extends ServerSocket {
    private boolean serverRunning = true;
    private List<GameThread> games = new ArrayList<>();

    public Server(int port) throws IOException {
        super(port);
    }

    public void listen() throws IOException, CommunicationException, GameNotFoundException {
        while (serverRunning) {
            System.out.println("LOOP");
            Socket newPlayer = accept();
            System.out.println("Hello!");
            BufferedReader newPlayerInputReader = getPlayerInputStreamReader(newPlayer);
            System.out.println("Waiting for player type");
            String joinerType = newPlayerInputReader.readLine();
            System.out.println("Processing...");
            processJoinerType(joinerType, newPlayer);
        }
    }

    private void processJoinerType(String joinerType, Socket player) throws IOException {
        BufferedReader hostInputReader = getPlayerInputStreamReader(player);
        PrintWriter hostOutoutWriter = getPlayerOutputStreamWriter(player);
        if (joinerType.equals("host")) {
            System.out.println("I got here");
            String line = "You are host";
            hostOutoutWriter.println(line);
            GameSettings settings = setUpGame(hostInputReader);
            GameThread newGame = new GameThread(settings, player, hostInputReader, hostOutoutWriter);
            games.add(newGame);
            newGame.start();
            System.out.println("Thread started");
        }
        else if(joinerType.equals("join")) {
        	try {
        		String message = "";
        		for (GameThread thread : games) {
                    GameSettings settings = thread.getSettings();
                    int started = thread.hasStarted() ? 1 : 0;
                    int numOfJoinedPlayers = thread.getNumberOfJoinedPlayers();
                    int numOfHumanPlayers = settings.getNumberOfHumanPlayers();
                    int numOfBots = settings.getNumberOfBots();
                    int gameId = games.indexOf(thread);
                    message += "possible" + " " + numOfHumanPlayers + " " + numOfBots + " " + numOfJoinedPlayers
                            + " " + gameId + " " + started + "x";

                }
                message = message.substring(0, message.length() - 1);
        		hostOutoutWriter.println(message);
        		String chosenIDLine = hostInputReader.readLine();
        		System.out.println(chosenIDLine);
        		int id = Integer.parseInt(chosenIDLine.split(" ")[1]);
        		GameThread gameThread = findOpenGame(id);
        		if (gameThread == null)
        		    throw new GameNotFoundException();
        		gameThread.addPlayer(player, hostInputReader, hostOutoutWriter);
        	}
        	catch(GameNotFoundException e) {
        		hostOutoutWriter.println("No game found");
        	}
        }
    }

    private GameThread findOpenGame() throws GameNotFoundException {
        Optional<GameThread> possibleGame = games.stream().filter(game -> !game.hasStarted())
                .findFirst();
        if(possibleGame.isPresent()) {
            return possibleGame.get();
        }
        else {
            throw new GameNotFoundException();
        }
    }

    private GameThread findOpenGame(int id) throws  GameNotFoundException {
        return games.get(id);
    }

    private GameSettings setUpGame(BufferedReader hostInputReader) throws IOException {
        System.out.println("Inside setUp");
        String gameOptionsLine = hostInputReader.readLine();
        System.out.println("Options: "+ gameOptionsLine);
        return new GameSettings(gameOptionsLine);
    }

    private BufferedReader getPlayerInputStreamReader(Socket player) throws IOException {
        return new BufferedReader(new InputStreamReader(player.getInputStream()));
    }

    private PrintWriter getPlayerOutputStreamWriter(Socket player) throws IOException {
        return new PrintWriter(new OutputStreamWriter(player.getOutputStream()), true);
    }
}