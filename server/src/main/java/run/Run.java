package run;

import server.Server;

import java.io.IOException;
/**
 * A classe Run contém o método principal que inicializa e executa um servidor.
 */
public class Run {
    /**
     * O método principal que inicia a execução do servidor.
     *
     * @param args argumentos de linha de comando (não utilizado neste contexto)
     */
    public static void main(String[] args) {
        try {
            Server server = new Server(8080);
            server.listen();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
}
