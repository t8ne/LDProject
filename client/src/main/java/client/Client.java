package client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

import gui.CreateGameStage;
import javafx.stage.Stage;

public class Client {
	private BufferedReader br;
	private PrintWriter out;
	private Stage stage;


	public Client(String address, int port) throws IOException {
		Socket socket = new Socket(address, port);
        this.br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        this.out = new PrintWriter(socket.getOutputStream(), true);
        
        ListenerThread listener = new ListenerThread(br, this);
        listener.start();
	}
	
	public void sendOption(String option) {
		out.println(option);
	}
	
	public void setStage(Stage stage) {
		this.stage = stage;
	}
	
	public void closePreviousStage() {
		if(this.stage != null)
			stage.close();
	}
}
