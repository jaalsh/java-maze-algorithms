package generator;

import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import util.Cell;
import util.DFSCell;

public class HKGen extends GeneratorPanel {

	private static final long serialVersionUID = -4429681545327332926L;
	private List<DFSCell> grid = new ArrayList<DFSCell>();
	private DFSCell current;
	
	public HKGen(int rows, int cols) {
		super(rows, cols);
		for(int x = 0; x < rows; x++) {
			for(int y = 0; y < cols; y++) {
				grid.add(new DFSCell(x, y));
			}
		}
		setGrid(grid);
		current = grid.get(0);
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
			current.removeWalls(next);
			current = next;
		} else {
			// hunt
			Optional<DFSCell> opt = grid.parallelStream().filter(c -> c.isVisited() && c.getNeighbours(grid).size() > 0).findAny();
			if (opt.isPresent()) {
				current = opt.get();
			}
		}
	}
	
	public void setCurrent(DFSCell current) {
		this.current = current;
	}

	@Override
	   protected void paintComponent(Graphics g) {
	   	for (Cell c: grid) {
				c.draw(g);
			} 
	   	current.highlight(g);
	   }
}
