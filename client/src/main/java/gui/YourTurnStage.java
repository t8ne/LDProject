package gui;

import client.Client;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
/**
 * A classe YourTurnStage representa o palco que indica ao jogador que é a sua vez de jogar.
 * Ele exibe uma mensagem e um botão para o jogador poder pular a sua vez.
 */
public class YourTurnStage extends Stage {
	private Client client;
	/**
	 * Constrói um YourTurnStage para o cliente especificado.
	 *
	 * @param client o cliente associado a esta janela de turno
	 */
	public YourTurnStage(Client client) {
		this.setTitle("Chinese checkers");
		
		GridPane gridPane = new GridPane();
		gridPane.setAlignment(Pos.CENTER);
		gridPane.setHgap(10);
		gridPane.setVgap(10);
		
		Label label = new Label("É a tua vez!");
		gridPane.add(label, 0, 0);

		Button button = new Button("Saltar a vez");
		
		gridPane.add(button, 0, 1);
		
		Scene scene = new Scene(gridPane, 200, 150);
		this.setScene(scene);
	}
}
