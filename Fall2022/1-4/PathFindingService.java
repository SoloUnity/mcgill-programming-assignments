
package finalproject;

import finalproject.system.Tile;

import java.util.*;

public abstract class PathFindingService {
	Tile source;
	Graph g;
    private HashMap<Tile, Integer> distances = new HashMap<>();
    private HashMap<Tile, Tile> predecessors = new HashMap<>();

	public PathFindingService(Tile start) {
    	this.source = start;
    }

	public abstract void generateGraph();

    //TODO level 4: Implement basic dijkstra's algorithm to find a path to the final unknown destination
    public ArrayList<Tile> findPath(Tile startNode) {

        dijkstra(g.getVertexList(), g.getAllEdges(), startNode);
        return getPath();

    }

    //TODO level 5: Implement basic dijkstra's algorithm to path find to a known destination
    public ArrayList<Tile> findPath(Tile start, Tile end) {

        dijkstra(g.getVertexList(), g.getAllEdges(), start);

    	return getPath(start, end);
    }

    //TODO level 5: Implement basic dijkstra's algorithm to path find to the final destination passing through given waypoints
    public ArrayList<Tile> findPath(Tile start, LinkedList<Tile> waypoints){

        ArrayList<Tile> path = new ArrayList<>();

        Tile current = start;

        while(!waypoints.isEmpty()) {

            Tile waypoint = waypoints.remove();
            ArrayList<Tile> waypointPath = findPath(current, waypoint);
            current = waypoint;

            for (Tile tile: waypointPath) {
                if (!tile.equals(waypoint)) {
                    path.add(tile);
                }


            }
        }

        Tile end = null;

        for (Tile tile: this.g.getVertexList()) {
            if (tile.isDestination) {
                end = tile;
                break;
            }
        }

        ArrayList<Tile> waypointPath = findPath(current, end);
        for (Tile tile: waypointPath) {
            path.add(tile);
        }

        return path;
    }

    // Helper Functions
    public void dijkstra(ArrayList<Tile> vertices, ArrayList<Graph.Edge> edges, Tile start) {

        initSingleSource(vertices, start);
        TilePriorityQ pq = new TilePriorityQ(this.g.getVertexList());

        while (!pq.isEmpty()) {

            Tile min = pq.removeMin();

            for (Graph.Edge edge : this.g.getEdges(min)) {

                Tile vertex = edge.getEnd();
                double weight = edge.weight;

                relax(min, vertex, weight, pq);

            }
        }
    }


    public void initSingleSource(ArrayList<Tile> vertices, Tile start) {

        for (Tile tile : vertices) {
            tile.costEstimate = Integer.MAX_VALUE;
            tile.predecessor = null;

            distances.put(tile, Integer.MAX_VALUE);
            predecessors.put(tile, null);
        }
        start.costEstimate = 0;

        distances.put(start, 0);

    }

    public void relax(Tile min, Tile vertex, double weight, TilePriorityQ pq) {

        double distanceToNeighbour = distances.get(min) + weight;

        /*
        if (min.isStart) {
            System.out.print("START ");
        }
        else if (min.isDestination) {
            System.out.print("END ");
        }
        else {
            System.out.print("PATH ");
        }

        System.out.println(min + " " + "(" + distances.get(min) + " + " + weight + ")" + distanceToNeighbour + " < " + distances.get(vertex) + " " + vertex + " " + (distanceToNeighbour < distances.get(vertex)));
        */
        if (distanceToNeighbour < distances.get(vertex)) {

            distances.put(vertex, (int) distanceToNeighbour);
            predecessors.put(vertex, min);

            pq.updateKeys(vertex, min, (int) distanceToNeighbour);
            //System.out.println("RELAX " + vertex + " " + min + " " + distanceToNeighbour);

        }

    }

    public ArrayList<Tile> getPath() {



        ArrayList<Tile> path = new ArrayList<>();
        Tile end = null;

        for (Tile tile: this.g.getVertexList()) {

            if (tile.isDestination) {
                end = tile;
                break;
            }

        }

        Tile current = end;

        // Check if a path exists
        if (end == null || predecessors.get(current) == null) {
            return new ArrayList<>();
        }

        path.add(current);

        while (predecessors.get(current) != null) {
            current = predecessors.get(current);
            path.add(current);
        }

        int size = path.size();

        for (int i = 0; i < size / 2; i++) {
            Tile temp = path.get(i);
            path.set(i, path.get(size - i - 1));
            path.set(size - i - 1, temp);
        }

        return path;
    }

    public ArrayList<Tile> getPath(Tile start, Tile end) {

        ArrayList<Tile> path = new ArrayList<>();

        Tile currentTile = end;

        while (!currentTile.equals(start)) {
            path.add(currentTile);
            currentTile = predecessors.get(currentTile);
        }

        path.add(start);

        int size = path.size();

        for (int i = 0; i < size / 2; i++) {
            Tile temp = path.get(i);
            path.set(i, path.get(size - i - 1));
            path.set(size - i - 1, temp);
        }

        return path;

    }


}



