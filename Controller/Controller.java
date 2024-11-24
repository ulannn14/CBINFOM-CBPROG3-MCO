package Controller;

import Model.*;
import View.*;

import java.security.Provider.Service;
import java.util.ArrayList;
import javax.management.InstanceAlreadyExistsException;

// Importing all classes from the model package
import view.*;   // Importing all classes from the view package

/*
Needed methods for the models:

- for setters, also change current value of the object not just the database
setName(string name) {
    this.name = name;
}

Respondent: In the constructor, add to database as soon as possible
            public static List<Respondent> fetchAllUsers()
            --- query all user then return the list
            fetchAllRespondents return all respondents
Program User: abstract method of static fetchUser to find user in the database, then adapt to the three subclasses
            --- return respective objects, null if not found
              static changePassword that has 4 parameters, 
                 1. check the database if a user exists and tama yung 2 securty credentials
                 2. if yes, update the password in the database
            --- return the following (1. Successful - match lahat, 2. No username exists, 3. Mismatch security question, 4. Wrong Security Password, in that hierarchy)
              static checkUsernameValid (boolean)
            --- false if already exist
              modify setters to also change it rin sa database
              static ProgramUser.fetchGeneraldata (see controller method guestviewdata)
            --- return set of filtered survey result asked by the user
Analyst: In the constructor, automatically add the following: analyst<n analyst>, analyst12345, firts sec question, analyst54321, also add sa dataase           
Instance: 
              Instance.fetchAllComplaintReports();       
    */ 



public class Controller {

    public class Controller {
        Service service = new Service();
    }

    public void Homepage() {
        HomepageView homepageView = new HomepageView();
        int userType = 0; 

        // Login
        homepageView.setLoginButtonListener(e -> {
            homepageView.setErrorMessages(false);
            
            String username = homepageView.getUsername();
            String password = homepageView.getPassword();

            Admin admin = Admin.fetchUser(username, password);
            if (admin != null) {
                homepageView.dispose();
                AdminWelcome(admin);
            }
            
            Analyst analyst = Analyst.fetchUser(username, password);
            if (analyst != null) {
                homepageView.dispose();
                AnalystWelcome(analyst);
            }

            Respondent respondent = Respondent.fetchUser(username, password);
            if (respondent != null) {
                homepageView.dispose();
                RespondentWelcome(respondent);
            }
            
            homepageView.failedLogin();

        } );

        // Listeners
        homepageView.setSignupButtonListener(e -> { Signup(); homepageView.dispose(); } );
        homepageView.setForgotPasswordButtonListener(e -> { ForgotPassword(); homepageView.dispose(); } );
        homepageView.setFindRecommendedPathButtonListener(e -> { FindShortestPath(); homepageView.dispose(); });
        homepageView.setGeneralResultButtonListener(e -> { ViewSurveyData(); homepageView.dispose(); } );
    }



    public void Signup() {
        SignupView signupView = new SignupView();

        signupView.setSignupListener(e -> {
            signupView.setErrorMessages(false);

            if (signupView.validateForm() == true) {
                String name = signupView.getName();
                String email = signupView.getEmail();
                String username = signupView.getUsername();
                String password = signupView.getPassword();
                String securityQuestion = signupView.getSecurityQuestion();
                String securityPassword = signupView.getSecurityPassword();
                Date birthdate = signupView.getBirthday();

                validUsername = ProgramUser.checkUsernameValid(username);

                if (validUsername) {
                    Respondent respondent = new Respondent(username, password, securityQuestion, securityPassword, 3, name, birthdate, email);
                    signupView.dispose();
                    RespondentWelcomeView(respondent);
                }
                else 
                    signupView.notUniqueUsername();
            }
        } );

        signupView.setBackButtonListener(e -> {
            signupView.dispose(); // Close the signup view
            Homepage();
        } );
    }

    
    // view for forgetPassword 
    // reason for overloading: uses the same view naman sila pero need lang ipasa user if change password to eliminate checking of database
    public void PasswordManager() {
        PasswordManagerView pmv = new PasswordManagerView();

        pmv.setChangePasswordButtonListener(e -> {
            pmv.setErrorMessages(false);
            String username = pmv.getUsername();
            String newPassword = pmv.getPassword();
            String securityQuestion = pmv.getSecurityQuestion();
            String securityPassword = pmv.getSecurityPassword();

            // if wala pa existing user
            int successful = ProgramUser.changePassword(username, newPassword, securityQuestion, securityPassword);
            int nextView;

            switch (successful)
            case 1: 
                nextView = pmv.validatePassword(newPassword);
                if (nextView) {
                    pmv.dispose(); Homepage();
                }
            case 2: pmv.wrongUsername(); break; // When no username exists 
            case 3: pmv.wrongSecurityQuestion(); break;
            case 4: pmv.wrongSecurityPassword(); break;

        } );

        pmv.setBackButtonListener(e -> {
            pmv.dispose();
            Homepage();
        } );
    }

