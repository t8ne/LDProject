package game.board;

import game.board.field.Field;
import game.board.field.FieldColor;
import game.board.field.fieldinitializer.ConcreteFieldInitializer;
import game.board.field.fieldinitializer.FieldInitializer;

import java.util.Arrays;
import java.util.Optional;

import static game.board.BoardInformation.NUMBER_OF_FIELDS;


public class Board {

    private Field[] fields = new Field[NUMBER_OF_FIELDS];
    private FieldInitializer fieldInitializer;

    public Board() {
        fieldInitializer = new ConcreteFieldInitializer(this);
        initializeFields();
    }

    private void initializeFields() {
        fieldInitializer.initializeFields();
    }

    public Field[] getFields() {
        return fields;
    }
    
    public FieldInitializer getInitializer() {
    	return this.fieldInitializer;
    }

    public Field[] getFields(FieldColor color) {
        Field[] matchingFields = new Field[10];
        int i = 0;
        for (Field field : fields) {
            if (field.getColor() == color) {
                matchingFields[i] = field;
                i++;
            }
        }
        return matchingFields;
    }

    public Field getFieldByCoordinates(int row, int diagonal) throws NoSuchFieldException {
        Optional<Field> matching_field = Arrays.stream(fields).filter(field -> coordinatesMatch(field, row, diagonal))
                .findFirst();
        if(!matching_field.isPresent()) {
            throw new NoSuchFieldException();
        }
        return matching_field.get();

    }

    private boolean coordinatesMatch(Field field, int row, int diagonal) {
        return field.getRow() == row && field.getDiagonal() == diagonal;
    }
}
