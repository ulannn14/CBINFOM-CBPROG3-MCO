package Model;

public class DateClass {
    private int year;
    private int month;
    private int day;

    public DateClass() {
        
    }

    public DateClass(int month, int day, int year) {
        this.month = month;
        this.day = day;
        this.year = year;
    }

    public DateClass(String month, String day, String year) {
        this.month = Integer.parseInt(month);
        this.day = Integer.parseInt(day);
        this.year = Integer.parseInt(year);
    }

    public void setYear(int year){
        this.year = year;
    }

    public void setMonth(int month){
        this.month = month;
    }

    public void setDay(int day){
        this.day = day;
    }

    public int getYear(){
        return year;
    }

    public int getMonth(){
        return month;
    }

    public int getDay(){
        return day;
    }

    public static DateClass convertToDate (String wholeBirthDate) {
        // convert to date
        //return a DateClass;
        return null;
    }

    public String convertToString() {
        String monthName, whole;
        
        switch (month) {
            case 1: monthName = "January"; break;
            case 2: monthName = "February"; break;
            case 3: monthName = "March"; break;
            case 4: monthName = "April"; break;
            case 5: monthName = "May"; break;
            case 6: monthName = "June"; break;
            case 7: monthName = "July"; break;
            case 8: monthName = "August"; break;
            case 9: monthName = "September"; break;
            case 10: monthName = "October"; break;
            case 11: monthName = "November"; break;
            case 12: monthName = "December"; break;
            default: monthName = "Invalid month"; break;
        }

        whole = monthName + " " + day + ", " + year;
        return whole;
    }
    
// 
    public String convertToStringNumber () {
        String temp = (String.format("%02d", month).concat("/").concat(String.format("%02d", day)).concat("/").concat(String.format("%02d", year)));
        return temp;
    }
}