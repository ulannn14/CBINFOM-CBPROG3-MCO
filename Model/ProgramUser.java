package Model;

import java.util.ArrayList;
import DatabaseConnection.*;
/*
Program User: abstract class of static fetchUser to find user in the database, then adapt to the three subclasses
            --- return respective objects, null if not found
*/ 

import java.util.Scanner;

abstract public class ProgramUser extends DatabaseConnection {
    protected String username;
    protected String accountPassword;
    protected String securityQuestion;
    protected String securityPassword;
    protected int userType;

    // SETTERS
    public void setUsername(String username){
        this.username = username;
    }

    public void setAccountPassword(String accountPassword){

        // used in changing the password, so must also reflect in the database

        this.accountPassword = accountPassword;
    }

    public void setSecurityQuestion(String securityQuestion){
        this.securityQuestion = securityQuestion;
                // changed in the program,, so must also reflect in the database
    }

    public void setSecurityPassword(String securityPassword){
        this.securityPassword = securityPassword;
                // changed in the program, so must also reflect in the database

    }

    public void setUserType(int userType){
        this.userType = userType;
    }

    // GETTERS
    public String getUsername(){
        return username;
    }

    public String getAccountPassword(){
        return accountPassword;
    }

    public String getSecurityQuestion(){
        return securityQuestion;
    }

    public String getSecurityPassword(){
        return securityPassword;
    }

    public int getUserType(){
        return userType;
    }

    public abstract <User extends ProgramUser> User fetchUser(String username, String password);

    // static since we cannot create an instance of 
    public static int changePassword(String username, String newPassword, String securityQuestion, String securityPassword) {
        /*
                 static changePassword that has 4 parameters, 
                 1. check the database if a user exists and tama yung 2 securty credentials
                 2. if yes, update the password in the database
            --- return the following (1. Successful - match lahat, 2. No username exists, 3. Mismatch security question, 4. Wrong Security Password, in that hierarchy)
         */
        return -1;
    }

    public static boolean checkUsernameValid(String username) {
        /* 
            static checkUsernameValid (boolean)
            --- false if already exist
        */
        return false;
    }





}