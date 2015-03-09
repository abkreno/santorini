package eg.edu.guc.santorini.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

@SuppressWarnings("serial")
public class StartWindow extends JFrame implements ActionListener {
	private JButton startButton, p1Pyramid, p1Cube, p2Pyramid, p2Cube,
			styleButton, aiButton;
	private JTextField player1Name;
	private JTextField player2Name;
	private int p1Type = 1;
	private int p2Type = 2;
	private String p1Name = "Player1";
	private String p2Name = "Player2";
	private String AiName = "CPU";
	private String style = "collection1/";
	private boolean AI = false;

	private JPanel player1SelectPanel, player2SelectPanel, donePanel;

	public StartWindow() {
		super("New Game");
		setSize(450, 600);
		setLocation(450, 40);
		setResizable(false);
		this.setIconImage(new ImageIcon("res/santoriniIco.png").getImage());
		Container content = getContentPane();
		content.setBackground(Color.black);
		content.setLayout(new GridLayout(2, 1, 1, 1));
		createPanels();
		createButtons();
		Menu menu = new Menu(this);
		this.setJMenuBar(menu);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setVisible(true);
	}

	public void createPanels() {

		JPanel selectionPanel = new JPanel(new GridLayout(1, 2, 1, 1));
		add(selectionPanel);

		player1SelectPanel = new JPanel(new GridLayout(4, 1, 1, 1));
		player1SelectPanel.setBackground(Color.LIGHT_GRAY);
		selectionPanel.add(player1SelectPanel);

		player2SelectPanel = new JPanel(new GridLayout(4, 1, 1, 1));
		player2SelectPanel.setBackground(Color.lightGray);
		selectionPanel.add(player2SelectPanel);

		donePanel = new JPanel(new GridLayout(5, 1, 1, 1));
		donePanel.setBackground(Color.black);
		add(donePanel, BorderLayout.SOUTH);

	}

	public void createButtons() {

		JLabel player1 = new JLabel("PLAYER 1");
		player1.setHorizontalAlignment(JLabel.CENTER);
		player1.setFont(new Font("Lucida Handwriting", Font.BOLD, 14));
		player1.setForeground(Color.BLACK);
		player1SelectPanel.add(player1);

		createP1SelectPanel();

		JLabel player2 = new JLabel("PLAYER 2");
		player2.setHorizontalAlignment(JLabel.CENTER);
		player2.setFont(new Font("Lucida Handwriting", Font.BOLD, 14));
		player2.setForeground(Color.WHITE);
		player2SelectPanel.add(player2);

		createP2SelectPanel();

		createStartButton();
		createLoadButton();
		createAIButton();
		createStyleButton();
		createExitButton();
	}

	/**
	 * Creating Player 1 Selection Panel
	 */
	private void createP1SelectPanel() {
		JPanel p1NamePanel = new JPanel(new GridLayout(1, 2));
		createP1CubeSelect();
		createP1PyramidSelect();
		p1NamePanel.setBackground(Color.GRAY);
		player1SelectPanel.add(p1NamePanel);
		player1Name = new JTextField();
		player1Name.setHorizontalAlignment(JTextField.CENTER);
		player1Name.setText("Player1");
		p1NamePanel.add(player1Name);

	}

	/**
	 * Creating Player 2 Selection Panel
	 */
	private void createP2SelectPanel() {
		createP2CubeSelect();
		createP2PyramidSelect();
		JPanel p2NamePanel = new JPanel(new GridLayout(1, 2));
		p2NamePanel.setBackground(Color.GRAY);
		player2SelectPanel.add(p2NamePanel);
		player2Name = new JTextField();
		// JLabel nameIndicator2 = new JLabel(" Name : ");
		// nameIndicator2.setFont(new Font("Consolas", Font.PLAIN, 18));
		// nameIndicator2.setForeground(Color.BLACK);
		// p2NamePanel.add(nameIndicator2);
		player2Name.setHorizontalAlignment(JTextField.CENTER);
		player2Name.setText("Player2");
		p2NamePanel.add(player2Name);

	}

	/**
	 * Creating Start button to Start the game
	 */
	private void createStartButton() {
		ImageIcon start = new ImageIcon("res/Start.png");
		startButton = new JButton(start);
		startButton.setEnabled(true);
		startButton.setActionCommand("Start");
		donePanel.add(startButton, BorderLayout.CENTER);
		startButton.setOpaque(false);
		startButton.setFocusPainted(false);
		startButton.setBackground(Color.gray);
		startButton.setBorder(BorderFactory.createEmptyBorder());
		startButton.addActionListener(this);
	}

