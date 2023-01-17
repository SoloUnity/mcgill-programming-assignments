package finalproject;


import finalproject.system.Tile;
import finalproject.tiles.MetroTile;

import java.util.ArrayList;

public class ShortestPath extends PathFindingService {
    //TODO level 4: find distance prioritized path
    public ShortestPath(Tile start) {
        super(start);
        generateGraph();
    }

	@Override
	public void generateGraph() {
		// TODO Auto-generated method stub

        ArrayList<Tile> walkableTiles = GraphTraversal.BFS(this.source);
        this.g = new Graph(walkableTiles);

        for (Tile tile: walkableTiles) {

            tile.costEstimate = tile.distanceCost;

            for (Tile neighbour: tile.neighbors) {

                if ((tile instanceof MetroTile) && (neighbour instanceof MetroTile)) {

                    this.g.addEdge(tile, neighbour, ((MetroTile) neighbour).metroDistanceCost);

                }
                else if (neighbour.isWalkable()) {

                    this.g.addEdge(tile, neighbour, neighbour.distanceCost);

                }
            }
        }
	}
}