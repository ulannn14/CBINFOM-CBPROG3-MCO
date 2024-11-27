package Model;

//represent specific days associated with a place.
public class Day extends Place {
    private int dayIdx; // Numeric index for the day (0 = Monday, 1 = Tuesday, ..., 6 = Sunday)
    private String dayName; // The name of the day (e.g., "Monday", "Tuesday")

    // Constructor
    public Day(int dayIdx, String dayName) {
        this.dayIdx = dayIdx;
        this.dayName = dayName;
    }

    // Getter for dayIdx
    public int getDayIdx() { return dayIdx; }
    public String getDayName() { return dayName; }
    public void setDayIdx(int x) { dayIdx = x; }
    public void setDayName(String x ) { dayName = x; }

    // Convert dayIdx to dayName
    public static String getDayNameFromIdx(int dayIdx) {
        switch (dayIdx) {
            case 0: return "Monday";
            case 1: return "Tuesday";
            case 2: return "Wednesday";
            case 3: return "Thursday";
            case 4: return "Friday";
            case 5: return "Saturday";
            case 6: return "Sunday";
            default: return "Invalid day index";
        }
    }

    // Convert dayName to dayIdx
    public static int getDayIdxFromName(String dayName) {
        switch (dayName.toLowerCase()) {
            case "monday": return 0;
            case "tuesday": return 1;
            case "wednesday": return 2;
            case "thursday": return 3;
            case "friday": return 4;
            case "saturday": return 5;
            case "sunday": return 6;
            default: return -1; // Return a default value indicating an error
        }
    }    
}
