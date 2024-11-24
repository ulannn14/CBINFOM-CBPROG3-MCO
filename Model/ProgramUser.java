package Model;

/*
Program User: abstract class of static fetchUser to find user in the database, then adapt to the three subclasses
            --- return respective objects, null if not found
*/ 

import java.util.Scanner;

public class ProgramUser extends DatabaseConnection{
    protected String username;
    protected String accountPassword;
    protected String securityQuestion;
    protected String securityPassword;
    protected int userType;
    protected Scanner scan = new Scanner(System.in);

    // SETTERS
    public void setUsername(String username){
        this.username = username;
    }

    public void setAccountPassword(String accountPassword){
        this.accountPassword = accountPassword;
    }

    public void setSecurityQuestion(String securityQuestion){
        this.securityQuestion = securityQuestion;
    }

    public void setSecurityPassword(String securityPassword){
        this.securityPassword = securityPassword;
    }

    public void setUserType(int userType){
        this.userType = userType;
    }

    public void changePassword(){
        String inpSecurityPassword;
        System.out.println(securityQuestion);
        do{
            System.out.print("Enter security password: ");
            inpSecurityPassword = scan.nextLine(); scan.nextLine();
            if (inpSecurityPassword != this.securityPassword)
                System.out.println("Incorrect. Please try again.\n");
        } while (inpSecurityPassword != this.securityPassword);
        
        System.out.print("Enter new password: ");
        this.accountPassword = scan.nextLine();
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
}