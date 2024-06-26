package server.exceptions;
/**
 * A exceção GameNotFoundException é lançada quando não é possível encontrar um jogo específico no servidor.
 * Isso geralmente ocorre quando um cliente tenta acessar um jogo que não existe ou não está mais disponível.
 */
public class GameNotFoundException extends Exception {
    /**
     * Construtor que cria uma nova exceção GameNotFoundException com uma mensagem padrão.
     */
    public GameNotFoundException() {
        super("No game found");
    }
}
