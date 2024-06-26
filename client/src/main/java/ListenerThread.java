import java.io.BufferedReader;
import java.io.PrintWriter;
/**
 * A classe ListenerThread é uma thread que escuta continuamente mensagens recebidas através de um BufferedReader.
 * Ela imprime no console todas as linhas de texto recebidas.
 */
public class ListenerThread extends Thread {
    private BufferedReader br;
    private PrintWriter out;
    /**
     * Constrói um ListenerThread com o BufferedReader e PrintWriter especificados.
     *
     * @param br o BufferedReader para receber mensagens
     * @param out o PrintWriter para enviar mensagens (não utilizado neste contexto)
     */
    public ListenerThread(BufferedReader br, PrintWriter out) {
        this.br = br;
        this.out = out;
    }
    /**
     * Método principal da thread, que executa a escuta contínua das mensagens recebidas.
     * Quando uma linha de texto é recebida e está pronta para leitura, imprime-a no console.
     */
    public void run() {
        while (true) {
            try {
                String currentLine;
                if (br.ready() && (currentLine = br.readLine()) != null) {
                    System.out.println(currentLine);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}

