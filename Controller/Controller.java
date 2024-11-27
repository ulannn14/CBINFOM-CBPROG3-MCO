package Controller;

import Model.Respondent;
import Model.*;
import View.*;
import ServiceClassPackage.*;

import java.security.Provider.Service;
import java.util.ArrayList;
import javax.management.InstanceAlreadyExistsException;

public class Controller {

    private Instance model = new Instance();
    ServiceClass service = new ServiceClass();

    public void initializeInstancesController(Instance[] instances) {
        model.initializeInstances(instances);
    }

    public void Homepage(Instance[] instances) {
        HomepageView homepageView = new HomepageView();

        // Login
        homepageView.setLoginButtonListener(e -> {
            homepageView.setErrorMessages(false);
            
            String username = homepageView.getUsername();
            String password = homepageView.getPassword();

            Admin admin = new Admin();
            admin = admin.fetchUser(username, password);
            if (admin != null) {
                homepageView.dispose();
                AdminWelcome(admin, instances);
            }
            
            Analyst analyst = new Analyst(); 
            analyst = analyst.fetchUser(username, password);
            if (analyst != null) {
                homepageView.dispose();
                AnalystWelcome(analyst, instances);
            }

            Respondent respondent = new Respondent();
            respondent = respondent.fetchUser(username, password);
            if (respondent != null) {
                homepageView.dispose();
                RespondentWelcome(respondent, instances);
            }
            
            homepageView.failedLogin();

        } );

        // Listeners
        homepageView.setSignupButtonListener(e -> { Signup(instances); homepageView.dispose(); } );
        homepageView.setForgotPasswordButtonListener(e -> { PasswordManager(instances); homepageView.dispose(); } );
        homepageView.setFindRecommendedPathButtonListener(e -> { GuestChooseLRecommendedPath(instances); homepageView.dispose(); });
        homepageView.setGeneralResultButtonListener(e -> { GuestSelectSurveyData(instances); homepageView.dispose(); } );
    }

