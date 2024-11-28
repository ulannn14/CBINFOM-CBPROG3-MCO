package Pathfinding;

public class Edge {
    public int targetNode;
    public double weight; // in meters

    public Edge() {
        // default constructor
    }

    public Edge(int targetNode, double weight) {
        this.targetNode = targetNode;
        this.weight = weight;
    }
}