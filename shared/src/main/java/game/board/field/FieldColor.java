package game.board.field;

public enum FieldColor {
    RED,
    GREEN,
    BLUE,
    YELLOW,
    PURPLE,
    ORANGE,
    NONE;

    public static FieldColor fromNumber(int number) {
        switch (number) {
            case 0:
                return FieldColor.RED;
            case 1:
                return FieldColor.GREEN;
            case 2:
                return FieldColor.BLUE;
            case 3:
                return FieldColor.YELLOW;
            case 4:
                return FieldColor.PURPLE;
            case 5:
                return FieldColor.ORANGE;
            default:
                return FieldColor.NONE;
        }
    }
	
	/*public static FieldColor fromNumber(int number) {
        switch (number) {
            case 0:
                return FieldColor.RED;
            case 1:
                return FieldColor.GREEN;
            case 2:
                return FieldColor.BLUE;
            case 3:
                return FieldColor.ORANGE;
            case 4:
                return FieldColor.PURPLE;
            case 5:
                return FieldColor.YELLOW;
            default:
                return FieldColor.NONE;
        }
    }*/

    //
    public static FieldColor getEnemy(FieldColor color) {
        switch (color) {
            case RED:
                return GREEN;
            case GREEN:
                return RED;
            case BLUE:
                return ORANGE;
            case ORANGE:
                return BLUE;
            case PURPLE:
                return YELLOW;
            case YELLOW:
                return PURPLE;
            default:
                return NONE;
        }
    }
    
    public String toString() {
    	switch (this) {
    	case RED:
    		return "Red";
    	case GREEN:
            return "Green";
        case BLUE:
            return "Blue";
        case ORANGE:
            return "Orange";
        case PURPLE:
            return "Purple";
        case YELLOW:
            return "Yellow";
        default:
            return "";
    	}
    }
}
