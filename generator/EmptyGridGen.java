package generator;

import java.awt.Graphics;
import java.util.ArrayList;
import java.util.List;

import util.Cell;
import util.DFSCell;

public class EmptyGridGen extends GeneratorPanel {

	private static final long serialVersionUID = 7237062514425122227L;
	private List<Cell> grid = new ArrayList<Cell>();

	public EmptyGridGen(int rows, int cols) {
		super(rows, cols);
		for (int x = 0; x < rows; x++) {
			for (int y = 0; y < cols; y++) {
				grid.add(new DFSCell(x, y));
			}
		}
	}
	
	@Override
	protected void paintComponent(Graphics g) {
		for (Cell c : grid) {
			c.draw(g);
		}
	}
}
