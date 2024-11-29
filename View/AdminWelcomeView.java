package View;

import java.awt.*;
import java.awt.event.ActionListener;
import javax.swing.*;

public class AdminWelcomeView extends JFrame {
    final private JButton respondentsDataButton = new JButton("View Respondents' Data");
    final private JButton analystsDataButton = new JButton("View Analysts' Data");
    final private JButton addAnalystButton = new JButton("Add New Analyst");
    final private JButton commentSummaryButton = new JButton("View Complaint Reports");
    final private JButton changeSecQuesPassButton = new JButton("Change Security Ques. and Pass.");
    final private JButton changePassButton = new JButton("Change Password");
    final private JButton incidentReportButton = new JButton("Generate Incident Reports");
    final private JButton recommendedPathButton = new JButton("Find Recommended Path");
    final private JButton generalDataButton = new JButton("View General Data");
    final private JButton logoutButton = new JButton("Logout");
    
    final private JLabel headerLabel = new JLabel("Welcome, <admin_username>.");
    final private JLabel subheaderLabel = new JLabel("What would you like to do today?");
    final private JLabel respondentsDataLabel = new JLabel("<html>View detailed information and survey<br>responses from all registered<br>respondents.</html>");
    final private JLabel analystsDataLabel = new JLabel("<html>Access and review the data and activity<br>logs of all analysts.</html>");
    final private JLabel addAnalystLabel = new JLabel("<html>Add a new analyst to the system for<br>data management and analysis.</html>");
    final private JLabel complaintReportsLabel = new JLabel("<html>View complaint reports made by<br>analysts based on respondent<br>comments and feedback.</html>");
    final private JLabel changeSecQuesPassLabel = new JLabel("<html>Update your account security question<br>and password for enhanced security<br>and access control.</html>");
    final private JLabel changePassLabel = new JLabel("<html>Update your account password for<br>enhanced security and access control.</html>");
    final private JLabel incidentReportLabel = new JLabel("<html>Generate incident reports based on<br>analyst comment summaries and other<br>details.</html>");
    final private JLabel recommendedPathLabel = new JLabel("<html>Discover the shortest and safest path to<br>your destination.</html>");
    final private JLabel generalDataLabel = new JLabel("<html>Access general data, including insights<br>on safety ratings and feedback from<br>respondents.</html>");

    public void main(String[] args){
        JFrame frame = new JFrame("Welcome, Admin");

        // Create a custom JPanel
        JPanel panel = new JPanel();

        frame.setSize(1200, 700);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false);
        frame.add(panel);

        panel.setLayout(null); 

        respondentsDataButton.setBounds(70, 176, 280, 41);
        panel.add(respondentsDataButton);
        analystsDataButton.setBounds(70, 290, 280, 41);
        panel.add(analystsDataButton);
		addAnalystButton.setBounds(70, 404, 280, 41);
        panel.add(addAnalystButton);
		commentSummaryButton.setBounds(459, 176, 280, 41);
        panel.add(commentSummaryButton);
		changeSecQuesPassButton.setBounds(459, 290, 280, 41);
        panel.add(changeSecQuesPassButton);
		changePassButton.setBounds(459, 404, 280, 41);
        panel.add(changePassButton);
        incidentReportButton.setBounds(849, 176, 280, 41);
        panel.add(incidentReportButton);
		recommendedPathButton.setBounds(849, 290, 280, 41);
        panel.add(recommendedPathButton);
		generalDataButton.setBounds(849, 404, 280, 41);
        panel.add(generalDataButton);
		logoutButton.setBounds(50, 550, 100, 40);
        panel.add(logoutButton);

        headerLabel.setBounds(70, 70, 500, 60);
        headerLabel.setFont(new Font("Garamond", Font.BOLD, 30));
		panel.add(headerLabel);
		subheaderLabel.setBounds(70, 115, 272, 18);
		panel.add(subheaderLabel);
        respondentsDataLabel.setBounds(70, 216, 280, 62);
		panel.add(respondentsDataLabel);
		analystsDataLabel.setBounds(70, 330, 280, 40);
		panel.add(analystsDataLabel);
		addAnalystLabel.setBounds(70, 444, 280, 40);
		panel.add(addAnalystLabel);
        complaintReportsLabel.setBounds(459, 216, 280, 62);
		panel.add(complaintReportsLabel);
		changeSecQuesPassLabel.setBounds(459, 330, 280, 62);
		panel.add(changeSecQuesPassLabel);
		changePassLabel.setBounds(459, 444, 280, 40);
		panel.add(changePassLabel);
        incidentReportLabel.setBounds(849, 216, 280, 62);
		panel.add(incidentReportLabel);
		recommendedPathLabel.setBounds(849, 330, 280, 40);
		panel.add(recommendedPathLabel);
		generalDataLabel.setBounds(849, 444, 280, 62);
		panel.add(generalDataLabel);

        frame.setVisible(true);
    }

	
    // Method for setting listeners
    public void setViewRespondentsDataButtonListener(ActionListener listener) {
        respondentsDataButton.addActionListener(listener);
    }

    public void setViewAnalystsDataButtonListener(ActionListener listener) {
        analystsDataButton.addActionListener(listener);
    }

    public void setAddNewAnalystButtonListener(ActionListener listener) {
        addAnalystButton.addActionListener(listener);
    }

    public void setViewCommentSummaryReportsButtonListener(ActionListener listener) {
        commentSummaryButton.addActionListener(listener);
    }

    public void setChangeSecurityQuesAndPassButtonListener(ActionListener listener) {
        changeSecQuesPassButton.addActionListener(listener);
    }

    public void setChangePasswordButtonListener(ActionListener listener) {
        changePassButton.addActionListener(listener);
    }

    public void setGenerateIncidentReportsButtonListener(ActionListener listener) {
        incidentReportButton.addActionListener(listener);
    }

    public void setFindRecommendedPathButtonListener(ActionListener listener) {
        recommendedPathButton.addActionListener(listener);
    }

    public void setViewGeneralDataButtonListener(ActionListener listener) {
        generalDataButton.addActionListener(listener);
    }

    public void setBackButtonListener(ActionListener listener) {
        logoutButton.addActionListener(listener);
    }




}