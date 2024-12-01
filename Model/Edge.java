package Model;

public class Edge {
    private int targetNode;
    private double weight;
    private int streetID;

    public Edge() {
        this.targetNode = -1;
        this.weight = 0.0;
    }

    public Edge(int targetNode, double weight, int streetID) {
        this.targetNode = targetNode;
        this.weight = weight;
        this.streetID = streetID;
    }

    public void setTargetNode(int targetNode) {
        this.targetNode = targetNode;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }

    public void setStreetID(int streetID) {
        this.streetID = streetID;
    }

    public int getTargetNode() {
        return targetNode;
    }

    public double getWeight() {
        return weight;
    }

    public int getStreetID() {
        return streetID;
    }
}
