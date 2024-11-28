package Controller;

import Model.*;
import View.*;
import ServiceClassPackage.*;

import java.util.ArrayList;

public class Controller {

    private Instance[] instances = new Instance[Constants.MAX_INSTANCE];
    ServiceClass service = new ServiceClass();

    // Will populate the instances of Controller
    public void initializeInstancesController() {
        Instance.initializeInstances(this.instances);
    }

    public void Homepage() {
        HomepageView homepageView = new HomepageView();
    
        // Login
        homepageView.setLoginButtonListener(e -> {
            homepageView.setErrorMessages(false);
            validateLogin(homepageView, homepageView.getUsername(), homepageView.getPassword());
        } );

        homepageView.setSignupButtonListener(e -> { Signup(); homepageView.dispose(); } );
        homepageView.setForgotPasswordButtonListener(e -> { PasswordManager(); homepageView.dispose(); } );
        //homepageView.setFindRecommendedPathButtonListener(e -> { GuestChooseLRecommendedPath(); homepageView.dispose(); });
        //homepageView.setGeneralResultButtonListener(e -> { GuestSelectSurveyData(); homepageView.dispose(); } );

    }
    
    private void validateLogin(HomepageView homepageView, String username, String password) {
        // Try to fetch the user from Admin, Analyst, and Respondent
        if (Admin.fetchUser(username, password) != null) {
            homepageView.dispose();
            AdminWelcome(Admin.fetchUser(username, password));
        } else if (Analyst.fetchUser(username, password) != null) {
            homepageView.dispose();
            AnalystWelcome(Analyst.fetchUser(username, password));
        } else if (Respondent.fetchUser(username, password) != null) {
            homepageView.dispose();
            RespondentWelcome(Respondent.fetchUser(username, password));
        } else {
            homepageView.failedLogin();
        }
    }

    public void Signup() {
        SignupView signupView = new SignupView();

        signupView.setSignupButtonListener(e -> {
            signupView.setErrorMessages(false);
            if (signupView.validateForm() == true) {
                String name = signupView.getName();
                String email = signupView.getEmail();
                String username = signupView.getUsername();
                String password = signupView.getPassword();
                String securityQuestion = signupView.getSecurityQuestion();
                String securityPassword = signupView.getSecurityPassword();
                DateClass birthdate = DateClass.convertToDate(signupView.getBirthday());
                int userType = 3;

                boolean validUsername = ProgramUser.checkUsernameValid(username);

                if (validUsername) {
                    Respondent respondent = new Respondent(username, password, securityQuestion, securityPassword, userType, name, birthdate, email);
                    signupView.dispose();
                    RespondentWelcome(respondent);
                }
                else 
                    signupView.notUniqueUsername();
            }
        } );

        signupView.setBackButtonListener(e -> { signupView.dispose(); Homepage(); } );
    }

