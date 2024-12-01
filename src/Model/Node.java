package Model;

import java.util.ArrayList;

public class Node {
    private int nodeID, streetID1, streetID2;
    private String nodeName;
    private double streetZScore1, streetZScore2;
    private ArrayList<Edge> connectedPlace = new ArrayList<>();

    public Node() {
        
    }

    public Node(int nodeID) {
        this.nodeID = nodeID;
    }

    public Node(int nodeID, String nodeName, int streetID1, double streetZScore1, int streetID2, double streetZScore2) {
        this.nodeID = nodeID;
        this.nodeName = nodeName;
        this.streetID1 = streetID1;
        this.streetZScore1 = streetZScore1;
        this.streetID2 = streetID2;
        this.streetZScore2 = streetZScore2;
    }

    public void addNeighbor(int targetNode, double weight, int streetID) {
        if (connectedPlace == null) {
            connectedPlace = new ArrayList<>();
        }
        connectedPlace.add(new Edge(targetNode, weight, streetID));
    }

    public void setNodeID(int nodeID) {
        this.nodeID = nodeID;
    }

    public void setNodeName(String nodeName) {
        this.nodeName = nodeName;
    }

    public void setStreet1ID(int streetID1) {
        this.streetID1 = streetID1;
    }
    
    public void setStreet2ID(int streetID2) {
        this.streetID2 = streetID2;
    }

    public void setStreet1ZScore(int streetID1, double streetZScore1) {
        if (this.streetID1 == streetID1) 
            this.streetZScore1 = streetZScore1;
    }

    public void setStreet2ZScore(int streetID2, double streetZScore2) {
        if (this.streetID2 == streetID2) 
            this.streetZScore2 = streetZScore2;
    }

    public int getNodeID() {
        return nodeID;
    }

    public int getNodeID(String nodeName) {
        if(this.nodeName.equals(nodeName))
            return nodeID;
        else
            return -1;
    }

    public String getNodeName() {
        return nodeName;
    }

    public String getNodeName(int nodeID) {
        if(this.nodeID == nodeID)
            return nodeName;
        else 
            return null;
    }

    public int getStreet1ID() {
        return streetID1;
    }

    public int getStreet2ID() {
        return streetID2;
    }

    public double getStreetZScore(int streetID) {
        if (streetID == streetID1) return streetZScore1;
        if (streetID == streetID2) return streetZScore2;
        return Double.NEGATIVE_INFINITY;
    }

    public ArrayList<Edge> getConnectedPlaces() {
        return connectedPlace;
    }
}
