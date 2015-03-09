package eg.edu.guc.santorini.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import eg.edu.guc.santorini.Board;
import eg.edu.guc.santorini.adapters.BoardAdapter;
import eg.edu.guc.santorini.exceptions.InvalidMoveException;
import eg.edu.guc.santorini.exceptions.InvalidPlacementException;
import eg.edu.guc.santorini.players.Player;

@SuppressWarnings("serial")
public class Window extends JFrame implements MouseListener, ActionListener {
	private String p1Name, p2Name;
	private Tile[][] grid;
	private Tile tempLabel;
	private JPanel gridPanel, statusPanel, buttonPanel;
	private JLabel turnLabel;
	private String style = "collection1/";

	private int p1Type, p2Type;
	private BoardAdapter adapter;

	public Window(int p1Type, int p2Type, String p1Name, String p2Name) {
		super("Santorini");
		this.p1Type = p1Type;
		this.p2Type = p2Type % 10;
		this.p1Name = p1Name;
		this.p2Name = p2Name;
		Player p1 = new Player(p1Name, p1Type);
		Player p2 = new Player(p2Name, p2Type % 10);
		adapter = new BoardAdapter(p1, p2);
		if (p2Type > 2) {
			adapter.setAI(true);
		}

		// Window Stuff
		setSize(1000, 700);
		setLocation(200, 10);
		Dimension minimumSize = new Dimension(1000, 700);
		this.setMinimumSize(minimumSize);
		this.setIconImage(new ImageIcon("res/santoriniIco.png").getImage());
		Container content = getContentPane();
		content.setBackground(Color.CYAN);
		content.setLayout(new BorderLayout());

		grid = new Tile[5][5];

		createPanels(5, 5);
		createGrid(5, 5);
		createStatusPanel();
		createButtons();
		Menu menu = new Menu(this);
		this.setJMenuBar(menu);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setVisible(true);
	}

	public Window(int p1Type, int p2Type, BoardAdapter loadedAdabter) {
		super("Santorini");
		this.p1Type = p1Type;
		this.p2Type = p2Type;
		this.p1Name = loadedAdabter.getBoard().getP1().getName();
		this.p2Name = loadedAdabter.getBoard().getP2().getName();
		adapter = loadedAdabter;
		// Window Stuff
		setSize(1000, 700);
		setLocation(200, 10);
		Dimension minimumSize = new Dimension(1000, 700);
		this.setMinimumSize(minimumSize);
		Container content = getContentPane();
		content.setBackground(Color.WHITE);
		content.setLayout(new BorderLayout());

		grid = new Tile[5][5];

		createPanels(5, 5);
		createGrid(5, 5);
		createStatusPanel();
		createButtons();
		Menu menu = new Menu(this);
		this.setJMenuBar(menu);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setVisible(true);
	}

	public void createPanels(int row, int column) {
		// Create Panel for the tiles grid
		gridPanel = new JPanel(new GridLayout(row, column));
		gridPanel.setBackground(Color.white);
		add(gridPanel, BorderLayout.CENTER);

		// Create panel for the status panel
		statusPanel = new JPanel(new GridLayout(1, 1));
		statusPanel.setBackground(Color.black);
		add(statusPanel, BorderLayout.SOUTH);

		// Create panel for the button panel
		buttonPanel = new JPanel(new GridLayout(11, 2));
		buttonPanel.setBackground(Color.white);
		buttonPanel.setBorder(BorderFactory.createLineBorder(Color.black));
		add(buttonPanel, BorderLayout.EAST);
	}

	public void createStatusPanel() {
		JLabel label = new JLabel("Game Status: ");
		label.setFont(new Font("consolas", Font.PLAIN, 18));
		label.setForeground(Color.white);
		label.setHorizontalAlignment(JLabel.CENTER);
		if (adapter.getBoard().whatNow().contains("p1")) {
			turnLabel = new JLabel(p1Name + "\'s Turn");
		} else {
			turnLabel = new JLabel(p2Name + "\'s Turn");
		}
		turnLabel.setFont(new Font("consolas", Font.PLAIN, 18));
		turnLabel.setForeground(Color.white);
		turnLabel.setHorizontalAlignment(JLabel.CENTER);
		statusPanel.add(label);
		statusPanel.add(turnLabel);
	}

	public void createButtons() {
		createHomeButton();
		createSaveButton();
		createRestartButton();
		createStyleButton();
		createOffButton();
	}

	private void createHomeButton() {
		ImageIcon homeIcon = new ImageIcon("res/home.png");
		JButton homeButton = new JButton(homeIcon);
		homeButton.setActionCommand("Home");
		buttonPanel.add(homeButton);
		homeButton.setOpaque(false);
		homeButton.setFocusPainted(false);
		homeButton.setBorder(BorderFactory.createEmptyBorder());
		homeButton.setBackground(new Color(0, 0, 0, 0));
		homeButton.addActionListener(this);
		JLabel gap = new JLabel("");
		buttonPanel.add(gap);
	}

