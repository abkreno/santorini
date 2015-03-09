package eg.edu.guc.santorini.gui;

import java.awt.Color;
import java.awt.Container;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Scanner;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import eg.edu.guc.santorini.adapters.BoardAdapter;

@SuppressWarnings("serial")
public class SaveWindow extends JFrame implements ActionListener, MouseListener {

	private JPanel buttonPanel;
	private JLabel tempLabel;
	private BoardAdapter adapter;
	private String file;
	private JTextField fileName;
	private ArrayList<String> savedFiles;

	public SaveWindow(Window w) {
		super("Save");

		try {
			FileReader f = new FileReader("saveFile.txt");
			savedFiles = new ArrayList<String>();
			Scanner sc = new Scanner(f);
			while (sc.hasNext()) {
				savedFiles.add(sc.next());
			}
			PrintWriter output = new PrintWriter("saveFile.txt");
			for (int i = 0; i < savedFiles.size(); i++) {
				output.println(savedFiles.get(i));
			}
			sc.close();
			output.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		if (!(w == null)) {
			this.adapter = w.getAdabter();
		}
		setSize(390, 400);
		setLocation(500, 100);
		Container content = getContentPane();
		content.setBackground(Color.BLACK);
		content.setLayout(new GridLayout(1, 1, 1, 1));
		setResizable(false);
		createPanels();
		setVisible(true);
	}

	public void createPanels() {
		int numOfLoaded = 0;
		if (savedFiles.size() - 10 > 0) {
			numOfLoaded = savedFiles.size() - 10;
		}
		buttonPanel = new JPanel(new GridLayout(14 + numOfLoaded, 1, 1, 1));
		buttonPanel.setBackground(Color.BLACK);
		buttonPanel.setVisible(true);
		add(buttonPanel);
		createButtons();
		createNewFilePanel();
		createDoneButton();
	}

	public void createButtons() {
		JLabel topTitle = new JLabel();
		topTitle.setBackground(Color.BLACK);
		topTitle.setHorizontalAlignment(JLabel.CENTER);
		topTitle.setText("Saved Games");
		topTitle.setFont(new Font("Lucida Handwriting", Font.BOLD, 14));
		topTitle.setForeground(Color.WHITE);
		buttonPanel.add(topTitle);

		JLabel blank = new JLabel();
		blank.setBackground(Color.BLACK);
		buttonPanel.add(blank);
		for (int i = 0; i < savedFiles.size(); i++) {
			JLabel loaded = new JLabel();
			loaded.setBackground(Color.BLACK);
			loaded.setText(savedFiles.get(i));
			loaded.setFont(new Font("Lucida Handwriting", Font.BOLD, 11));
			loaded.setForeground(Color.WHITE);
			loaded.addMouseListener(this);
			loaded.setHorizontalAlignment(JLabel.CENTER);
			buttonPanel.add(loaded);
		}
		for (int i = 0; i < 10 - savedFiles.size(); i++) {
			JLabel empty = new JLabel();
			empty.setBackground(Color.BLACK);
			empty.setText("-");
			empty.setFont(new Font("Lucida Handwriting", Font.BOLD, 14));
			empty.setForeground(Color.WHITE);
			empty.addMouseListener(this);
			empty.setHorizontalAlignment(JLabel.CENTER);
			buttonPanel.add(empty);
		}
	}

	public void createNewFilePanel() {
		JPanel file = new JPanel(new GridLayout(1, 3));
		file.setBackground(Color.BLACK);
		JLabel blank2 = new JLabel();
		file.add(blank2);
		buttonPanel.add(file);
		fileName = new JTextField();
		JLabel blank = new JLabel();
		file.add(fileName);
		file.add(blank);
		fileName.setHorizontalAlignment(JTextField.CENTER);
		fileName.setText("");
	}

	public void createDoneButton() {
		JPanel buttons = new JPanel(new GridLayout(0, 3));
		buttons.setBackground(Color.BLACK);
		buttonPanel.add(buttons);

		ImageIcon doneIcon = new ImageIcon("res/Save2.png");
		JButton done = new JButton(doneIcon);
		done.setOpaque(false);
		done.setFocusPainted(false);
		done.setBackground(Color.BLACK);
		done.setBorder(BorderFactory.createEmptyBorder());
		buttons.add(done);
		done.setVisible(true);
		done.setActionCommand("Done");
		done.addActionListener(this);

		ImageIcon deleteIcon = new ImageIcon("res/Delete.png");
		JButton delete = new JButton(deleteIcon);
		delete.setOpaque(false);
		delete.setFocusPainted(false);
		delete.setBackground(Color.BLACK);
		delete.setBorder(BorderFactory.createEmptyBorder());
		buttons.add(delete);
		delete.setVisible(true);
		delete.setActionCommand("Delete");
		delete.addActionListener(this);

		ImageIcon exitIcon = new ImageIcon("res/Back.png");
		JButton exit = new JButton(exitIcon);
		exit.setOpaque(false);
		exit.setFocusPainted(false);
		exit.setBackground(Color.BLACK);
		exit.setBorder(BorderFactory.createEmptyBorder());
		buttons.add(exit);
		exit.setVisible(true);
		exit.setActionCommand("Back");
		exit.addActionListener(this);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getActionCommand().equals("Back")) {
			this.setVisible(false);
		} else if (e.getActionCommand().equals("Done")) {
			file = fileName.getText();
			try {
				saveFile(file);
			} catch (FileNotFoundException e1) {
				e1.printStackTrace();
			} catch (IOException e1) {
				e1.printStackTrace();
			}
			this.setVisible(false);
		} else if (e.getActionCommand().equals("Delete")) {
			deleteFile();
		}
	}

