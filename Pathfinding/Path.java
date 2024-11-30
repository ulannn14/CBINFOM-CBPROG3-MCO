package Pathfinding;

import DatabaseConnection.*;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.PriorityQueue;

/* In controller: 
            Path shortestPath = new Path(start, goal);
            Path zScorePath = new Path(start, goal);
            String shortestPathString;
            String zScorePathString;

            shortestPath.modifiedAStar(false, 0);
            zScorePath.modifiedAStar(true, 3);
            shortestPathString = shortestPath.getPathDetails(shortestPath.getPath);
            zScorePathString = zScorePath.getPathDetails(zScorePath.getPath);

            In view:

            System.out.println("Shortest Path: " + shortestPathString);
            
            if (zScorePath.getPath().isEmpty()) {
                System.out.println("No path exists where all nodes have a rating of at least 3.");
            } else {
                System.out.println("Path with minimum rating 3: " + zScorePathString);
            }
*/


// In model:
public class Path extends DatabaseConnection {
    private static final int maxPlaces = 71;
    private static int start, goal, day, time;
    private static Node[] graph = new Node[maxPlaces];
    private static ArrayList<Integer> path = new ArrayList<>();

    public Path() {
        // default constructor
    }

    // instantiate with places to start and end as well as the selected day and time
    public Path(int start, int goal, int day, int time) {
        this.start = start;
        this.goal = goal;
        this.day = day;
        this.time = time;

        createQuery();
    }

    @Override
    public void createQuery() {
        try (Connection connection = createConnection();
             Statement statement = connection.createStatement();) {

            // Execute a Query
            String query = "SELECT P.placeID, S1.streetName AS streetName1, S2.streetName AS streetName2, E.connectedPlaceID, E.weight " +
                            "FROM  Instance I " +
                            "JOIN Place P ON I.placeID = P.placeID " +
                            "JOIN Streets S1 ON P.streetID1 = S1.streetID " +
                            "JOIN  Streets S2 ON P.streetID2 = S2.streetID " +
                            "LEFT JOIN Edge E ON P.placeID = E.placeID" + 
                            "WHERE I.dayID = 1 AND I.timeID = 1";
            ResultSet resultSet = statement.executeQuery(query);

            int nodeID, connectedPlaceID;
            String nodeName = "", str1, str2;
            double nodeZscore, weight;
           
            while (resultSet.next()) {
                nodeID = resultSet.getInt("placeID");
                nodeID -= 1; // indexing in sql always starts at 1
                str1 = resultSet.getString("streetName1");
                str2 = resultSet.getString("streetName2");
                connectedPlaceID = resultSet.getInt("connectedPlaceID");
                weight = resultSet.getDouble("weight");
                nodeName = str1.concat(" - ").concat(str2).concat(" Intersection");
                nodeZscore = resultSet.getDouble("zScore");
                graph[nodeID] = new Node(nodeID, nodeName, nodeZscore);
                graph[nodeID].addNeighbor(connectedPlaceID, weight);
            }
        } catch (SQLException e) {
            System.err.println("Error inserting data: " + e.getMessage());
        }
    }
    
    public static void modifiedAStar(boolean ZScoreFilter, int criticalValue) {
        double[] distances = new double[graph.length];
        int[] previous = new int[graph.length];
        boolean[] visited = new boolean[graph.length];

        for (int i = 0; i < distances.length; i++) {
            distances[i] = Double.MAX_VALUE;
            previous[i] = -1;
        }

        distances[start] = 0; // set distance of the start node to 0
        PriorityQueue<State> queue = new PriorityQueue<>(); // create priority queue of states
        queue.add(new State(start, 0)); // add state of the start node

        while (!queue.isEmpty()) {
            State current = queue.poll(); // get and remove head of the queue
            int currentNode = current.node;

            if (!visited[currentNode]) {
                visited[currentNode] = true;

                for (Edge edge : graph[currentNode].connectedPlace) {
                    int neighbor = edge.targetNode;

                    if (!visited[neighbor] && (!ZScoreFilter || graph[neighbor].zscore < criticalValue)) {
                        double newCost = distances[currentNode] + edge.weight;

                        if (newCost < distances[neighbor]) {
                            distances[neighbor] = newCost;
                            previous[neighbor] = currentNode;
                            queue.add(new State(neighbor, newCost));
                        }
                    }
                }
            }
        }

        for (int i = goal; i != -1; i = previous[i]) {
            path.add(0, i);
        }

        if (path.get(0) != start) {
            path.clear();
        }
    }

    public ArrayList<Integer> getPath() {
        return path;
    }

    /*
    private static String getPathDetails(ArrayList<Integer> path) {
        String details = "";

        for (int i = 0; i < path.size(); i++) {
            if (i > 0) details.concat(" -> ");
                details.concat(graph[path.get(i)].name);
        }

        return details;
    } */
}