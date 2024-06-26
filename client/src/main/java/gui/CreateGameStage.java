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
import run.Run;
/**
 * A classe <code>CreateGameStage</code> representa a janela de criação de um novo jogo.
 * Esta janela permite que o usuário defina o número de jogadores e bots antes de iniciar um novo jogo.
 * <p>
 * Herda de <code>Stage</code> e utiliza JavaFX para a interface gráfica.
 * </p>
 *
 * @version 1.0
 * @since 2024-06-25
 */
public class CreateGameStage extends Stage {
	/**
	 * Construtor da classe <code>CreateGameStage</code>.
	 *
	 *  O cliente associado a esta janela de criação de jogo.
	 */
	public CreateGameStage(Client client) {
		this.setResizable(false);
		this.setTitle("Chinese checkers");

		GridPane gridPane = new GridPane();
		gridPane.setAlignment(Pos.CENTER);
		gridPane.setHgap(10);
		gridPane.setVgap(10);

		Label playersLabel = new Label("Nº de Jogadores:");
		gridPane.add(playersLabel, 0, 1);

		TextField playersTextField = new TextField();
		gridPane.add(playersTextField, 1, 1);

		Label botsLabel = new Label("Nº de Bots:");
		gridPane.add(botsLabel, 0, 2);

		TextField botsTextField = new TextField();
		gridPane.add(botsTextField, 1, 2);

		HBox hBox = new HBox(10);
		hBox.setAlignment(Pos.BOTTOM_CENTER);

		Button okButton = new Button("OK");
		okButton.setOnAction(new EventForCreateGameStage(this, playersTextField,
				botsTextField, client));
		hBox.getChildren().add(okButton);

		// Voltar button
		Button voltarButton = new Button("Voltar");
		voltarButton.setOnAction(e -> {
			this.close();
			try {
				new Run().start(new Stage());
			} catch (Exception ex) {
				ex.printStackTrace();
			}
		});
		hBox.getChildren().add(voltarButton); // Add Voltar button to HBox

		gridPane.add(hBox, 0, 3, 2, 1); // Span the HBox across two columns

		Scene scene = new Scene(gridPane, 400, 300);
		this.setScene(scene);
	}
}