    public void Signup(Instance[] instances) {
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
                    RespondentWelcome(respondent, instances);
                }
                else 
                    signupView.notUniqueUsername();
            }
        } );

        signupView.setBackButtonListener(e -> { signupView.dispose(); Homepage(instances); } );
    }

    // view for forgetPassword 
    // reason for overloading: uses the same view naman sila pero need lang ipasa user if change password to eliminate checking of database
    public void PasswordManager(Instance[] instances) {
        PasswordManagerView pmv = new PasswordManagerView();

        pmv.setChangePasswordButtonListener(e -> {
            pmv.setErrorMessages(false);
            String username = pmv.getUsername();
            String newPassword = pmv.getPassword();
            String securityQuestion = pmv.getSecurityQuestion();
            String securityPassword = pmv.getSecurityPassword();

            // if wala pa existing user
            int successful = ProgramUser.changePassword(username, newPassword, securityQuestion, securityPassword);
            boolean nextView;

            switch (successful) {
            case 1: 
                nextView = pmv.validatePassword(newPassword);
                if (nextView) { pmv.dispose(); Homepage(instances); }
            case 2: pmv.wrongUsername(); break; // When no username exists 
            case 3: pmv.wrongSecurityQuestion(); break;
            case 4: pmv.wrongSecurityPassword(); break;
            }

        } );

        pmv.setBackButtonListener(e -> { pmv.dispose(); Homepage(instances); } );
    }

    // change password
    public void PasswordManager(ProgramUser user, Instance[] instances) {
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
                            if (user instanceof Admin)
                                AdminWelcome((Admin) user, instances);
                            else if (user instanceof Analyst)
                                AnalystWelcome((Analyst) user, instances);
                            else if (user instanceof Respondent)
                                RespondentWelcome((Respondent) user, instances);
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
                AdminWelcome((Admin) user, instances);
            else if (user instanceof Analyst)
                AnalystWelcome((Analyst) user, instances);
            else if (user instanceof Respondent)
                RespondentWelcome((Respondent) user, instances);
        } );

    }
    
    public void ChangeSecurityView(ProgramUser user, Instance[] instances) {
        ChangeSecurityView csv = new ChangeSecurityView();
        boolean nextView = true;
        nextView = csv.validateSecurityQuestion() && csv.validateSecurityPassword(); // boolean

        csv.setChangeSecurityDetailsButtonListener(e -> {
            csv.setErrorMessages(false);
            String newSecurityQuestion = csv.getSecurityQuestion(); // get input
            String newSecurityPassword = csv.getSecurityPassword(); // get input
            String password = csv.getPassword(); // get input

 
            if (password.equals(user.getAccountPassword()) == false) { // no return value
                csv.wrongPassword();
            }

            if (password.equals(user.getAccountPassword()) == false) {
                // should be updated sa databse
                user.setSecurityQuestion(newSecurityQuestion);
                user.setSecurityPassword(newSecurityPassword);
                csv.dispose();
                if (user instanceof Admin)
                    AdminWelcome((Admin) user, instances);
                else if (user instanceof Analyst)
                    AnalystWelcome((Analyst) user, instances);
                else if (user instanceof Respondent)
                    RespondentWelcome((Respondent) user, instances);
            }
        } );
 
        csv.setBackButtonListener(e -> {
            if (user instanceof Admin)
                AdminWelcome((Admin) user, instances);
            else if (user instanceof Analyst)
                AnalystWelcome((Analyst) user, instances);
            else if (user instanceof Respondent)
                RespondentWelcome((Respondent) user, instances);
        } );
    }  

    public void AdminWelcome(Admin admin, Instance[] instances) {
        AdminWelcomeView awv = new AdminWelcomeView();
        
        awv.setViewRespondentsDataButtonListener (e -> { AdminViewRespondentData(admin,instances); awv.dispose(); } );
        awv.setViewAnalystsDataButtonListener (e -> { AdminViewAnalystData(admin,instances); awv.dispose(); } );
        awv.setAddNewAnalystButtonListener (e -> { AdminAddAnalystView(admin,instances); awv.dispose(); } );        
        awv.setViewComplaintReportsButtonListener(e -> { AdminViewComplaintReportsView(admin,instances); awv.dispose(); } );
        awv.setChangeSecurityQuesAndPassButtonListener(e -> { ChangeSecurityView(admin,instances); awv.dispose(); } );
        awv.setChangePasswordButtonListener(e -> { PasswordManager(admin,instances); awv.dispose(); } );
        awv.setGenerateIncidentReportsButtonListener(e -> { AdminGenerateIncidentReport(admin,instances); awv.dispose(); } );        
        awv.setFindRecommendedPathButtonListener(e-> { GuestChooseLRecommendedPath(admin,instances); awv.dispose(); } );
        awv.setViewGeneralDataButtonListener(e-> {GuestSelectSurveyData(admin,instances); awv.dispose(); } );       

        awv.setBackButtonListener(e -> {awv.dispose(); Homepage(instances); } );
    }

    public void AdminViewRespondentData(Admin admin, Instance[] instances) {
        ArrayList<Respondent> allRespondents = Respondent.fetchAllRespondents();        
        AdminViewRespondentDataView avrdv = new AdminViewRespondentDataView(allRespondents);
        
        avrdv.setBackButtonListener(e -> { avrdv.dispose(); AdminWelcome(admin,instances); } );
    }


    public void AdminViewAnalystData(Admin admin, Instance[] instances) {
        ArrayList<Analyst> allAnalysts = Analyst.fetchAllAnalysts();        
        AdminViewAnalystDataView avadv = new AdminViewAnalystDataView(allAnalysts);
        
        avadv.setBackButtonListener(e -> { avadv.dispose(); AdminWelcome(admin,instances); } );
    }

    public void AdminAddAnalyst(Admin admin, Instance[] instances) {
        AdminAddAnalystView aaav = new AdminAddAnalystView();
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
                AdminWelcome(admin,instances);
            }           
            else if (!validUsername)
                aaav.notUniqueUsername();    
        } );

        aaav.setBackButtonListener(e -> { aaav.dispose(); AdminWelcome(admin,instances); } );
    }
    

    public void AdminViewComplaintReports(Admin admin, Instance[] instances) {
        AdminViewIncidentReportsView avirv = new AdminViewIncidentReportsView(instances);

        avirv.setBackButtonListener(e -> {avirv.dispose(); AdminWelcome(admin,instances); } );
    }

    public void AdminGenerateIncidentReport(Admin admin, Instance[] instances) {
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
                AdminWelcome(admin, instances);
            }
        } ); 

        agirv.setBackButtonListener(e -> {agirv.dispose(); AdminWelcome(admin, instances); } );
    }

    public void AnalystWelcome(Analyst analyst, Instance[] instances) {
        AnalystWelcomeView anwv = new AnalystWelcomeView();

        anwv.setViewSurveyDataButtonListener(e -> { AnalystViewSurveyData(analyst); anwv.dispose(); } );
        anwv.setAnalystGenerateComplaintReportButtonListener(e -> { AnalystGenerateComplaintReport(analyst); anwv.dispose(); } );
        anwv.setChangeSecurityQuesAndPassButtonListener(e -> { ChangeSecurityView(analyst); anwv.dispose(); } );
        anwv.setChangePasswordButtonListener(e -> { PasswordManager(analyst); anwv.dispose(); } );
        anwv.setFindRecommendedPathButtonListener(e-> { GuestChooseLRecommendedPath(analyst); anwv.dispose(); } );
        anwv.setViewGeneralDataButtonListener(e-> {GuestSelectSurveyData(analyst); anwv.dispose(); } );       

        anwv.setBackButtonListener(e -> {anwv.dispose(); Homepage(); } );
    }

    public void AnalystViewSurveyData(Analyst analyst, Instance[] instances) {
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
                ArrayList<int> tagsToDelete = amtacv.getTagsIDs(); // return int tagID to delete
                instance.deleteTags(tagsToDelete);
                ArrayList<int> commentSummariesToDelete = amtacv.getCommentSummariesIDs(); // return int csID to delete
                instance.deleteCS(commentSummariesToDelete); 
                amtacv.dispose(); 
                AnalystViewSurveyData(analyst);
            }
        } );

        amtacv.setAddTagButtonListener(e -> {
            amtacv.setErrorMessages(false); 
            if (amtacv.validateAddTag() == true) {
                ArrayList<String> newTags = amtacv.getNewTags();
                instance.addTags(newTags);
                amtacv.dispose(); 
                AnalystViewSurveyData(Analyst analyst);
            }
        } );

        amtacv.setAddCommentButtonListener(e -> { 
            amtacv.setErrorMessages(false);
            if (amtacv.validateAddCS() == true) {
                CommentSummary newCS = amtacv.getNewCS();   // MUST RETURN CS!!!
                instance.addCommentSummary(newCS);
                amtacv.dispose(); 
                AnalystViewSurveyData(Analyst analyst);
            }
        } );        

        amtacv.setBackButtonListener(e -> {amtacv.dispose(); AnalystViewSurveyData(analyst); } );
    }

    public void AnalystGenerateComplaintReport(Analyst analyst, Instance[] instances) {
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

        rwv.setBackButtonListener(e -> {rwv.dispose(); Homepage(); } );
    }

    // mali pa to !!!
    public void RespondentTakeSurvey(Respondent respondent, Instance[] instances) {
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

    public void RespondentViewHistory(Respondent respondent, Instance[] instances) {
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
    
    // as guest
    public void GuestChooseLRecommendedPath(Instance[] instances) {
        service.zTestComputation(instances);
        guestChooseLRecommendedPathView gclrpv = new guestChooseLRecommendedPathView();
         
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
                ArrayList<Survey> surveys = user.fetchGeneraldata(gssdv.getPlaces(), gssdv.getDays(), gssdv.getTimes(), gssdv.getRankingType(), gssdv.getNumRec());
                guestSurveyData(); 
            }
        } );

        gssdv.setBackButtonListener(e -> {gssdv.dispose(); Homepage(); } );        
    }

    public void GuestSurveyData(ArrayList<Survey> surveys) {
        GuestSurveyDataView gsdv = new GuestSurveyDataView(surveys);
        gsdv.setBackButtonListener(e -> {gsdv.dispose(); Homepage(); } );        
    }

}