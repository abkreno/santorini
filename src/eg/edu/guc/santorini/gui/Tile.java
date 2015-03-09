package eg.edu.guc.santorini.gui;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JLabel;

import eg.edu.guc.santorini.Cell;

@SuppressWarnings("serial")
public class Tile extends JLabel {

	private int layer = 0;
	private boolean highligthed;
	private Cell cell;
	private String path = "0";

	public Tile() {
		super();
	}

	public Tile(Icon ico) {
		super(ico);
	}

	public void setLayer(int layer) {
		this.layer = layer;
	}

	public void setCell(Cell cell) {
		this.cell = cell;
	}

	public void setPath(String p) {
		this.path = p;
	}

	public int getLayer() {
		return layer;
	}

	public Cell getCell() {
		return this.cell;
	}

	public String getPath() {
		return this.path;
	}

	public boolean isHighlighted() {
		return highligthed;
	}

	/**
	 * 
	 * Changing the image of the {@code Tile} and highlighting it if this is the
	 * player's turn
	 */
	public void changeImage(String string) {
		if (string.contains("A")) {
			highligthed = true;
		} else {
			highligthed = false;
		}
		ImageIcon icon = new ImageIcon("res/" + string + ".png");
		this.setPath(string);
		this.setIcon(icon);
	}
}
