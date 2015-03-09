package eg.edu.guc.santorini;

import java.util.ArrayList;

import eg.edu.guc.santorini.exceptions.InvalidMoveException;
import eg.edu.guc.santorini.exceptions.InvalidPlacementException;
import eg.edu.guc.santorini.players.Player;
import eg.edu.guc.santorini.tiles.Piece;
import eg.edu.guc.santorini.utilities.Location;

public class Board implements BoardInterface {

	private static final Location INITIAL_LOCATION_P1_T1 = new Location(0, 0);
	private static final Location INITIAL_LOCATION_P1_T2 = new Location(4, 1);

	private static final Location INITIAL_LOCATION_P2_T1 = new Location(0, 3);
	private static final Location INITIAL_LOCATION_P2_T2 = new Location(4, 4);

	private Cell[][] grid = new Cell[SIDE][SIDE];
	private Player p1, p2;
	private boolean moved = false;
	private boolean placed = true;
	private boolean p1Turn = true;

	/**
	 * Creates a new Board setting all levels to zero and setting players pieces
	 * in initial locations
	 * 
	 */
	public Board(Player p1, Player p2) {
		this.p1 = p1;
		this.p2 = p2;

		p1.getT1().setLocation(INITIAL_LOCATION_P1_T1);
		p1.getT2().setLocation(INITIAL_LOCATION_P1_T2);
		p2.getT1().setLocation(INITIAL_LOCATION_P2_T1);
		p2.getT2().setLocation(INITIAL_LOCATION_P2_T2);

		for (int y = 0; y < 5; y++) {
			for (int x = 0; x < 5; x++) {
				grid[y][x] = new Cell(0);
				grid[y][x].setLocation(new Location(y, x));
			}
		}

		grid[0][0].setPlayer(p1);
		grid[4][1].setPlayer(p1);
		grid[0][3].setPlayer(p2);
		grid[4][4].setPlayer(p2);

	}

	public Board(String[][] display, Player p1, Player p2) {
		iniziliazePlayers(p1, p2);
		int p1T1x = -1;
		int p1T1y = -1;
		int p1T2x = -1;
		int p1T2y = -1;
		int p2T1x = -1;
		int p2T1y = -1;
		int p2T2x = -1;
		int p2T2y = -1;
		Boolean p1T1Done = false, p2T1Done = false;
		for (int y = 0; y < 5; y++) {
			for (int x = 0; x < 5; x++) {
				grid[y][x] = new Cell(Integer.parseInt(display[y][x].charAt(0)
						+ ""));
				grid[y][x].setLocation(new Location(y, x));
				if (display[y][x].length() > 1) {
					if (display[y][x].charAt(2) == '1') {
						if (!p1T1Done) {
							p1.getT1().setLocation(new Location(y, x));
							p1T1x = x;
							p1T1y = y;
							p1T1Done = true;
						} else {
							p1.getT2().setLocation(new Location(y, x));
							p1T2x = x;
							p1T2y = y;
						}
					} else if (display[y][x].length() > 1) {
						if (display[y][x].charAt(2) == '2') {
							if (!p2T1Done) {
								p2.getT1().setLocation(new Location(y, x));
								p2T1x = x;
								p2T1y = y;
								p2T1Done = true;
							} else {
								p2.getT2().setLocation(new Location(y, x));
								p2T2x = x;
								p2T2y = y;
							}
						}
					}
				}
			}
		}
		grid[p1T1y][p1T1x].setPlayer(p1);
		grid[p1T2y][p1T2x].setPlayer(p1);
		grid[p2T1y][p2T1x].setPlayer(p2);
		grid[p2T2y][p2T2x].setPlayer(p2);
	}

	public void iniziliazePlayers(Player p1, Player p2) {
		this.p1 = p1;
		this.p2 = p2;
	}

	// Getters

	public Player getP1() {
		return this.p1;
	}

	public Player getP2() {
		return this.p2;
	}

	public boolean isMoved() {
		return moved;
	}

	public boolean isPlaced() {
		return placed;
	}

	public Cell[][] getGrid() {
		return this.grid;
	}

	public boolean isP1Turn() {
		return p1Turn;
	}

	// Setters

	public void setGrid(Cell[][] grid) {
		this.grid = grid;
	}

	public void setP1(Player p1) {
		this.p1 = p1;
	}

	public void setP2(Player p2) {
		this.p2 = p2;
	}

	public void setMoved(boolean moved) {
		this.moved = moved;
	}

	public void setPlaced(boolean placed) {
		this.placed = placed;
	}

	public void setP1Turn(boolean p1Turn) {
		this.p1Turn = p1Turn;
	}

	/**
	 * getting the current turn's player
	 */
	public Player getTurn() {
		if (p1Turn) {
			return p1;
		} else {
			return p2;
		}
	}

