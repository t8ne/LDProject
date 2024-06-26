package gui;

import client.Client;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Font;
import javafx.stage.Stage;
/**
 * A classe InformationStage exibe uma janela de informação com uma mensagem especificada.
 * A janela é utilizada para informar os usuários sobre o status atual, como aguardar por mais jogadores.
 */
public class InformationStage extends Stage {
	private String string;
	/**
	 * Constrói um InformationStage com a mensagem especificada.
	 *
	 * @param string a mensagem a ser exibida na janela de informação
	 */
	public InformationStage(String string) {
		this.string = string;
		
		Label label = new Label(string);
		this.setTitle("Chinese Checkers");
		label.setFont(new Font(15));
		StackPane root = new StackPane();
        root.getChildren().add(label);
        Scene scene = new Scene(root, 400, 300);
        this.setScene(scene);
	}
}
