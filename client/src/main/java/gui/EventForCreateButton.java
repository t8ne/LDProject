package gui;

import client.Client;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.stage.Stage;
/**
 * A classe EventForCreateButton lida com o evento quando o botão "Criar" é pressionado.
 * Ela envia uma opção para o cliente hospedar um jogo, fecha o estágio atual e abre um novo estágio para criar um jogo.
 */
public class EventForCreateButton implements EventHandler<ActionEvent> {
	private Stage stage;
	private Client client;
	/**
	 * Constrói um EventForCreateButton com o estágio e o cliente especificados.
	 *
	 * @param stage o estágio atual que será fechado quando o botão for pressionado
	 * @param client o cliente que enviará a opção "host"
	 */
	public EventForCreateButton(Stage stage, Client client) {
		this.stage = stage;
		this.client = client;
	}
	/**
	 * Lida com o evento quando o botão "Criar" é pressionado.
	 * Envia a opção "host" para o cliente, fecha o estágio atual e abre um novo estágio para criar um jogo.
	 *
	 * @param actionEvent o evento disparado quando o botão é pressionado
	 */
	@Override
	public void handle(ActionEvent actionEvent) {
		client.sendOption("host");
		this.stage.close();
		CreateGameStage newStage = new CreateGameStage(client);
		newStage.show();
	}
}
