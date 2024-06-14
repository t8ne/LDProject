package game.gamesettings;

public final class GameSettings {
    private int numberOfHumanPlayers;
    private int numberOfBots;

    public GameSettings(String line) throws NumberFormatException {
        String[] parameters = line.split(" ");
        String nubmerOfHumanPlayersString = parameters[0];
        String numberOfBotsString = parameters[1];
        numberOfHumanPlayers = Integer.parseInt(nubmerOfHumanPlayersString);
        numberOfBots = Integer.parseInt(numberOfBotsString);
    }

    public int getNumberOfHumanPlayers() {
        return numberOfHumanPlayers;
    }

    public int getNumberOfBots() {
        return numberOfBots;
    }
    
    public String toString() {
    	return Integer.toString(numberOfHumanPlayers) + " " + Integer.toString(numberOfBots);
    }
}
