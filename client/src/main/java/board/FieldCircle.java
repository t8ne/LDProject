package board;

import game.board.field.Field;
import game.board.field.FieldColor;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
/**
 * A classe FieldCircle representa visualmente um círculo que representa um campo (Field) no tabuleiro.
 * <p>
 * Esta classe extende Circle e implementa BoardElement, permitindo interações e identificação como um elemento de tabuleiro.
 * </p>
 *
 * @version 1.0
 * @since 2024-06-25
 */
public class FieldCircle extends Circle implements BoardElement {
	private Field field;
	/**
	 * Construtor da classe FieldCircle.
	 *
	 *  O objeto Field associado a este círculo.
	 *  O palco do tabuleiro onde o círculo será adicionado como um evento de clique.
	 */
	public FieldCircle(Field field, BoardStage boardStage) {
		this.field = field;
		
		this.setColor();
		this.setRadius(BoardData.fieldSize);
		this.setCoordinates();
		this.addEventFilter(MouseEvent.MOUSE_CLICKED, boardStage);
	}
	
	private void setCoordinates() {
		this.setCenterX(this.calculateX());
		this.setCenterY(this.calculateY());
	}
	
	private double calculateX() {
		double r = getRadius();
		double d = BoardData.gapSize;
		return ((2*r +d))*this.field.getRow() +
				((2*r+d)*1/2)* this.field.getDiagonal();
	}
	
	private double calculateY() {
		double r = getRadius();
		double d = BoardData.gapSize;
		return (2*r+d) * Math.sqrt(3) / 2 * this.field.getDiagonal() + BoardData.fieldSize;
	}//dopisałam +BoardSize.fieldSize
	
	private void setColor() {
		FieldColor color = this.field.getColor();
		
		if(color == FieldColor.BLUE)
			this.setFill(Color.LIGHTBLUE);
		else if(color == FieldColor.GREEN)
			this.setFill(Color.LIGHTGREEN);
		else if(color == FieldColor.ORANGE)
			this.setFill(Color.rgb(253, 221, 148));
		else if(color == FieldColor.PURPLE)
			this.setFill(Color.PLUM);
		else if(color == FieldColor.RED)
			this.setFill(Color.rgb(234,60,83));
		else if(color == FieldColor.YELLOW)
			this.setFill(Color.rgb(255, 253, 124));
		else if(color == FieldColor.NONE)
			this.setFill(Color.LIGHTGRAY);
	}

	@Override
	public boolean isField() {
		return true;
	}

	@Override
	public boolean isPiece() {
		return false;
	}
	/**
	 * Obtém o objeto Field associado a este círculo.
	 *
	 * @return O objeto Field associado.
	 */
	public Field getField() {
		return this.field;
	}

	@Override
	public FieldColor getColor() {
		return field.getColor();
	}
	/**
	 * Verifica se o campo associado a este círculo está livre.
	 *
	 * @return <code>true</code> se o campo estiver livre, caso contrário código = falso.
	 */
	public boolean isFree() {
		return field.isFree();
	}
}
