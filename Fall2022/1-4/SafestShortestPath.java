package finalproject;


import java.util.ArrayList;
import java.util.LinkedList;

import finalproject.system.Tile;
import finalproject.tiles.MetroTile;

public class SafestShortestPath extends ShortestPath {
	public int health;
	public Graph costGraph;
	public Graph damageGraph;
	public Graph aggregatedGraph;

	//TODO level 8: finish class for finding the safest shortest path with given health constraint
	public SafestShortestPath(Tile start, int health) {
		super(start);
		this.health = health;
	}

	public void generateGraph() {

		// TODO Auto-generated method stub

		ArrayList<Tile> walkableTiles = GraphTraversal.BFS(this.source);

		this.costGraph = new Graph(walkableTiles);

		for (Tile tile: walkableTiles) {

			tile.costEstimate = tile.distanceCost;

			for (Tile neighbour: tile.neighbors) {

				if ((tile instanceof MetroTile) && (neighbour instanceof MetroTile)) {

					this.costGraph.addEdge(tile, neighbour, ((MetroTile) neighbour).metroDistanceCost);

				}
				else if (neighbour.isWalkable()) {
					this.costGraph.addEdge(tile, neighbour, neighbour.distanceCost);
				}
			}
		}

		this.damageGraph = new Graph(walkableTiles);

		for (Tile tile: walkableTiles) {

			tile.costEstimate = tile.damageCost;

			for (Tile neighbour: tile.neighbors) {

				if (neighbour.isWalkable()) {
					this.damageGraph.addEdge(tile, neighbour, neighbour.damageCost);
				}
			}
		}

		this.aggregatedGraph = new Graph(walkableTiles);

		for (Tile tile: walkableTiles) {

			tile.costEstimate = tile.damageCost;

			for (Tile neighbour: tile.neighbors) {

				if (neighbour.isWalkable()) {
					this.aggregatedGraph.addEdge(tile, neighbour, neighbour.damageCost);
				}
			}
		}
	}

	@Override
	public ArrayList<Tile> findPath(Tile start, LinkedList<Tile> waypoints){

		ArrayList<Tile> pc;
		ArrayList<Tile> pd;
		ArrayList<Tile> pr;

		super.g = this.costGraph;
		pc = super.findPath(start, waypoints);

		if (calculateDMG(pc) < this.health) {
			return pc;
		}

		super.g = this.damageGraph;
		pd = super.findPath(start, waypoints);

		if (calculateDMG(pd) > health) {
			return null;
		}


		while (true) {

			double cPC = calculateDistance(pc);
			double cPD = calculateDistance(pd);
			double dPD = calculateDMG(pd);
			double dPC = calculateDMG(pc);

			double lambda = ((cPC - cPD) / (dPD - dPC));

			for (Tile tile: this.aggregatedGraph.getVertexList()) {

				tile.costEstimate = tile.distanceCost + (lambda * tile.damageCost);

				for (Graph.Edge edge: this.aggregatedGraph.getEdges(tile)) {

					edge.weight = tile.distanceCost + (lambda * tile.damageCost);

				}

			}

			super.g = aggregatedGraph;

			pr = super.findPath(start, waypoints);

			if (calculateAggregate(pr) ==  calculateAggregate(pc)) {
				return pd;
			}
			else if (calculateDMG(pr) <= this.health) {
				pd = pr;
			}
			else {
				pc = pr;
			}

		}
	}

	public double calculateDMG(ArrayList<Tile> path) {

		return this.damageGraph.computePathCost(path);

	}

	public double calculateDistance(ArrayList<Tile> path) {

		return this.costGraph.computePathCost(path);

	}

	public double calculateAggregate(ArrayList<Tile> path) {

		return this.aggregatedGraph.computePathCost(path);

	}

}