package Model;

import DatabaseConnection.*; 

import java.sql.SQLException;
import java.util.ArrayList;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.PriorityQueue;

import DatabaseConnection.DatabaseConnection;

public class Path extends DatabaseConnection {
    private final static int maxPlaces = 72;
    private static Node[] graph = new Node[maxPlaces];
    private static ArrayList<Integer> pathByZScore = new ArrayList<>();
    private static ArrayList<Integer> shortestPath = new ArrayList<>();
    private int day, time, startNode, goalNode;
    private double criticalValue;


    public static Node[] getAllNodes() {
        return graph.clone();
    }

    public Path() {

    }

    public Path(int day, int time, int startNode, int goalNode, double criticalValue) {
        this.day = day;
        this.time = time;
        this.startNode = startNode;
        this.goalNode = goalNode;
        this.criticalValue = criticalValue;

        createQuery();
        findPathWithZScore(startNode, goalNode, criticalValue);
        findShortestPath(startNode, goalNode);
    }

    @Override
    public void createQuery() {
        try (Connection connection = createConnection();
            Statement statement = connection.createStatement()) {

            statement.executeUpdate("USE ISSPa");

            String query = "SELECT N.nodeID, S1.streetID AS streetID1, S2.streetID AS streetID2, " +
                "E.connectedNodeID, E.weight, E.streetID, " +
                "S1_z.zScore AS zScore1, S2_z.zScore AS zScore2, S1.streetName AS streetName1, S2.streetName AS streetName2 " +
                "FROM Node N " +
                "JOIN Streets S1 ON N.streetID1 = S1.streetID " +
                "JOIN Streets S2 ON N.streetID2 = S2.streetID " +
                "LEFT JOIN Edge E ON N.nodeID = E.nodeID " +
                "LEFT JOIN Instance S1_z ON S1_z.streetID = N.streetID1 AND S1_z.dayID = " + day + " AND S1_z.timeID = " + time + " " +
                "LEFT JOIN Instance S2_z ON S2_z.streetID = N.streetID2 AND S2_z.dayID = " + day + " AND S2_z.timeID = " + time + " " +
                "WHERE EXISTS ( " +
                "    SELECT 1 FROM Instance I " +
                "    WHERE (I.streetID = N.streetID1 OR I.streetID = N.streetID2) " +
                "    AND I.dayID = " + day + " AND I.timeID = " + time + " " +
                ")";

            ResultSet resultSet = statement.executeQuery(query);

            System.out.println(query);
          for (int i = 0; i < graph.length; i++) {
                graph[i] = new Node(i);
            }

            while (resultSet.next()) {
                int nodeID = resultSet.getInt("nodeID") - 1;
                int streetID1 = resultSet.getInt("streetID1");
                int streetID2 = resultSet.getInt("streetID2");
                double zscore1 = resultSet.getDouble("zScore1");
                double zscore2 = resultSet.getDouble("zScore2");
                String streetName1 = resultSet.getString("streetName1");
                String streetName2 = resultSet.getString("streetName2");
                String nodeName = streetName1.concat(" - ").concat(streetName2);

                if (graph[nodeID] == null) {
                    graph[nodeID] = new Node(nodeID, nodeName, streetID1, zscore1, streetID2, zscore2);
                } else {
                    graph[nodeID].setNodeName(nodeName);
                    graph[nodeID].setStreet1ID(streetID1);
                    graph[nodeID].setStreet1ZScore(streetID1, zscore1);
                    graph[nodeID].setStreet2ID(streetID2);
                    graph[nodeID].setStreet2ZScore(streetID2, zscore2);
                }

                System.out.println(nodeName);
                int connectedNodeID = resultSet.getInt("connectedNodeID");
                if (connectedNodeID > 0) {
                    double weight = resultSet.getDouble("weight");
                    int streetID = resultSet.getInt("streetID");
                    connectedNodeID -= 1;

                    graph[nodeID].addNeighbor(connectedNodeID, weight, streetID);

                    if (graph[connectedNodeID] == null) {
                        graph[connectedNodeID] = new Node(connectedNodeID);
                    }

                    graph[connectedNodeID].addNeighbor(nodeID, weight, streetID);
                }
            }

            connection.close();
        } catch (SQLException e) {
            System.err.println("Error loading data: " + e.getMessage());
        }
    }

