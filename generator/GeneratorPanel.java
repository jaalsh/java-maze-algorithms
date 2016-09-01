package generator;

import java.awt.Dimension;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JPanel;
import javax.swing.Timer;

import main.Maze;
import solver.DFSSolver;
import util.Cell;

// can't be instantiated - only inherited.
public abstract class GeneratorPanel extends JPanel {

	private static final long serialVersionUID = -8315090580110172837L;
	private static final int RUN_SPEED = 1; // probably not needed... tbh
	
	protected final Timer timer = new Timer(RUN_SPEED, null);
	private List<Cell> grid = new ArrayList<Cell>();
	private int rows, cols; // only needed to call solver...

	public GeneratorPanel(int rows, int cols) {
		this.rows = rows;
		this.cols = cols;
    }
	
	public void setCurrent(Cell current) {} // to be implemented.

    @Override
    public Dimension getPreferredSize() {
    	// +1 pixel on width and height so bottom and right borders can be drawn.
        return new Dimension(Maze.WIDTH + 1, Maze.HEIGHT + 1);
    }
    
    protected void solve() {
		new DFSSolver(rows, cols, Cell.convertList(grid), this);
	}
    
    @SuppressWarnings("unchecked")
	public void setGrid(List<? extends Cell> grid) {
		this.grid = (List<Cell>) grid;
	}
}