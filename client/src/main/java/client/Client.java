package client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;

import javafx.stage.Stage;

public class Client {
	private BufferedReader br;
	private PrintWriter out;
	private Socket socket;
	private Stage stage;

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

	public void sendOption(String option) {
		if (out != null) {
			out.println(option);
		} else {
			System.err.println("Output stream is not available.");
		}
	}

	public void setStage(Stage stage) {
		this.stage = stage;
	}

	public void closePreviousStage() {
		if (this.stage != null) {
			stage.close();
		}
	}

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