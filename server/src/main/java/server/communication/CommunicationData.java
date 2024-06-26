package server.communication;

import java.io.*;
import java.net.Socket;
/**
 * A classe CommunicationData gerencia os dados de comunicação para vários jogadores em um servidor.
 * Ela mantém os sockets, leitores e escritores para entrada e saída de dados para cada jogador.
 */
public final class CommunicationData {
    private Socket[] playerSockets;
    private BufferedReader[] playerInputReaders;
    private PrintWriter[] playerOutputWriters;
    private boolean[] playersConnected;
    int currentNumberOfPlayers = 0;
    /**
     * Configura o número inicial de jogadores com os arrays necessários.
     *
     * @param numberOfHumanPlayers o número de jogadores humanos a serem suportados
     */
    public void setUp(final int numberOfHumanPlayers) {
        playerSockets = new Socket[numberOfHumanPlayers];
        playerInputReaders = new BufferedReader[numberOfHumanPlayers];
        playerOutputWriters = new PrintWriter[numberOfHumanPlayers];
        playersConnected = new boolean[numberOfHumanPlayers];
    }
    /**
     * Adiciona um novo jogador ao servidor com o socket, leitor e escritor especificados.
     * Também envia uma mensagem inicial ao jogador.
     *
     * @param playerSocket o socket do jogador
     * @param br o leitor de entrada para o jogador
     * @param pw o escritor de saída para o jogador
     */
    public void addPlayer(Socket playerSocket, BufferedReader br, PrintWriter pw) {
        pw.println("Hello player");
        pw.println(currentNumberOfPlayers);
        playerSockets[currentNumberOfPlayers] = playerSocket;
        playerInputReaders[currentNumberOfPlayers] = br;
        playerOutputWriters[currentNumberOfPlayers] = pw;
        playersConnected[currentNumberOfPlayers] = true;
        currentNumberOfPlayers++;
    }
    /**
     * Obtém o leitor de entrada para um jogador específico pelo número do jogador.
     *
     * @param number o número do jogador
     * @return o leitor de entrada para o jogador especificado
     */
    public BufferedReader getInputReaderByNumber(int number) {
        return playerInputReaders[number];
    }
    /**
     * Obtém o escritor de saída para um jogador específico pelo número do jogador.
     *
     * @param number o número do jogador
     * @return o escritor de saída para o jogador especificado
     */
    public PrintWriter getPrintWriterByNumber(int number) {
        return playerOutputWriters[number];
    }
    /**
     * Envia uma mensagem para todos os jogadores conectados.
     *
     * @param message a mensagem a ser enviada para todos os jogadores
     */
    public void sendMessageToAllPlayers(String message) {
    	for(PrintWriter pw: playerOutputWriters) {
    		pw.println(message);
    	}
    }
}
