package Model;

import java.util.ArrayList;
import java.util.Scanner;

public class Analyst extends ProgramUser {

    public Analyst() {

    }

    public Analyst(String username, String accountPassword, String securityQuestion, String securityPassword, int userType) {
        this.username = username;
        this.accountPassword = accountPassword;
        this.securityQuestion = securityQuestion;
        this.securityPassword = securityPassword;
        this.userType = userType;

        // add to databse
            // count how many existing user
            // make the following: username<Count+1>, accountPassword: analyst12345, securityQuestion: first question sa dropdown, security password: analyst12345, userType: 2 
    }

    public Analyst fetchUser(String username, String password) {
        // find the matched username and password of userType = 2 in the database
            // return null if wala,
            // return the whole user if meron (populate an instance of the user then return it)
    }

    public static ArrayList<Analyst> fetchallAnalysts() {
        // database, return all analyst
    }    

}