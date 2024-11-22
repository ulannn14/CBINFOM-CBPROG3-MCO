//contain attributes and methods related to places.
public class Place extends Instance {

    // Attributes
    private int placeIdx; // Unique identifier for the place
    private String placeName; // Name of the place
	private int placeType; // 0 for Landmark, 1 for Street
    private Place[] connectedPlaces; // Array of connected places
    private int numConnectedPlaces; // Number of connected places

    // Constructor
    public Place(int placeIdx, String placeName, int placeType) {
        this.placeIdx = placeIdx;
        this.placeName = placeName;
		this.placeType = placeType;
        this.connectedPlaces = new Place[44]; // 19 landmarks, 25 streets = 44 places
        this.numConnectedPlaces = 0;
    }

    // Method to add a connected place
    public void addConnectedPlace(Place place) {
        if (numConnectedPlaces < connectedPlaces.length) {
            connectedPlaces[numConnectedPlaces] = place;
			numConnectedPlaces++;
        }
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