    // view for forgetPassword 
    // reason for overloading: uses the same view naman sila pero need lang ipasa user if change password to eliminate checking of database
    public void PasswordManager() {
        PasswordManagerView pmv = new PasswordManagerView();
    
        pmv.setChangePasswordButtonListener(e -> {
            pmv.setErrorMessages(false);
            switch (ProgramUser.changePassword(pmv.getUsername(), pmv.getPassword(), pmv.getSecurityQuestion(), pmv.getSecurityPassword())) {
                case 1: 
                    if (pmv.validatePassword(pmv.getPassword())) { pmv.dispose(); Homepage(); } break;
                case 2: pmv.wrongUsername(); break;
                case 3: pmv.wrongSecurityQuestion(); break;
                case 4: pmv.wrongSecurityPassword(); break;
            }
        });
    
        pmv.setBackButtonListener(e -> { pmv.dispose(); Homepage(); });
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

            if (username.equals(user.getUsername()) == true) {
                if (securityQuestion.equals(user.getSecurityQuestion()) == true) {
                    if (securityPassword.equals(user.getSecurityPassword()) == true) {
                        if (pmv.validatePassword(newPassword) == true) {
                            user.setAccountPassword(newPassword);
                            pmv.dispose();
                            if (user instanceof Admin admin)
                                AdminWelcome(admin);
                            else if (user instanceof Analyst analyst)
                                AnalystWelcome(analyst);
                            else if (user instanceof Respondent respondent)
                                RespondentWelcome(respondent);
                        }
                    } else
                        pmv.wrongSecurityPassword();
                } else 
                    pmv.wrongSecurityPassword();
            } else
                pmv.wrongUsername();

        } );

        pmv.setBackButtonListener(e -> {
            if (user instanceof Admin admin)
                AdminWelcome(admin);
            else if (user instanceof Analyst analyst)
                AnalystWelcome(analyst);
            else if (user instanceof Respondent respondent)
                RespondentWelcome(respondent);
        } );

    }
    
    public void ChangeSecurity (ProgramUser user) {
        ChangeSecurityView csv = new ChangeSecurityView();

        csv.setChangeSecurityDetailsButtonListener(e -> {
            csv.setErrorMessages(false);
            String password = csv.getPassword(); // get input

            if ((csv.validateSecurityQuestion()) && (csv.validateSecurityPassword())) {
                if (password.equals(user.getAccountPassword()) == false) { // no return value
                    csv.wrongPassword();
                } else if (password.equals(user.getAccountPassword()) == true) {
                    // should be updated sa databse
                    user.setSecurityQuestion(csv.getSecurityPassword());
                    user.setSecurityPassword(csv.getSecurityPassword());
                    csv.dispose();
                    if (user instanceof Admin admin)
                        AdminWelcome(admin);
                    else if (user instanceof Analyst analyst)
                        AnalystWelcome(analyst);
                    else if (user instanceof Respondent respondent)
                        RespondentWelcome(respondent);
                }
            }
        } );
 
        csv.setBackButtonListener(e -> {
            if (user instanceof Admin admin)
                AdminWelcome(admin);
            else if (user instanceof Analyst analyst)
                AnalystWelcome(analyst);
            else if (user instanceof Respondent respondent)
                RespondentWelcome(respondent);
        } );
    }  

    public void AdminWelcome(Admin admin) {
        AdminWelcomeView awv = new AdminWelcomeView();
        
        awv.setViewRespondentsDataButtonListener (e -> { AdminViewRespondentData(admin); awv.dispose(); } );
        awv.setViewAnalystsDataButtonListener (e -> { AdminViewAnalystData(admin); awv.dispose(); } );
        awv.setAddNewAnalystButtonListener (e -> { AdminAddAnalyst(admin); awv.dispose(); } );        
        awv.setViewComplaintReportsButtonListener(e -> { AdminViewComplaintReports(admin); awv.dispose(); } );
        awv.setChangeSecurityQuesAndPassButtonListener(e -> { ChangeSecurity(admin); awv.dispose(); } );
        awv.setChangePasswordButtonListener(e -> { PasswordManager(admin); awv.dispose(); } );
        awv.setGenerateIncidentReportsButtonListener(e -> { AdminGenerateIncidentReport(admin); awv.dispose(); } );        
        //awv.setFindRecommendedPathButtonListener(e-> { GuestChooseLRecommendedPath(admin); awv.dispose(); } );
        //awv.setViewGeneralDataButtonListener(e-> {GuestSelectSurveyData(admin); awv.dispose(); } );       

        awv.setBackButtonListener(e -> {awv.dispose(); Homepage(); } );
    }

    public void AdminViewRespondentData(Admin admin) {
        ArrayList<Respondent> allRespondents = Respondent.fetchAllRespondents();
        
        ArrayList<String> allNames = new ArrayList<>();
        ArrayList<DateClass> allBirthdates = new ArrayList<>();
        ArrayList<Integer> allAges = new ArrayList<>();
        ArrayList<String> allEmails = new ArrayList<>();
        ArrayList<String> allUsernames = new ArrayList<>();
        ArrayList<String> allSQs = new ArrayList<>();
        ArrayList<DateClass> allDateJoineds = new ArrayList<>();

        for (Respondent respondent : allRespondents) {
            allNames.add(respondent.getName());
            allBirthdates.add(respondent.getBirthdate());
            allAges.add(respondent.getAge());
            allEmails.add(respondent.getEmailAddress());
            allUsernames.add(respondent.getUsername());
            allSQs.add(respondent.getSecurityQuestion());
            allDateJoineds.add(respondent.getDateJoined());
        }

        AdminViewRespondentDataView avrdv = new AdminViewRespondentDataView(allNames,allBirthdates,allAges,allEmails,allUsernames,allSQs,allDateJoineds);
        avrdv.setBackButtonListener(e -> { avrdv.dispose(); AdminWelcome(admin); } );
    }


    public void AdminViewAnalystData(Admin admin) {
        ArrayList<Analyst> allAnalysts = Analyst.fetchallAnalysts();
        
        ArrayList<String> allUsernames = new ArrayList<>();
        ArrayList<String> allSQs = new ArrayList<>();
        ArrayList<DateClass> allDateJoineds = new ArrayList<>();

        for (Analyst analyst : allAnalysts) {
            allUsernames.add(analyst.getUsername());
            allSQs.add(analyst.getSecurityQuestion());
            allDateJoineds.add(analyst.getDateJoined());
        }
        
        AdminViewAnalystDataView avadv = new AdminViewAnalystDataView(allUsernames,allSQs,allDateJoineds); 
        avadv.setBackButtonListener(e -> { avadv.dispose(); AdminWelcome(admin); } );
    }

    public void AdminAddAnalyst(Admin admin) {
        AdminAddAnalystView aaav = new AdminAddAnalystView();
    
        aaav.setAddNewAnalystButtonListener(e -> {
            aaav.setErrorMessages(false);
    
            boolean validUsername = ProgramUser.checkUsernameValid(username); 
            boolean validPassword = aaav.validatePassword();
    
            if (validUsername && validPassword) {
                Analyst analyst = new Analyst(aaav.getUsername(), aaav.getPassword()); // Example constructor
                
                aaav.dispose(); // Close the view
                AdminWelcome(admin); // Return to AdminWelcome screen
            } else {
                if (!validUsername) {
                    aaav.notUniqueUsername(); // Show message for invalid username
                }
            }
        } );
    
        // Listener for back button
        aaav.setBackButtonListener(e -> {
            aaav.dispose(); // Close current view
            AdminWelcome(admin); // Return to AdminWelcome screen
        });
    }
    
    
