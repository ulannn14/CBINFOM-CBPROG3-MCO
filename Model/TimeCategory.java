package Model;


// edit time categories
public class TimeCategory extends Day {

    // Attributes
    private int timeIdx; // Unique identifier for the time
    private String timeName; // Name or description of the time (e.g., "Morning Rush Hour", etc.)

    // Constructor
    public TimeCategory (int timeIdx, String timeName) {
        this.timeIdx = timeIdx;
        this.timeName = getTimeNameFromIdx(timeIdx); // Convert the timeIdx to a human-readable time category
    }

    public TimeCategory () {
        
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
    public static String getTimeNameFromIdx(int timeId) {
        switch (timeId) {
            case 1:
                return "Morning Rush Hour [6:00 to 8:59]";
            case 2:
                return "Mid-morning [9:00 to 10:59]";
            case 3:
                return "Lunchtime [11:00 to 12:59]";
            case 4:
                return "Afternoon [13:00 to 16:59]";
            case 5:
                return "Night Rush Hour [17:00 to 21:59]";
            case 6:
                return "Rest of the Day [22:00 to 5:59]";
            default:
                return "Invalid time index";
        }        
    }

    public static int getTimeIndex(String timePeriod) {
    switch (timePeriod) {
        case "Morning Rush Hour [6:00 to 8:59]":
            return 1;
        case "Mid-morning [9:00 to 10:59]":
            return 2;
        case "Lunchtime [11:00 to 12:59]":
            return 3;
        case "Afternoon [13:00 to 16:59]":
            return 4;
        case "Night Rush Hour [17:00 to 21:59]":
            return 5;
        case "Rest of the Day [22:00 to 5:59]":
            return 6;
        default:
            return -1; // Invalid time period
    }
    }

}
