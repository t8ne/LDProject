package gui;

import client.Client;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.stage.Stage;

public class EventForCreateButton implements EventHandler<ActionEvent> {
	private Stage stage;
	private Client client;
	
	public EventForCreateButton(Stage stage, Client client) {
		this.stage = stage;
		this.client = client;
	}
	
	@Override
	public void handle(ActionEvent actionEvent) {
		client.sendOption("host");
		this.stage.close();
		CreateGameStage newStage = new CreateGameStage(client);
		newStage.show();
	}
}
