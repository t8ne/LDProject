package board.field.fieldinitializer;

import game.board.Board;
import game.board.field.Field;
import game.board.field.FieldColor;
import game.board.field.fieldinitializer.ConcreteFieldInitializer;
import game.board.field.fieldinitializer.FieldInitializer;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class FieldInitializerTest {

    private Board board;
    private FieldInitializer fieldInitializer;
    private Field[] initializedFields;

    @Before
    public void prepareTest() {
        board = new Board();
    }

    @Test
    public void HexagonIsProperlyInitialized() {
        try {
            performHexagonTest();
        }
        catch (NoSuchFieldException e) {
            onNoSuchFieldException(e);
        }
    }

    @Test
    public void HomeFieldsAreProperlyInitialized() {
        try {
            performHomeFieldsTest();
        }
        catch (NoSuchFieldException e) {
            onNoSuchFieldException(e);
        }
    }

    private void performHexagonTest() throws NoSuchFieldException {
        Field potentialHexagonField1 = board.getFieldByCoordinates(4, 8);
        Field potentialHexagonField2 = board.getFieldByCoordinates(4, 12);
        Field potentialHexagonField3 = board.getFieldByCoordinates(12, 4);
        Assert.assertTrue(potentialHexagonField1.isLegal() && !potentialHexagonField1.isHomeField());
        Assert.assertTrue(potentialHexagonField2.isLegal() && !potentialHexagonField1.isHomeField());
        Assert.assertTrue(potentialHexagonField3.isLegal() && !potentialHexagonField3.isHomeField());
    }

    private void performHomeFieldsTest() throws NoSuchFieldException {
        Field potentialRedField =  board.getFieldByCoordinates(13, 4);
        Field potentialGreenField =  board.getFieldByCoordinates(3, 12);
        Field potentialBlueField = board.getFieldByCoordinates(4,4);
        Field potentialYellowField =board.getFieldByCoordinates(4, 14);
        Field potentialPurpleField =board.getFieldByCoordinates(9, 3);
        Field potentialOrangeField =board.getFieldByCoordinates(10, 11);

        Assert.assertTrue(isProperlyIntializedHomeField(potentialRedField, FieldColor.RED));
        Assert.assertTrue(isProperlyIntializedHomeField(potentialGreenField, FieldColor.GREEN));
        Assert.assertTrue(isProperlyIntializedHomeField(potentialBlueField, FieldColor.BLUE));
        Assert.assertTrue(isProperlyIntializedHomeField(potentialYellowField, FieldColor.YELLOW));
        Assert.assertTrue(isProperlyIntializedHomeField(potentialPurpleField, FieldColor.PURPLE));
        Assert.assertTrue(isProperlyIntializedHomeField(potentialOrangeField, FieldColor.ORANGE));
    }

    private boolean isProperlyIntializedHomeField(Field field, FieldColor desiredColor) {
        return field.getColor() == desiredColor && field.isHomeField();
    }

    private void onNoSuchFieldException(NoSuchFieldException e) {
        System.out.println(e.getMessage());
    }
}
