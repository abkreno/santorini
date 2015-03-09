package eg.edu.guc.santorini.players;

import eg.edu.guc.santorini.tiles.Cube;
import eg.edu.guc.santorini.tiles.Piece;
import eg.edu.guc.santorini.tiles.Pyramid;
import eg.edu.guc.santorini.utilities.Location;

public class Player {
	private static final Location INITIAL_LOCATION = new Location(0, 0);

	private String name;
	private Piece t1;
	private Piece t2;
	private int type;

	public Player(String name, int type) {
		this.name = name;
		this.type = type;
		if (type == 1) {
			t1 = new Cube(INITIAL_LOCATION);
			t2 = new Cube(INITIAL_LOCATION);
		}

		else if (type == 2) {
			t1 = new Pyramid(INITIAL_LOCATION);
			t2 = new Pyramid(INITIAL_LOCATION);
		}
	}

	// Getters
	public String getName() {
		return name;
	}

	public Piece getT1() {
		return t1;
	}

	public Piece getT2() {
		return t2;
	}

	public int getType() {
		return type;
	}

	// Setters
	public void setName(String name) {
		this.name = name;
	}

	public void setT1(Piece t1) {
		this.t1 = t1;
	}

	public void setT2(Piece t2) {
		this.t2 = t2;
	}

}