	/**
	 * Creating Start button to Start the game
	 */
	private void createLoadButton() {
		ImageIcon load = new ImageIcon("res/Load.png");
		JButton loadButton = new JButton(load);
		loadButton.setActionCommand("Load");
		donePanel.add(loadButton, BorderLayout.CENTER);
		loadButton.setOpaque(false);
		loadButton.setFocusPainted(false);
		loadButton.setBackground(Color.gray);
		loadButton.setBorder(BorderFactory.createEmptyBorder());
		loadButton.addActionListener(this);
	}

	/**
	 * Creating Start button to Change to AI
	 */
	private void createAIButton() {
		ImageIcon ai = new ImageIcon("res/twoPlayers.png");
		aiButton = new JButton(ai);
		aiButton.setActionCommand("AI");
		donePanel.add(aiButton, BorderLayout.CENTER);
		aiButton.setOpaque(false);
		aiButton.setFocusPainted(false);
		aiButton.setBackground(Color.gray);
		aiButton.setBorder(BorderFactory.createEmptyBorder());
		aiButton.addActionListener(this);
	}

	/**
	 * Creating Style button to Change the Style
	 */
	private void createStyleButton() {
		String style = this.style;
		ImageIcon styleImg = new ImageIcon("res/" + style + "Style2.png");
		styleButton = new JButton(styleImg);
		styleButton.setActionCommand("Style");
		donePanel.add(styleButton, BorderLayout.CENTER);
		styleButton.setOpaque(false);
		styleButton.setFocusPainted(false);
		styleButton.setBackground(Color.gray);
		styleButton.setBorder(BorderFactory.createEmptyBorder());
		styleButton.addActionListener(this);
	}

	/**
	 * Creating Exit button to End the Program
	 */
	private void createExitButton() {
		ImageIcon ex = new ImageIcon("res/Exit.png");
		JButton exit = new JButton(ex);
		exit.setActionCommand("Exit");
		donePanel.add(exit, BorderLayout.CENTER);
		exit.setOpaque(false);
		exit.setFocusPainted(false);
		exit.setBackground(Color.gray);
		exit.setBorder(BorderFactory.createEmptyBorder());
		exit.addActionListener(this);
	}

	/**
	 * Create button to select Pyramid for P2
	 */
	private void createP2PyramidSelect() {
		ImageIcon icon4 = new ImageIcon("res/" + style + "p2PyramidChecked.png");
		p2Pyramid = new JButton(icon4);
		p2Pyramid.setActionCommand("p2Pyramid");
		player2SelectPanel.add(p2Pyramid);
		p2Pyramid.setOpaque(false);
		p2Pyramid.setFocusPainted(false);
		p2Pyramid.setBorder(BorderFactory.createEmptyBorder());
		p2Pyramid.setBackground(new Color(0, 0, 0, 0));
		p2Pyramid.addActionListener(this);
	}

	/**
	 * Create button to select Cube for P2
	 */
	private void createP2CubeSelect() {
		ImageIcon icon3 = new ImageIcon("res/" + style + "p2Cube.png");
		p2Cube = new JButton(icon3);
		p2Cube.setActionCommand("p2Cube");
		player2SelectPanel.add(p2Cube);
		p2Cube.setOpaque(false);
		p2Cube.setFocusPainted(false);
		p2Cube.setBorder(BorderFactory.createEmptyBorder());
		p2Cube.setBackground(new Color(0, 0, 0, 0));
		p2Cube.addActionListener(this);
	}

	/**
	 * Create button to select Pyramid for P1
	 */
	private void createP1PyramidSelect() {
		ImageIcon icon2 = new ImageIcon("res/" + style + "p1Pyramid.png");
		p1Pyramid = new JButton(icon2);
		p1Pyramid.setActionCommand("p1Pyramid");
		player1SelectPanel.add(p1Pyramid);
		p1Pyramid.setOpaque(false);
		p1Pyramid.setFocusPainted(false);
		p1Pyramid.setBorder(BorderFactory.createEmptyBorder());
		p1Pyramid.setBackground(new Color(0, 0, 0, 0));
		p1Pyramid.addActionListener(this);
	}

	/**
	 * Create button to select Cube for P1
	 */
	private void createP1CubeSelect() {
		ImageIcon icon = new ImageIcon("res/" + style + "p1CubeChecked.png");
		p1Cube = new JButton(icon);
		p1Cube.setActionCommand("p1Cube");
		player1SelectPanel.add(p1Cube);
		p1Cube.setOpaque(false);
		p1Cube.setFocusPainted(false);
		p1Cube.setBorder(BorderFactory.createEmptyBorder());
		p1Cube.setBackground(new Color(0, 0, 0, 0));
		p1Cube.addActionListener(this);
	}

	// getters
	public int getP1Type() {
		return p1Type;
	}

	public int getP2Type() {
		return p2Type;
	}

