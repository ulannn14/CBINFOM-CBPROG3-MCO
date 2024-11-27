import java.util.ArrayList;
import java.util.PriorityQueue;

class Paths {
    static class Node {
        int id;        // Node identifier
        String name;   // Node name
        double zscore;    // Node zscore
        ArrayList<Edge> connectedPlace = new ArrayList<>();

        Node(int id, String name, int zscore) {
            this.id = id;
            this.name = name;
            this.zscore = zscore;
        }

        void addNeighbor(int neighborID, int weight) {
            neighbors.add(new Edge(neighborId, weight));
        }

        String getName(){
            return this.name;
        }
    }

    static class Edge {
        int targetNode;
        double weight; // in meters

        Edge(int targetNode, int weight) {
            this.targetNode = targetNode;
            this.weight = weight;
        }
    }

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

    public static ArrayList<Integer> aStar(Node[] graph, int start, int goal, boolean ZScoreFilter, int criticalValue) {
        int[] distances = new int[graph.length];
        int[] previous = new int[graph.length];
        boolean[] visited = new boolean[graph.length];

        for (int i = 0; i < distances.length; i++) {
            distances[i] = Integer.MAX_VALUE;
            previous[i] = -1;
        }

        distances[start] = 0;
        PriorityQueue<State> queue = new PriorityQueue<>();
        queue.add(new State(start, 0));

        while (!queue.isEmpty()) {
            State current = queue.poll();
            int currentNode = current.node;

            if (!visited[currentNode]) {
                visited[currentNode] = true;

                for (Edge edge : graph[currentNode].connectedPlace) {
                    int neighbor = edge.targetNode;

                    if (!visited[neighbor] && (!ZScoreFilter || graph[neighbor].zscore < criticalValue)) {
                        int newCost = distances[currentNode] + edge.weight;

                        if (newCost < distances[neighbor]) {
                            distances[neighbor] = newCost;
                            previous[neighbor] = currentNode;
                            queue.add(new State(neighbor, newCost));
                        }
                    }
                }
            }
        }

        return constructPath(previous, start, goal);
    }

    private static ArrayList<Integer> constructPath(int[] previous, int start, int goal) {
        ArrayList<Integer> path = new ArrayList<>();

        for (int i = goal; i != -1; i = previous[i]) {
            path.add(0, i);
        }

        if (path.get(0) != start) {
            path.clear(); // No path found
        }

        return path;
    }

    private static String getPathDetails(ArrayList<Integer> path, Node[] graph) {
        String details = "";

        for (int i = 0; i < path.size(); i++) {
            if (i > 0) details.concat(" -> ");
                details.concat(graph[path.get(i)].name);
        }

        return details;
    }

    public static findPath(int start, ){
        // Create graph
        Node[] graph = new Node[71];
        int start, end;

        for(int i = 0; i < totalNodes; i++){
            //get in database
            graph[i] = new Node(i, nodeName, nodezscore);
            
            while(!all nodes connected are listed){
                //get in database
                graph[i].addNeighbor(i, node connected);       
            }
        }

        for(int i = 0; i < totalNodes; i++){
            if(startString.equalsIgnorecase(Node[i].getName))
                start = i;
            if(endString.equalsIgnorecase(Node[i].getName))
                end = i;
        }

        // Shortest path
        ArrayList<Integer> shortestPath = aStar(graph, start, goal, false, 0);
        System.out.println("Shortest Path: " + getPathDetails(shortestPath, graph));

        // Path with zScore 
        ArrayList<Integer> zScorePath = aStar(graph, start, goal, true, 3);
        if (zScorePath.isEmpty()) {
            System.out.println("No path exists where all nodes have a rating of at least 3.");
        } else {
            System.out.println("Path with minimum rating 3: " + getPathDetails(minRatingPath, graph));
        }
    }
}
