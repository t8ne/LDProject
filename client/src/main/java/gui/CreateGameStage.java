package gui;

import client.Client;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

public class CreateGameStage extends Stage {
	
	public CreateGameStage(Client client) {
		this.setTitle("Chinese checkers");
		
		GridPane gridPane = new GridPane();
		gridPane.setAlignment(Pos.CENTER);
		gridPane.setHgap(10);
		gridPane.setVgap(10);
		
		Label playersLabel = new Label("Number of players:");
		gridPane.add(playersLabel, 0, 0);

		TextField playersTextField = new TextField();
		gridPane.add(playersTextField, 1, 0);

		Label botsLabel = new Label("Number of bots:");
		gridPane.add(botsLabel, 0, 1);
		
		TextField botsTextField = new TextField();
		gridPane.add(botsTextField, 1, 1);
		
		HBox hBox = new HBox(10);
		hBox.setAlignment(Pos.BOTTOM_CENTER);
		
		Button okButton = new Button("OK");
		okButton.setOnAction(new EventForCreateGameStage(this, playersTextField, 
				botsTextField, client));
		hBox.getChildren().add(okButton);
		gridPane.add(hBox, 0, 2);
		
		Scene scene = new Scene(gridPane, 400, 300);
		this.setScene(scene);
	}
}
