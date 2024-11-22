package controller;

//import models

import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.Objects;
import java.util.stream.Collectors;

// progmraUser.login ; check datavase if nag-eexist, 0 if no, 1 admin to 3 respondent
// programUser.forgotPassword
// (Respondent class) everytime na instantiated siya, add to database na agad 

public class Controller {

    public void Homepage() {
        HomepageView homepageView = new HomepageView();
        int userType = 0; 

        // Login
        homepageView.setLoginButtonListener(e -> {
            do {
                String username = homepageView.getUsername();
                String password = homepageView.getPassword();
                userType = user1.login(username, password);
                if (userType == 0)
                    homepageView.failedLogin();
            } while (userType == 0);

            switch(userType) {
                case 1: AdminWelcome(username, password); homepageView.dispose(); break;
                case 2: AnalystWelcome(username, password); homepageView.dispose(); break;
                case 3: RespondentWelcome(username, password); homepageView.dispose(); break;
            }
        } );

        // Listeners
        homepageView.setSignupButtonListener(e -> { Signup(); homepageView.dispose(); } );
        homepageView.setForgotPasswordButtonListener(e -> { ForgotPassword(); homepageView.dispose(); } );
        homepageView.setFindShortestPathButtonListener(e -> { FindShortestPath(); homepageView.dispose(); } );
        homepageView.setViewSurveyDataButtonListener(e -> { ViewSurveyData(); homepageView.dispose(); } );
    }



    public void Signup() {
        SignupView signupView = new SignupView();

        signupView.setSubmitButtonListener(e -> {
            if (signupView.validateForm() == true) {
                String name = signupView.getName();
                String email = signupView.getEmail();
                String username = signupView.getUsername();
                String password = signupView.getPassword();
                String securityQuestion = signupView.getSecurityQuestion();
                String securityPassword = signupView.getSecurityPassword();
                Date birthday = signupView.getBirthday();
                //ASK LIAN// Respondent newRes = new Respondent(null, null, null, null, 0, null, null, null)
                
                signupView.dispose();
                Homepage();
            }
        } );

        signupView.setCancelButtonListener(e -> {
            signupView.dispose(); // Close the signup view
            Homepage();
        } );
    }

    

    public void ForgotPassword() {

    }



}