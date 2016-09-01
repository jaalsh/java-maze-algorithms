package util;

import java.awt.Graphics;
import java.util.ArrayList;
import java.util.List;

public interface Cell {
	
	public void draw(Graphics g);
	
	public void highlight(Graphics g);
	
	public int getX();
	
	public int getY();
	
	public boolean[] getWalls();
	
	public boolean isVisited();
	
	@SuppressWarnings("unchecked")
	public static <T> List<T> convertList(List<? extends Cell> grid) {
		List<T> newGrid = new ArrayList<T>();
		for (Cell c: grid) {
			newGrid.add((T)c);
		}
		return newGrid;
    }
}