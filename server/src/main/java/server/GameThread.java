package server;

import game.Game;
import game.board.field.Field;
import game.board.field.FieldColor;
import game.board.field.FieldStatus;
import game.board.piece.Piece;
import game.gamesettings.GameSettings;
import game.player.Player;
import server.communication.CommunicationData;
import server.validation.MoveDetails;
import server.validation.GameAnalyzer;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class GameThread extends Thread {
    private boolean started = false;
    private boolean isOver = false;
    private CommunicationData communicationData = new CommunicationData();
    private Game game;
    private GameAnalyzer validator;
    private int currentPlayerNumber;
    private boolean hasJumped = false;
    private Piece lastMovedPiece = null;
    private boolean skip = false;

    private int numberOfJoinedPlayers = 0;
    private GameSettings settings;

    public GameThread(GameSettings settings, Socket host, BufferedReader br, PrintWriter pw) throws IOException {
        this.settings = settings;
        communicationData.setUp(settings.getNumberOfHumanPlayers());
        game = new Game(settings);
        //game.setUp();
        addPlayer(host, br, pw);
        validator = new GameAnalyzer(game);
    }

    public int getNumberOfJoinedPlayers() {
        return numberOfJoinedPlayers;
    }

    public GameSettings getSettings() {
        return settings;
    }



    @Override
    public void run()  {

        Random rand = new Random();
        while(numberOfJoinedPlayers < game.getNumberOfHumanPlayers()) {
            System.out.println("Waiting for " + (game.getNumberOfHumanPlayers() - numberOfJoinedPlayers)  + "more players");
            try {
                synchronized(this) {
                    wait(5000);
                }
            }
            catch (InterruptedException ex) {}
        }
        communicationData.sendMessageToAllPlayers("Game started");
        game.setUp();
        started = true;
        currentPlayerNumber = rand.nextInt(game.getNumberOfPlayers());
        while (!isOver) {
            if(!game.getPlayerByNumber(currentPlayerNumber).isBot()) {
                try {
                	if(!this.isWinner(currentPlayerNumber)) {
                		MoveDetails newMoveDetails = listenForMove();
                		makeMove(newMoveDetails);
                	}
                    endMove();
                }
                catch (Exception ex) {
                    System.out.println("Making move error");
                    ex.printStackTrace();
                }
            }
            else {
            	try {
            		if(!this.isWinner(currentPlayerNumber)) {
            			
            			
            			try {
                            synchronized(this) {
                                wait(200);
                            }
                        }
                        catch (InterruptedException ex) {}
            			
            			
            			Player botPlayer = game.getPlayerByNumber(currentPlayerNumber);
            			MoveDetails moveDetails = this.makeBotsMove(botPlayer);
            			
            			makeMove(moveDetails);
            			endMove();
            			
            		}
            	}
            	catch(Exception e) {
            		e.printStackTrace();
            	}
            }
        }
    }

    private MoveDetails listenForMove() throws Exception {
        boolean isMoveLegal = false;
        MoveDetails details;
        System.out.println("Waiting for move of player "+currentPlayerNumber);
        BufferedReader playerInputReader = communicationData.getInputReaderByNumber(currentPlayerNumber);
        PrintWriter playerPrinrWriter = communicationData.getPrintWriterByNumber(currentPlayerNumber);
        playerPrinrWriter.println("Your turn.");
        
        Player currentPlayer = game.getPlayerByNumber(currentPlayerNumber);
        do {
            String moveLine = playerInputReader.readLine();
            
            if(moveLine.contains("skip")) {
            	this.skip = true;
            	return null;
            }
            details = new MoveDetails(currentPlayer, moveLine);
            isMoveLegal = validator.isValid(details, hasJumped, lastMovedPiece);
        } while (!isMoveLegal);
        return details;
    }

    private void makeMove(MoveDetails details) throws Exception {
    	if(details != null) {
            int initialRow = details.getInitialRow();
            int initialDiagonal = details.getInitialDiagonal();
            int destinationRow = details.getDestinationRow();
            int destinationDiagonal = details.getDestinationDiagonal();

            Field initialField = game.getFieldByCoordinates(initialRow, initialDiagonal);
            Field destinationField = game.getFieldByCoordinates(destinationRow, destinationDiagonal);
            Piece targetPiece = game.getPieceByField(initialField);

            initialField.setStatus(FieldStatus.FREE);
            targetPiece.setField(destinationField);
            destinationField.setStatus(FieldStatus.OCCUPIED);
            this.communicationData.sendMessageToAllPlayers("move: " + details.moveToString() + " " + currentPlayerNumber);

            lastMovedPiece = targetPiece;
            hasJumped = validator.moveIsJump(initialField, destinationField);
    	}
    }
    
    private boolean isWinner(int number) {
    	Player player = this.game.getPlayerByNumber(number);
    	return this.game.isWinner(player);
    }

    public void addPlayer(Socket player, BufferedReader br, PrintWriter pw) throws IOException {
        communicationData.addPlayer(player, br, pw);
        GameSettings gameSettings = this.game.getSettings();
        pw.println(gameSettings.toString());
        numberOfJoinedPlayers++;
    }

    public boolean hasStarted() {
        return started;
    }
    
    public void endMove() throws Exception {
    	if(skip == true) {
    		if(this.hasFinished(currentPlayerNumber) && !this.isWinner(currentPlayerNumber)) {
            	this.addWinner(currentPlayerNumber);
            	this.communicationData.sendMessageToAllPlayers("winner " + currentPlayerNumber);
            }
            if(this.over())
            	this.isOver = true;
			
            currentPlayerNumber = (currentPlayerNumber + 1) % game.getNumberOfPlayers();
			hasJumped = false;
			lastMovedPiece = null;
			skip = false;
    	}
    	else if (!hasPossibleJumps(lastMovedPiece)) {
            hasJumped = false;
            lastMovedPiece = null;
            
            if(this.hasFinished(currentPlayerNumber) && !this.isWinner(currentPlayerNumber)) {
            	this.addWinner(currentPlayerNumber);
            	this.communicationData.sendMessageToAllPlayers("winner " + currentPlayerNumber);
            }
            if(this.over())
            	this.isOver = true;
            
            currentPlayerNumber = (currentPlayerNumber + 1) % game.getNumberOfPlayers();
        }

    }

    private boolean hasPossibleJumps(Piece piece) throws Exception {//dodałam && hasJumped
        return validator.hasPossibleJumps(piece) && hasJumped;
    }
    
    private boolean over() {
    	
    	return this.game.getNumberOfPlayers() - 1 == this.game.getNumberOfWinners();
    }
    
    private boolean hasFinished(int number) {
    	return this.validator.hasFinished(this.game.getPlayerByNumber(number));
    }
    
    private void addWinner(int number) {
    	this.game.addWinner(this.game.getPlayerByNumber(number));
    }
    
    private Field field(int row, int diagonal) {
        return game.getFieldByCoordinates(row, diagonal);
    }
    
    private MoveDetails makeBotsMove(Player botPlayer) throws Exception {
    	MoveDetails moveDetails = null;
    	FieldColor botsColor = botPlayer.getColor();
    	this.communicationData.sendMessageToAllPlayers(botsColor.toString());
		Piece[] botsPieces = game.getPlayerPieces(botsColor);
		FieldColor enemyColor = FieldColor.getEnemy(botsColor);
		Field[] enemyFields = game.getBoard().getFields(enemyColor);
		boolean isMoveLegal = false;
		int i = 0;
		Field destinationField = null;
		int destinationRow = 0;
		int destinationDiagonal = 0;
		Random generator = new Random();
		
		switch(enemyColor) {
			case RED: destinationField = field(16,4); break;
			case GREEN: destinationField = field(0,12); break;
			case BLUE: destinationField = field(4,4); break;
			case YELLOW: destinationField = field(4,16); break;
			case PURPLE: destinationField = field(12,0); break;
			case ORANGE: destinationField = field(12,12); break;
			default: break;
		}
		
		destinationRow = destinationField.getRow();
		destinationDiagonal = destinationField.getDiagonal();
		
		for(Piece botsPiece: botsPieces) {
			List<Field> legalFields = new ArrayList<Field>();
			int row = botsPiece.getPosition().getRow();
			int diagonal = botsPiece.getPosition().getDiagonal();
			Field legalField;
			int rowDiff = Math.abs(destinationRow - row);
			int diagonalDiff = Math.abs(destinationDiagonal - diagonal);
			
			legalField = field(row, diagonal + 1);
			if(legalField.getRow() != 0 || legalField.getDiagonal() != 0) {
				legalFields.add(legalField);
			}
			
			legalField = field(row, diagonal - 1);
			if(legalField.getRow() != 0 || legalField.getDiagonal() != 0) {
				legalFields.add(legalField);
			}
			
			legalField = field(row + 1, diagonal);
			if(legalField.getRow() != 0 || legalField.getDiagonal() != 0) {
				legalFields.add(legalField);
			}
			
			legalField = field(row - 1, diagonal);
			if(legalField.getRow() != 0 || legalField.getDiagonal() != 0) {
				legalFields.add(legalField);
			}
			
			legalField = field(row - 1, diagonal + 1);
			if(legalField.getRow() != 0 || legalField.getDiagonal() != 0) {
				legalFields.add(legalField);
			}
			
			legalField = field(row + 1, diagonal - 1);
			if(legalField.getRow() != 0 || legalField.getDiagonal() != 0) {
				legalFields.add(legalField);
			}
			
			int numberOfLegalFields = legalFields.size();
			String moveLine;
			for(i=0;i<10;i++) {
				Field field = legalFields.get(generator.nextInt(numberOfLegalFields));
				moveLine = Integer.toString(row) + " " + Integer.toString(diagonal) + " " +
							Integer.toString(field.getRow()) + " " + Integer.toString(field.getDiagonal());
				moveDetails = new MoveDetails(botPlayer, moveLine);
				isMoveLegal = validator.isValid(moveDetails, hasJumped, lastMovedPiece);
				int newRowDiff = Math.abs(field.getRow() - destinationRow);
				int newDiagonalDiff = Math.abs(field.getDiagonal() - destinationDiagonal);
				if(isMoveLegal && ((newRowDiff<rowDiff && newDiagonalDiff<diagonalDiff) ||
					(newRowDiff<rowDiff && newDiagonalDiff==diagonalDiff) || 
					(newRowDiff==rowDiff && newDiagonalDiff<diagonalDiff))) {
					return moveDetails;
				}
			}
			
			}
		Piece randomPiece = botsPieces[generator.nextInt(10)];
		int row = randomPiece.getPosition().getRow();
		int diagonal = randomPiece.getPosition().getDiagonal();
		List<Field> legalFields = new ArrayList<Field>();
		Field legalField;
		legalField = field(row, diagonal + 1);
		if(legalField.getRow() != 0 || legalField.getDiagonal() != 0) {
			legalFields.add(legalField);
		}
		
		legalField = field(row, diagonal - 1);
		if(legalField.getRow() != 0 || legalField.getDiagonal() != 0) {
			legalFields.add(legalField);
		}
		
		legalField = field(row + 1, diagonal);
		if(legalField.getRow() != 0 || legalField.getDiagonal() != 0) {
			legalFields.add(legalField);
		}
		
		legalField = field(row - 1, diagonal);
		if(legalField.getRow() != 0 || legalField.getDiagonal() != 0) {
			legalFields.add(legalField);
		}
		
		legalField = field(row - 1, diagonal + 1);
		if(legalField.getRow() != 0 || legalField.getDiagonal() != 0) {
			legalFields.add(legalField);
		}
		
		legalField = field(row + 1, diagonal - 1);
		if(legalField.getRow() != 0 || legalField.getDiagonal() != 0) {
			legalFields.add(legalField);
		}
		
		String moveLine;
		for(Field field: legalFields) {
			moveLine = Integer.toString(row) + " " + Integer.toString(diagonal) + " " +
		Integer.toString(field.getRow()) + " " + Integer.toString(field.getDiagonal());
			moveDetails = new MoveDetails(botPlayer, moveLine);
			isMoveLegal = validator.isValid(moveDetails, hasJumped, lastMovedPiece);
			if(isMoveLegal) {
				return moveDetails;
			}
		}
		
		skip = true;
    	return null;
    }
}