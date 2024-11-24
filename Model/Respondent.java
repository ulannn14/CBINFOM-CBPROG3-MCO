import java.time.LocalDate;

public class Respondent extends ProgramUser{
    private String name;
    private Date birthdate = new Date();
    private String phoneNumber;
    private Survey[] surveyHistory = new Survey[100];
    private int numSurveyHistory = 0;

    public Respondent(String username, String accountPassword, String securityQuestion, String securityPassword, int userType, String name, Date birthdate, String phoneNumber){
        this.username = username;
        this.accountPassword = accountPassword;
        this.securityQuestion = securityQuestion;
        this.securityPassword = securityPassword;
        this.userType = userType;
        this.name = name;
        this.birthdate = birthdate;
        this.phoneNumber = phoneNumber;
    }

    // SETTERS
    public void setName(String name){
        this.name = name;
    }

    public void setBirthdate(Date birthdate){
        this.birthdate = birthdate;
    }

    public void setPhoneNumber(String phoneNumber){
        this.phoneNumber = phoneNumber;
    }

    public void addSurvey(Survey survey){
        surveyHistory[numSurveyHistory] = new Survey();
        surveyHistory[numSurveyHistory] = survey;
        numSurveyHistory++;
    }

    // GETTERS
    public String getName(){
        return name;
    }

    public Date getBirthdate(){
        return birthdate;
    }

    public String getPhoneNumber(){
        return phoneNumber;
    }

    public Date getAge(){
        LocalDate today = LocalDate.now();
        int year = today.getYear();
        int month = today.getMonthValue();
        int day = today.getDayOfMonth();
        int age = year - birthdate.getYear();
        boolean flag;

        if (year >= birthdate.getYear()){
            if (year == birthdate.getYear()){
                if (month >= birthdate.getMonth()){
                    if (month == birthdate.getMonth()){
                        if (day >= birthdate.getDay()) flag = true;
                        else flag = false;
                    } else flag = true;
                } else flag = false;
            } else flag = true
        } else flag = false;
        
        if (!flag) age -= 1;
        return age;
    }

    public Survey getHistory(int historyIdx){
        return surveyHistory[historyIdx];
    }

    public void takeSurvey(){
        
    }
}