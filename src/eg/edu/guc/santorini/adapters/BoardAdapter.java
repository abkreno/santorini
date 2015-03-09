package eg.edu.guc.santorini.adapters;

import java.util.ArrayList;

import eg.edu.guc.santorini.Board;
import eg.edu.guc.santorini.Cell;
import eg.edu.guc.santorini.ai.AIPlayer;
import eg.edu.guc.santorini.exceptions.InvalidMoveException;
import eg.edu.guc.santorini.exceptions.InvalidPlacementException;
import eg.edu.guc.santorini.gui.Tile;
import eg.edu.guc.santorini.players.Player;
import eg.edu.guc.santorini.tiles.Piece;
import eg.edu.guc.santorini.utilities.Location;

public class BoardAdapter {

	private Board myBoard;
	private Tile[][] tiles;
	private Piece currentPiece;
	private String style = "collection1/";
	private AIPlayer ai;
	private boolean AI = false;

	public BoardAdapter(Player p1, Player p2) {
		myBoard = new Board(p1, p2);
		initializeBoard();
	}

	public BoardAdapter(Player p1, Player p2, Board loaded) {
		myBoard = loaded;
		initializeBoard();
	}

	private void initializeBoard() {
		tiles = new Tile[5][5];
		for (int y = 0; y < 5; y++) {
			for (int x = 0; x < 5; x++) {
				tiles[y][x] = new Tile();
			}
		}
	}

	public void setBoard(Board b) {
		this.myBoard = b;
	}

	public void setAI(boolean AI) {
		this.AI = AI;
	}

	public Board getBoard() {
		return myBoard;
	}

	public boolean isAI() {
		return AI;
	}

	public Tile[][] showPlacements(Piece piece) {
		ArrayList<Location> placements = piece.possiblePlacements();
		getUpdated();
		for (int i = 0; i < placements.size(); i++) {
			if (myBoard.canPlace(piece, placements.get(i))) {
				int y = placements.get(i).getY();
				int x = placements.get(i).getX();
				int level = tiles[y][x].getCell().getLevel();
				tiles[y][x].changeImage(style + level + "A");
			}
		}
		return tiles;
	}

	public Tile[][] showMoves(Piece piece) {
		ArrayList<Location> moves = piece.possibleMoves();
		for (int i = 0; i < moves.size(); i++) {
			if (myBoard.canMove(piece, moves.get(i))) {
				int y = moves.get(i).getY();
				int x = moves.get(i).getX();
				int level = tiles[y][x].getCell().getLevel();
				tiles[y][x].changeImage(style + level + "A");
			}
		}
		return tiles;
	}

	public Tile[][] lightMoves(Piece piece) {
		ArrayList<Location> moves = piece.possibleMoves();
		for (int i = 0; i < moves.size(); i++) {
			if (myBoard.canMove(piece, moves.get(i))) {
				int y = moves.get(i).getY();
				int x = moves.get(i).getX();
				int level = tiles[y][x].getCell().getLevel();
				tiles[y][x].changeImage(style + level + "H");
			}
		}
		return tiles;
	}

	// return the board updated from the engine
	public Tile[][] getUpdated() {
		String[][] source = myBoard.display();
		Cell[][] cells = myBoard.getGrid();
		for (int y = 0; y < 5; y++) {
			for (int x = 0; x < 5; x++) {
				tiles[y][x].changeImage(style + source[y][x]);
				tiles[y][x].setCell(cells[y][x]);
			}
		}
		return tiles;
	}

	public Tile[][] getUpdated2(Board board) {
		String[][] source = board.display();
		Cell[][] cells = board.getGrid();
		for (int y = 0; y < 5; y++) {
			for (int x = 0; x < 5; x++) {
				tiles[y][x].changeImage(style + source[y][x]);
				tiles[y][x].setCell(cells[y][x]);
			}
		}
		return tiles;
	}

