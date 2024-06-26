package gui;

import board.BoardStage;
import client.Client;
import game.Game;
import game.gamesettings.GameSettings;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
/**
 * A classe EventForCreateGameStage lida com o evento quando o botão "Criar Jogo" é pressionado.
 * Ela verifica se os números de jogadores e bots são válidos, envia as opções para o cliente,
 * e abre um novo estágio para criar um jogo ou exibe uma mensagem de erro se os números forem inválidos.
 */
public class EventForCreateGameStage implements EventHandler<ActionEvent> {
	private Stage stage;
	private TextField playersTextField;
	private TextField botsTextField;
	private Client client;
	/**
	 * Constrói um EventForCreateGameStage com o estágio, campos de texto para jogadores e bots, e o cliente especificados.
	 *
	 * @param stage o estágio atual que será fechado quando o botão for pressionado
	 * @param playersTextField o campo de texto onde o número de jogadores é inserido
	 * @param botsTextField o campo de texto onde o número de bots é inserido
	 * @param client o cliente que enviará as opções de jogo
	 */
	public EventForCreateGameStage(Stage stage, TextField playersTextField, 
			TextField botsTextField, Client client) {
		this.stage = stage;
		this.playersTextField = playersTextField;
		this.botsTextField = botsTextField;
		this.client = client;
	}
	/**
	 * Verifica se os números de jogadores e bots inseridos são válidos.
	 *
	 * @return true se os números forem válidos, false caso contrário
	 */
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
	/**
	 * Lida com o evento quando o botão "Criar Jogo" é pressionado.
	 * Verifica se os números de jogadores e bots são válidos, envia a opção para o cliente,
	 * e abre um novo estágio para criar um jogo ou exibe uma mensagem de erro se os números forem inválidos.
	 *
	 * @param event o evento disparado quando o botão é pressionado
	 */
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

			InformationStage informationStage = new InformationStage("Á espera de mais jogadores...");
			informationStage.show();
			client.setStage(informationStage);
		}
		else {
			this.playersTextField.setText("Chinese checkers can be played by 2, 4, 5 or 6 players.");
			this.botsTextField.setText("Chinese checkers can be played by 2, 4, 5 or 6 players.");
		}
	}
}
