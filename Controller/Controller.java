package Controller;

import Model.*;
import View.*;
import ServiceClassPackage.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.awt.event.*;
//import javax.swing.JOptionPane;

@SuppressWarnings("unused")
public class Controller {

    final private Instance[] instances = new Instance[Constants.MAX_INSTANCE];
    ServiceClass service = new ServiceClass();

    // Will populate the instances of Controller
    public void initializeInstancesController() {
        Instance.initializeInstances(this.instances);
    }

    public String convertDateToStringNumber (DateClass date) {
        String temp = (String.format("%02d", date.getMonth()).concat("/").concat(String.format("%02d", date.getDay())).concat("/").concat(String.format("%02d", date.getYear())));
        return temp;
    }

    @SuppressWarnings("unused")
    public void Homepage() {
        HomepageView homepageView = new HomepageView();
    
        // Login
        homepageView.setLoginButtonListener(e -> {
            homepageView.setErrorMessages(false);
            validateLogin(homepageView, homepageView.getUsername(), homepageView.getPassword());
        } );


        homepageView.setSignupButtonListener(e -> { Signup(); homepageView.dispose(); } );
        homepageView.setForgotPasswordButtonListener(e -> { PasswordManager(); homepageView.dispose(); } );
        homepageView.setFindRecommendedPathButtonListener(e -> { GuestChooseRecommendedPath(); homepageView.dispose(); });
        homepageView.setGeneralResultButtonListener(e -> { GuestSelectGeneralData(); homepageView.dispose(); } );
        homepageView.setWindowCloseListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                service.zTestComputation(instances);
                // Close the database
                homepageView.dispose();
            }
        });
    }
    
    private void validateLogin(HomepageView homepageView, String username, String password) {
        // Try to fetch the user from Admin, Analyst, and Respondent
        Admin admin = new Admin();
        Analyst analyst = new Analyst();
        Respondent respondent = new Respondent();
        if (admin.fetchUser(username, password) != null) {
            homepageView.dispose();
            AdminWelcome(admin);
        } else if (analyst.fetchUser(username, password) != null) {
            homepageView.dispose();
            AnalystWelcome(analyst);
        } else if (respondent.fetchUser(username, password) != null) {
            homepageView.dispose();
            RespondentWelcome(respondent);
        } else {
            homepageView.failedLogin();
        }
    }

    @SuppressWarnings("unused")
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
                DateClass birthdate = new DateClass(signupView.getMonth(), signupView.getDay(), signupView.getYear());
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
                case 1 -> {
                    if (pmv.validatePassword(pmv.getPassword())) { pmv.dispose(); Homepage(); }
                }
                case 2 -> pmv.wrongUsername();
                case 3 -> pmv.wrongSecurityQuestion();
                case 4 -> pmv.wrongSecurityPassword();
            }
        } );
    
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
                            switch (user) {
                                case Admin admin -> AdminWelcome(admin);
                                case Analyst analyst -> AnalystWelcome(analyst);
                                case Respondent respondent -> RespondentWelcome(respondent);
                                default -> Homepage();  // This handles any other cases, like when user is not an Admin, Analyst, or Respondent
                            }
                        }
                    } else
                        pmv.wrongSecurityPassword();
                } else 
                    pmv.wrongSecurityPassword();
            } else
                pmv.wrongUsername();

        } );

        pmv.setBackButtonListener(e -> {
            switch (user) {
                case Admin admin -> AdminWelcome(admin);
                case Analyst analyst -> AnalystWelcome(analyst);
                case Respondent respondent -> RespondentWelcome(respondent);
                default -> Homepage();  // This handles any other cases, like when user is not an Admin, Analyst, or Respondent
            }
        } );

    }
    
    public void ChangeSecurity (ProgramUser user) {
        ChangeSecurityView csv = new ChangeSecurityView(user.getUsername());

        csv.setChangeSecurityDetailsButtonListener(e -> {
            csv.setErrorMessages(false);
            String password = csv.getPassword(); // get input

            if (csv.validateFields() == true) {
                if (password.equals(user.getAccountPassword()) == false) { // no return value
                    csv.wrongPassword();
                } else if (password.equals(user.getAccountPassword()) == true) {
                    // should be updated sa databse
                    user.setSecurityQuestion(csv.getSecurityPassword());
                    user.setSecurityPassword(csv.getSecurityPassword());
                    csv.dispose();
                    switch (user) {
                        case Admin admin -> AdminWelcome(admin);
                        case Analyst analyst -> AnalystWelcome(analyst);
                        case Respondent respondent -> RespondentWelcome(respondent);
                        default -> Homepage();  // This handles any other cases, like when user is not an Admin, Analyst, or Respondent
                    }
                }
            }
        } );
 
        csv.setBackButtonListener(e -> {
            switch (user) {
                case Admin admin -> AdminWelcome(admin);
                case Analyst analyst -> AnalystWelcome(analyst);
                case Respondent respondent -> RespondentWelcome(respondent);
                default -> Homepage();  // This handles any other cases, like when user is not an Admin, Analyst, or Respondent
            }
        } );
    }  

    public void AdminWelcome(Admin admin) {
        AdminWelcomeView awv = new AdminWelcomeView();
        
        awv.setViewRespondentsDataButtonListener (e -> { AdminViewRespondentData(admin); awv.dispose(); } );
        awv.setViewAnalystsDataButtonListener (e -> { AdminViewAnalystData(admin); awv.dispose(); } );
        awv.setAddNewAnalystButtonListener (e -> { AdminAddAnalyst(admin); awv.dispose(); } );        
        awv.setViewCommentSummaryReportsButtonListener(e -> { AdminViewCommentSummaryReports(admin); awv.dispose(); } );
        awv.setChangeSecurityQuesAndPassButtonListener(e -> { ChangeSecurity(admin); awv.dispose(); } );
        awv.setChangePasswordButtonListener(e -> { PasswordManager(admin); awv.dispose(); } );
        awv.setGenerateIncidentReportsButtonListener(e -> { AdminGenerateIncidentReport(admin); awv.dispose(); } );        
        awv.setFindRecommendedPathButtonListener(e-> { GuestChooseRecommendedPath(admin); awv.dispose(); } );
        awv.setViewGeneralDataButtonListener(e-> { GuestSelectGeneralData(admin); awv.dispose(); } );       

        awv.setBackButtonListener(e -> {awv.dispose(); Homepage(); } );
    }

    public void AdminViewRespondentData(Admin admin) {
        ArrayList<Respondent> allRespondents = Respondent.fetchAllRespondents();

        
        int nCounter = allRespondents.size();
        int[] allAges = new int[nCounter];
        String[] allNames = new String[nCounter], allEmails = new String[nCounter], 
                 allUsernames = new String[nCounter], allSQs = new String[nCounter], 
                 allBirthdates = new String[nCounter], allDateJoineds = new String[nCounter];

        for (int x = 0; x < nCounter; x++) {
            allNames[x] = allRespondents.get(x).getName();
            allBirthdates[x] = allRespondents.get(x).getBirthdate().convertToString();
            allAges[x] = allRespondents.get(x).getAge();
            allEmails[x] = allRespondents.get(x).getEmailAddress();
            allUsernames[x] = allRespondents.get(x).getUsername();
            allSQs[x] = allRespondents.get(x).getSecurityQuestion();
            allDateJoineds[x] = allRespondents.get(x).getDateJoined().convertToString();
        }

        AdminViewRespondentDataView avrdv = new AdminViewRespondentDataView(allNames,allBirthdates,allAges,allEmails,allUsernames,allSQs,allDateJoineds);
        avrdv.setBackButtonListener(e -> { avrdv.dispose(); AdminWelcome(admin); } );
    }


    public void AdminViewAnalystData(Admin admin) {
        ArrayList<Analyst> allAnalysts = Analyst.fetchallAnalysts();
        
        int nCounter = allAnalysts.size();
        String[] allUsernames = new String[nCounter], allSQs = new String[nCounter], 
                 allDateJoineds = new String[nCounter];

        for (int x = 0; x < nCounter; x++) {
            allUsernames[x] = allAnalysts.get(x).getUsername();
            allSQs[x] = allAnalysts.get(x).getSecurityQuestion();
            allDateJoineds[x] = allAnalysts.get(x).getDateJoined().convertToString();
        }
        
        AdminViewAnalystDataView avadv = new AdminViewAnalystDataView(allUsernames,allSQs,allDateJoineds); 
        avadv.setBackButtonListener(e -> { avadv.dispose(); AdminWelcome(admin); } );
    }

    public void AdminAddAnalyst(Admin admin) {
        AdminAddAnalystView aaav = new AdminAddAnalystView();
    
        aaav.setAddButtonListener(e -> {
            aaav.setErrorMessages(false);
    
            boolean validUsername = ProgramUser.checkUsernameValid(aaav.getUsername()); 
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
    
    
    public void AdminViewCommentSummaryReports(Admin admin) {
        int[] instanceIDs = new int[Constants.MAX_INSTANCE];
        String[] places = new String[Constants.MAX_INSTANCE], days = new String[Constants.MAX_INSTANCE], 
                times = new String[Constants.MAX_INSTANCE], interpretations = new String[Constants.MAX_INSTANCE];
        String[][] commentSummaries = new String[Constants.MAX_INSTANCE][100];
        int counterCS = 0;

        // access data based sa unang parameter
        for (int i=0; i<Constants.MAX_INSTANCE; i++) {
            instanceIDs[i] = instances[i].getInstanceID();
            places[i] = instances[i].getPlaceName();
            days[i] = instances[i].getDayName();
            times[i] = instances[i].getTimeName();
            interpretations[i] = instances[i].getInterpretation();
            counterCS = instances[i].getSummaries().size();

            for (int j=0; i<counterCS; i++) {
                ArrayList<Observation> tempCommentSummaries = instances[i].getSummaries();
                commentSummaries[i][j] = "[" + tempCommentSummaries.get(j).getUsername() + "] " + tempCommentSummaries.get(j).getCSorTag();
            }
        }
        AdminViewCommentSummaryReportsView avcsrv = new AdminViewCommentSummaryReportsView(instanceIDs,places,days,times,interpretations,commentSummaries,counterCS);
        avcsrv.setBackButtonListener(e -> {avcsrv.dispose(); AdminWelcome(admin); } );
    }

    public void AdminGenerateIncidentReport(Admin admin) {
        AdminGenerateIncidentReportView agirv = new AdminGenerateIncidentReportView();
        
        agirv.setSubmitButtonListener(e -> {
            agirv.setErrorMessages(false);

            if (agirv.validateFields() == true) {
                String recipient = agirv.getToField();
                String sender = agirv.getFromField();
                String body = agirv.getReportField();
                LocalDate today = LocalDate.now();
                DateClass dateCreated = new DateClass(today.getMonthValue(), today.getDayOfMonth(), today.getYear()); 
                IncidentReport incidentReport = new IncidentReport(recipient, sender, body, dateCreated);

                int idx = getInstanceID(agirv.getPlaceName(), agirv.getDayName(), agirv.getTimeName());
                instances[idx].addIncidentReport(incidentReport);
                incidentReport.saveToFile(instances[idx].getPlaceName(), instances[idx].getDayName(), instances[idx].getTimeName(), instances[idx].getIncidentReportCount());
                
                agirv.dispose(); 
                AdminWelcome(admin);
            }
        } ); 

        agirv.setBackButtonListener(e -> {agirv.dispose(); AdminWelcome(admin); } );
    }

    public int getInstanceID(String place, String day, String time) {
        for (int i=0; i<Constants.MAX_INSTANCE; i++) {
            Instance instance = instances[i];
            if (instance.getPlaceName().equals(place) && instance.getTimeName().equals(day) && instance.getDayName().equals(time))
                return i;
        }
        return -1;
    }

    public void AnalystWelcome(Analyst analyst) {
        AnalystWelcomeView anwv = new AnalystWelcomeView();

        anwv.setViewInstancesDataButtonListener(e -> { AnalystViewInstancesData(analyst); anwv.dispose(); } );
        anwv.setChangeSecurityQuesAndPassButtonListener(e -> { ChangeSecurity(analyst); anwv.dispose(); } );
        anwv.setChangePasswordButtonListener(e -> { PasswordManager(analyst); anwv.dispose(); } );
        anwv.setFindRecommendedPathButtonListener(e-> { GuestChooseRecommendedPath(analyst); anwv.dispose(); } );
        anwv.setViewGeneralDataButtonListener(e-> { GuestSelectGeneralData(analyst); anwv.dispose(); } );       

        anwv.setBackButtonListener(e -> {anwv.dispose(); Homepage(); } );
    }


    public void AnalystViewInstancesData(Analyst analyst) {
        service.zTestComputation(instances);
        
        int[] instanceIDs = new int[Constants.MAX_INSTANCE];
        String[] places = new String[Constants.MAX_INSTANCE];
        String[] days = new String[Constants.MAX_INSTANCE];
        String[] times = new String[Constants.MAX_INSTANCE];
        double[] zScores = new double[Constants.MAX_INSTANCE];
        int[] sampleSizes = new int[Constants.MAX_INSTANCE];
        double[] sampleMeans = new double[Constants.MAX_INSTANCE];
        String[] interpretations = new String[Constants.MAX_INSTANCE];
        Object[][][] surveyDetails = new Object[Constants.MAX_INSTANCE][][];

        String[][] commentSummaries = new String[Constants.MAX_INSTANCE][100];
        String[][] tags = new String[Constants.MAX_INSTANCE][100];
        
        int counterCS = 0, counterTag = 0;

        // access data based sa unang parameter
        for (int i=0; i<Constants.MAX_INSTANCE; i++) {
            instanceIDs[i] = instances[i].getInstanceID();
            places[i] = instances[i].getPlaceName();
            days[i] = instances[i].getDayName();
            times[i] = instances[i].getTimeName();
            zScores[i] = instances[i].getZScore();
            sampleSizes[i] = instances[i].getSampleSize();
            sampleMeans[i] = instances[i].getSampleMean();
            interpretations[i] = instances[i].getInterpretation();

            counterCS = instances[i].getSummaries().size();
            counterTag = instances[i].getTags().size();

            ///// add mo sa string yung dateAdded [ 10/11/2024 | username ] hatdog
            for (int j=0; i<counterCS; i++) {
                ArrayList<Observation> tempCommentSummaries = instances[i].getSummaries();
                commentSummaries[i][j] = "[ " + convertDateToStringNumber(tempCommentSummaries.get(j).getDateAdded()) + " | " + tempCommentSummaries.get(j).getUsername() + " ] " + tempCommentSummaries.get(j).getCSorTag();
            }

            for (int j=0; i<counterTag; i++) {
                ArrayList<Observation> tempTags = instances[i].getTags();
                tags[i][j] = "[ " + convertDateToStringNumber(tempTags.get(j).getDateAdded()) + " | " + tempTags.get(j).getUsername() + " ] " + tempTags.get(j).getCSorTag();
            }
        }

        for (int i = 0; i < Constants.MAX_INSTANCE; i++){
            ArrayList <Survey> tempSurveys = instances[i].getSurveys();
            for (int j = 0; j < tempSurveys.size(); j++){
                surveyDetails[i][j][0] = convertDateToStringNumber(tempSurveys.get(i).getDateTaken());
                surveyDetails[i][j][1] = tempSurveys.get(i).getSurveyMean();
                surveyDetails[i][j][2] = tempSurveys.get(i).getRespondentUsername();
                surveyDetails[i][j][3] = tempSurveys.get(i).getComment();
            }
        }

        AnalystViewInstancesDataView avsdv = new AnalystViewInstancesDataView(instanceIDs, places, days, times, zScores, sampleSizes, sampleMeans, interpretations, commentSummaries, tags, surveyDetails, counterCS, counterTag);
        
        avsdv.setModifyButtonListener(e -> {
            if (avsdv.validateInstanceID(instanceIDs) == true) {
                int index = Integer.parseInt(avsdv.getInstanceID());
                index--;
                Instance selectedInstance = instances[index]; // instance id directly ng chinoose
                AnalystModifyTagsAndComments(analyst, selectedInstance);
            }
        } );

        avsdv.setBackButtonListener(e -> {avsdv.dispose(); AnalystWelcome(analyst); } );
    }
    
    public void AnalystModifyTagsAndComments(Analyst analyst, Instance instance) {
        // need to have parameter instance to know which tag to display
        ArrayList<Observation> tempCS = instance.getSummaries();
        ArrayList<Observation> tempTags = instance.getTags();
        int counterCS = instance.getSummaries().size();
        int counterTag = instance.getTags().size();

        String[] summaries = new String[counterCS];
        String[] tags = new String[counterTag];

        for (int j=0; j<counterCS; j++) {
            summaries[j] = "[ " + convertDateToStringNumber(tempCS.get(j).getDateAdded()) + " | " + tempCS.get(j).getUsername() + " ] " + tempCS.get(j).getCSorTag();
        }

        for (int j=0; j<counterTag; j++) {
            tags[j] = "[ " + convertDateToStringNumber(tempTags.get(j).getDateAdded()) + " | " + tempTags.get(j).getUsername() + " ] " + tempTags.get(j).getCSorTag();
        }        

        AnalystModifyTagsAndCommentsView amtacv = new AnalystModifyTagsAndCommentsView(instance.getInstanceID(),summaries,tags,counterCS,counterTag);

        amtacv.setDeleteButtonListener(e -> { 
            amtacv.setErrorMessages(false);
            if (amtacv.validateDelete() == true) {
                // provide IDs to delete
                ArrayList<Integer> tagsToDelete = amtacv.getTagIDsToDelete(); // return int tagID to delete
                instance.deleteTags(tagsToDelete);
                ArrayList<Integer> commentSummariesToDelete = amtacv.getSummariesIDsToDelete(); // return int csID to delete
                instance.deleteSummaries(commentSummariesToDelete); 
                amtacv.dispose(); 
                AnalystViewInstancesData(analyst);
            }
        } );

        amtacv.setAddTagButtonListener(e -> {
            amtacv.setErrorMessages(false); 
            if (amtacv.validateAddTag() == true) {
                Observation newTag = new Observation(analyst.getUsername(), amtacv.getNewTag(), 1, instance.getInstanceID());
                instance.addTag(newTag);
                amtacv.dispose(); 
                AnalystViewInstancesData(analyst);
            }
        } );

        amtacv.setAddSummaryButtonListener(e -> { 
            amtacv.setErrorMessages(false);
            if (amtacv.validateAddCS() == true) {
                Observation newCS = new Observation(analyst.getUsername(), amtacv.getNewCS(), 2, instance.getInstanceID());   // MUST RETURN STRING
                instance.addCommentSummary(newCS);
                amtacv.dispose(); 
                AnalystViewInstancesData(analyst);
            }
        } );        

        amtacv.setBackButtonListener(e -> {amtacv.dispose(); AnalystViewInstancesData(analyst); } );
    }

    
    public void RespondentWelcome(Respondent respondent) {
        RespondentWelcomeView rwv = new RespondentWelcomeView();

        rwv.setTakeSurveyButtonListener(e -> { rwv.dispose(); RespondentTakeSurvey(respondent); } );
        rwv.setViewSurveyHistoryButtonListener(e -> { rwv.dispose(); RespondentViewHistory(respondent); } );
        rwv.setViewOrUpdateProfileButtonListener(e -> { rwv.dispose(); RespondentProfile(respondent); } );
        rwv.setChangeSecurityQuesAndPassButtonListener(e -> { rwv.dispose(); ChangeSecurity(respondent); } );
        rwv.setChangePasswordButtonListener(e -> { rwv.dispose(); PasswordManager(respondent); } );
        rwv.setFindRecommendedPathButtonListener(e-> { rwv.dispose(); GuestChooseRecommendedPath(respondent); } );
        rwv.setViewGeneralDataButtonListener(e-> { rwv.dispose(); GuestSelectGeneralData(respondent); } );       

        rwv.setBackButtonListener(e -> {rwv.dispose(); Homepage(); } );
    }

    // mali pa to !!!
    public void RespondentTakeSurvey(Respondent respondent) {
        RespondentTakeSurveyView rtsv = new RespondentTakeSurveyView();
        int[] answers = new int[20];

        rtsv.setSubmitButtonListener(e -> { 
            rtsv.setErrorMessages(false);
            if (rtsv.validateForm(answers) == true) {
                // must be able to give the following:
                            
                int idx = getInstanceID(rtsv.getPlaceName(), rtsv.getPlaceDay(), rtsv.getPlaceTime());
                LocalDate today = LocalDate.now();
                DateClass dateTaken = new DateClass(today.getMonthValue(), today.getDayOfMonth(), today.getYear()); 

                // must be converted to an IDX
                Survey survey = new Survey(answers, respondent.getUsername(), rtsv.getComment(), dateTaken, idx);
                instances[idx-1].takeSurvey(survey);
                instances[idx-1].setUpdated(false);
                rtsv.dispose();
                RespondentWelcome(respondent);
            }
        } );

        rtsv.setBackButtonListener(e -> {rtsv.dispose(); RespondentWelcome(respondent); } );
    }

    public void PlaceNameToPlaceID() {

    }


    public void RespondentViewHistory(Respondent respondent) {
        ArrayList<Survey> surveys = Instance.fetchByRespondentUsername(respondent.getUsername());
        
        int[][] answers = new int[Constants.MAX_INSTANCE][20];   
        String[] places = new String[surveys.size()], days = new String[surveys.size()], times = new String[surveys.size()], dateTakens = new String[surveys.size()];  // Initialize with the size of survey
        int surveyCounter = surveys.size();
        // iterate through each survey
        for (int i=0; i<surveyCounter; i++) {
            places[i] = instances[surveys.get(i).getInstanceID()-1].getPlaceName();
            days[i] = instances[surveys.get(i).getInstanceID()-1].getDayName();
            times[i] = instances[surveys.get(i).getInstanceID()-1].getTimeName();
            DateClass tempDate = surveys.get(i).getDateTaken();
            dateTakens[i] = tempDate.convertToString();

            System.arraycopy(surveys.get(i).getAnswers(), 0, answers[i], 0, 20); 
        }
        
        RespondentViewHistoryView rvhv = new RespondentViewHistoryView(answers,places,days,times,dateTakens);
        rvhv.setBackButtonListener(e -> {rvhv.dispose(); RespondentWelcome(respondent); } );
    }
    
    public void RespondentProfile(Respondent respondent) {
        //
        int[] birthday = {respondent.getBirthdate().getMonth(), respondent.getBirthdate().getDay(), respondent.getBirthdate().getYear()};
        int[] dateJoined = {respondent.getDateJoined().getMonth(), respondent.getDateJoined().getDay(), respondent.getDateJoined().getYear()};
        RespondentProfileView rpv = new RespondentProfileView(respondent.getName(), respondent.getEmailAddress(), respondent.getUsername(), birthday, respondent.getAge(), dateJoined);

        rpv.setUpdateDetailsButtonListener(e -> {
            rpv.setErrorMessages(false);

            if (rpv.validateFields() == true) {
                if (ProgramUser.checkUsernameValid(rpv.getUsername()) == true) {
                    rpv.dispose();
                    RespondentProfile(respondent);
                }
                else
                rpv.notUniqueUsername();
            }
        } );
        
        rpv.setBackButtonListener(e -> {rpv.dispose(); RespondentWelcome(respondent); } );
    }
    
    


    /////////// -----------------------------
    public void GuestSelectGeneralData() {
        service.zTestComputation(instances);
        GuestSelectGeneralDataView gssdv = new GuestSelectGeneralDataView();
        gssdv.setSubmitButtonListener(e -> {
            gssdv.setErrorMessages(false);
            if (gssdv.validateForms() == true) {
                ArrayList<String> places = gssdv.getPlaces(), days = gssdv.getDays(), times = gssdv.getTimes();
                String recommendation = gssdv.getRecommendation();
                int nRec = gssdv.getRecNum();
                ArrayList<Instance> newInstances;
    
                if (recommendation.equals("")) // if 1 - walang pinili 
                    newInstances = ServiceClass.FilterData(instances, places, days, times);
                else 
                    newInstances = ServiceClass.RankData(instances, places, days, times, recommendation, nRec);
                
                gssdv.dispose(); 
                GuestGeneralData(newInstances); 
            }
        } );
        gssdv.setBackButtonListener(e -> { gssdv.dispose(); Homepage(); } );
    }
    

    public void GuestSelectGeneralData(ProgramUser user) {
        service.zTestComputation(instances);
        GuestSelectGeneralDataView gssdv = new GuestSelectGeneralDataView();
        gssdv.setSubmitButtonListener(e -> {
            gssdv.setErrorMessages(false);
            if (gssdv.validateForms() == true) {
                ArrayList<String> places = gssdv.getPlaces(), days = gssdv.getDays(), times = gssdv.getTimes();
                String recommendation = gssdv.getRecommendation();
                int nRec = gssdv.getRecNum();
                ArrayList<Instance> newInstances;
    
                if (recommendation == null)
                    newInstances = ServiceClass.FilterData(instances, places, days, times);
                else 
                    newInstances = ServiceClass.RankData(instances, places, days, times, recommendation, nRec);
                
                gssdv.dispose(); 
                GuestGeneralData(newInstances,user); 
            }
        } );
    
        gssdv.setBackButtonListener(e -> {
            gssdv.dispose();
            switch (user) {
                case Admin admin -> AdminWelcome(admin);
                case Analyst analyst -> AnalystWelcome(analyst);
                case Respondent respondent -> RespondentWelcome(respondent);
                  default -> Homepage();  // This handles any other cases, like when user is not an Admin, Analyst, or Respondent
          } } );
    }
  

    public void GuestGeneralData(ArrayList<Instance> x) {
        final Instance[] localInstances = x.toArray(Instance[]::new);
        String[] places = new String[Constants.MAX_INSTANCE], days = new String[Constants.MAX_INSTANCE], times = new String[Constants.MAX_INSTANCE], interpretations = new String[Constants.MAX_INSTANCE];
        String[][] commentSummaries = new String[Constants.MAX_INSTANCE][100], tags = new String[Constants.MAX_INSTANCE][100];        
        int counterCS = 0, counterTag = 0;

        for (int i=0; i<Constants.MAX_INSTANCE; i++) {
            places[i] = localInstances[i].getPlaceName(); days[i] = localInstances[i].getDayName();
            times[i] = localInstances[i].getTimeName(); interpretations[i] = localInstances[i].getInterpretation();
            counterCS = localInstances[i].getSummaries().size();
            counterTag = localInstances[i].getTags().size();

            for (int j=0; i<counterCS; i++) {
                ArrayList<Observation> tempCommentSummaries = localInstances[i].getSummaries();
                commentSummaries[i][j] = tempCommentSummaries.get(j).getCSorTag();
            }
            for (int j=0; i<counterTag; i++) {
                ArrayList<Observation> tempTags = localInstances[i].getTags();
                tags[i][j] = tempTags.get(j).getCSorTag();
            }
        }
        GuestGeneralDataView gsdv = new GuestGeneralDataView(places,days,times,interpretations,commentSummaries,counterCS,tags,counterTag);
        gsdv.setBackButtonListener(e -> {gsdv.dispose(); Homepage(); } ); 
    }

    public void GuestGeneralData(ArrayList<Instance> x, ProgramUser user) {
        final Instance[] localInstances = x.toArray(Instance[]::new);
        String[] places = new String[Constants.MAX_INSTANCE], days = new String[Constants.MAX_INSTANCE], times = new String[Constants.MAX_INSTANCE], interpretations = new String[Constants.MAX_INSTANCE];
        String[][] commentSummaries = new String[Constants.MAX_INSTANCE][100], tags = new String[Constants.MAX_INSTANCE][100];        
        int counterCS = 0, counterTag = 0;

        for (int i=0; i<Constants.MAX_INSTANCE; i++) {
            places[i] = localInstances[i].getPlaceName(); days[i] = localInstances[i].getDayName();
            times[i] = localInstances[i].getTimeName(); interpretations[i] = localInstances[i].getInterpretation();
            counterCS = localInstances[i].getSummaries().size();
            counterTag = localInstances[i].getTags().size();

            for (int j=0; i<counterCS; i++) {
                ArrayList<Observation> tempCommentSummaries = localInstances[i].getSummaries();
                commentSummaries[i][j] = tempCommentSummaries.get(j).getCSorTag();
            }
            for (int j=0; i<counterTag; i++) {
                ArrayList<Observation> tempTags = localInstances[i].getTags();
                tags[i][j] = tempTags.get(j).getCSorTag();
            }
        }
        GuestGeneralDataView gsdv = new GuestGeneralDataView(places,days,times,interpretations,commentSummaries,counterCS,tags,counterTag);
        gsdv.setBackButtonListener(e -> {
            gsdv.dispose();
            switch (user) {
                case Admin admin -> AdminWelcome(admin);
                case Analyst analyst -> AnalystWelcome(analyst);
                case Respondent respondent -> RespondentWelcome(respondent);
                  default -> Homepage();  // This handles any other cases, like when user is not an Admin, Analyst, or Respondent
          } } );
    }


    // as guest
    public void GuestChooseRecommendedPath() {
        service.zTestComputation(instances);

        Node[] tempNodes = Path.getAllNodes(); 
        ArrayList<String> allNodesNames = new ArrayList<>();
        GuestChooseRecommendedPathView gclrpv = new GuestChooseRecommendedPathView(allNodesNames);
        for (int i=0; i<72; i++) {
            if (tempNodes[i].getNodeName().equals(gclrpv.getStartNode()))
                allNodesNames.add(tempNodes[i].getNodeName());            
        } 

        gclrpv.setSubmitButtonListener(e -> {
            int startNodeID = -1, goalNodeID = -1;
            if (gclrpv.validateFields() == true) {
                for (int i=0; i<72; i++) {
                    if (tempNodes[i].getNodeName().equals(gclrpv.getStartNode()))
                        startNodeID = i;
                    if (tempNodes[i].getNodeName().equals(gclrpv.getGoalNode()))
                        goalNodeID = i;
                }
                Path path = new Path(Day.getDayIdxFromName(gclrpv.getDay()), TimeCategory.getTimeIndex(gclrpv.getTime()), startNodeID, goalNodeID, 0);    
                if (path.getPathByZScore().isEmpty())
                    GuestRecommendedPath(path.getShortestPath(), 0);
                else
                    GuestRecommendedPath(path.getPathByZScore(), 1);
            }
        } );
        gclrpv.setBackButtonListener(e -> {gclrpv.dispose(); Homepage(); } ); 
    }


    public void GuestChooseRecommendedPath(ProgramUser user) {
        service.zTestComputation(instances);

        Node[] tempNodes = Path.getAllNodes(); 
        ArrayList<String> allNodesNames = new ArrayList<>();
        GuestChooseRecommendedPathView gclrpv = new GuestChooseRecommendedPathView(allNodesNames);
        for (int i=0; i<72; i++) {
            if (tempNodes[i].getNodeName().equals(gclrpv.getStartNode()))
                allNodesNames.add(tempNodes[i].getNodeName());            
        } 

        gclrpv.setSubmitButtonListener(e -> {
            int startNodeID = -1, goalNodeID = -1;
            if (gclrpv.validateFields() == true) {
                for (int i=0; i<72; i++) {
                    if (tempNodes[i].getNodeName().equals(gclrpv.getStartNode()))
                        startNodeID = i;
                    if (tempNodes[i].getNodeName().equals(gclrpv.getGoalNode()))
                        goalNodeID = i;
                }
                Path path = new Path(Day.getDayIdxFromName(gclrpv.getDay()), TimeCategory.getTimeIndex(gclrpv.getTime()), startNodeID, goalNodeID, 0);    
                if (path.getPathByZScore().isEmpty())
                    GuestRecommendedPath(path.getShortestPath(), 0, user);
                else
                    GuestRecommendedPath(path.getPathByZScore(), 1, user);
            }
        } );
        gclrpv.setBackButtonListener(e -> {
            gclrpv.dispose();
            switch (user) {
                case Admin admin -> AdminWelcome(admin);
                case Analyst analyst -> AnalystWelcome(analyst);
                case Respondent respondent -> RespondentWelcome(respondent);
                  default -> Homepage();  // This handles any other cases, like when user is not an Admin, Analyst, or Respondent
          } } );
    }


    public void GuestRecommendedPath(ArrayList<Integer> path, int flag) {
        GuestRecommendedPathView grpv = new GuestRecommendedPathView(path,flag);
        grpv.setBackButtonListener(e -> { grpv.dispose(); Homepage(); } );
    }

    public void GuestRecommendedPath(ArrayList<Integer> path, int flag, ProgramUser user) {
        GuestRecommendedPathView grpv = new GuestRecommendedPathView(path,flag);
        grpv.setBackButtonListener(e -> {
            switch (user) {
                case Admin admin -> AdminWelcome(admin);
                case Analyst analyst -> AnalystWelcome(analyst);
                case Respondent respondent -> RespondentWelcome(respondent);
                default -> Homepage();  // This handles any other cases, like when user is not an Admin, Analyst, or Respondent
            }
        } );
    }

}