	public Tile[][] getPlacement(Tile tempLabel)
			throws InvalidPlacementException {
		if (myBoard.whatNow().equalsIgnoreCase("show p1 placements")) {
			if (tempLabel.getCell().getPlayer() == null) {
				if (tempLabel.isHighlighted()) {
					if (myBoard.getP1().getT1().equals(currentPiece)) {
						myBoard.place(myBoard.getP1().getT1(), tempLabel
								.getCell().getLocation());
						return getUpdated();
					} else {
						myBoard.place(myBoard.getP1().getT2(), tempLabel
								.getCell().getLocation());
						return getUpdated();
					}
				}
			}
		}
		if (myBoard.whatNow().equalsIgnoreCase("show p2 placements")) {
			if (tempLabel.getCell().getPlayer() == null) {
				if (tempLabel.isHighlighted()) {
					if (myBoard.getP2().getT1().equals(currentPiece)) {
						myBoard.place(myBoard.getP2().getT1(), tempLabel
								.getCell().getLocation());
						return getUpdated();
					} else {
						myBoard.place(myBoard.getP2().getT2(), tempLabel
								.getCell().getLocation());
						return getUpdated();
					}
				}
			}
		}
		return null;
	}

	public Tile[][] getMove(Tile tempLabel) throws InvalidMoveException,
			InvalidPlacementException {

		if (myBoard.whatNow().equalsIgnoreCase("show p1 moves")) {
			return tryMovingPlayer1(tempLabel);
		}
		if (myBoard.whatNow().equalsIgnoreCase("show p2 moves")) {
			return tryMovingPlayer2(tempLabel);
		}
		return null;
	}

	// AI PART HERE

	public Board copyBoard(Board b, boolean isMoved, boolean isPlaced,
			boolean isP1Turn) {
		String p1Name = b.getP1().getName();
		int p1Type = b.getP1().getType();
		String p2Name = b.getP2().getName();
		int p2Type = b.getP2().getType();
		String[][] display = b.display();
		String[][] copiedDisplay = new String[5][5];
		for (int i = 0; i < 5; i++) {
			for (int j = 0; j < 5; j++) {
				copiedDisplay[i][j] = display[i][j];
			}
		}
		Board copied = new Board(copiedDisplay, new Player(p1Name, p1Type),
				new Player(p2Name, p2Type));
		copied.setMoved(isMoved);
		copied.setPlaced(isPlaced);
		copied.setP1Turn(isP1Turn);
		return copied;
	}

	public Tile[][] getAIMove() throws InvalidMoveException,
			InvalidPlacementException {
		Board copy = copyBoard(myBoard, myBoard.isMoved(), myBoard.isPlaced(),
				myBoard.isP1Turn());
		boolean noValidMoves = false;
		if (AI) {
			ai = new AIPlayer(myBoard);
			int[] move = ai.move();
			if (move[1] == 1) {
				myBoard.move(myBoard.getP2().getT1(), new Location(move[2],
						move[3]));
				if (!myBoard.isGameOver()) {
					try {
						myBoard.place(myBoard.getP2().getT1(), new Location(
								move[4], move[5]));
					} catch (InvalidPlacementException e) {
						noValidMoves = true;
					}
				}
			} else if (move[1] == 2) {
				myBoard.move(myBoard.getP2().getT2(), new Location(move[2],
						move[3]));
				if (!myBoard.isGameOver()) {
					try {
						myBoard.place(myBoard.getP2().getT2(), new Location(
								move[4], move[5]));
					} catch (InvalidPlacementException e) {
						noValidMoves = true;
					}
				}
			} else {
				noValidMoves = true;
			}
			if (noValidMoves) {
				if (!copy.isGameOver())
					myBoard = ai.getRandomMovePiece1(copy);
			}

			return getUpdated();
		}
		return null;

	}

