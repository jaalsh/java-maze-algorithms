package main;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.EventQueue;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.LayoutManager;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.border.EmptyBorder;

import generator.DFSGen;
import generator.EmptyGridGen;
import generator.HKGen;

public class Maze {

	public static final int WIDTH = 800;
	public static final int HEIGHT = 800;
	public static final int W = 20;

	private int cols, rows;

	/**
	 * @param args
	 *            the command line arguments
	 */
	public static void main(String[] args) {
		new Maze();
	}

	public Maze() {
		cols = Math.floorDiv(WIDTH, W);
		rows = Math.floorDiv(HEIGHT, W);

		EventQueue.invokeLater(new Runnable() {
			@Override
			public void run() {
				try {
					UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
				} catch (ClassNotFoundException | InstantiationException | IllegalAccessException
						| UnsupportedLookAndFeelException ex) {
					ex.printStackTrace();
				}
				createAndShowGUI();
			}
		});
	}

	private void addComponentsToPane(Container pane) {
		GridBagConstraints constraints = new GridBagConstraints();
		constraints.anchor = GridBagConstraints.NORTHEAST;
		
		JButton button = new JButton("Run");
		button.addActionListener (new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            	pane.add(new HKGen(rows, cols));
            	pane.repaint();
                pane.revalidate();
            }
        });      
		pane.add(button, constraints);
	}

	private void createAndShowGUI() {
		JFrame frame = new JFrame("Maze");
		JPanel container = new JPanel(new GridBagLayout());
		container.setBackground(Color.BLACK);
		container.setBorder(new EmptyBorder(20, 20, 20, 20));
		frame.setContentPane(container);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		container.add(new EmptyGridGen(rows, cols));

		// Set up the content pane.
		addComponentsToPane(frame.getContentPane());

		// container.add(genComboBox, constraints);
		// container.add(runButton);

		frame.pack();
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);

		// Display the window.
		frame.pack();
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
	}
	
	private void genMaze(Container pane) {
		pane.add(new HKGen(rows, cols));
	}
}