package run;

import server.Server;

import java.io.IOException;

public class Run {
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