	public void move(Piece piece, Location newLocation)
			throws InvalidMoveException {
		int currentX = piece.getLocation().getX();
		int currentY = piece.getLocation().getY();
		Cell currentCell = grid[currentY][currentX];

		Player cellPlayer = currentCell.getPlayer();
		if (isGameOver() || (p1Turn && cellPlayer == p2)
				|| ((!(p1Turn) && cellPlayer == p1))
				|| !(canMove(piece, newLocation)) || isMoved() || !isPlaced()) {
			throw new InvalidMoveException();
		} else {

			int targetX = newLocation.getX();
			int targetY = newLocation.getY();

			piece.setLocation(newLocation);
			grid[targetY][targetX].setPlayer(cellPlayer);
			grid[currentY][currentX].setPlayer(null);
			this.moved = true;
			this.placed = false;
			this.p1Turn = !this.p1Turn;
		}

	}

	public void place(Piece piece, Location newLocation)
			throws InvalidPlacementException {
		if (isGameOver() || !canPlace(piece, newLocation) || !isMoved()
				|| isPlaced()) {
			throw new InvalidPlacementException();
		} else {
			int targetX = newLocation.getX();
			int targetY = newLocation.getY();
			grid[targetY][targetX].addLevel();
			this.moved = false;
			this.placed = true;
		}

	}

	public boolean canMove(Piece piece, Location location) {
		if (location == null || piece == null) {
			return false;
		}

		Cell currentCell = grid[piece.getLocation().getY()][piece.getLocation()
				.getX()];
		int currentLevel = currentCell.getLevel();

		Cell targetCell = grid[location.getY()][location.getX()];
		int targetLevel = targetCell.getLevel();

		if (!(piece.possibleMoves().contains(location))
				|| targetCell.isDestroyed()
				|| ((targetLevel > currentLevel) && (targetLevel - currentLevel > 1))
				|| targetCell.getPlayer() != null) {
			return false;
		}
		return true;

	}

	public boolean canPlace(Piece piece, Location location) {
		if (location == null || piece == null) {
			return false;
		}

		Cell targetCell = grid[location.getY()][location.getX()];

		if (!(piece.possiblePlacements().contains(location))
				|| targetCell.isDestroyed() || targetCell.getPlayer() != null) {
			return false;
		}
		return true;
	}

	/*
	 * Notice the | here not || because we want to remove the element always
	 * from the arraylist , we can do it also using the following code :
	 * 
	 * while {(!piece1Moves.isEmpty()) { hasMoves = hasMoves ||
	 * canMove(player.getT1(), piece1Moves.get(0)); piece1Moves.remove(0); }
	 */
	public boolean hasNoMoves(Player player) {
		ArrayList<Location> piece1Moves = new ArrayList<Location>();
		piece1Moves.addAll(player.getT1().possibleMoves());
		ArrayList<Location> piece2Moves = new ArrayList<Location>();
		piece2Moves.addAll(player.getT2().possibleMoves());
		boolean hasMoves = false;

		if (piece1Moves.isEmpty() && piece2Moves.isEmpty()) {
			return true;
		}

		while (!piece1Moves.isEmpty()) {
			hasMoves = hasMoves
					| canMove(player.getT1(), piece1Moves.remove(0));
		}

		while (!piece2Moves.isEmpty()) {
			hasMoves = hasMoves
					| canMove(player.getT2(), piece2Moves.remove(0));
		}

		return !hasMoves;
	}

	public boolean isWinner(Player player) {
		int xT1 = player.getT1().getLocation().getX();
		int yT1 = player.getT1().getLocation().getY();

		int xT2 = player.getT2().getLocation().getX();
		int yT2 = player.getT2().getLocation().getY();

		if (grid[yT1][xT1].getLevel() == 3 || grid[yT2][xT2].getLevel() == 3
				|| ((player == p1) && hasNoMoves(p2) && getTurn() == p2)
				|| ((player == p2) && hasNoMoves(p1) && getTurn() == p1)) {
			return true;
		}

		return false;

	}

	public boolean isGameOver() {
		return isWinner(p1) || isWinner(p2);
	}

	public Player getWinner() {
		if (isWinner(p1)) {
			return p1;
		} else if (isWinner(p2)) {
			return p2;
		} else {
			return null;
		}
	}

	public String whatNow() {
		if (isWinner(p1)) {
			return "p1wins";
		}
		if (isWinner(p2)) {
			return "p2wins";
		}
		if (!moved) {
			if (p1Turn) {
				return "show p1 moves";
			}
			if (!p1Turn) {
				return "show p2 moves";
			}
		} else if (moved) {
			if (!p1Turn) {
				return "show p1 placements";
			}
			if (p1Turn) {
				return "show p2 placements";
			}
		}
		return null;
	}

	public String[][] display() {
		String[][] res = new String[SIDE][SIDE];
		for (int y = 0; y < 5; y++) {
			for (int x = 0; x < 5; x++) {
				if (grid[y][x].getPlayer() == null) {
					res[y][x] = grid[y][x].toString();
				} else if (grid[y][x].getPlayer() == p1) {
					res[y][x] = grid[y][x].toString() + "1";
				} else {
					res[y][x] = grid[y][x].toString() + "2";
				}
			}
		}

		return res;
	}
}