// mac paedit adminViewCommentSummaryReportsView
    public void AdminViewComplaintReports(Admin admin) {
        AdminViewIncidentReportsView avirv = new AdminViewIncidentReportsView(instances);

        avirv.setBackButtonListener(e -> {avirv.dispose(); AdminWelcome(admin); } );
    }

    public void AdminGenerateIncidentReport(Admin admin) {
        AdminGenerateIncidentReportView agirv = new AdminGenerateIncidentReportView();
        
        agirv.setSubmitButtonListener(e -> {
            agirv.setErrorMessages(false);

            if (agirv.validateFields() == true) {
                String Recipient = agirv.getToField();
                String Sender = agirv.getFromField();
                String Body = agirv.getBodyField();
                IncidentReport incidentReport = new IncidentReport(Recipient, Sender, Body);

                instances[agirv.getIndexID()].addIncidentReport(incidentReport);
                incidentReport.saveToFile(instances[agirv.getIndexID()].getPlaceName(), instances[agirv.getIndexID()].getDayName(), instances[agirv.getIndexID()].getTimeName(), instances[agirv.getIndexID()].getIncidentReportCount());
                
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

        anwv.setBackButtonListener(e -> {anwv.dispose(); Homepage(instances); } );
    }

    public void AnalystViewSurveyData(Analyst analyst) {
        AnalystViewSurveyDataView avsdv = new AnalystViewSurveyDataView(instances);
        
        avsdv.setModifyButtonListener(e -> {
            if (avsdv.validateInstanceID() == true) {
                Instance selectedInstance = instances[avsdv.getInstanceID()]; // instance id directly ng chinoose
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
                ArrayList<Integer> tagsToDelete = amtacv.getTagsIDs(); // return int tagID to delete
                instance.deleteTags(tagsToDelete);
                ArrayList<Integer> commentSummariesToDelete = amtacv.getCommentSummariesIDs(); // return int csID to delete
                instance.deleteCS(commentSummariesToDelete); 
                amtacv.dispose(); 
                AnalystViewSurveyData(analyst,instance);
            }
        } );

        amtacv.setAddTagButtonListener(e -> {
            amtacv.setErrorMessages(false); 
            if (amtacv.validateAddTag() == true) {
                ArrayList<String> newTags = amtacv.getNewTags();
                instance.addTags(newTags);
                amtacv.dispose(); 
                AnalystViewSurveyData(analyst,instance);
            }
        } );

        amtacv.setAddCommentButtonListener(e -> { 
            amtacv.setErrorMessages(false);
            if (amtacv.validateAddCS() == true) {
                CommentSummary newCS = amtacv.getNewCS();   // MUST RETURN CS!!!
                instance.addCommentSummary(newCS);
                amtacv.dispose(); 
                AnalystViewSurveyData(analyst,instance);
            }
        } );        

        amtacv.setBackButtonListener(e -> {amtacv.dispose(); AnalystViewSurveyData(analyst); } );
    }

    public void AnalystGenerateComplaintReport(Analyst analyst) {
        AnalystGenerateComplaintReportView agcrv = new AnalystGenerateComplaintReportView();
        
        agcrv.setSubmitButtonListener(e -> { 
            agcrv.setErrorMessages(false);

            if (agcrv.validateFields() == true) {
                instances[agcrv.getInstanceID()].addNewComplaintReport(agcrv.getReport());
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

        rwv.setBackButtonListener(e -> {rwv.dispose(); Homepage(instances); } );
    }

    // mali pa to !!!
    public void RespondentTakeSurvey(Respondent respondent) {
        RespondentTakeSurveyView rtsv = new RespondentTakeSurveyView();

        rtsv.setSubmitButtonListener(e -> { 
            rtsv.setErrorMessages(false);
            if (validateForm() == true) {
                Survey survey = rtsv.getSurvey();
                instances[rtsv.getInstanceID()].takeSurvey(survey);
                rtsv.dispose();
                RespondentWelcome(respondent);
            }
        } );

        rtsv.setBackButtonListener(e -> {rtsv.dispose(); RespondentWelcome(respondent); } );
    }

    public void RespondentViewHistory(Respondent respondent) {
        RespondentViewHistoryView rvhv = new RespondentViewHistoryView(instances);
       
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
    
    /*
    // as guest
    public void GuestChooseLRecommendedPath(Instance[] instances) {
        service.zTestComputation(instances);
        guestChooseLRecommendedPathView gclrpv = new guestChooseLRecommendedPathView();
        
        gclrpv.setSubmitButtonListener( e -> {
            gclrpv.setErrorMessages(false);

            if (validateField() == true)
                
                GuestRecommendedPath();

        } );
    }

    public void GuestRecommendedPath() {

    }

    public void GuestSelectSurveyData(Instance[] instances) {
        service.zTestComputation(instances);
        GuestSelectSurveyDataView gssdv = new GuestSelectSurveyDataView();
        
        gssdv.setSubmitButtonListener(e -> {
            gssdv.setErrorMessages(false);
            if (validateForm() == true) {
                gssdv.dispose();
                // 0 - no ranking
                // 1 - best to worst
                // 2 - worst to best
                ArrayList<Survey> surveys = ProgramUser.fetchGeneraldata(gssdv.getPlaces(), gssdv.getDays(), gssdv.getTimes(), gssdv.getRankingType(), gssdv.getNumRec());
                GuestSurveyData(); 
            }
        } );

        gssdv.setBackButtonListener(e -> {gssdv.dispose(); Homepage(); } );        
    }

    public void GuestSurveyData(ArrayList<Survey> surveys) {
        GuestSurveyDataView gsdv = new GuestSurveyDataView(surveys);
        gsdv.setBackButtonListener(e -> {gsdv.dispose(); Homepage(); } );        
    }
 */
}