	private void createSaveButton() {
		ImageIcon saveIcon = new ImageIcon("res/Save.png");
		JButton saveButton = new JButton(saveIcon);
		saveButton.setActionCommand("Save");
		buttonPanel.add(saveButton);
		saveButton.setOpaque(false);
		saveButton.setFocusPainted(false);
		saveButton.setBorder(BorderFactory.createEmptyBorder());
		saveButton.setBackground(new Color(0, 0, 0, 0));
		saveButton.addActionListener(this);
		JLabel gap = new JLabel("");
		buttonPanel.add(gap);
	}

	private void createRestartButton() {
		ImageIcon restartIcon = new ImageIcon("res/Restart.png");
		JButton restartButton = new JButton(restartIcon);
		restartButton.setActionCommand("Restart");
		buttonPanel.add(restartButton);
		restartButton.setOpaque(false);
		restartButton.setFocusPainted(false);
		restartButton.setBorder(BorderFactory.createEmptyBorder());
		restartButton.setBackground(new Color(0, 0, 0, 0));
		restartButton.addActionListener(this);
		JLabel gap = new JLabel("");
		buttonPanel.add(gap);
	}

	private void createOffButton() {
		ImageIcon offIcon = new ImageIcon("res/Off.png");
		JButton offButton = new JButton(offIcon);
		offButton.setActionCommand("Exit");
		buttonPanel.add(offButton);
		offButton.setOpaque(false);
		offButton.setFocusPainted(false);
		offButton.setBorder(BorderFactory.createEmptyBorder());
		offButton.setBackground(new Color(0, 0, 0, 0));
		offButton.addActionListener(this);
	}

	public void createStyleButton() {

		ImageIcon styleIcon = new ImageIcon("res/Style.png");
		JButton styleButton = new JButton(styleIcon);
		styleButton.setActionCommand("Style");
		buttonPanel.add(styleButton);
		styleButton.setOpaque(false);
		styleButton.setFocusPainted(false);
		styleButton.setBorder(BorderFactory.createEmptyBorder());
		styleButton.setBackground(new Color(0, 0, 0, 0));
		styleButton.addActionListener(this);

		JLabel gap1 = new JLabel("");
		buttonPanel.add(gap1);
	}

	public void createGrid(int gridRows, int gridColumns) {
		Tile[][] tiles = adapter.getUpdated();
		for (int y = 0; y < 5; y++) {
			for (int x = 0; x < 5; x++) {
				ImageIcon icon = (ImageIcon) tiles[y][x].getIcon();
				grid[y][x] = new Tile(icon);
				grid[y][x].setCell(tiles[y][x].getCell());

				gridPanel.add(grid[y][x]);
				grid[y][x].addMouseListener(this);
			}
		}
	}

	public void updateGrid(Tile[][] tiles) {
		for (int y = 0; y < 5; y++) {
			for (int x = 0; x < 5; x++) {
				grid[y][x].changeImage(tiles[y][x].getPath());
				grid[y][x].setCell(tiles[y][x].getCell());
			}
		}
	}

	public boolean isHighlighted() {
		for (int i = 0; i < 5; i++) {
			for (int j = 0; j < 5; j++) {
				if (grid[i][j].isHighlighted()) {
					return true;
				}
			}
		}
		return false;
	}

	public void save() {
		new SaveWindow(this);
	}

	public void setStyle(String s) {
		this.style = s;
	}

	public void changeStyle(String s) {
		if (s.equals("collection1/")) {
			adapter.changeStyle("collection1/");
			gridPanel.setBackground(Color.WHITE);
			buttonPanel.setBackground(Color.WHITE);
			validate();
			updateGrid(adapter.getUpdated());
		} else if (s.equals("collection2/")) {
			adapter.changeStyle("collection2/");
			gridPanel.setBackground(Color.WHITE);
			buttonPanel.setBackground(Color.WHITE);
			validate();
			updateGrid(adapter.getUpdated());
		} else if (s.equals("collection3/")) {
			adapter.changeStyle("collection3/");
			gridPanel.setBackground(Color.WHITE);
			buttonPanel.setBackground(Color.WHITE);
			validate();
			updateGrid(adapter.getUpdated());
		}
	}

	public int getP1Type() {
		return p1Type;
	}

	public int getP2Type() {
		return p2Type;
	}

	public BoardAdapter getAdabter() {
		return this.adapter;
	}

	@Override
	public void mouseClicked(MouseEvent e) {

	}

	@Override
	public void mouseEntered(MouseEvent e) {

	}

	@Override
	public void mouseExited(MouseEvent e) {

	}

