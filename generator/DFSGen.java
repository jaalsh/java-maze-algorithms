package generator;

import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

import util.Cell;
import util.DFSCell;

public class DFSGen extends GeneratorPanel {

	private static final long serialVersionUID = 4766570447860590373L;
	private Stack<DFSCell> stack;
	private List<DFSCell> grid = new ArrayList<DFSCell>();
	private DFSCell current;

	public DFSGen(int rows, int cols) {
		super(rows, cols);
		for (int x = 0; x < rows; x++) {
			for (int y = 0; y < cols; y++) {
				grid.add(new DFSCell(x, y));
			}
		}
		setGrid(grid);
		current = grid.get(0);
		stack = new Stack<DFSCell>();
		timer.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (!grid.parallelStream().allMatch(c -> c.isVisited())) {
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
		} else if (!stack.isEmpty()) {
			try {
				current = stack.pop();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	public void setCurrent(DFSCell current) {
		this.current = current;
	}

	@Override
	protected void paintComponent(Graphics g) {
		for (Cell c : grid) {
			c.draw(g);
		}
		current.highlight(g);
	}
}