	private void deleteFile() {
		try {
			FileReader f = new FileReader("saveFile.txt");
			ArrayList<String> str = new ArrayList<String>();
			Scanner sc = new Scanner(f);
			while (sc.hasNext()) {
				str.add(sc.next());
			}
			PrintWriter output = new PrintWriter("saveFile.txt");
			for (int i = 0; i < savedFiles.size(); i++) {
				if (!(str.get(i).equals(file))) {
					output.println(str.get(i));
				}
			}
			sc.close();
			output.close();

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		for (int i = 0; i < savedFiles.size(); i++) {
			if (savedFiles.get(i).equals(file)) {
				savedFiles.remove(i);
			}
		}
		this.remove(buttonPanel);
		createPanels();
		validate();

	}

	private void saveFile(String file) throws IOException {
		// Saving the file name in savedFiles.txt
		String temp = "";
		for (int i = 0; i < file.length(); i++) {
			if (file.charAt(i) == ' ') {
				break;
			}
			temp += file.charAt(i);
		}
		if (adapter == null) {
			return;
		}
		try {
			FileReader f = new FileReader("saveFile.txt");
			ArrayList<String> str = new ArrayList<String>();
			Scanner sc = new Scanner(f);
			while (sc.hasNext()) {
				str.add(sc.next());
			}
			PrintWriter output = new PrintWriter("saveFile.txt");
			boolean printed = false;
			for (int i = 0; i < savedFiles.size(); i++) {
				if (!(str.get(i).equals(temp))) {
					output.println(str.get(i));
				} else {
					output.println(temp);
					printed = true;
				}
			}
			if (!printed) {
				output.println(temp);
			}
			sc.close();
			output.close();

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		writeData();

	}

	public void writeData() throws IOException {
		String temp = "";
		for (int i = 0; i < file.length(); i++) {
			if (file.charAt(i) == ' ') {
				break;
			}
			temp += file.charAt(i);
		}
		ObjectOutputStream opj = new ObjectOutputStream(new FileOutputStream(
				temp + ".bin"));
		int p1Type = 0, p2Type = 0;
		String[][] display = adapter.getBoard().display();
		String[][] output = new String[6][6];
		for (int i = 0; i < 5; i++) {
			for (int j = 0; j < 5; j++) {
				output[i][j] = display[i][j];
				if (display[i][j].length() > 1) {
					if (display[i][j].charAt(2) == '1') {
						if (display[i][j].charAt(1) == 'C') {
							p1Type = 1;
						} else {
							p1Type = 2;
						}
					} else if (display[i][j].charAt(2) == '2') {
						if (display[i][j].charAt(1) == 'C') {
							p2Type = 1;
						} else {
							p2Type = 2;
						}
					}
				}
			}
		}
		output[5][0] = p1Type + "";
		output[5][1] = p2Type + "";
		output[5][2] = adapter.getBoard().getP1().getName();
		output[5][3] = adapter.getBoard().getP2().getName();
		output[5][4] = adapter.getBoard().whatNow();
		opj.writeObject(output);
		opj.close();

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
		if (!file.equals("-")) {
			fileName.setText(file);
		} else {
			fileName.setText("");
		}
	}

	@Override
	public void mouseReleased(MouseEvent arg0) {
		tempLabel.setBorder(BorderFactory.createEmptyBorder());
	}
}
