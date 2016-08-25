package generator;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import main.DFSCell;
import main.Stack;
import solver.DFSSolver;

public class DFSGen extends GeneratorPanel {
	
	private static final long serialVersionUID = 4766570447860590373L;
	private Stack<DFSCell> stack;
	private int rows, cols;

	public DFSGen(int rows, int cols, List<DFSCell> grid) {
		super(rows, cols, grid);
		this.rows = rows;
		this.cols = cols;
		stack = new Stack<DFSCell>(rows*cols);
		timer.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            	if (!grid.parallelStream().allMatch(c-> c.isVisited())) {
            		carve();
            	} else {
            		current = grid.get(0);
            		solve();
            		timer.stop();
            	}
                repaint();
            }
        });
        timer.start();
	}
	
	private void carve() {
		current.setVisited(true);
		DFSCell next = current.getNeighbour(grid);
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
	
	private void solve() {
		new DFSSolver(rows, cols, grid, this);
	}
}