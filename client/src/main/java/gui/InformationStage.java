package gui;

import client.Client;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Font;
import javafx.stage.Stage;

public class InformationStage extends Stage {
	private String string;
	
	public InformationStage(String string) {
		this.string = string;
		
		Label label = new Label(string);
		this.setTitle("Chinese checkers");
		label.setFont(new Font(15));
		StackPane root = new StackPane();
        root.getChildren().add(label);
        Scene scene = new Scene(root, 400, 300);
        this.setScene(scene);
	}
}
