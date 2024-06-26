package gui;

import client.ConnectionCredentials;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;


/**
 * A classe ServerInfoStage representa o palco para configurar informações de conexão com o servidor.
 * Esta classe exibe campos para inserir o endereço IP e a porta do servidor e um botão para conectar.
 */
public class ServerInfoStage extends Stage {
    ConnectionCredentials credentials;
    /**
     * Constrói um ServerInfoStage com as credenciais de conexão especificadas.
     *
     * @param credentials as credenciais de conexão com o servidor
     */
    public ServerInfoStage(ConnectionCredentials credentials) {
        this.credentials = credentials;
        GridPane pane = new GridPane();
        pane.setAlignment(Pos.CENTER);
        pane.setHgap(10);
        pane.setVgap(10);
        Scene scene = new Scene(pane, 600, 400);
        Label ipLabel = new Label("Endereço IP: ");
        Label portLabel = new Label("Porta: ");
        TextField ipField = new TextField();
        TextField portField = new TextField();
        Button button = new Button("Conectar");

        pane.add(ipLabel, 0, 0);
        pane.add(portLabel, 0, 1);
        pane.add(ipField, 1, 0);
        pane.add(portField, 1, 1);
        pane.add(button, 2, 1);

        button.setOnAction( e -> {
            credentials.address = ipField.getText();
            credentials.port = Integer.parseInt(portField.getText());
            this.close();
        });

        setScene(scene);
    }

}
