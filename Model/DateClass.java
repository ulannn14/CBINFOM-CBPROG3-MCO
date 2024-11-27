package Model;

public class DateClass {
    private int year;
    private int month;
    private int day;

    public DateClass(int month, int day, int year) {
        this.month = month;
        this.day = day;
        this.year = year;
    }

    public DateClass(String month, String day, String year) {
        // algo to onvert to int 
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
}