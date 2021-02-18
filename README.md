# Java Maze Algorithms

The primary goal of this project is to animate maze generation and solving algorithms. Not every algorithm is included, and the implementations may not be the most efficient
due to the animation involved.

Main method is located in main/Maze.java and can be ran using an IDE or from the [command line.](http://www.skylit.com/javamethods/faqs/javaindos.html)

*If running on a small resolution you may need to comment out or remove lines 95 and 96 in main/Maze.java which set the row count for the drop down menus.*

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
* Spiral Backtracker
* Wilson's
* Zig-Zag

## Solving Algorithms

* Breadth First Search
* Bidirectional Depth First Search (Recursive Backtracker)
* Depth First Search (Recursive Backtracker)
* Dijkstra's

## Useful Links

* [astrolog.org Think Labyrinth: Maze Algorithms](http://www.astrolog.org/labyrnth/algrithm.htm)
* [wikipedia.org Maze generation algorithm](https://en.wikipedia.org/wiki/Maze_generation_algorithm)
* [jamisbuck.org Maze Algorithms](http://www.jamisbuck.org/mazes/)

## Task List

- [x] Sort generation and solving algorithms into alphabetical order (A-Z) in drop down menus.
- [ ] Test that Quad Depth First Search produces a 'perfect' maze.
- [ ] Test that Quad Depth First Search doesn't produce a maze with isolations.
- *Both these tests will fail, need to fix.*
- [x] Optimise Houston's algorithm so that we don't keep checking that 1/3 of the maze has been visited once we pass the 1/3 visited threshold.
- [ ] Implement the minor refinement written in comments for Wilson's algorithm. This refinement could also be applied to Houston's.
- [ ] Update readme with a description of the Zig-Zag algorithm.
- [ ] Update readme to highlight which algorithms are "standard" and which ones have been created for the project.
- [ ] Implement test methods. Such as a "flood fill" test to find any isolations within the maze.

## Notes

### Aldous-Broder
  * **Warning:** This algorithm is never guaranteed to finish generating a maze. It will most likely get stuck for a long time before it completes generation, if it does terminate at all.

### Growing Tree
  * Could use stack to always get "newest" cell or queue to always get "oldest" cell

### Eller's
  * Eller's algorithm is typically implemented to generate one **row** at a time, however I've implemented it to focus on **columns**. The principles are the still the same.
  If you choose to focus on columns you can generate a maze with a more horizontal bias by choosing to carve down less frequently, likewise if you focus on rows you can       choose to carve right less frequently to generate a vertical biased maze. More help: [neocomputer.org Eller's Algorithm](http://www.neocomputer.org/projects/eller.html)
  * Regarding the algorithms origin:

> I asked the maintainer of "Think Labyrinth" about the origin of Eller's algorithm and here was his response:
"Eller's algorithm is named after computer programmer Marlin Eller, CEO of sunhawk.com. He invented this algorithm in 1982, which is the earliest use of it I know of. He never published it, but he did tell me about it, so I chose to name the algorithm after him."

[Jeffrey Winter, 3 Jan 2011](http://weblog.jamisbuck.org/2010/12/29/maze-generation-eller-s-algorithm)


### Sidewinder
  * Like in Eller's algorithm my implementation focuses on **columns** rather than traditional implementations that focus on **rows**.

### Houston's
  * Quote from Jamis Buck explaining this algorithm:

> This hybrid algorithm was described to me by Robin Houston. It begins with the Aldous-Broder algorithm, to fill out the grid, and then switches to Wilson's after the grid is about 1/3 populated. This gives you better performance than either algorithm by itself, but while intuitively it would seem this preserves the properties of the original algorithms, it is not yet certain whether this still creates a uniform spanning tree or not.

### Kruskal's
  * Rather than keep a list of edges (walls), I've opted to make the algorithm node (vertices/cell/passage) based. This means, rather than pick a random wall from a list, I pick a random cell and loop through each wall. The effect is __essentially__ the same, just it was simpler to code in this manner.

### Prim's
  * I've implemented a modified version of Prim's algorithm that looks at cells. Quoting Walter D. Pullen author of [astrolog.org](http://www.astrolog.org/):

> This is Prim's algorithm to produce a minimum spanning tree, modified so all edge weights are the same, but also implemented by looking at cells instead of edges. It requires storage proportional to the size of the Maze. During creation, each cell is one of three types: (1) "In": The cell is part of the Maze and has been carved into already, (2) "Frontier": The cell is not part of the Maze and has not been carved into yet, but is next to a cell that's already "in", and (3) "Out": The cell is not part of the Maze yet, and none of its neighbors are "in" either. Start by picking a cell, making it "in", and setting all its neighbors to "frontier". Proceed by picking a "frontier" cell at random, and carving into it from one of its neighbor cells that are "in". Change that "frontier" cell to "in", and update any of its neighbors that are "out" to "frontier". The Maze is done when there are no more "frontier" cells left (which means there are no more "out" cells left either, so they're all "in"). This algorithm results in Mazes with a very low "river" factor with many short dead ends, and a rather direct solution. The resulting Maze is very similar to simplified Prim's algorithm, with only the subtle distinction that indentations in the expanding tree get filled only if that frontier cell is randomly selected, as opposed to having triple the chance of filling that cell via one of the frontier edges leading to it. It also runs very fast, faster than simplified Prim's algorithm because it doesn't need to compose and maintain a list of edges.

### QuadDFS
 * The Quad Depth First Search implementation may not always produce perfect mazes.
 * Description of a perfect maze, again quoting Walter D. Pullen author of [astrolog.org](http://www.astrolog.org/):

> A "perfect" Maze means one without any loops or closed circuits, and without any inaccessible areas. Also called a simply-connected Maze. From each point, there is exactly one path to any other point. The Maze has exactly one solution. In Computer Science terms, such a Maze can be described as a spanning tree over the set of cells or vertices.

### Spiral Backtracker
  * This is an algorithm to generate mazes with spiral patterns. It is similar to the DFS algorithm and also uses a stack data structure to back track. However, instead of going in a direction randomly, you choose a direction to travel in and then travel in that direction for __x__ cells, repeating once you've hit a cell already in the maze or a border. The maze is done once all cells have been visited. See spiral screenshot for mazes that can be generated using the algorithm. The distance you choose to travel in is entirely up to you, you can even choose a random number to get varying distances. If you choose a distance of 1, you will have a DFS that can't travel in the previous direction just travelled.

### Zig Zag
  * This is an algorithm to generate mazes with zig zag lines or patterns across the maze. 