    // change password
    public void PasswordManager(ProgramUser user) {
        PasswordManagerView pmv = new PasswordManagerView();

        pmv.setChangePasswordButtonListener(e -> { 
            pmv.setErrorMessages(false);
            String username = pmv.getUsername();
            String newPassword = pmv.getPassword();
            String securityQuestion = pmv.getSecurityQuestion();
            String securityPassword = pmv.getSecurityPassword();

            if (username.equals(user.getUsername) == true) {
                if (securityQuestion.equals(user.getSecurityQuestion) == true) {
                    if (getSecurityPassword.equals(user.getSecurityPassword) == true) {
                        if (pmv.validatePassword(newPassword) == true) {
                            user.setPassword(newPassword);
                            pmv.dispose();
                            if (user instanceof Admin)
                                AdminWelcome((Admin) user);
                            else if (user instanceof Analyst)
                                AnalystWelcome((Analyst) user);
                            else if (user instanceof Respondent)
                                RespondentWelcome((Respondent) user);
                        }
                    } else
                        pmv.wrongSecurityPassword();
                } else 
                    pmv.wrongSecurityPassword();
            } else
                pmv.wrongUsername();

        } );

        pmv.setBackButtonListener(e -> {
            if (user instanceof Admin)
                AdminWelcome((Admin) user);
            else if (user instanceof Analyst)
                AnalystWelcome((Analyst) user);
            else if (user instanceof Respondent)
                RespondentWelcome((Respondent) user);
        } );

    }
    
    public void ChangeSecurityView(ProgramUser user) {
        ChangeSecurityView csv = new ChangeSecurityView();
        boolean nextView = true;
        csv.setChangeSecurityDetailsButtonListener(e -> {
            pmw.setErrorMessages(false);
            String newSecurityQuestion = csv.getSecurityQuestion(); // get input
            String newSecurityPassword = csv.getSecurityPassword(); // get input
            String password = csv.getPassword(); // get input

            nextView = csv.validateSecurityQuestion(); // boolean
            nextView = csv.validateSecurityPassword(); // boolean
            if (password.equals(user.password()) == false) { // no return value
                wrongPassword();
                nextView = false
            }

            if (nextView) {
                // should be updated sa databse
                user.setSecurityQuestion();
                user.setSecurityPassword();
                csv.dispose();
                if (user instanceof Admin)
                    AdminWelcome((Admin) user);
                else if (user instanceof Analyst)
                    AnalystWelcome((Analyst) user);
                else if (user instanceof Respondent)
                    RespondentWelcome((Respondent) user);
            }
        } );
 
        csv.setBackButtonListener(e -> {
            if (user instanceof Admin)
                AdminWelcome((Admin) user);
            else if (user instanceof Analyst)
                AnalystWelcome((Analyst) user);
            else if (user instanceof Respondent)
                RespondentWelcome((Respondent) user);
        } );
    }  

