package gui;

import board.BoardStage;
import client.Client;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.stage.Stage;
/**
 * A classe SkipTurnEvent implementa EventHandler para lidar com eventos de "Pular Turno".
 * Quando ativado, envia uma opção para o servidor para pular o turno do jogador atual.
 */
public class SkipTurnEvent implements EventHandler<ActionEvent> {
	private Client client;
	private BoardStage boardStage;
	private boolean active;
	private int counter;
	/**
	 * Constrói um SkipTurnEvent com o cliente e o BoardStage especificados.
	 *
	 * @param client o cliente que envia opções para o servidor
	 * @param boardStage o palco do tabuleiro associado a este evento
	 */
	public SkipTurnEvent(Client client, BoardStage stage) {
		this.client = client;
		this.boardStage = stage;
		this.active = false;
		this.counter = 0;
	}
	/**
	 * Ativa o evento de "Pular Turno".
	 */
	public void activate() {
		this.active = true;
	}
	/**
	 * Desativa o evento de "Pular Turno" e reinicia o contador interno.
	 */
	public void setUnactive() {
		this.active = false;
		this.counter = 0;
	}
	/**
	 * Manipula o evento de ação, enviando a opção "skip" para o servidor quando ativado pela primeira vez.
	 *
	 * @param event o evento de ação associado ao clique do botão
	 */
	@Override
	public void handle(ActionEvent event) {
		if(active && counter==0) {
			client.sendOption("skip");
			boardStage.setLabel("Espera pela tua vez...");
			setUnactive();
			boardStage.setUnactive();
			counter++;
		}
	}
}
