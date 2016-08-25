package main;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.GridBagLayout;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.border.EmptyBorder;

import generator.DFSGen;

public class Maze {
	
	public static final int WIDTH = 800;
	public static final int HEIGHT = 800;
	public static final int W = 20;
	
	private int cols, rows;
	private List<DFSCell> grid = new ArrayList<DFSCell>();

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        new Maze();
    }

    public Maze() {
    	
    	cols = Math.floorDiv(WIDTH, W);
		rows = Math.floorDiv(HEIGHT, W);
		
		for(int x = 0; x < rows; x++) {
			for(int y = 0; y < cols; y++) {
				grid.add(new DFSCell(x, y));
			}
		}
    	
        EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                try {
                    UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
                } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException ex) {
                    ex.printStackTrace();
                }

                JFrame frame = new JFrame("Maze | | |");
                JPanel content = new JPanel(new GridBagLayout());
                content.setBackground(Color.BLACK);
                content.setBorder(new EmptyBorder(20, 20, 20, 20));
                frame.setContentPane(content);
                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                frame.add(new DFSGen(rows, cols, grid)); // add return from switch statement in GeneratorComboBox!
                frame.pack();
                frame.setLocationRelativeTo(null);
                frame.setVisible(true);
            }
        });
    }
}	