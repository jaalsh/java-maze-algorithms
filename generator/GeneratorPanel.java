package generator;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JPanel;
import javax.swing.Timer;

import main.DFSCell;
import main.Maze;

// can't be instantiated - only inherited.
public abstract class GeneratorPanel extends JPanel {

	private static final long serialVersionUID = -8315090580110172837L;
	protected List<DFSCell> grid = new ArrayList<DFSCell>();
	protected DFSCell current;
	protected final Timer timer = new Timer(1, null);
	
	public void setCurrent(DFSCell current) {
		this.current = current;
	}

	public GeneratorPanel(int rows, int cols, List<DFSCell> grid) {
		this.grid = grid;
		this.current = grid.get(0);
		setBackground(Color.BLACK);  
    }

    @Override
    public Dimension getPreferredSize() {
    	// +1 pixel on width and height so bottom and right borders can be drawn.
        return new Dimension(Maze.WIDTH + 1, Maze.HEIGHT + 1);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
    	for (DFSCell c: grid) {
 			c.draw(g);
 		}                 
        current.highlight(g);
    }
}