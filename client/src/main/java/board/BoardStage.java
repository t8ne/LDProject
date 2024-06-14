package board;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import client.Client;
import game.Game;
import game.board.field.Field;
import game.board.piece.Piece;
import game.player.Player;
import gui.SkipTurnEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;


public class BoardStage extends Stage implements EventHandler<MouseEvent> {
	@FXML
	private Game game;
	private Player player;
	private PieceCircle activePiece;
	private Client client;
	private boolean active;
	private List<PieceCircle> pieces;
	private Label turnLabel;
	private Button skipButton;
	private Label colorLabel;
	private SkipTurnEvent skipEvent;
	
	public BoardStage(Game game, int numberOfPlayer, Client client) {
		this.game = game;
		this.setResizable(false);
		this.activePiece = null;
		this.player = game.getPlayerByNumber(numberOfPlayer);
		this.colorLabel = new Label("You are " + player.getColor().toString() + ".");
		this.client = client;
		this.active = false;
		this.pieces = new ArrayList<PieceCircle>();
		this.turnLabel = new Label("Wait for you turn...");
		this.skipButton = new Button("Skip turn");
		this.skipEvent = new SkipTurnEvent(client, this);
		skipButton.setOnAction(skipEvent);
		
		drawBoard();
	}
	
	private void drawBoard() {
		Group group = new Group();
		
		for(Field field: this.game.getBoard().getFields()) {
			this.drawField(field, group);
		}
		
		for(Piece piece: this.game.getPieces()) {
			this.drawPiece(piece, group);
		}
		
		GridPane grid = new GridPane();
		grid.setAlignment(Pos.CENTER);
		grid.setHgap(10);
		grid.setVgap(10);
		grid.add(colorLabel, 0, 0);
		grid.add(turnLabel, 1, 0);
		grid.add(skipButton, 2, 0);
		
		GridPane gridPane = new GridPane();
		gridPane.setAlignment(Pos.CENTER);
		gridPane.setHgap(10);
		gridPane.setVgap(10);
		gridPane.add(group, 0, 0);
		gridPane.add(grid, 0, 1);
		Scene scene = new Scene(gridPane,gridPane.prefWidth(0) * 2, BoardData.fieldSize * 2 + gridPane.prefHeight(0));
		this.setScene(scene);
	}
	
	private void drawField(Field field, Group group) {
		FieldCircle fieldCircle = new FieldCircle(field, this);
		group.getChildren().add(fieldCircle);
	}
	
	private void drawPiece(Piece piece, Group group) {
		PieceCircle pieceCircle = new PieceCircle(piece, this);
		this.pieces.add(pieceCircle);
		group.getChildren().add(pieceCircle);
	}
	
	private boolean isMyElement(BoardElement element) {
		return element.getColor().equals(player.getColor());
	}
	
	private PieceCircle getPieceCircle(Piece piece) throws Exception {
		for(PieceCircle pieceCircle: this.pieces) {
			if(pieceCircle.getPiece() == piece)
				return pieceCircle;
		}
		throw new Exception("Piece doesn't exist");
	}
	
	public void setLabel(String string) {
		this.turnLabel.setText(string);
	}
	
	public void activate() {
		this.active = true;
		this.skipEvent.activate();
	}
	
	public void setUnactive() {
		this.active = false;
	}
	
	public void makeMove(String moveLine) throws Exception {
		String[] line = moveLine.split(" ");
		int initialRow = Integer.parseInt(line[1]);
		int initialDiagonal = Integer.parseInt(line[2]);
		Piece piece = game.getPieceByField(game.getFieldByCoordinates(initialRow, initialDiagonal));
		int destRow = Integer.parseInt(line[3]);
		int destDiagonal = Integer.parseInt(line[4]);
		Field newPosition = game.getFieldByCoordinates(destRow, destDiagonal);
		this.getPieceCircle(piece).move(newPosition, client);
		
		this.activePiece = null;
		this.active = false;
		this.skipEvent.setUnactive();
	}

	@Override
	public void handle(MouseEvent event) {
		
		Object source = event.getSource();
		BoardElement element = (BoardElement) source;
		
		if(this.active == true && element.isPiece() && isMyElement(element)) {
			this.activePiece = (PieceCircle) element;
		}
		else if(element.isField() && this.active == true) {
			FieldCircle fieldCircle = (FieldCircle) element;
			Field newPosition = fieldCircle.getField();
			if(this.activePiece != null) { ;
				client.sendOption(activePiece.getPiece().getPosition().positionToString() + " " + newPosition.positionToString());
			}
		}
	}
}
