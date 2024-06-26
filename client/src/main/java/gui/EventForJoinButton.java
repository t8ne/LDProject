package gui;

import client.Client;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.stage.Stage;
/**
 * A classe EventForJoinButton lida com o evento quando o botão "Entrar" é pressionado.
 * Ela envia uma opção para o cliente ingressar em um jogo, fecha o estágio atual e abre um novo estágio informando que está aguardando mais jogadores.
 */
public class EventForJoinButton implements EventHandler<ActionEvent> {
	private Stage stage;
	private Client client;
	/**
	 * Constrói um EventForJoinButton com o estágio e o cliente especificados.
	 *
	 * @param stage o estágio atual que será fechado quando o botão for pressionado
	 * @param client o cliente que enviará a opção "join"
	 */
	public EventForJoinButton(Stage stage, Client client) {
		this.stage = stage;
		this.client = client;
	}
	/**
	 * Lida com o evento quando o botão "Entrar" é pressionado.
	 * Envia a opção "join" para o cliente, fecha o estágio atual e abre um novo estágio informando que está aguardando mais jogadores.
	 *
	 * @param actionEvent o evento disparado quando o botão é pressionado
	 */
	@Override
	public void handle(ActionEvent actionEvent) {
		client.sendOption("join");
		this.stage.close();
		InformationStage informationStage = new InformationStage("Á espera de mais jogadores...");
		informationStage.show();
		client.setStage(informationStage);
	}
}
