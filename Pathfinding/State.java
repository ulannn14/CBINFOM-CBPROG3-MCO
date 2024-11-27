static class State implements Comparable<State> {
    int node;
    int cost;

    State(int node, int cost) {
        this.node = node;
        this.cost = cost;
    }

    @Override
    public int compareTo(State other) {
        return Integer.compare(this.cost, other.cost);
    }
}