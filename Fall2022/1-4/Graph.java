package finalproject;


import java.util.ArrayList;
import java.util.HashMap;
import finalproject.system.Tile;

public class Graph {
    // TODO level 2: Add fields that can help you implement this data type
    public ArrayList<Tile> vertexList;
    public HashMap<Tile, ArrayList<Edge>> edges;

    // TODO level 2: initialize and assign all variables inside the constructor
    public Graph(ArrayList<Tile> vertices) {
        if (vertices == null || vertices.isEmpty()) {
            throw new IllegalArgumentException("Vertices cannot be null or empty");
        }
        this.vertexList = vertices;
        this.edges = new HashMap<>();

        for (Tile vertex : vertices) {
            this.edges.put(vertex, new ArrayList<>());
        }

    }

    // TODO level 2: add an edge to the graph
    public void addEdge(Tile origin, Tile destination, double weight){
        if (origin == null || destination == null) {
            throw new IllegalArgumentException("Origin and destination cannot be null");
        }
        Edge edge = new Edge(origin, destination, weight);
        this.edges.get(origin).add(edge);
    }

    // TODO level 2: return a list of all edges in the graph
    public ArrayList<Edge> getAllEdges() {
        if (edges == null) {
            return null;
        }

        ArrayList<Edge> temp = new ArrayList<>();

        // Add all edges from the sets of edges for each vertex
        for (Tile vertex: vertexList) {
            for (Edge edge: edges.get(vertex)) {
                temp.add(edge);
            }
        }

        return temp;
    }

    // TODO level 2: return list of tiles adjacent to t
    public ArrayList<Tile> getNeighbors(Tile t) {
        if (edges == null) {
            return null;
        }

        ArrayList<Tile> temp = new ArrayList<>();

        for(Edge edge : this.edges.get(t)) {
            temp.add(edge.getEnd());
        }

        return temp;
    }

    // TODO level 2: return total cost for the input path

    public double computePathCost(ArrayList<Tile> path) {
        if (path == null || path.size() < 2) {
            return 0;
        }

        double total = 0;

        for (int i = 0; i < path.size() - 1; i++) {

            for(Edge edge: this.edges.get(path.get(i))) {

                if (edge.getEnd() == path.get(i + 1)) {
                    total += edge.weight;
                    break;
                }
            }
        }

        return total;
    }


    // Helper function
    public ArrayList<Tile> getVertexList() {
        return vertexList;
    }

    public ArrayList<Edge> getEdges(Tile vertex) {
        return edges.get(vertex);
    }


    public static class Edge{
        Tile origin;
        Tile destination;
        double weight;

        // TODO level 2: initialize appropriate fields
        public Edge(Tile s, Tile d, double cost){
            if (s == null || d == null) {
                throw new IllegalArgumentException("Origin and destination cannot be null");
            }
            this.origin = s;
            this.destination = d;
            this.weight = cost;
        }

        // TODO level 2: getter function 1
        public Tile getStart(){
            if (origin == null) {
                return null;
            }
            return origin;
        }

        // TODO level 2: getter function 2
        public Tile getEnd() {
            if (destination == null) {
                return null;
            }
            return destination;
        }

    }

}
