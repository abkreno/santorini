package eg.edu.guc.santorini.tiles;

import java.util.ArrayList;

import eg.edu.guc.santorini.utilities.Location;

public class Cube extends Piece {

	public Cube(Location location) {
		super(location);
	}

	// Cubes can move horizontally and vertically
	public ArrayList<Location> possibleMoves() {
		ArrayList<Location> possibleMoves = new ArrayList<Location>();

		int x = this.getLocation().getX();
		int y = this.getLocation().getY();

		Location moveUp = new Location(y - 1, x);
		Location moveDown = new Location(y + 1, x);
		Location moveRight = new Location(y, x + 1);
		Location moveLeft = new Location(y, x - 1);

		if (moveUp.onGrid()) {
			possibleMoves.add(moveUp);
		}

		if (moveDown.onGrid()) {
			possibleMoves.add(moveDown);
		}

		if (moveLeft.onGrid()) {
			possibleMoves.add(moveLeft);
		}

		if (moveRight.onGrid()) {
			possibleMoves.add(moveRight);
		}

		return possibleMoves;

	}
}