    private void findPathWithZScore(int startNode, int goalNode, double criticalValue) {
        double[] distances = new double[graph.length];
        int[] previous = new int[graph.length];
        boolean[] visited = new boolean[graph.length];
    
        for (int i = 0; i < graph.length; i++) {
            distances[i] = Double.MAX_VALUE;
            previous[i] = -1;
        }

        distances[startNode] = 0;
        PriorityQueue<State> queue = new PriorityQueue<>();
        queue.add(new State(startNode, 0, -1));
    
        boolean goalReached = false;
    
        while (!queue.isEmpty() && !goalReached) {
            State current = queue.poll();
            int currentNode = current.getNode();

    
            if (!visited[currentNode]) {
                visited[currentNode] = true;
    
                goalReached = (currentNode == goalNode);
    
                if (!goalReached) {
                    for (Edge edge : graph[currentNode].getConnectedPlaces()) {
                        int neighbor = edge.getTargetNode();
                        Node neighborNode = graph[neighbor];
    
                        int streetID = edge.getStreetID();
    
                        boolean isSafeStreet = neighborNode.getStreetZScore(streetID) >= criticalValue;
    
                        if (!visited[neighbor] && isSafeStreet) {
                            double newCost = distances[currentNode] + edge.getWeight() + neighborNode.getStreetZScore(streetID);
                            if (newCost < distances[neighbor]) {
                                distances[neighbor] = newCost;
                                previous[neighbor] = currentNode;
                                queue.add(new State(neighbor, newCost, streetID));
                            }
                        }
                    }
                }
            }
        }

        pathByZScore.clear();
        
        int currentNode = goalNode;
        while (currentNode != -1) {
            pathByZScore.add(0, currentNode);
            currentNode = previous[currentNode];
        }

        for (int i = 0; i < pathByZScore.size(); i++) {
            pathByZScore.set(i, pathByZScore.get(i) + 1);
        }

        if(pathByZScore.size() == 1){
            pathByZScore.clear();   
        }
    }    

    private void findShortestPath(int startNode, int goalNode) {
        double[] distances = new double[graph.length];
        int[] previous = new int[graph.length];
        boolean[] visited = new boolean[graph.length];
        for (int i = 0; i < graph.length; i++) {
            distances[i] = Double.MAX_VALUE;
            previous[i] = -1;
        }
    
        distances[startNode] = 0;
        PriorityQueue<State> queue = new PriorityQueue<>();
        queue.add(new State(startNode, 0, -1));
    
        boolean goalReached = false;
    
        while (!queue.isEmpty() && !goalReached) {
            State current = queue.poll();
            int currentNode = current.getNode();
    
            if (!visited[currentNode]) {
                visited[currentNode] = true;
    
                goalReached = (currentNode == goalNode);

                if (!goalReached) {
                    for (Edge edge : graph[currentNode].getConnectedPlaces()) {
                        int neighbor = edge.getTargetNode();
    
                        if (!visited[neighbor]) {
                            double newCost = distances[currentNode] + edge.getWeight();
                            if (newCost < distances[neighbor]) {
                                distances[neighbor] = newCost;
                                previous[neighbor] = currentNode;
                                queue.add(new State(neighbor, newCost, -1));
                            }
                        }
                    }
                }
            }
        }
    
        shortestPath.clear();
        int currentNode = goalNode;
        while (currentNode != -1) {
            shortestPath.add(0, currentNode);
            currentNode = previous[currentNode];
        }

        for (int i = 0; i < shortestPath.size(); i++) {
            shortestPath.set(i, shortestPath.get(i) + 1);
        }
    }

    public ArrayList<Integer> getPathByZScore() {
        return pathByZScore;
    }
    

    public ArrayList<Integer> getShortestPath() {
        return shortestPath;
    }
}