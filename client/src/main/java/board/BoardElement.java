package board;

import game.board.field.FieldColor;
/**
 * A interface <code>BoardElement</code> define os métodos que devem ser implementados por qualquer elemento em um tabuleiro de jogo.
 * <p>
 * Um elemento do tabuleiro pode ser um campo ou uma peça, e deve ter uma cor associada.
 * </p>
 *
 * @version 1.0
 * @since 2024-06-25
 */
public interface BoardElement {
	/**
	 * Verifica se o elemento é um campo.
	 */
	boolean isField();
	/**
	 * Verifica se o elemento é uma peça.
	 *
	 */
	boolean isPiece();
	/**
	 * Obtém a cor do elemento.
	 *
	 * @return a cor do elemento como um objeto <code>FieldColor</code>
	 */
	FieldColor getColor();
}
