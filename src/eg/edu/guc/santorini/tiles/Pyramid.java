package eg.edu.guc.santorini.tiles;

import java.util.ArrayList;

import eg.edu.guc.santorini.utilities.Location;

public class Pyramid extends Piece {

	public Pyramid(Location location) {
		super(location);
	}

	// Pyramids moves on diagonals only !
	public ArrayList<Location> possibleMoves() {
		ArrayList<Location> possibleMoves = new ArrayList<Location>();

		int x = this.getLocation().getX();
		int y = this.getLocation().getY();

		Location moveUpRight = new Location(y - 1, x + 1);
		Location moveUpLeft = new Location(y - 1, x - 1);
		Location moveDownLeft = new Location(y + 1, x - 1);
		Location moveDownRight = new Location(y + 1, x + 1);

		if (moveUpRight.onGrid()) {
			possibleMoves.add(moveUpRight);
		}
		if (moveUpLeft.onGrid()) {
			possibleMoves.add(moveUpLeft);
		}
		if (moveDownLeft.onGrid()) {
			possibleMoves.add(moveDownLeft);
		}
		if (moveDownRight.onGrid()) {
			possibleMoves.add(moveDownRight);
		}
		return possibleMoves;

	}

}
