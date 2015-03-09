package eg.edu.guc.santorini.tiles;

import java.util.ArrayList;
import eg.edu.guc.santorini.utilities.Location;

public interface PieceInterface {
	ArrayList<Location> possibleMoves();

	ArrayList<Location> possiblePlacements();
}