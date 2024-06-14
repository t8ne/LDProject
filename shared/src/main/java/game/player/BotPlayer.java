package game.player;

import game.board.field.FieldColor;

public final class BotPlayer extends AbstractPlayer {

    public BotPlayer(FieldColor playerColor) {
        homeColor = playerColor;
        enemyColor = FieldColor.getEnemy(playerColor);
    }
    @Override
    public boolean isBot() {
        return true;
    }
}
