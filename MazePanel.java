import java.awt.Color;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Graphics;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.Timer;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.border.EmptyBorder;

public class MazePanel {
	
	private static final int WIDTH = 800;
	private static final int HEIGHT = 800;
	public static final int W = 20;
	
	private int cols, rows;
	private List<Cell> grid = new ArrayList<Cell>();
	private Stack<Cell> stack;

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        new MazePanel();
    }

    public MazePanel() {
    	
    	cols = Math.floorDiv(WIDTH, W);
		rows = Math.floorDiv(HEIGHT, W);
		
		stack = new Stack<Cell>(rows*cols);
		
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
                frame.add(new TestPane());
                frame.pack();
                frame.setLocationRelativeTo(null);
                frame.setVisible(true);
            }
        });
    }

    public class TestPane extends JPanel {
        
        private Cell current = grid.get(0);

        public TestPane() {
        	setBackground(Color.BLACK);  
        	
            Timer timer = new Timer(40, new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    carve();
                    repaint();
                }
            });
            timer.start();
        }

    	private void carve() {
    		current.setVisited(true);
    		Cell next = current.checkNeighbours(grid, current);
    		if (next != null) {
    			stack.push(current);
    			current.removeWalls(current, next);
    			current = next;
    		} else if (!stack.isStackEmpty()){
    			try {
    				current = stack.pop();
    			} catch (Exception e) {
    				e.printStackTrace();
    			}
    		}
    	}

        @Override
        public Dimension getPreferredSize() {
        	// +1 pixel on width and height so bottom and right borders can be drawn.
            return new Dimension(MazePanel.WIDTH + 1, MazePanel.HEIGHT + 1);
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            // maybe use G2D?
            
            for (Cell c: grid) {
    			c.draw(g);
    		}
            current.highlight(g);
        }

    }
}