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
	private int placeType; // 0 for Landmark, 1 for Street
    private Place[] connectedPlaces; // Array of connected places
    private int numConnectedPlaces; // Number of connected places

    public Place() {
        
    }

    // Constructor
    public Place(int placeIdx, String placeName, int placeType) {
        this.placeIdx = placeIdx;
        this.placeName = placeName;
		this.placeType = placeType;
        this.connectedPlaces = new Place[44]; // 19 landmarks, 25 streets = 44 places
        this.numConnectedPlaces = 0;
    }

    public 

    // Method to add a connected place
    public void addConnectedPlace(Place place) {
        if (numConnectedPlaces < connectedPlaces.length) {
            connectedPlaces[numConnectedPlaces] = place;
			numConnectedPlaces++;
        }
    }

    public ArrayList<Integer> fetchPlaceIDs(){
        ArrayList<Integer> placeIDs = new ArrayList();
        int temp;

        try (Connection connection = createConnection();
             Statement statement = connection.createStatement();) {

            // Execute a Query
            String query = "SELECT * FROM Place";
            ResultSet resultSet = statement.executeQuery(query);

            while (resultSet.next()) {
                temp = resultSet.getInt("placeID");
                temp -= 1;
                placeIDs.add(temp);
            }
        } catch (SQLException e) {
            System.err.println("Error inserting data: " + e.getMessage());
        }

        return placeIDs;
    }

    public ArrayList<String> fetchPlaceNames(){
        ArrayList<String> placeNames = new ArrayList();
        String temp = "";

        try (Connection connection = createConnection();
             Statement statement = connection.createStatement();) {

            // Execute a Query
            String query = "SELECT S1.streetName AS streetName1, S2.streetName AS streetName2 " +
                            "FROM  Place P " +
                            "JOIN Streets S1 ON P.streetID1 = S1.streetID " +
                            "JOIN  Streets S2 ON P.streetID2 = S2.streetID";
            ResultSet resultSet = statement.executeQuery(query);

            while (resultSet.next()) {
                temp.concat(resultSet.getString("streetName1")).concat(" - ").concat(resultSet.getString("streetName2")).concat(" Intersection");
                placeNames.add(temp);
            }
        } catch (SQLException e) {
            System.err.println("Error inserting data: " + e.getMessage());
        }

        return placeNames;
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