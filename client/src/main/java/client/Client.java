package client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;

import javafx.stage.Stage;
/**
 * A classe Client representa um cliente que se conecta a um servidor via socket.
 * <p>
 * Esta classe gerencia a conexão do cliente, envio de opções para o servidor e a recepção de mensagens do servidor.
 * </p>
 *
 * @version 1.0
 * @since 2024-06-25
 */
public class Client {
	private BufferedReader br;
	private PrintWriter out;
	private Socket socket;
	private Stage stage;
	/**
	 * Construtor da classe <code>Client</code>.
	 *
	 * @param address O endereço IP do servidor ao qual o cliente se conectará.
	 * @param port A porta do servidor à qual o cliente se conectará.
	 * @throws IOException Se ocorrer um erro ao tentar se conectar ao servidor.
	 */
	public Client(String address, int port) {
		try {
			this.socket = new Socket(address, port);
			this.br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			this.out = new PrintWriter(socket.getOutputStream(), true);

			ListenerThread listener = new ListenerThread(br, this);
			listener.start();
		} catch (UnknownHostException e) {
			System.err.println("Unknown host: " + address);
			e.printStackTrace();
		} catch (IOException e) {
			System.err.println("Couldn't get I/O for the connection to: " + address);
			e.printStackTrace();
			closeResources(); // Ensure resources are closed if an error occurs
		} catch (Exception e) {
			System.err.println("Connection error: " + e.getMessage());
			e.printStackTrace();
			closeResources(); // Ensure resources are closed if an error occurs
		}
	}
	/**
	 * Envia uma opção para o servidor.
	 *
	 * @param option A opção que será enviada ao servidor.
	 */
	public void sendOption(String option) {
		if (out != null) {
			out.println(option);
		} else {
			System.err.println("Output stream is not available.");
		}
	}
	/**
	 * Define o estágio da interface gráfica associado a este cliente.
	 *
	 * @param stage O estágio da interface gráfica.
	 */
	public void setStage(Stage stage) {
		this.stage = stage;
	}

	public void closePreviousStage() {
		if (this.stage != null) {
			stage.close();
		}
	}
	/**
	 * Desconecta o cliente do servidor, fechando todos os recursos de entrada e saída.
	 */
	public void disconnect() {
		try {
			if (out != null) {
				out.println("disconnect"); // Send a disconnect message to the server if needed
				out.close();
			}
			if (br != null) {
				br.close();
			}
			if (socket != null) {
				socket.close();
			}
			System.out.println("Disconectado do Servidor.");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private void closeResources() {
		try {
			if (out != null) {
				out.close();
			}
			if (br != null) {
				br.close();
			}
			if (socket != null) {
				socket.close();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}