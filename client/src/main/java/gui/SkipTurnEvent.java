package gui;

import board.BoardStage;
import client.Client;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.stage.Stage;

public class SkipTurnEvent implements EventHandler<ActionEvent> {
	private Client client;
	private BoardStage boardStage;
	private boolean active;
	private int counter;
	
	public SkipTurnEvent(Client client, BoardStage stage) {
		this.client = client;
		this.boardStage = stage;
		this.active = false;
		this.counter = 0;
	}
	
	public void activate() {
		this.active = true;
	}
	
	public void setUnactive() {
		this.active = false;
		this.counter = 0;
	}
	
	@Override
	public void handle(ActionEvent event) {
		if(active && counter==0) {
			client.sendOption("skip");
			boardStage.setLabel("Wait for your turn...");
			setUnactive();
			boardStage.setUnactive();
			counter++;
		}
	}
}