	public String getP1Name() {
		return this.p1Name;
	}

	public String getP2Name() {
		return this.p2Name;
	}

	public String getAIName() {
		this.AiName = randomAIName();
		return this.AiName;
	}

	// setters
	public void setP1Type(int p1Type) {
		this.p1Type = p1Type;
	}

	public void setP2Type(int p2Type) {
		this.p2Type = p2Type;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getActionCommand().equals("p1Cube")) {
			p1Cube.setIcon(new ImageIcon("res/" + style + "p1CubeChecked.png"));
			p1Pyramid.setIcon(new ImageIcon("res/" + style + "p1Pyramid.png"));
			setP1Type(1);
		} else if (e.getActionCommand().equals("p2Cube")) {
			p2Cube.setIcon(new ImageIcon("res/" + style + "p2CubeChecked.png"));
			p2Pyramid.setIcon(new ImageIcon("res/" + style + "p2Pyramid.png"));
			setP2Type(1);
		} else if (e.getActionCommand().equals("p1Pyramid")) {
			p1Cube.setIcon(new ImageIcon("res/" + style + "p1Cube.png"));
			p1Pyramid.setIcon(new ImageIcon("res/" + style
					+ "p1PyramidChecked.png"));
			setP1Type(2);
		} else if (e.getActionCommand().equals("p2Pyramid")) {
			p2Cube.setIcon(new ImageIcon("res/" + style + "p2Cube.png"));
			p2Pyramid.setIcon(new ImageIcon("res/" + style
					+ "p2PyramidChecked.png"));
			setP2Type(2);
		} else if (e.getActionCommand().equals("Exit")) {
			System.exit(0);
		} else if (e.getActionCommand().equals("Start")) {
			p1Name = player1Name.getText();
			p2Name = player2Name.getText();
			if (p1Name == null || p1Name.equals("")) {
				p1Name = "Player1";
			} else if (p2Name == null || p2Name.equals("")) {
				p2Name = "Player2";
			}
			Window w;
			if (AI) {
				w = new Window(getP1Type(), getP2Type() + 10, getP1Name(),
						getAIName());
			} else {
				w = new Window(getP1Type(), getP2Type(), getP1Name(),
						getP2Name());
			}
			w.changeStyle(style);
			w.setStyle(style);
			this.setVisible(false);
		} else if (e.getActionCommand().equals("Load")) {
			new LoadWindow();
			this.setVisible(false);
		} else if (e.getActionCommand().equals("Style")) {
			if (style.equals("collection1/")) {
				changeStyle("collection2/");
			} else if (style.equals("collection2/")) {
				changeStyle("collection3/");
			} else {
				changeStyle("collection1/");
			}
		} else if (e.getActionCommand().equals("AI")) {
			changeOpponent();
		}
	}

	@SuppressWarnings("deprecation")
	public void changeOpponent() {
		if (!AI) {
			player2Name.setText(getAIName());
			player2Name.disable();
			aiButton.setIcon(new ImageIcon("res/vsComputer.png"));
			AI = true;
		} else {
			player2Name.setText(p2Name);
			player2Name.enable();
			aiButton.setIcon(new ImageIcon("res/twoPlayers.png"));
			AI = false;
		}
	}

	public void changeStyle(String string) {
		this.style = string;
		this.styleButton.setIcon(new ImageIcon("res/" + style + "Style2.png"));
		if (getP1Type() == 1) {
			this.p1Cube.setIcon(new ImageIcon("res/" + style
					+ "p1CubeChecked.png"));
			this.p1Pyramid.setIcon(new ImageIcon("res/" + style
					+ "p1Pyramid.png"));
		} else {
			this.p1Cube.setIcon(new ImageIcon("res/" + style + "p1Cube.png"));
			this.p1Pyramid.setIcon(new ImageIcon("res/" + style
					+ "p1PyramidChecked.png"));
		}
		if (getP2Type() == 1) {
			this.p2Cube.setIcon(new ImageIcon("res/" + style
					+ "p2CubeChecked.png"));
			this.p2Pyramid.setIcon(new ImageIcon("res/" + style
					+ "p2Pyramid.png"));
		} else {
			this.p2Cube.setIcon(new ImageIcon("res/" + style + "p2Cube.png"));
			this.p2Pyramid.setIcon(new ImageIcon("res/" + style
					+ "p2PyramidChecked.png"));
		}
	}

	public String getStyle() {
		return style;
	}

	public void setStyle(String style) {
		this.style = style;
	}

	public String randomAIName() {
		String[] names = { "AbkrenoBOT", "KadyBOT", "Sale7BOT", "ConanBOT",
				"METianBOT" };
		int r = (int) (4.0 * Math.random());
		return names[r];

	}

}
