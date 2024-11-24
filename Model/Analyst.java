package Model;

import java.util.Scanner;

public class Analyst{
    private boolean loginStatus = false;

    public Analyst(String username, String accountPassword, int userType){
        this.username = username;
        this.accountPassword = accountPassword;
        this.userType = userType;
    }

    public void firstLogin(){
        System.out.println("Enter security question: ");
        securityQuestion = scan.nextLine(); scan.nextLine();
        System.out.println("Enter security password: ");
        securityPassword = scan.nextLine(); scan.nextLine();
        loginStatus = true;
    }

    public void generateAnalystResult(){

    }

    public void addTag(){

    }

    public void editTag(){

    }

    public void deleteTag(){

    }

    public void addCommentSummary(){

    }

    public void editCommentSummary(){

    }

    public void deleteCommentSummary(){

    }
}