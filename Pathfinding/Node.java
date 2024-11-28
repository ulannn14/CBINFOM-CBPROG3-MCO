package Pathfinding;

import java.util.ArrayList;

public class Node {
    public int id;        // Node identifier
    public String name;   // Node name
    public double zscore;    // Node zscore
    public Edge neighbor = new Edge();
    public ArrayList<Edge> connectedPlace = new ArrayList<>();

    public Node() {
        // default constructor
    }

    public Node(int id, String name, double zscore) {
        this.id = id;
        this.name = name;
        this.zscore = zscore;
    }

    public void addNeighbor(int neighborID, double weight) {
        Edge edge = new Edge(neighborID, weight);
        connectedPlace.add(edge);
    }

}