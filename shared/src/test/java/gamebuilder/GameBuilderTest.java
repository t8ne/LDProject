package gamebuilder;

import static org.junit.Assert.*;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import game.Game;
import game.board.field.Field;
import game.board.field.FieldColor;
import game.board.piece.Piece;
import game.gamebuilder.ConcreteGameBuilder;
import game.gamesettings.GameSettings;

public class GameBuilderTest {
	
	private Game createGame(int numberOfHumanPlayers, int numberOfBots) {
		String settingsLine = Integer.toString(numberOfHumanPlayers) + " " + Integer.toString(numberOfBots);
		//w konstruktorze klasy Game tworzony jest obiekt klasy ConcreteGameBuilder
		Game game = new Game(new GameSettings(settingsLine));
		//ta metoda wywołuje metodę buildGame(this) w klasie ConcreteGameBuilder
		game.setUp();
		return game;
	}
	
	@Test
	public void testNumberOfPlayers() {
		int numberOfHumanPlayers = 4;
		int numberOfBots = 2;
		Game game = this.createGame(numberOfHumanPlayers, numberOfBots);
		Assert.assertTrue(game.getNumberOfPlayers() == numberOfHumanPlayers + numberOfBots);
	}
	
	@Test
	public void testNumberOfHumanPlayers() {
		int numberOfHumanPlayers = 4;
		int numberOfBots = 2;
		Game game = this.createGame(numberOfHumanPlayers, numberOfBots);
		Assert.assertTrue(game.getNumberOfHumanPlayers() == numberOfHumanPlayers);
	}
	
	@Test
	public void testNumberOfBots() {
		int numberOfHumanPlayers = 4;
		int numberOfBots = 2;
		Game game = this.createGame(numberOfHumanPlayers, numberOfBots);
		Assert.assertTrue(game.getNumberOfBots() == numberOfBots);
	}
	
	@Test
	public void testGettingFieldByCoordinates() {
		int numberOfHumanPlayers = 2;
		int numberOfBots = 0;
		Game game = this.createGame(numberOfHumanPlayers, numberOfBots);
		Field resultField = game.getFieldByCoordinates(0, 12);
		Assert.assertTrue(resultField.getRow() == 0);
		Assert.assertTrue(resultField.getDiagonal() == 12);
		
		resultField = game.getFieldByCoordinates(3, 13); //takie pole nie istnieje
		Assert.assertTrue(resultField.getRow() == 0);
		Assert.assertTrue(resultField.getDiagonal() == 0);
		Assert.assertTrue(resultField.getColor().equals(FieldColor.NONE));
		Assert.assertFalse(resultField.isLegal());
		
		resultField = game.getFieldByCoordinates(12, 12);
		Assert.assertTrue(resultField.getRow() == 12 && resultField.getDiagonal() == 12);
	}
	
	@Test
	public void testGettingPieceByField() {
		int numberOfHumanPlayers = 2;
		int numberOfBots = 0;
		Game game = this.createGame(numberOfHumanPlayers, numberOfBots);
		Field field = game.getFieldByCoordinates(16, 4);
		try {
			Piece piece = game.getPieceByField(field);
			
		} catch (Exception e) {
			Assert.fail("Field is empty");
			e.printStackTrace();
		}
		
		field = game.getFieldByCoordinates(2, 11);
		try {
			Piece piece = game.getPieceByField(field);
			
		} catch (Exception e) {
			Assert.fail("Field is empty");
			e.printStackTrace();
		}
	}
	
	@Test(expected = Exception.class)
	public void testExceptionOfGetPieceByField() throws Exception {
		int numberOfHumanPlayers = 2;
		int numberOfBots = 0;
		Game game = this.createGame(numberOfHumanPlayers, numberOfBots);
		Field field = game.getFieldByCoordinates(12, 12);
		Piece piece = game.getPieceByField(field);
	}
}
