package gui;

import client.Client;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.stage.Stage;

public class EventForJoinButton implements EventHandler<ActionEvent> {
	private Stage stage;
	private Client client;
	
	public EventForJoinButton(Stage stage, Client client) {
		this.stage = stage;
		this.client = client;
	}
	
	@Override
	public void handle(ActionEvent actionEvent) {
		client.sendOption("join");
		this.stage.close();
		InformationStage informationStage = new InformationStage("Á espera de mais jogadores...");
		informationStage.show();
		client.setStage(informationStage);
	}
}