    public void AdminWelcome(Admin admin) {
        AdminWelcomeView awv = new AdminWelcomeView();

        awv.setViewRespondentsDataButtonListener (e -> { AdminViewRespondentData(admin); awv.dispose(); } );
        awv.setViewAnalystsDataButtonListener (e -> { AdminViewAnalystData(admin); awv.dispose(); } );
        awv.setAddNewAnalystButtonListener (e -> { AdminAddAnalystView(admin); awv.dispose(); } );        
        awv.setViewComplaintReportsButtonListener(e -> { AdminViewComplaintReportsView(admin); awv.dispose(); } );
        awv.setChangeSecurityQuesAndPassButtonListener(e -> { ChangeSecurityView(admin); awv.dispose(); } );
        awv.setChangePasswordButtonListener(e -> { PasswordManager(admin); awv.dispose(); } );
        awv.setGenerateIncidentReportsButtonListener(e -> { AdminGenerateIncidentReport(admin); awv.dispose(); } );        
        awv.setFindRecommendedPathButtonListener(e-> { GuestChooseLRecommendedPath(admin); awv.dispose(); } );
        awv.setViewGeneralDataButtonListener(e-> {GuestSelectSurveyData(admin); awv.dispose(); } );       

        awv.setBackButtonListener(e -> {awv.dispose(); Homepage(); } );
    }

    public void AdminViewRespondentData(Admin admin) {
        ArrayList<Respondent> allRespondents = Respondent.fetchAllRespondents();        
        AdminViewRespondentDataView avrdv = new AdminViewRespondentDataView(allRespondents);
        
        avrdv.setBackButtonListener(e -> {
            avrdv.dispose();
            AdminWelcome(admin);
        } );
    }


    public void AdminViewAnalystData(Admin admin) {
        ArrayList<Analyst> allAnalysts = Respondent.fetchAllAnalysts();        
        adminViewAnalystDataView avadv = new adminViewAnalystDataView(allAnalysts);
        
        avadv.setBackButtonListener(e -> {
            avadv.dispose();
            AdminWelcome(admin);
        } );
    }

    public void AdminAddAnalystView(Admin admin) {
        adminAddAnalystView aaav = new adminAddAnalystView();
        boolean validUsername, validPassword;
        aaav.setChangePasswordButtonListener(e -> {
            aaav.setErrorMessages(false);        
            String aaav.getUsername();
            String aaav.getPassword();

            validUsername = ProgramUser.checkUsernameValid(username);
            validPassword = aaav.validatePassword(password);

            if (validUsername && validPassword) {
                Analyst analyst = new Analyst(); 
                aaav.dispose();
                AdminWelcome(admin);
            }           
            else if (!validUsername)
                aaav.notUniqueUsername();    
        } );

        avadv.setBackButtonListener(e -> {
            avadv.dispose();
            AdminWelcome(admin);
        } );
    }
    

    public void AdminViewComplaintReportsView(Admin admin) {
        ArrayList<String> AllComplaintReport = Instance.fetchAllComplaintReports();
        adminViewIncidentReportsView avirv = new adminViewIncidentReportsView(AllComplaintReport);

        avirv.setBackButtonListener(e -> {avrdv.dispose(); AdminWelcome(admin); } );
    }

    public void AdminGenerateIncidentReport(Admin admin) {
        AdminGenerateIncidentReportView agirv = new AdminGenerateIncidentReportView();
        agirv.setSubmitButtonListener(e -> {
            agirv.setErrorMessages(false);
            Recipient = agirv.getToField();
            Sender = agirv.getFromField();
            Body = agirv.getBodyField();

            if (agirv.validateFields() == true) {
                IncidentReport incidentReport = new IncidentReport(Recipient, Sender, Body);
                service.createIncidentReport(incidentReport);
                agirv.dispose(); 
                AdminWelcome(admin);
            }
        } ); 

        agirv.setBackButtonListener(e -> {agirv.dispose(); AdminWelcome(admin); } );
    }

