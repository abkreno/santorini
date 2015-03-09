package eg.edu.guc.santorini.tiles;

import java.util.ArrayList;

import eg.edu.guc.santorini.utilities.Location;

/*
 * Note that this class implements PieceInterface so there's a possibleMoves
 * method for each of It's subclasses
 */

public abstract class Piece implements PieceInterface {
	private Location location;

	public Piece(Location location) {
		this.location = location;
	}

	// Getters

	public Location getLocation() {
		return this.location;
	}

	// Setters

	public void setLocation(Location newLocation) {
		this.location = newLocation;
	}

	public ArrayList<Location> possiblePlacements() {

		ArrayList<Location> possiblePlacements = new ArrayList<Location>();

		int x = this.getLocation().getX();
		int y = this.getLocation().getY();

		for (int i = y - 1; i <= y + 1; i++) {
			for (int j = x - 1; j <= x + 1; j++) {
				Location tryMove = new Location(i, j);
				if (!(i == y && j == x) && tryMove.onGrid()) {
					possiblePlacements.add(tryMove);
				}
			}
		}

		return possiblePlacements;

	}

	

}
