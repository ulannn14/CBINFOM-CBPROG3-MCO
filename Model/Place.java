package Model;

import DatabaseConnection.*;

import java.util.ArrayList;
import java.sql.SQLException;
import java.sql.DriverManager;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

//contain attributes and methods related to places.
public class Place extends DatabaseConnection{

    // Attributes
    private int placeIdx; // Unique identifier for the place
    private String placeName; // Name of the place

    public Place() {
        
    }

    // Constructor
    public Place(int placeIdx, String placeName) {
        this.placeIdx = placeIdx;
        this.placeName = placeName;
    }

    // Method to add a connected place
    public void addConnectedPlace(Place place) {
        if (numConnectedPlaces < connectedPlaces.length) {
            connectedPlaces[numConnectedPlaces] = place;
			numConnectedPlaces++;
        }
    }

    public ArrayList<Integer> fetchPlaceIDs(){
        ArrayList<Integer> streetIDs = new ArrayList();
        int temp;

        try (Connection connection = createConnection();
             Statement statement = connection.createStatement();) {

            // Execute a Query
            String query = "SELECT * FROM Streets";
            ResultSet resultSet = statement.executeQuery(query);

            while (resultSet.next()) {
                temp = resultSet.getInt("streetID");
                temp -= 1;
                streetIDs.add(temp);
            }
        } catch (SQLException e) {
            System.err.println("Error inserting data: " + e.getMessage());
        }

        return streetIDs;
    }

    public static ArrayList<String> fetchPlaceNames(){
        ArrayList<String> streetNames = new ArrayList<>();
        String temp = "";

        try (Connection connection = createConnection();
             Statement statement = connection.createStatement();) {

            // Execute a Query
            String query = "SELECT * FROM Streets";
            ResultSet resultSet = statement.executeQuery(query);

            while (resultSet.next()) {
                temp = resultSet.getString("streetName");
                streetNames.add(temp);
            }
        } catch (SQLException e) {
            System.err.println("Error inserting data: " + e.getMessage());
        }

        return streetNames;
    }

    public void setPlaceIdx(int placeIdx) {
        this.placeIdx = placeIdx;
    }

    public void setPlaceName(String placeName) {
        this.placeName = placeName;
    }

    // Getter for placeIdx
    public int getPlaceIdx() {
        return placeIdx;
    }

    // Getter for placeName
    public String getPlaceName() {
        return placeName;
    }
	
	 // Getter for placeType (Landmark or Street)
    public int getPlaceType() {
        return placeType;
    }

    // Getter for numConnectedPlaces
    public int getNumConnectedPlaces() {
        return numConnectedPlaces;
    }

    // Getter for a specific connected place by index
    public Place getConnectedPlace(int connectedPlaceIdx) {
        if (connectedPlaceIdx < 0 || connectedPlaceIdx >= numConnectedPlaces) {
			System.out.println("Error: Invalid index for connected place.");
			return null; // Return null if the index is invalid
		}
		else {
			return connectedPlaces[connectedPlaceIdx];
		}
       
    }
	
}