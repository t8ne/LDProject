package game.board.field.fieldinitializer;

import game.board.Board;
import game.board.BoardInformation;
import game.board.field.*;

public class ConcreteFieldInitializer extends FieldInitializer {
    public ConcreteFieldInitializer(Board board) {
        this.fields = board.getFields();
    }

    private int[][] hexagonBounds = {{8, 12}, {7, 12}, {6, 12}, {5, 12}, {4, 12}, {4, 11}, {4, 10}, {4, 9}, {4, 8}};
    private int current_counter = 0;

    @Override
    protected void initializePlayerHomeFields() {
        int color_number = 0;
        for (int[][] playerHomeCoordinates : BoardInformation.PLAYER_FIELDS_COORDINATES) {
            for (int[] pieceCoordinates : playerHomeCoordinates) {
                int row = pieceCoordinates[0];
                int diagonal = pieceCoordinates[1];
                fields[current_counter] = new HomeField(FieldColor.fromNumber(color_number), row, diagonal);
                current_counter++;
            }
            color_number++;
        }
    }

    @Override
    protected void initializeHexagonFields() {
        for (int i = 4; i <= 12; i++) {
            int lower_bound = hexagonBounds[i - 4][0];
            int upper_bound = hexagonBounds[i - 4][1];
            for (int j = lower_bound; j <= upper_bound; j++) {
                fields[current_counter] = new NeutralField(i, j);
                current_counter++;
            }
        }
    }
}