    public void AnalystWelcome(Analyst analyst) {
        AnalystWelcomeView anwv = new AnalystWelcomeView();

        anwv.setViewSurveyDataButtonListener(e -> { AnalystViewSurveyData(analyst); anwv.dispose(); } );
        anwv.setAnalystGenerateComplaintReportButtonListener(e -> { AnalystGenerateComplaintReport(analyst); anwv.dispose(); } );
        anwv.setChangeSecurityQuesAndPassButtonListener(e -> { ChangeSecurityView(analyst); anwv.dispose(); } );
        anwv.setChangePasswordButtonListener(e -> { PasswordManager(analyst); anwv.dispose(); } );
        anwv.setFindRecommendedPathButtonListener(e-> { GuestChooseLRecommendedPath(analyst); anwv.dispose(); } );
        anwv.setViewGeneralDataButtonListener(e-> {GuestSelectSurveyData(analyst); anwv.dispose(); } );       

        anwv.setBackButtonListener(e -> {anwv.dispose(); Homepage(); } );
    }

    public void AnalystViewSurveyData(Analyst analyst) {
        ArrayList<Instance> AnalystSurveyData = Instance.fetchAnalystSurveyData();
        AnalystViewSurveyDataView avsdv = new AnalystViewSurveyDataView(AnalystSurveyData);
        
        avsdv.setModifyButtonListener(e -> {
            if (avsdv.validateInstanceID() == true) {
                int index = avsdv.getIndex();
                index--;
                Instance selectedInstance = AnalystSurveyData.get(index);
                analystModifyTagsAndComments(analyst, selectedInstance);
            }
        } );

        avsdv.setBackButtonListener(e -> {avsdv.dispose(); AnalystWelcome(analyst); } );
    }
    
    public void analystModifyTagsAndComments(Analyst analyst, Instance instance) {
        // need to have parameter instance to know which tag to display
        AnalystModifyTagsAndCommentsView amtacv = new AnalystModifyTagsAndCommentsView(instance);

        amtacv.setDeleteButtonListener(e -> { 
            amtacv.setErrorMessages(false);
            if (amtacv.validateDelete() == true) {
                ArrayList<int> tagsToDelete = amtacv.getTagsIDs();
                analyst.deleteTags(tagsToDelete);
                ArrayList<int> commentSummariesToDelete = amtacv.getCommentSummariesIDs();
                analyst.deleteCommentSummaries(commentSummariesToDelete); 
                amtacv.dispose(); 
                AnalystViewSurveyData(analyst);
            }
        } );

        amtacv.setAddTagButtonListener(e -> {
            amtacv.setErrorMessages(false); 
            if (amtacv.validateAddTag() == true) {
                ArrayList<String> newTags = amtacv.getNewTags();
                analyst.addNewTags(newTags);
                amtacv.dispose(); 
                AnalystViewSurveyData(Analyst analyst);
            }
        } );

        amtacv.setAddCommentButtonListener(e -> { 
            amtacv.setErrorMessages(false);
            if (amtacv.validateAddCS() == true) {
                String newCS = amtacv.getNewCS();
                analyst.addNewCS(newCS);
                amtacv.dispose(); 
                AnalystViewSurveyData(Analyst analyst);
            }
        } );        

        amtacv.setBackButtonListener(e -> {amtacv.dispose(); AnalystViewSurveyData(analyst); } );
    }

