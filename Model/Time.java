package Model;


// edit time categories
public class Time extends Instance {

    // Attributes
    private int timeIdx; // Unique identifier for the time
    private String timeName; // Name or description of the time (e.g., "Morning Rush Hour", etc.)

    // Constructor
    public Time(int timeIdx) {
        this.timeIdx = timeIdx;
        this.timeName = getTimeNameFromIdx(timeIdx); // Convert the timeIdx to a human-readable time category
    }

    // Getter for timeIdx
    public int getTimeIdx() {
        return timeIdx;
    }

    // Getter for timeName
    public String getTimeName() {
        return timeName;
    }

    // Convert timeIdx to timeName
    public static String getTimeNameFromIdx(int timeIdx) {
        switch (timeIdx) {
            case 0: return "Morning Rush Hour";
            case 1: return "";
            case 2: return "";
            case 3: return "";
			case 4: return "";
			case 5: return "";
            default: return "Invalid time index";
        }
    }
}
