static class Node {
    private int id;        // Node identifier
    private String name;   // Node name
    private double zscore;    // Node zscore
    private ArrayList<Edge> connectedPlace = new ArrayList<>();

    public Node(int id, String name, int zscore) {
        this.id = id;
        this.name = name;
        this.zscore = zscore;
    }

    public void addNeighbor(int neighborID, int weight) {
        edge 
        connectedPlace.add(new Edge(neighborId, weight));
    }

    public String getName(){
        return this.name;
    }
}