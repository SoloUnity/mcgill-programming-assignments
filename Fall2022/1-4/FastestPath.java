package finalproject;

import finalproject.system.Tile;
import finalproject.tiles.MetroTile;

import java.util.ArrayList;

public class FastestPath extends PathFindingService {
    //TODO level 6: find time prioritized path
    public FastestPath(Tile start) {
        super(start);
        generateGraph();
    }

	@Override
	public void generateGraph() {
		// TODO Auto-generated method stub

        ArrayList<Tile> walkableTiles = GraphTraversal.BFS(this.source);
        this.g = new Graph(walkableTiles);

        for (Tile tile: walkableTiles) {

            tile.costEstimate = tile.timeCost;

            for (Tile neighbour: tile.neighbors) {

                if ((tile instanceof MetroTile) && (neighbour instanceof MetroTile)) {

                    this.g.addEdge(tile, neighbour, ((MetroTile) neighbour).metroTimeCost);

                }
                else if (neighbour.isWalkable()) {
                    this.g.addEdge(tile, neighbour, neighbour.timeCost);
                }
            }
        }
	}
}
