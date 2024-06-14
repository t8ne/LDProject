package server.communication;

import java.io.*;
import java.net.Socket;

public final class CommunicationData {
    private Socket[] playerSockets;
    private BufferedReader[] playerInputReaders;
    private PrintWriter[] playerOutputWriters;
    private boolean[] playersConnected;
    int currentNumberOfPlayers = 0;

    public void setUp(final int numberOfHumanPlayers) {
        playerSockets = new Socket[numberOfHumanPlayers];
        playerInputReaders = new BufferedReader[numberOfHumanPlayers];
        playerOutputWriters = new PrintWriter[numberOfHumanPlayers];
        playersConnected = new boolean[numberOfHumanPlayers];
    }

    public void addPlayer(Socket playerSocket, BufferedReader br, PrintWriter pw) {
        pw.println("Hello player");
        pw.println(currentNumberOfPlayers);
        playerSockets[currentNumberOfPlayers] = playerSocket;
        playerInputReaders[currentNumberOfPlayers] = br;
        playerOutputWriters[currentNumberOfPlayers] = pw;
        playersConnected[currentNumberOfPlayers] = true;
        currentNumberOfPlayers++;
    }

    public BufferedReader getInputReaderByNumber(int number) {
        return playerInputReaders[number];
    }

    public PrintWriter getPrintWriterByNumber(int number) {
        return playerOutputWriters[number];
    }
    
    public void sendMessageToAllPlayers(String message) {
    	for(PrintWriter pw: playerOutputWriters) {
    		pw.println(message);
    	}
    }
}
