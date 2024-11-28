package Pathfinding;

public class State implements Comparable<State> {
    public int node;
    public double cost;

    public State() {
        // default constructor
    }
    
    public State(int node, double cost) {
        this.node = node;
        this.cost = cost;
    }

   @Override
    public int compareTo(State other) {
        return Double.compare(this.cost, other.cost);
    }

}