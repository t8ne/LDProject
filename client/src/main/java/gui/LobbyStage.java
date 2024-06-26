package gui;

import client.Client;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.List;
/**
 * A classe LobbyStage representa o palco da sala de espera do jogo.
 * Esta classe cria uma janela que exibe informações sobre jogos disponíveis e permite que o jogador se junte a um jogo.
 */
public class LobbyStage extends Stage {
    private int currentRow = 1;
    private Client client;
    /**
     * Representa uma linha de informações de jogo na interface da sala de espera.
     */
    private class GameInfoRow {
        public Label humanPlayerLabel;
        public Label botPlayerLabel;
        public Label numJoinedLabel;
        public Label gameIdLabel;
        public JoinButton joinButton;

        /**
         * Constrói uma linha de informações de jogo com os dados fornecidos.
         *
         * @param humanPlayers número de jogadores humanos
         * @param botPlayers número de bots
         * @param joinedPlayers número de jogadores já juntados
         * @param gameId ID do jogo
         * @param hasStarted indica se o jogo já começou (0 para não, 1 para sim)
         */
        public GameInfoRow(String humanPlayers, String botPlayers, String joinedPlayers, String gameId, String hasStarted) {
            humanPlayerLabel = new Label(humanPlayers);
            botPlayerLabel = new Label(botPlayers);
            numJoinedLabel = new Label(joinedPlayers);
            gameIdLabel = new Label(gameId);
            joinButton = new JoinButton(Integer.parseInt(hasStarted));

        }


    }
    /**
     * Botão personalizado para se juntar a um jogo, que pode ser desativado se o jogo já tiver começado.
     */
    private class JoinButton extends Button {
        JoinButton(int gameStarted) {
            setText("Join");
            setDisable(gameStarted == 1);
        }
    }
    /**
     * Constrói um LobbyStage com o cliente especificado e uma lista de argumentos de jogo.
     *
     * @param client o cliente que se conectará aos jogos
     * @param args uma lista de strings contendo os argumentos de cada jogo disponível
     */
    public LobbyStage(Client client, List<String> args) {
        this.client = client;
        GridPane grid = new GridPane();
        grid.setVgap(50);
        grid.setHgap(50);
        grid.setAlignment(Pos.CENTER);

        Label humans = new Label("Human players");
        Label bots = new Label("Bots");
        Label joined = new Label("Joined players");
        Label id = new Label("Game id:");
        Label started = new Label("In progress: ");

        grid.addRow(0, humans, bots, joined, id, started);
        List<GameInfoRow> rows = new ArrayList<>();
        for (String argLine : args) {
            String[] params = argLine.split(" ");
            GameInfoRow row = new GameInfoRow(params[1], params[2], params[3], params[4], params[5]);
            row.joinButton.setOnAction(e -> {
                client.sendOption("choose "+row.gameIdLabel.getText());
                this.close();
            } );
            rows.add(row);
        }

        for (GameInfoRow row : rows) {
            grid.addRow(currentRow, row.humanPlayerLabel, row.botPlayerLabel, row.numJoinedLabel, row.gameIdLabel, row.joinButton);
            currentRow++;
        }

        Scene scene = new Scene(grid);
        setScene(scene);


    }

}
