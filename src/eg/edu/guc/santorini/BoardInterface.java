package eg.edu.guc.santorini;

import eg.edu.guc.santorini.exceptions.InvalidMoveException;
import eg.edu.guc.santorini.exceptions.InvalidPlacementException;
import eg.edu.guc.santorini.players.Player;
import eg.edu.guc.santorini.tiles.Piece;
import eg.edu.guc.santorini.utilities.Location;

public interface BoardInterface {
	int SIDE = 5;

	void move(Piece piece, Location newLocation) throws InvalidMoveException;

	void place(Piece piece, Location newLocation)
			throws InvalidPlacementException;

	boolean isGameOver();

	boolean isWinner(Player player);

	boolean hasNoMoves(Player player);

	Player getWinner();

	boolean canMove(Piece piece, Location location);

	boolean canPlace(Piece piece, Location location);

	Player getTurn();

	String[][] display();
}