	private Tile[][] tryMovingPlayer1(Tile clickedTile)
			throws InvalidMoveException {
		if (clickedTile.getCell().getPlayer() != null
				&& clickedTile.getCell().getPlayer().equals(myBoard.getP1())) {
			if (clickedTile.getCell().getLocation()
					.equals(myBoard.getP1().getT1().getLocation())) {
				getUpdated();
				currentPiece = clickedTile.getCell().getPlayer().getT1();
				return showMoves(myBoard.getP1().getT1());
			} else {
				getUpdated();
				currentPiece = clickedTile.getCell().getPlayer().getT2();
				return showMoves(myBoard.getP1().getT2());
			}
		} else if (clickedTile.getCell().getPlayer() == null) {
			if (clickedTile.isHighlighted()) {
				if (myBoard.getP1().getT1().equals(currentPiece)) {
					myBoard.move(myBoard.getP1().getT1(), clickedTile.getCell()
							.getLocation());

					getUpdated();
					if (!myBoard.isGameOver()) {
						return showPlacements(myBoard.getP1().getT1());
					}
				} else {
					myBoard.move(myBoard.getP1().getT2(), clickedTile.getCell()
							.getLocation());

					getUpdated();
					if (!myBoard.isGameOver()) {
						return showPlacements(myBoard.getP1().getT2());
					}
				}
			}
		} else if (clickedTile.getCell().getPlayer() != null
				&& clickedTile.getCell().getPlayer().equals(myBoard.getP2())) {
			if (clickedTile.getCell().getLocation()
					.equals(myBoard.getP2().getT1().getLocation())) {
				getUpdated();
				currentPiece = clickedTile.getCell().getPlayer().getT1();
				return lightMoves(myBoard.getP2().getT1());
			} else {
				getUpdated();
				currentPiece = clickedTile.getCell().getPlayer().getT2();
				return lightMoves(myBoard.getP2().getT2());
			}
		}
		return null;
	}

	private Tile[][] tryMovingPlayer2(Tile tempLabel)
			throws InvalidMoveException {
		if (tempLabel.getCell().getPlayer() != null
				&& tempLabel.getCell().getPlayer().equals(myBoard.getP2())) {
			if (tempLabel.getCell().getLocation()
					.equals(myBoard.getP2().getT1().getLocation())) {
				getUpdated();
				currentPiece = tempLabel.getCell().getPlayer().getT1();
				return showMoves(myBoard.getP2().getT1());
			} else {
				getUpdated();
				currentPiece = tempLabel.getCell().getPlayer().getT2();
				return showMoves(myBoard.getP2().getT2());
			}
		} else if (tempLabel.getCell().getPlayer() == null) {
			if (tempLabel.isHighlighted()) {
				if (myBoard.getP2().getT1().equals(currentPiece)) {
					myBoard.move(myBoard.getP2().getT1(), tempLabel.getCell()
							.getLocation());

					getUpdated();
					if (myBoard.getWinner() == null) {
						return showPlacements(myBoard.getP2().getT1());
					}
				} else {
					myBoard.move(myBoard.getP2().getT2(), tempLabel.getCell()
							.getLocation());

					getUpdated();
					if (myBoard.getWinner() == null) {
						return showPlacements(myBoard.getP2().getT2());
					}
				}
			}
		} else if (tempLabel.getCell().getPlayer() != null
				&& tempLabel.getCell().getPlayer().equals(myBoard.getP1())) {
			if (tempLabel.getCell().getLocation()
					.equals(myBoard.getP1().getT1().getLocation())) {
				getUpdated();
				currentPiece = tempLabel.getCell().getPlayer().getT1();
				return lightMoves(myBoard.getP1().getT1());
			} else {
				getUpdated();
				currentPiece = tempLabel.getCell().getPlayer().getT2();
				return lightMoves(myBoard.getP1().getT2());
			}
		}
		return null;
	}

	public void changeStyle(String string) {
		this.style = string;

	}

}
