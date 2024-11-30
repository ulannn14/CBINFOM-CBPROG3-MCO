package Controller;

import Model.*;
import View.*;
import ServiceClassPackage.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;

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
        awv.setViewCommentSummaryReportsButtonListener(e -> { AdminViewCommentSummaryReports(admin); awv.dispose(); } );
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
    
        aaav.setAddButtonListener(e -> {
            aaav.setErrorMessages(false);
    
            boolean validUsername = ProgramUser.checkUsernameValid(aaav.getUsername()); 
            boolean validPassword = aaav.validatePassword();
    
            if (validUsername && validPassword) {
                @SuppressWarnings("unused")
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
        String[] places = new String[Constants.MAX_INSTANCE];
        String[] days = new String[Constants.MAX_INSTANCE];
        String[] times = new String[Constants.MAX_INSTANCE];
        String[][] commentSummaries = new String[Constants.MAX_INSTANCE][100];
        String[][] admins = new String[Constants.MAX_INSTANCE][100];
        String[] interpretations = new String[Constants.MAX_INSTANCE]; 

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
                ArrayList<CommentSummaryAndTag> tempCommentSummaries = instances[i].getSummaries();
                commentSummaries[i][j] = tempCommentSummaries.get(j).getCSorTag();
                admins[i][j] = tempCommentSummaries.get(j).getUsername();
            }
        }
        AdminViewCommentSummaryReportsView avcsrv = new AdminViewCommentSummaryReportsView(instanceIDs,places,days,times,interpretations,commentSummaries,admins,counterCS);

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
        //anwv.setFindRecommendedPathButtonListener(e-> { GuestChooseLRecommendedPath(analyst); anwv.dispose(); } );
        //anwv.setViewGeneralDataButtonListener(e-> {GuestSelectSurveyData(analyst); anwv.dispose(); } );       

        anwv.setBackButtonListener(e -> {anwv.dispose(); Homepage(); } );
    }


    public void AnalystViewInstancesData(Analyst analyst) {
        service.zTestComputation(instances);
        
        int[] instanceIDs = new int[Constants.MAX_INSTANCE];
        String[] places = new String[Constants.MAX_INSTANCE];
        String[] days = new String[Constants.MAX_INSTANCE];
        String[] times = new String[Constants.MAX_INSTANCE];
        Double[] zScores = new Double[Constants.MAX_INSTANCE];
        String[] interpretations = new String[Constants.MAX_INSTANCE]; 

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
            interpretations[i] = instances[i].getInterpretation();

            counterCS = instances[i].getSummaries().size();
            counterTag = instances[i].getTags().size();

            for (int j=0; i<counterCS; i++) {
                ArrayList<CommentSummaryAndTag> tempCommentSummaries = instances[i].getSummaries();
                commentSummaries[i][j] = "[" +  tempCommentSummaries.get(j).getUsername() + "] " + tempCommentSummaries.get(j).getCSorTag();
            }

            for (int j=0; i<counterTag; i++) {
                ArrayList<CommentSummaryAndTag> tempTags = instances[i].getTags();
                tags[i][j] = "[" +  tempTags.get(j).getUsername() + "] " + tempTags.get(j).getCSorTag();
            }

        }

        AnalystViewInstancesDataView avsdv = new AnalystViewInstancesDataView(instanceIDs,places,days,times,zScores,interpretations,counterCS,counterTag);
        
        avsdv.setModifyButtonListener(e -> {
            if (avsdv.validateInstanceID() == true) {
                int index = avsdv.getInstanceID();
                index--;
                Instance selectedInstance = instances[index]; // instance id directly ng chinoose
                analystModifyTagsAndComments(analyst, selectedInstance);
            }
        } );

        avsdv.setBackButtonListener(e -> {avsdv.dispose(); AnalystWelcome(analyst); } );
    }
    
    public void analystModifyTagsAndComments(Analyst analyst, Instance instance) {
        // need to have parameter instance to know which tag to display
        ArrayList<CommentSummaryAndTag> tempCS = instance.getSummaries();
        ArrayList<CommentSummaryAndTag> tempTags = instance.getTags();
        int counterCS = instance.getSummaries().size();
        int counterTag = instance.getTags().size();

        String[] summaries = new String[counterCS];
        String[] tags = new String[counterTag];

        for (int i=0; i<counterCS; i++) {
            summaries[i] = "[" + tempCS.get(i).getUsername() + "] " + tempCS.get(i).getCSorTag();
        }

        for (int i=0; i<counterTag; i++) {
            tags[i] = "[" + tempTags.get(i).getUsername() + "] " + tempTags.get(i).getCSorTag();
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
                CommentSummaryAndTag newTag = new CommentSummaryAndTag(analyst.getUsername(), amtacv.getNewTag());
                instance.addTag(newTag);
                amtacv.dispose(); 
                AnalystViewInstancesData(analyst);
            }
        } );

        amtacv.setAddSummaryButtonListener(e -> { 
            amtacv.setErrorMessages(false);
            if (amtacv.validateAddCS() == true) {
                CommentSummaryAndTag newCS = new CommentSummaryAndTag(analyst.getUsername(), amtacv.getNewCS());   // MUST RETURN STRING
                instance.addCommentSummary(newCS);
                amtacv.dispose(); 
                AnalystViewInstancesData(analyst);
            }
        } );        

        amtacv.setBackButtonListener(e -> {amtacv.dispose(); AnalystViewSurveyData(analyst); } );
    }

    
    public void RespondentWelcome(Respondent respondent) {
        RespondentWelcomeView rwv = new RespondentWelcomeView();

        rwv.setTakeSurveyButtonListener(e -> { rwv.dispose(); RespondentTakeSurvey(respondent); } );
        rwv.setViewSurveyHistoryButtonListener(e -> { rwv.dispose(); RespondentViewHistory(respondent); } );
        rwv.setViewOrUpdateProfileButtonListener(e -> { rwv.dispose(); RespondentProfile(respondent); } );
        rwv.setChangeSecurityQuesAndPassButtonListener(e -> { rwv.dispose(); ChangeSecurity(respondent); } );
        rwv.setChangePasswordButtonListener(e -> { rwv.dispose(); PasswordManager(respondent); } );
        //rwv.setFindRecommendedPathButtonListener(e-> { rwv.dispose(); GuestChooseLRecommendedPath(respondent); } );
        //rwv.setViewGeneralDataButtonListener(e-> { rwv.dispose(); GuestSelectSurveyData(respondent); } );       

        rwv.setBackButtonListener(e -> {rwv.dispose(); Homepage(); } );
    }

    // mali pa to !!!
    public void RespondentTakeSurvey(Respondent respondent) {
        RespondentTakeSurveyView rtsv = new RespondentTakeSurveyView();

        rtsv.setSubmitButtonListener(e -> { 
            rtsv.setErrorMessages(false);
            if (validateForm() == true) {
                // must be able to give the following:
                            
                int idx = getInstanceID(rtsv.getPlaceName(), rtsv.getPlaceDay(), rtsv.getPlaceTime());
                LocalDate today = LocalDate.now();
                DateClass dateTaken = new DateClass(today.getMonthValue(), today.getDayOfMonth(), today.getYear()); 

                Survey survey = new Survey(rtsv.getAnswers(), respondent.getUsername(), rtsv.getComment(), dateTaken, rtsv.getPlace(), rtsv.getDay(), rtsv.getTime());
                instances[idx].takeSurvey(survey);
                rtsv.dispose();
                RespondentWelcome(respondent);
            }
        } );

        rtsv.setBackButtonListener(e -> {rtsv.dispose(); RespondentWelcome(respondent); } );
    }

    public void RespondentViewHistory(Respondent respondent) {
        ArrayList<Survey> surveys = Instance.fetchByRespondentUsername(respondent.getUsername());
        
        int[][] answers = new int[Constants.MAX_INSTANCE][20];   
        String[] places;
        String[] days;
        String[] times;
        String[] dateTakens;
        int surveyCounter = surveys.size();
        
        // iterate through each survey
        for (int i=0; i<surveyCounter; i++) {
            places[i] = surveys.get(i).getPlaceName();
            days[i] = surveys.get(i).getDayName();
            times[i] = surveys.get(i).getDayName();
            DateClass tempDate = surveys.get(i).getDateTaken();
            dateTakens[i] = tempDate.convertToString();

            int[] tempAnswers = new int[20]; // Declare and initialize the array
            tempAnswers = surveys.get(i).getAnswers(); // Assign the answers from the survey to tempAnswers
            for (int j=0; j<20; j++) {
                answers[i][j] = tempAnswers[j];
            } 
        }
        
        RespondentViewHistoryView rvhv = new RespondentViewHistoryView(answers,places,days,times,dateTakens);
        
        

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
                rpv.;notUniqueUsername();
            }

        } );
        
        rpv.setBackButtonListener(e -> {rpv.dispose(); RespondentWelcome(respondent); } );
    }
    
    
    // as guest
    public void GuestChooseLRecommendedPath(Instance[] instances) {
        service.zTestComputation(instances);
        guestChooseLRecommendedPathView gclrpv = new guestChooseLRecommendedPathView();
        
        gclrpv.setSubmitButtonListener( e -> {
            gclrpv.setErrorMessages(false);

            if (validateField() == true) {
            
                //ArrayList<String> placesToFind
            
                GuestRecommendedPath();
            }



        } );
    }

    public void GuestRecommendedPath() {
        
    }


    // if all, return all names in an arraylist ha
    public void GuestSelectSurveyData(Instance[] instances) {
        service.zTestComputation(instances);
        GuestSelectSurveyDataView gssdv = new GuestSelectSurveyDataView();
        
        gssdv.setSubmitButtonListener(e -> {
            gssdv.setErrorMessages(false);
            if (validateForm() == true) {
                
                ArrayList<String> places = gssdv.getPlaces();
                ArrayList<String> days = gssdv.getDays();
                ArrayList<String> times = gssdv.getTimes();
                String recommendation = gssdv.getRecommendation();
                int nRec = gssdv.getRecNum();

                ArrayList<Instance> newInstances;

                if (recommendation != null) {
                    newInstances = FilterData(gssdv, instances, places, days, times);
                } else 
                    newInstances = RankData(gssdv, instances, places, days, times, recommendation, nRec);
                
                gssdv.dispose();
                GuestSurveyData(newInstances); 
            }
        } );

        gssdv.setBackButtonListener(e -> {gssdv.dispose(); Homepage(); } );        
    }


    public ArrayList<Instance> FilterData(GuestSelectSurveyDataView gssdv, Instance[] instances, ArrayList<String> places, ArrayList<String> days, ArrayList<String> times) {
        ArrayList<Instance> temp = new ArrayList<>();
        for (int i=0; i<Constants.MAX_INSTANCE; i++ ) {
            if (places.contains(instances[i].getPlaceName()) && days.contains(instances[i].getDayName()) && times.contains(instances[i].getTimeName()) && instances[i].getSampleSize() != 0) {
                temp.add(instances[i]);
            }
        }
        return temp;
    }

    public ArrayList<Instance> RankData(GuestSelectSurveyDataView gssdv, Instance[] instances, ArrayList<String> places, 
                         ArrayList<String> days, ArrayList<String> times, String recommendation, int nRec) {
        ArrayList<Instance> temp = new ArrayList<>();
        int counter = 0;
        for (int i=0; (i<Constants.MAX_INSTANCE) && (counter <= nRec); i++ ) {
            if (places.contains(instances[i].getPlaceName()) && days.contains(instances[i].getDayName()) && times.contains(instances[i].getTimeName()) && instances[i].getSampleSize() != 0) {
                temp.add(instances[i]);
                counter++;
            }
        }

        // sort
        if ( recommendation.equals("Best to worst") ) {
            Collections.sort(temp, (Instance o1, Instance o2) -> Double.compare(o1.getZScore(), o2.getZScore()));
        } else {
            Collections.sort(temp, (Instance o1, Instance o2) -> Double.compare(o2.getZScore(), o1.getZScore()));
        }

        return temp;
    }

    public void GuestSurveyData(ArrayList<Instance> x) {
        final Instance[] instances = x.toArray(Instance[]::new);
        String[] places = new String[Constants.MAX_INSTANCE];
        String[] days = new String[Constants.MAX_INSTANCE];
        String[] times = new String[Constants.MAX_INSTANCE];
        String[] interpretations = new String[Constants.MAX_INSTANCE]; 

        String[][] commentSummaries = new String[Constants.MAX_INSTANCE][100];
        String[][] tags = new String[Constants.MAX_INSTANCE][100];
        
        int counterCS = 0, counterTag = 0;

        // access data based sa unang parameter
        for (int i=0; i<Constants.MAX_INSTANCE; i++) {
            
            places[i] = instances[i].getPlaceName();
            days[i] = instances[i].getDayName();
            times[i] = instances[i].getTimeName();
            interpretations[i] = instances[i].getInterpretation();

            counterCS = instances[i].getSummaries().size();
            counterTag = instances[i].getTags().size();

            for (int j=0; i<counterCS; i++) {
                ArrayList<CommentSummaryAndTag> tempCommentSummaries = instances[i].getSummaries();
                commentSummaries[i][j] = tempCommentSummaries.get(j).getCSorTag();
            }

            for (int j=0; i<counterTag; i++) {
                ArrayList<CommentSummaryAndTag> tempTags = instances[i].getTags();
                tags[i][j] = tempTags.get(j).getCSorTag();
            }

        }


        GuestSurveyDataView gsdv = new GuestSurveyDataView(places,days,times,interpretations,commentSummaries,counterCS,tags,counterTag);
        gsdv.setBackButtonListener(e -> {gsdv.dispose(); Homepage(); } ); 

    }



}