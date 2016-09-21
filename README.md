# Java Mazes

The primary goal of this project is to animate maze generation and solving algorithms. Not every algorithm is included, and the implementations may not be the most efficient
due to the animation involved. 

## Generation Algorithms 

* Aldous-Broder
* Binary Tree
* Depth First Search (Recursive Backtracker)
* Eller's
* Growing Forest
* Growing Tree
* Hunt & Kill
* Houston's
* Kruskal's
* Prim's
* Quad Depth First Search
* Sidewinder
* Wilson's

## Solving Algorithms

* Breadth First Search
* Bidirectional Depth First Search (Recursive Backtracker)
* Depth First Search (Recursive Backtracker)
* Dijkstra's

## Useful Links

* [astrolog.org Think Labyrinth: Maze Algorithms](http://www.astrolog.org/labyrnth/algrithm.htm)
* [wikipedia.org Maze generation algorithm](https://en.wikipedia.org/wiki/Maze_generation_algorithm)
* [jamisbuck.org Maze Algorithms](http://www.jamisbuck.org/mazes/)

## Notes

### Growing Tree: 
  * Could use stack to always get "newest" cell or queue to always get "oldest" cell

### Eller's:
  * Eller's algorithm is typically implemented to generate one **row** at a time, however I've implemented it to focus on **columns**. The principles are the still the same.
  If you choose to focus on columns you can generate a maze with a more horizontal bias by choosing to carve down less frequently, likewise if you focus on rows you can       choose to carve right less frequently to generate a vertical biased maze. More help: [neocomputer.org Eller's Algorithm](http://www.neocomputer.org/projects/eller.html)
  
### Houston's

  * Quote from Jamis Buck explaining this algorithm: > This hybrid algorithm was described to me by Robin Houston. It begins with the Aldous-Broder algorithm, to fill out the grid, and then switches to Wilson's after the grid is about 1/3 populated.
>This gives you better performance than either algorithm by itself, but while intuitively it would seem this preserves the properties of the original algorithms, it is not yet certain whether this still creates a uniform spanning tree or not.
 
 ### Kruskal's
 
   * Rather than keep a list of edges (walls), I've opted to make the algorithm node (vertices/cell/passage) based. This means, rather than pick a random wall from a list, I pick a random cell and loop through each wall. The effect is __essentially__ the same, just it was simpler to code in this manner.
 
 ### Prim's
 
   * I've implemented the modified version of Prim's that looks at cells. Quoting Walter D. Pullen author of (astrolog.org)[http://www.astrolog.org/): > This is Prim's algorithm to produce a minimum spanning tree, modified so all edge weights are the same, but also implemented by looking at cells instead of edges. It requires storage proportional to the size of the Maze. During creation, each cell is one of three types: (1) "In": The cell is part of the Maze and has been carved into already, (2) "Frontier": The cell is not part of the Maze and has not been carved into yet, but is next to a cell that's already "in", and (3) "Out": The cell is not part of the Maze yet, and none of its neighbors are "in" either. Start by picking a cell, making it "in", and setting all its neighbors to "frontier". Proceed by picking a "frontier" cell at random, and carving into it from one of its neighbor cells that are "in". Change that "frontier" cell to "in", and update any of its neighbors that are "out" to "frontier". The Maze is done when there are no more "frontier" cells left (which means there are no more "out" cells left either, so they're all "in"). This algorithm results in Mazes with a very low "river" factor with many short dead ends, and a rather direct solution. The resulting Maze is very similar to simplified Prim's algorithm, with only the subtle distinction that indentations in the expanding tree get filled only if that frontier cell is randomly selected, as opposed to having triple the chance of filling that cell via one of the frontier edges leading to it. It also runs very fast, faster than simplified Prim's algorithm because it doesn't need to compose and maintain a list of edges. 
	 
### QuadDFS

   * The Quad Depth First Search implementation may not produce perfect mazes. Based on early and incomplete testing it does. For my needs I didn't need to create a perfect maze so I haven't been to concerned with testing.
   * Description of a perfect maze, again quoting Walter D. Pullen author of (astrolog.org)[http://www.astrolog.org/): >  A "perfect" Maze means one without any loops or closed circuits, and without any inaccessible areas. Also called a simply-connected Maze. From each point, there is exactly one path to any other point. The Maze has exactly one solution. In Computer Science terms, such a Maze can be described as a spanning tree over the set of cells or vertices.