package gui;

import board.BoardStage;
import client.Client;
import game.Game;
import game.gamesettings.GameSettings;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class EventForCreateGameStage implements EventHandler<ActionEvent> {
	private Stage stage;
	private TextField playersTextField;
	private TextField botsTextField;
	private Client client;
	
	public EventForCreateGameStage(Stage stage, TextField playersTextField, 
			TextField botsTextField, Client client) {
		this.stage = stage;
		this.playersTextField = playersTextField;
		this.botsTextField = botsTextField;
		this.client = client;
	}
	
	private boolean checkNumbers() {
		int players;
		int bots;
		try {
			players = Integer.parseInt(playersTextField.getText());
			bots = Integer.parseInt(botsTextField.getText());
			
			if(players+bots != 2 && players+bots != 4 && players+bots != 5 && players+bots != 6) {
				return false;
			}
		}
		catch(NumberFormatException e) {
			return false;
		}
		
		return true;
	}

	@Override
	public void handle(ActionEvent event) {
		String string;
		if(this.checkNumbers() == true) {
			string = this.playersTextField.getText()+ " " + this.botsTextField.getText();
			//Game game = new Game(new GameSettings(string));
			client.sendOption(string);
			this.stage.close();
			/*game.setUp();
			int numberOfPlayer;
			numberOfPlayer = client.getPlayerNumber();
			BoardStage boardStage = new BoardStage(game, numberOfPlayer);
			this.stage.close();
			boardStage.show();*/
			//this.stage.close();
			//client.setStage(stage);

			InformationStage informationStage = new InformationStage("Waiting for more players...");
			informationStage.show();
			client.setStage(informationStage);
		}
		else {
			this.playersTextField.setText("Chinese checkers can be played by 2, 4, 5 or 6 players.");
			this.botsTextField.setText("Chinese checkers can be played by 2, 4, 5 or 6 players.");
		}
	}
}
