package Model;

public class State implements Comparable<State> {
    private int node;
    private double cost;
    private int street;

    public State() {
        
    }

    public State(int node, double cost, int street) {
        this.node = node;
        this.cost = cost;
        this.street = street;
    }

    public void setNode(int node) {
        this.node = node;
    }

    public void setCost(double cost) {
        this.cost = cost;
    }

    public void setStreet(int street) {
        this.street = street;
    }

    public int getNode() {
        return node;
    }

    public double getCost() {
        return cost;
    }

    public int getStreet() {
        return street;
    }

    @Override
    public int compareTo(State other) {
        return Double.compare(this.cost, other.cost);
    }
}
