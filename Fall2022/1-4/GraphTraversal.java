package finalproject;

import finalproject.system.Tile;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedList;

public class GraphTraversal {
	//TODO level 1: implement BFS traversal starting from s
	public static ArrayList<Tile> BFS(Tile s) throws IllegalArgumentException {
		if (s == null || !s.isWalkable() || s.neighbors == null || s.neighbors.isEmpty()) {
			throw new IllegalArgumentException("Invalid input for BFS traversal");
		}

		LinkedList<Tile> queue = new LinkedList<>();
		ArrayList<Tile> visitedList = new ArrayList<>();
		HashSet<Tile> visited = new HashSet<>();

		visited.add(s);
		queue.add(s);

		while(!queue.isEmpty()) {
			Tile current = queue.poll();
			visitedList.add(current);

			for (Tile neighbour : current.neighbors) {
				if ((neighbour != null) && (neighbour.isWalkable()) && (!visited.contains(neighbour))) {
					visited.add(neighbour);
					queue.add(neighbour);
				}
			}
		}

		return visitedList;
	}

	//TODO level 1: implement DFS traversal starting from s
	public static ArrayList<Tile> DFS(Tile s) throws IllegalArgumentException {
		if (s == null || !s.isWalkable() || s.neighbors == null || s.neighbors.isEmpty()) {
			throw new IllegalArgumentException("Invalid input for DFS traversal");
		}

		LinkedList<Tile> stack = new LinkedList<>();
		ArrayList<Tile> visitedList = new ArrayList<>();
		HashSet<Tile> visited = new HashSet<>();

		stack.push(s);
		visited.add(s);
		while(!stack.isEmpty()) {
			Tile current = stack.poll();
			visitedList.add(current);

			for (Tile neighbor : current.neighbors) {
				if ((neighbor != null) && (neighbor.isWalkable()) && (!visited.contains(neighbor))) {
					visited.add(neighbor);
					stack.push(neighbor);
				}
			}
		}

		return visitedList;
	}
}