	public void printBoard(Board b) {
		for (int y = 0; y < 5; y++) {
			for (int x = 0; x < 5; x++) {
				if (b.getGrid()[y][x].getPlayer() == null) {
					System.out.print(b.getGrid()[y][x].toString());
				} else if (b.getGrid()[y][x].getPlayer() == b.getP1()) {
					System.out.print(b.getGrid()[y][x].toString() + "1");
				} else {
					System.out.print(b.getGrid()[y][x].toString() + "2");
				}
				System.out.print("  ");
			}
			System.out.println();
		}
		// System.out.println("the turn is for: " + b.getTurn().getName());
		// System.out.println("isP1Turn = " + b.isP1Turn());
		// System.out.println("isMoved = " + b.isMoved());
		// System.out.println("isPlaced = " + b.isPlaced());
	}

	@Override
	public void mousePressed(MouseEvent e) {
		tempLabel = (Tile) e.getSource();
		tempLabel.setBorder(BorderFactory.createLoweredSoftBevelBorder());
		Tile[][] update = new Tile[5][5];

		try {
			update = adapter.getMove(tempLabel);
		} catch (InvalidMoveException e1) {
			e1.printStackTrace();
		} catch (InvalidPlacementException e1) {
			e1.printStackTrace();
		}
		if (update == null) {
			try {
				update = adapter.getPlacement(tempLabel);
				if (!(update == null)) {
					// printBoard(adapter.getBoard());
					updateGrid(adapter.getUpdated2(adapter.getBoard()));
				}
				if (adapter.getBoard().whatNow().contains("p2")
						&& adapter.isAI()) {
					try {
						updateGrid(adapter.getAIMove());
					} catch (InvalidMoveException e1) {
						e1.printStackTrace();
					} catch (InvalidPlacementException e1) {
						e1.printStackTrace();
					}
				}
			} catch (InvalidPlacementException e1) {
				e1.printStackTrace();
			}
		}
		if (update != null) {
			updateGrid(update);
		}
		if (adapter.getBoard().whatNow().equals("p1wins")) {
			turnLabel.setText(p1Name + " Wins");
			updateGrid(adapter.getUpdated());
			new WinWindow(p1Name);
			this.setVisible(false);
		} else if (adapter.getBoard().whatNow().equals("p2wins")) {
			turnLabel.setText(p2Name + " Wins");
			updateGrid(adapter.getUpdated());
			new WinWindow(p2Name);
			this.setVisible(false);
		} else if (adapter.getBoard().whatNow().contains("p1")) {
			turnLabel.setText(p1Name + "\'s Turn");

		} else if (adapter.getBoard().whatNow().contains("p2")) {
			turnLabel.setText(p2Name + "\'s Turn");

		}
	}

	@Override
	public void mouseReleased(MouseEvent arg0) {
		tempLabel.setBorder(BorderFactory.createEmptyBorder());
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getActionCommand().equals("Home")) {
			this.setVisible(false);
			new StartWindow();
		} else if (e.getActionCommand().equals("Save")) {
			if (!(adapter.getBoard().whatNow().equals("show p1 placements"))
					&& !(adapter.getBoard().whatNow()
							.equals("show p2 placements"))) {
				save();
			}
		} else if (e.getActionCommand().equals("Restart")) {
			this.setVisible(false);
			Window w = new Window(this.p1Type, this.p2Type, this.p1Name,
					this.p2Name);
			if (adapter.isAI())
				w.adapter.setAI(true);
		} else if (e.getActionCommand().equals("Style")) {
			if (!isHighlighted()) {
				if (style.equals("collection1/")) {
					this.style = "collection2/";
					changeStyle(style);
				} else if (style.equals("collection2/")) {
					this.style = "collection3/";
					changeStyle(style);
				} else if (style.equals("collection3/")) {
					this.style = "collection1/";
					changeStyle(style);
				}
			}
		} else if (e.getActionCommand().equals("Exit")) {
			System.exit(0);
		}

	}

	public String getP1Name() {
		return p1Name;
	}

	public void setP1Name(String p1Name) {
		this.p1Name = p1Name;
	}

	public String getP2Name() {
		return p2Name;
	}

	public void setP2Name(String p2Name) {
		this.p2Name = p2Name;
	}

	public Tile[][] getGrid() {
		return grid;
	}

	public void setGrid(Tile[][] grid) {
		this.grid = grid;
	}

	public BoardAdapter getAdapter() {
		return adapter;
	}

	public void setAdapter(BoardAdapter adapter) {
		this.adapter = adapter;
	}

	public void setP1Type(int p1Type) {
		this.p1Type = p1Type;
	}

	public void setP2Type(int p2Type) {
		this.p2Type = p2Type;
	}

	public String getStyle() {
		return style;
	}

}
