package View;

import java.awt.*;
import javax.swing.*;
import java.awt.event.*;

public class RespondentWelcomeView extends FrameCanvas{
	final private JButton takeSurveyButton = new JButton("Take Survey");
	final private JButton viewSurveyHistoryButton = new JButton("View Survey History");
	final private JButton viewOrUpdateProfileButton = new JButton("View or Update Profile");
	final private JButton changePasswordButton = new JButton("Change Password");
	final private JButton changeSecurityQuesAndPassButton = new JButton("Change Security Ques. and Pass.");
	final private JButton findRecommendedPathButton = new JButton("Find Recommended Path");
	final private JButton viewGeneralDataButton = new JButton("View General Data");
    final private JButton backButton = new JButton("Logout");

    final private JLabel header = new JLabel("Welcome, <respondent_username>.");
	final private JLabel subheader = new JLabel("What would you like to do today?");
    final private JLabel takeSurveyLabel = new JLabel("<html>Participate in the survey and provide your<br>answers based on your experiences with<br>various places.</html>");
	final private JLabel viewSurveyHistoryLabel = new JLabel("<html>Access your past survey responses and view<br>details about the surveys you've completed.</html>");
	final private JLabel viewOrUpdateProfileLabel = new JLabel("<html>Edit your personal details such as name,<br>birthdate, and email.</html>");
	final private JLabel changePasswordLabel = new JLabel("<html>Update your account password for enhanced<br>security and access control.</html>");
	final private JLabel changeSecurityQuesAndPassLabel = new JLabel("<html>Update your account security question and<br>password for enhanced security and<br>access control.</html>");
	final private JLabel findRecommendedPathLabel = new JLabel("<html>Discover the shortest and safest path to<br>your destination.</html>");
	final private JLabel viewGeneralDataLabel = new JLabel("<html>Access general data, including insights<br>on safety ratings and feedback from<br>respondents.</html>");
    
    public RespondentWelcomeView(){
		super();

		setTitle("Welcome, Respondent");

        takeSurveyButton.setBounds(70, 176, 280, 41);
        panel.add(takeSurveyButton);

        viewSurveyHistoryButton.setBounds(70, 290, 280, 41);
        panel.add(viewSurveyHistoryButton);
		
		viewOrUpdateProfileButton.setBounds(70, 404, 280, 41);
        panel.add(viewOrUpdateProfileButton);
		
		changePasswordButton.setBounds(459, 176, 280, 41);
        panel.add(changePasswordButton);
		
		changeSecurityQuesAndPassButton.setBounds(459, 290, 280, 41);
        panel.add(changeSecurityQuesAndPassButton);
		
		findRecommendedPathButton.setBounds(849, 176, 280, 41);
        panel.add(findRecommendedPathButton);
		
		viewGeneralDataButton.setBounds(849, 290, 280, 41);
        panel.add(viewGeneralDataButton);
		
		backButton.setBounds(50, 550, 100, 40);
        panel.add(backButton);
		
		header.setBounds(70, 70, 500, 60);
        header.setFont(new Font("Garamond", Font.BOLD, 30));
		panel.add(header);
		
		subheader.setBounds(70, 115, 272, 18);
		panel.add(subheader);
		
		takeSurveyLabel.setBounds(70, 216, 280, 62);
		panel.add(takeSurveyLabel);
		
		viewSurveyHistoryLabel.setBounds(70, 330, 280, 40);
		panel.add(viewSurveyHistoryLabel);
		
		viewOrUpdateProfileLabel.setBounds(70, 444, 280, 40);
		panel.add(viewOrUpdateProfileLabel);
		
		changePasswordLabel.setBounds(459, 216, 280, 40);
		panel.add(changePasswordLabel);
		
		changeSecurityQuesAndPassLabel.setBounds(459, 330, 280, 62);
		panel.add(changeSecurityQuesAndPassLabel);
		
		findRecommendedPathLabel.setBounds(849, 216, 280, 40);
		panel.add(findRecommendedPathLabel);
		
		viewGeneralDataLabel.setBounds(849, 330, 280, 62);
		panel.add(viewGeneralDataLabel);
		
        frameSetVisible();
    }
	
	public void setTakeSurveyButtonListener(ActionListener listener){
		takeSurveyButton.addActionListener(listener);
	}
	
	public void setViewSurveyHistoryButtonListener(ActionListener listener){
		viewSurveyHistoryButton.addActionListener(listener);
	}
	
	public void setViewOrUpdateProfileButtonListener(ActionListener listener){
		viewOrUpdateProfileButton.addActionListener(listener);
	}
	
	public void setChangePasswordButtonListener(ActionListener listener){
		changePasswordButton.addActionListener(listener);
	}
	
	public void setChangeSecurityQuesAndPassButtonListener(ActionListener listener){
		changeSecurityQuesAndPassButton.addActionListener(listener);
	}
	
	public void setFindRecommendedPathButtonListener(ActionListener listener){
		findRecommendedPathButton.addActionListener(listener);
	}
	
	public void setViewGeneralDataButtonListener(ActionListener listener){
		viewGeneralDataButton.addActionListener(listener);
	}
	
	public void setBackButtonListener(ActionListener listener){
		backButton.addActionListener(listener);
	}
}