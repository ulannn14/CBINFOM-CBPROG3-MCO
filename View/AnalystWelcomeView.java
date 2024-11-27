package View;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;

public class AnalystWelcomeView extends JFrame{
	private JButton viewSurveyDataButton = new JButton("View Survey Data");
	private JButton generateComplaintReportButton = new JButton("Generate Complaint Report");
	private JButton changePasswordButton = new JButton("Change Password");
	private JButton changeSecurityQuesAndPassButton = new JButton("Change Security Ques. and Pass.");
	private JButton findRecommendedPathButton = new JButton("Find Recommended Path");
	private JButton viewGeneralDataButton = new JButton("View General Data");
    private JButton backButton = new JButton("Logout");

    final private JLabel header = new JLabel("Welcome, <analyst_username>.");
	final private JLabel subheader = new JLabel("What would you like to do today?");
    final private JLabel viewSurveyDataLabel = new JLabel("<html>Access detailed survey data to analyze safety<br>scores, times, and other relevant factors<br>for specific instances.</html>");
	final private JLabel generateComplaintReportLabel = new JLabel("<html>Generate a complaint report that summarizes<br>respondent comments and feedback.</html>");
	final private JLabel changePasswordLabel = new JLabel("<html>Update your account password for enhanced<br>security and access control.</html>");
	final private JLabel changeSecurityQuesAndPassLabel = new JLabel("<html>Update your account security question and<br>password for enhanced security and<br>access control.</html>");
	final private JLabel findRecommendedPathLabel = new JLabel("<html>Discover the shortest and safest path to<br>your destination.</html>");
	final private JLabel viewGeneralDataLabel = new JLabel("<html>Access general data, including insights<br>on safety ratings and feedback from<br>respondents.</html>");
	
	private Image backgroundImage;
    
    public AnalystWelcomeView(){
		JFrame frame = new JFrame("Welcome, Analyst");
		
		backgroundImage = new ImageIcon("fanalystWelcomeView.png").getImage();
        
		JPanel panel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
            }
        }; 		
		
		frame.setSize(1200, 700);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false);
        frame.add(panel);

        panel.setLayout(null); 

        viewSurveyDataButton.setBounds(70, 176, 280, 41);
        panel.add(viewSurveyDataButton);

        generateComplaintReportButton.setBounds(459, 176, 280, 41);
        panel.add(generateComplaintReportButton);
		
		changePasswordButton.setBounds(70, 290, 280, 41);
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
		
		viewSurveyDataLabel.setBounds(70, 216, 280, 62);
		panel.add(viewSurveyDataLabel);
		
		generateComplaintReportLabel.setBounds(459, 216, 280, 40);
		panel.add(generateComplaintReportLabel);
		
		changePasswordLabel.setBounds(70, 330, 280, 40);
		panel.add(changePasswordLabel);
		
		changeSecurityQuesAndPassLabel.setBounds(459, 330, 280, 62);
		panel.add(changeSecurityQuesAndPassLabel);
		
		findRecommendedPathLabel.setBounds(849, 216, 280, 40);
		panel.add(findRecommendedPathLabel);
		
		viewGeneralDataLabel.setBounds(849, 330, 280, 62);
		panel.add(viewGeneralDataLabel);
		
        frame.setVisible(true);
    }
	
	public void setViewSurveyDataButtonListener(ActionListener listener){
		viewSurveyDataButton.addActionListener(listener);
	}
	
	public void setGenerateComplaintReportButtonListener(ActionListener listener){
		generateComplaintReportButton.addActionListener(listener);
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