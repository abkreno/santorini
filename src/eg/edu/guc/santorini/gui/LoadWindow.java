package eg.edu.guc.santorini.gui;

import java.awt.Color;
import java.awt.Container;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Scanner;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import eg.edu.guc.santorini.Board;
import eg.edu.guc.santorini.adapters.BoardAdapter;
import eg.edu.guc.santorini.players.Player;

@SuppressWarnings("serial")
public class LoadWindow extends JFrame implements ActionListener, MouseListener {

	private JPanel buttonPanel;
	private String file;
	private JLabel tempLabel;
	private ArrayList<String> str;

	public LoadWindow() {
		super("Load");

		try {
			FileReader f = new FileReader("saveFile.txt");
			str = new ArrayList<String>();
			Scanner sc = new Scanner(f);
			while (sc.hasNext()) {
				str.add(sc.next());
			}
			PrintWriter output = new PrintWriter("saveFile.txt");
			for (int i = 0; i < str.size(); i++) {
				output.println(str.get(i));
			}
			sc.close();
			output.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		setSize(190, 450);
		setLocation(600, 100);
		Container content = getContentPane();
		content.setBackground(Color.BLACK);
		content.setLayout(new GridLayout(1, 1, 1, 1));
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setResizable(false);
		createPanels();
		createDoneButtons();
		setVisible(true);
	}

	public void createPanels() {
		int numOfLoaded = 0;
		if (str.size() - 10 > 0) {
			numOfLoaded = str.size() - 10;
		}
		buttonPanel = new JPanel(new GridLayout(13 + numOfLoaded, 1, 1, 1));
		buttonPanel.setBackground(Color.BLACK);
		buttonPanel.setVisible(true);
		add(buttonPanel);
		createNewFile();
	}

	public void createNewFile() {
		String s = "";
		if (str.size() == 0) {
			s = "No Game To Load!";
		} else {
			s = "Load Game : ";
		}
		JLabel loadNameLabel = new JLabel(s);
		loadNameLabel.setHorizontalAlignment(JLabel.CENTER);
		loadNameLabel.setFont(new Font("Consolas", Font.BOLD, 14));
		loadNameLabel.setForeground(Color.WHITE);
		buttonPanel.add(loadNameLabel);
		for (int i = 0; i < str.size(); i++) {

			JLabel loaded = new JLabel();
			loaded.setBackground(Color.BLACK);
			loaded.setText(str.get(i));
			loaded.addMouseListener(this);
			loaded.setFont(new Font("Consolas", Font.BOLD, 11));
			loaded.setForeground(Color.WHITE);
			loaded.setHorizontalAlignment(JLabel.CENTER);
			buttonPanel.add(loaded);
		}
		for (int i = 0; i < 10 - str.size(); i++) {
			JLabel empty = new JLabel();
			empty.setBackground(Color.BLACK);
			empty.setText("-");
			empty.addMouseListener(this);
			empty.setFont(new Font("Consolas", Font.BOLD, 14));
			empty.setForeground(Color.WHITE);
			empty.setHorizontalAlignment(JLabel.CENTER);
			buttonPanel.add(empty);
		}

	}

	public void createDoneButtons() {
		ImageIcon loadIcon = new ImageIcon("res/Load2.png");
		JButton load = new JButton(loadIcon);
		load.setOpaque(false);
		load.setFocusPainted(false);
		load.setBackground(Color.BLACK);
		load.setBorder(BorderFactory.createEmptyBorder());
		buttonPanel.add(load);
		load.setVisible(true);
		load.setActionCommand("Load");
		load.addActionListener(this);

		ImageIcon exitIcon = new ImageIcon("res/Back.png");
		JButton exit = new JButton(exitIcon);
		exit.setOpaque(false);
		exit.setFocusPainted(false);
		exit.setBackground(Color.BLACK);
		exit.setBorder(BorderFactory.createEmptyBorder());
		buttonPanel.add(exit);
		exit.setVisible(true);
		exit.setActionCommand("Back");
		exit.addActionListener(this);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getActionCommand().equals("Load") && file != null
				&& (!str.isEmpty())) {
			try {
				loadFile();
			} catch (FileNotFoundException e1) {
				e1.printStackTrace();
			} catch (ClassNotFoundException e1) {
				e1.printStackTrace();
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		} else if (e.getActionCommand().equals("Back")) {
			this.setVisible(false);
			new StartWindow();
		}
	}

	private void loadFile() throws IOException, ClassNotFoundException {
		if (file.equals("-")) {
			return;
		}
		ObjectInputStream obj = new ObjectInputStream(new FileInputStream(file
				+ ".bin"));

		String[][] input = (String[][]) obj.readObject();

		loadData(input);
		obj.close();

	}

	public void loadData(String[][] input) throws IOException {
		String[][] output = new String[5][5];
		for (int i = 0; i < 5; i++) {
			for (int j = 0; j < 5; j++) {
				output[i][j] = input[i][j];
			}
		}
		int p1Type = Integer.parseInt(input[5][0]);
		int p2Type = Integer.parseInt(input[5][1]);
		String p1Name = input[5][2];
		String p2Name = input[5][3];
		String whatNow = input[5][4];
		Player p1 = new Player(p1Name, p1Type);
		Player p2 = new Player(p2Name, p2Type);
		Board loaded = new Board(output, p1, p2);
		if (whatNow.equals("show p1 moves")) {
			loaded.setMoved(false);
			loaded.setP1Turn(true);
		} else if (whatNow.equals("show p2 moves")) {
			loaded.setMoved(false);
			loaded.setP1Turn(false);
		}
		BoardAdapter loadedAdapter = new BoardAdapter(p1, p2, loaded);
		this.setVisible(false);
		new Window(p1Type, p2Type, loadedAdapter);

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

	@Override
	public void mousePressed(MouseEvent e) {
		tempLabel = (JLabel) e.getSource();
		tempLabel.setBorder(BorderFactory.createLoweredSoftBevelBorder());
		file = tempLabel.getText();
	}

	@Override
	public void mouseReleased(MouseEvent arg0) {
		tempLabel.setBorder(BorderFactory.createEmptyBorder());
	}
}
