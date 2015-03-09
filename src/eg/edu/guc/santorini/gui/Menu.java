package eg.edu.guc.santorini.gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JSeparator;

@SuppressWarnings("serial")
public class Menu extends JMenuBar {
	public Menu(final JFrame callingFrame) {
		JMenu file = new JMenu("File");
		this.add(file);

		JMenu edit = new JMenu("Edit");
		this.add(edit);

		JMenu help = new JMenu("Help");
		this.add(help);

		// create menu items
		createNewGameMenu(callingFrame, file);

		JSeparator line = new JSeparator();
		file.add(line);

		JMenuItem load = new JMenuItem("Load");
		file.add(load);
		load.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				new LoadWindow();
				callingFrame.setVisible(false);

			}
		});

		JSeparator line2 = new JSeparator();
		file.add(line2);

		createExitButton(file);

		createChangeStyleMenu(callingFrame, edit);

		JSeparator line3 = new JSeparator();
		edit.add(line3);

		createResetMenu(callingFrame, edit);

		createHowToPlayMenu(help);

		JSeparator line4 = new JSeparator();
		help.add(line4);

		createAboutMenu(help);
	}

	private void createAboutMenu(JMenu help) {
		JMenuItem about = new JMenuItem("About");
		help.add(about);
		about.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				ImageIcon ico = new ImageIcon("res/santoriniIco.png");
				JOptionPane
						.showMessageDialog(
								null,
								"Created By : \n Ahmed Saleh Abdelwahab \n" 
								+ " Abdelrahman Hesham Elkady \n Mohammad Mostafa Elhamamsy",
								"About us", JOptionPane.INFORMATION_MESSAGE,
								ico);

			}
		});
	}

	private void createHowToPlayMenu(JMenu help) {
		JMenuItem howToPlay = new JMenuItem("How To Play");
		help.add(howToPlay);
		howToPlay.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent arg0) {
				JOptionPane
						.showMessageDialog(
								null,
								"Try to reach level 3 with one of your pieces or to " 
								+ "make your opponent have no moves",
								"How To Play", JOptionPane.INFORMATION_MESSAGE);
			}
		});
	}

	private void createResetMenu(final JFrame callingFrame, JMenu edit) {
		JMenuItem reset = new JMenuItem("Reset Board");
		edit.add(reset);
		reset.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					Window callingWindow = (Window) callingFrame;
					callingWindow.setVisible(false);
					new Window(callingWindow.getP1Type(), callingWindow
							.getP2Type(), callingWindow.getP1Name(),
							callingWindow.getP2Name());
				} catch (ClassCastException e2) {
					e2.printStackTrace();
				}

			}
		});
	}

	private void createChangeStyleMenu(final JFrame callingFrame, JMenu edit) {
		JMenuItem changeStyle = new JMenuItem("Change Style");
		edit.add(changeStyle);
		changeStyle.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					Window callingWindow = (Window) (callingFrame);

					if (!callingWindow.isHighlighted()) {
						if (callingWindow.getStyle().equals("collection1/")) {
							callingWindow.setStyle("collection2/");
							callingWindow.changeStyle(callingWindow.getStyle());
						} else if (callingWindow.getStyle().equals(
								"collection2/")) {
							callingWindow.setStyle("collection3/");
							callingWindow.changeStyle(callingWindow.getStyle());
						} else if (callingWindow.getStyle().equals(
								"collection3/")) {
							callingWindow.setStyle("collection1/");
							callingWindow.changeStyle(callingWindow.getStyle());
						}
					}
				} catch (ClassCastException e1) {
					e1.printStackTrace();
				}

				try {
					StartWindow callingStartWindow = (StartWindow) (callingFrame);

					if (callingStartWindow.getStyle().equals("collection1/")) {
						callingStartWindow.changeStyle("collection2/");
					} else if (callingStartWindow.getStyle().equals(
							"collection2/")) {
						callingStartWindow.changeStyle("collection3/");
					} else {
						callingStartWindow.changeStyle("collection1/");
					}
				} catch (ClassCastException e1) {
					e1.printStackTrace();
				}
			}

		});
	}

	private void createExitButton(JMenu file) {
		JMenuItem exit = new JMenuItem("Exit");
		file.add(exit);
		exit.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				int option = JOptionPane.YES_NO_OPTION;
				int choice = JOptionPane.showConfirmDialog(null, "Exit Game ?",
						"Exit", option);
				if (choice == 0) {
					System.exit(0);
				}
			}
		});
	}

	private void createNewGameMenu(final JFrame callingFrame, JMenu file) {
		JMenuItem newGame = new JMenuItem("New Game");
		file.add(newGame);
		newGame.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				new StartWindow();
				callingFrame.setVisible(false);
			}
		});
	}
}
