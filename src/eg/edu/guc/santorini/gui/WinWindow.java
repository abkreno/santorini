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

@SuppressWarnings("serial")
public class WinWindow extends JFrame implements ActionListener {

	private String winner;
	private JPanel winnerPanel;
	private JPanel decidePanel;

	public WinWindow(String winner) {
		super("Game Over");
		this.winner = winner;
		setSize(500, 500);
		setLocation(400, 100);
		this.setIconImage(new ImageIcon("res/santoriniIco.png").getImage());
		Container content = getContentPane();
		content.setBackground(Color.black);
		content.setLayout(new GridLayout(2, 1, 1, 1));
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setResizable(false);
		createPanels();
		createCongratulationsLabels();
		createNewGameButton();
		createExitButton();
		setVisible(true);
	}

	public void createCongratulationsLabels() {
		ImageIcon congratsTxt = new ImageIcon("res/" + "congratulations.png");
		JLabel congrats = new JLabel(congratsTxt);
		// congrats.setText("CONGRATULATIONS");
		// congrats.setHorizontalAlignment(JLabel.CENTER);
		// congrats.setFont(new Font("Consolas", Font.BOLD, 28));
		// congrats.setForeground(Color.WHITE);

		JLabel winnerName = new JLabel();
		winnerName.setText(getWinner());
		winnerName.setHorizontalAlignment(JLabel.CENTER);
		winnerName.setFont(new Font("Consolas", Font.BOLD, 28));
		winnerName.setForeground(Color.WHITE);

		winnerPanel.add(congrats, BorderLayout.CENTER);
		winnerPanel.add(winnerName, BorderLayout.SOUTH);

	}

	public void createPanels() {
		winnerPanel = new JPanel(new BorderLayout());
		winnerPanel.setBackground(Color.BLACK);
		winnerPanel.setVisible(true);
		add(winnerPanel);

		decidePanel = new JPanel(new GridLayout(2, 1, 1, 1));
		decidePanel.setBackground(Color.BLACK);
		decidePanel.setVisible(true);
		add(decidePanel);

	}

	public void createNewGameButton() {
		ImageIcon newGameIcon = new ImageIcon("res/newGame.png");
		JButton newGame = new JButton(newGameIcon);
		newGame.setOpaque(false);
		newGame.setFocusPainted(false);
		newGame.setBackground(Color.GRAY);
		newGame.setBorder(BorderFactory.createEmptyBorder());
		decidePanel.add(newGame);
		newGame.setVisible(true);
		newGame.setActionCommand("New game");
		newGame.addActionListener(this);
	}

	public void createExitButton() {
		ImageIcon exitIcon = new ImageIcon("res/exitFinish.png");
		JButton exit = new JButton(exitIcon);
		exit.setOpaque(false);
		exit.setFocusPainted(false);
		exit.setBackground(Color.GRAY);
		exit.setBorder(BorderFactory.createEmptyBorder());
		decidePanel.add(exit);
		exit.setVisible(true);
		exit.setActionCommand("Exit");
		exit.addActionListener(this);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getActionCommand().equalsIgnoreCase("New Game")) {
			this.setVisible(false);
			new StartWindow();
		} else if (e.getActionCommand().equalsIgnoreCase("Exit")) {
			System.exit(0);
		}

	}

	public String getWinner() {
		return winner;
	}

	public void setWinner(String winner) {
		this.winner = winner;
	}

}
