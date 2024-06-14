import java.io.BufferedReader;
import java.io.PrintWriter;

public class ListenerThread extends Thread {
    private BufferedReader br;
    private PrintWriter out;

    public ListenerThread(BufferedReader br, PrintWriter out) {
        this.br = br;
        this.out = out;
    }

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

