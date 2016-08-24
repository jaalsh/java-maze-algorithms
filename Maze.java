import java.awt.Color;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Graphics;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.Timer;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.border.EmptyBorder;

public class Maze {
	
	private static final int WIDTH = 800;
	private static final int HEIGHT = 800;
	public static final int W = 20;
	
	private int cols, rows;
	private List<Cell> grid = new ArrayList<Cell>();
	private Stack<Cell> stack;
	private Stack<Cell> path;

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        new Maze();
    }

    public Maze() {
    	
    	cols = Math.floorDiv(WIDTH, W);
		rows = Math.floorDiv(HEIGHT, W);
		
		stack = new Stack<Cell>(rows*cols);
		path = new Stack<Cell>(rows*cols);
		
		for(int x = 0; x < rows; x++) {
			for(int y = 0; y < cols; y++) {
				grid.add(new Cell(x, y));
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

                JFrame frame = new JFrame("Testing");
                JPanel content = new JPanel(new GridBagLayout());
                content.setBackground(Color.BLACK);
                content.setBorder(new EmptyBorder(20, 20, 20, 20));
                frame.setContentPane(content);
                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                frame.add(new MazePanel());
                frame.pack();
                frame.setLocationRelativeTo(null);
                frame.setVisible(true);
            }
        });
    }

    public class MazePanel extends JPanel {
        
		private static final long serialVersionUID = 1L;
		private Cell current = grid.get(0);
		private boolean genPath = false;

        public MazePanel() {
        	setBackground(Color.BLACK);  
        	
        	final Timer timer = new Timer(1, null);
        	
            timer.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                	if (!grid.parallelStream().allMatch(c-> c.isVisited())) {
                		carve();
                	} else {
                		if (!current.equals(grid.get(grid.size() - 1)) && genPath) {
                    		path();
                    	} else {
                    		current = grid.get(0);
                    		genPath = true;
                    	}              
                	}
                	if(genPath && current.equals(grid.get(grid.size() - 1))) {
                		while (!path.isStackEmpty()) {
                			try {
								path.pop().setPath(true);
							} catch (Exception e1) {
								// TODO Auto-generated catch block
								e1.printStackTrace();
							}
                		}
                		timer.stop();
                	}
                    repaint();
                }
            });
            timer.start();
        }

    	private void carve() {
    		current.setVisited(true);
    		Cell next = current.checkNeighbours(grid);
    		if (next != null) {
    			stack.push(current);
    			current.removeWalls(next);
    			current = next;
    		} else if (!stack.isStackEmpty()){
    			try {
    				current = stack.pop();
    			} catch (Exception e) {
    				e.printStackTrace();
    			}
    		}
    	}
    	
    	private void path() {
    		current.setDeadEnd(true);
    		Cell next = current.checkPathNeighbours(grid);
    		if (next != null) {
    			path.push(current);
    			current = next;
    		} else if (!path.isStackEmpty()){
    			try {
    				current = path.pop();
    			} catch (Exception e) {
    				e.printStackTrace();
    			}
    		}
    	}

        @Override
        public Dimension getPreferredSize() {
        	// +1 pixel on width and height so bottom and right borders can be drawn.
            return new Dimension(Maze.WIDTH + 1, Maze.HEIGHT + 1);
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
        	for (Cell c: grid) {
     			c.draw(g);
     		}                 
            current.highlight(g);
        }
    }
}