    public void AnalystGenerateComplaintReport(Analyst analyst) {
        AnalystGenerateComplaintReportView agcrv = new AnalystGenerateComplaintReportView();
        
        agcrv.setSubmitButtonListener(e -> { 
            agcrv.setErrorMessages(false);

            if (agcrv.validateFields() == true) {
                Instance instance = new Instance();
                instance.addNewComplaintReport(
                    agcrv.getLocation(), agcrv.getDay(), agcrv.getTime(), agcrv.getBody()
                );
                agcrv.dispose(); 
                AnalystWelcome(analyst);
            }

        } );
        
        agcrv.setBackButtonListener(e -> {agcrv.dispose(); AnalystWelcome(analyst); } );
    }

    
    public void RespondentWelcome(Respondent respondent) {
        RespondentWelcomeView rwv = new RespondentWelcomeView();

        rwv.setTakeSurveyButtonListener(e -> { rwv.dispose(); RespondentTakeSurvey(respondent); } );
        rwv.setViewSurveyHistoryButtonListener(e -> { rwv.dispose(); RespondentViewHistory(respondent); } );
        rwv.setViewOrUpdateProfileButtonListener(e -> { rwv.dispose(); RespondentProfile(respondent); } );
        rwv.setChangeSecurityQuesAndPassButtonListener(e -> { rwv.dispose(); ChangeSecurityView(respondent); } );
        rwv.setChangePasswordButtonListener(e -> { rwv.dispose(); PasswordManager(respondent); } );
        rwv.setFindRecommendedPathButtonListener(e-> { rwv.dispose(); GuestChooseLRecommendedPath(respondent); } );
        rwv.setViewGeneralDataButtonListener(e-> { rwv.dispose(); GuestSelectSurveyData(respondent); } );       

        rwv.setBackButtonListener(e -> {rwv.dispose(); Homepage(); } );
    }

    // mali pa to !!!
    public void RespondentTakeSurvey(Respondent respondent) {
        RespondentTakeSurveyView rtsv = new RespondentTakeSurveyView();
        Instance instance = new Instance();

        rtsv.setSubmitButtonListener(e -> { 
            rtsv.setErrorMessages(false);
            if (validateForm() == true) {
                Survey survey() = new Survey(rtsv.getLocation(), rtsv.getDay(), rtsv.getTime(), rtsv.getRatings(), rtsv.getComment(););
                instance.addSurvey(survey);
                rtsv.dispose();
                RespondentWelcome(respondent);
            }
        } );

        rtsv.setBackButtonListener(e -> {rtsv.dispose(); RespondentWelcome(respondent); } );
    }

    public void RespondentViewHistory(Respondent respondent) {
        ArrayList<Survey> surveys = service.fetchSurveysForRespondent(respondent.getID());
        RespondentViewHistoryView rvhv = new RespondentViewHistoryView(surveys);
       
        rvhv.setBackButtonListener(e -> {rvhv.dispose(); RespondentWelcome(respondent); } );
    }
    
    public void RespondentProfile(Respondent respondent) {
        RespondentProfileView rpv = new RespondentProfileView(respondent);
        
        rpv.setUpdateDetailsButtonListener(e -> {
            rpv.setErrorMessages(false);

            if (validateFields() == true) {
                if (checkUsernameValid() == true) {
                    rpv.dispose();
                    RespondentWelcome(respondent);
                }
                else
                    notUniqueUsername();
            }

        } );
        
        rpv.setBackButtonListener(e -> {rpv.dispose(); RespondentWelcome(respondent); } );
    }
    
    // as guest
    public void guestChooseLRecommendedPath() {
        guestChooseLRecommendedPathView gclrpv = new guestChooseLRecommendedPathView();
         
    }

    public void guestRecommendedPath() {

    }

    public void guestSelectSurveyData() {
        GuestSelectSurveyDataView gssdv = new GuestSelectSurveyDataView();
        
        gssdv.setSubmitButtonListener(e -> {
            ProgramUser user = new ProgramUser();
            gssdv.setErrorMessages(false);
            if (validateForm() == true) {
                gssdv.dispose();
                ArrayList<Survey> surveys = user.fetchGeneraldata(gssdv.getPlaces(), gssdv.getDays(), gssdv.getTimes(), gssdv.getRankingType(), gssdv.getNumRec());
                guestSurveyData(); 
            }
        } );

        gssdv.setBackButtonListener(e -> {gssdv.dispose(); Homepage() } );        
    }

    public void guestSurveyData(ArrayList<Survey> surveys) {
        GuestSurveyDataView gsdv = new GuestSurveyDataView(surveys);
        gsdv.setBackButtonListener(e -> {gsdv.dispose(); Homepage() } );        
    }

}