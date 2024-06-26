package board;

import client.Client;
import game.Game;
import game.board.field.Field;
import game.board.field.FieldColor;
import game.board.field.FieldStatus;
import game.board.piece.Piece;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
/**
 * A classe PieceCircle representa visualmente um círculo que representa uma peça (Piece) no tabuleiro.
 * <p>
 * Esta classe extende Circle e implementa BoardElement, permitindo interações e identificação como um elemento de tabuleiro.
 * </p>
 *
 * @version 1.0
 * @since 2024-06-25
 */
public class PieceCircle extends Circle implements BoardElement {
	private Piece piece;
	/**
	 * Construtor da classe PieceCircle.
	 *
	 *  O objeto Piece associado a este círculo.
	 *  O palco do tabuleiro onde o círculo será adicionado como um evento de clique.
	 */
	public PieceCircle(Piece piece, BoardStage boardStage) {
		this.piece = piece;
		this.setRadius(BoardData.fieldSize);
		this.setColor();
		this.setCoordinates(this.piece.getPosition());
		this.addEventFilter(MouseEvent.MOUSE_CLICKED, boardStage);
	}
	/**
	 * Move a peça para uma nova posição no tabuleiro.
	 *
	 *  A nova posição Field para onde a peça será movida.
	 *  O cliente responsável pela comunicação do jogo.
	 */
	public void move(Field newPosition, Client client) {
        this.setCoordinates(newPosition);
        piece.getPosition().setStatus(FieldStatus.FREE);
		newPosition.setStatus(FieldStatus.OCCUPIED);
		this.piece.setField(newPosition);
		
	}
	
	private void setCoordinates(Field field) {
		this.setCenterX(this.calculateX(field));
		this.setCenterY(this.calculateY(field));
	}
	
	private double calculateX(Field field) {
		double r = getRadius();
		double d = BoardData.gapSize;
		double row = field.getRow();
		double diagonal = field.getDiagonal();
		return ((2*r +d))*row+
				((2*r+d)*1/2)* diagonal;
	}
	
	private double calculateY(Field field) {
		double r = getRadius();
		double d = BoardData.gapSize;
		double diagonal = field.getDiagonal();
		return (2*r+d) * Math.sqrt(3) / 2 * diagonal + BoardData.fieldSize;
	}
	
	private void setColor() {
		FieldColor color = this.piece.getPieceColor();
		
		if(color == FieldColor.BLUE)
			this.setFill(Color.BLUE);
		else if(color == FieldColor.GREEN)
			this.setFill(Color.GREEN);
		else if(color == FieldColor.ORANGE)
			this.setFill(Color.ORANGE);
		else if(color == FieldColor.PURPLE)
			this.setFill(Color.PURPLE);
		else if(color == FieldColor.RED)
			this.setFill(Color.RED);
		else if(color == FieldColor.YELLOW)
			this.setFill(Color.YELLOW);
	}

	@Override
	public boolean isField() {
		return false;
	}

	@Override
	public boolean isPiece() {
		return true;
	}

	@Override
	public FieldColor getColor() {
		return piece.getPieceColor();
	}
	/**
	 * Obtém a linha onde a peça está localizada.
	 *
	 * return O número da linha onde a peça está localizada.
	 */
	public int getRow() {
		return this.piece.getPosition().getRow();
	}
	/**
	 * Obtém a diagonal onde a peça está localizada.
	 *
	 * return O número da diagonal onde a peça está localizada.
	 */
	public int getDiagonal() {
		return this.piece.getPosition().getDiagonal();
	}
	/**
	 * Obtém o objeto <code>Piece</code> associado a este círculo.
	 *
	 * return O objeto <code>Piece</code> associado.
	 */
	public Piece getPiece() {
		return this.piece;
	}
}
