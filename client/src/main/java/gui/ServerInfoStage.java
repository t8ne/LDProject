package gui;

import client.ConnectionCredentials;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;



public class ServerInfoStage extends Stage {
    ConnectionCredentials credentials;
    public ServerInfoStage(ConnectionCredentials credentials) {
        this.credentials = credentials;
        GridPane pane = new GridPane();
        pane.setAlignment(Pos.CENTER);
        pane.setHgap(10);
        pane.setVgap(10);
        Scene scene = new Scene(pane, 600, 400);
        Label ipLabel = new Label("IP Address: ");
        Label portLabel = new Label("Port: ");
        TextField ipField = new TextField();
        TextField portField = new TextField();
        Button button = new Button("Connect");

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
