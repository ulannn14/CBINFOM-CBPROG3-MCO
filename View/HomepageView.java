package View;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class HomepageView extends ViewAbstract {
    final private JButton forgotPasswordButton = new JButton("Forgot password?");
    final private JButton loginButton = new JButton("Login");
    final private JButton signupButton = new JButton("Sign up");
    final private JButton findRecommendedPathButton = new JButton("Find Recommended Path");
    final private JButton viewSurveyDataButton = new JButton("View Survey Data");

    final private JTextField usernameField = new JTextField(15);
    final private JPasswordField passwordField = new JPasswordField(15);

    final private JLabel titleLabel1 = new JLabel("ISSPa:");
    final private JLabel titleLabel2 = new JLabel("Intramuros Safety Survey & Pathfinder");
    final private JLabel usernameLabel = new JLabel("Username: ");
    final private JLabel passwordLabel = new JLabel("Password: ");
    final private JLabel signupLabel = new JLabel("No account yet?");
    final private JLabel guestLabel = new JLabel("Continue as Guest?");
    final private JLabel descriptionLabel = new JLabel("<html>ISSPa empowers visually-impaired<br>individuals to safely navigate Intramuros<br>by providing survey data on the safest<br>paths and times for walking.<br><br>Log in or sign up to create an account<br>and participate in the survey.</html>");

    final private JLabel wrongCredentialsLabel = new JLabel("Wrong credentials");
    final private JLabel invalidUsernamePasswordLabel = new JLabel("Invalid username or password");

    public HomepageView() {
        super();

        setTitle("ISSPa: Intramuros Safety Survey & Pathfinder");

        forgotPasswordButton.setBounds(718,385,154,18);
        panel.add(forgotPasswordButton);
        loginButton.setBounds(675,340,240,41);
        panel.add(loginButton);
        signupButton.setBounds(768,420,109,41);
        panel.add(signupButton);
        findRecommendedPathButton.setBounds(380,500,421,41);
        panel.add(findRecommendedPathButton);
        viewSurveyDataButton.setBounds(380,550,421,41);
        panel.add(viewSurveyDataButton);
        
        usernameField.setBounds(675,260,240,30);
        addPlaceholder(usernameField, " Enter username...");
        panel.add(usernameField);
        passwordField.setBounds(675,300,240,30);
        addPasswordPlaceholder(passwordField, " Enter password...");
        panel.add(passwordField);

        titleLabel1.setBounds(520,64,140,67);
        titleLabel1.setFont(new Font("Garamond", Font.BOLD, 50));
        panel.add(titleLabel1);   
        titleLabel2.setBounds(190,130,900,67);
        titleLabel2.setFont(new Font("Garamond", Font.BOLD, 50));
        panel.add(titleLabel2);
        usernameLabel.setBounds(595,260,80,30);
        panel.add(usernameLabel);
        passwordLabel.setBounds(595,300,76,30);
        panel.add(passwordLabel);
        guestLabel.setBounds(380,480,142,18);
        panel.add(guestLabel);
        signupLabel.setBounds(653,420,118,41);
        panel.add(signupLabel);
        descriptionLabel.setBounds(235,225,283,150);
        panel.add(descriptionLabel);

        wrongCredentialsLabel.setBounds(738, 220, 142, 20);
        wrongCredentialsLabel.setForeground(Color.RED);
        panel.add(wrongCredentialsLabel);
        
        invalidUsernamePasswordLabel.setBounds(711,236,211,18);
        invalidUsernamePasswordLabel.setForeground(Color.RED);
        panel.add(invalidUsernamePasswordLabel);

        setErrorMessages(false);

        frameSetVisible();
    }

    public void setErrorMessages(boolean visible){
        wrongCredentialsLabel.setVisible(visible);
        invalidUsernamePasswordLabel.setVisible(visible);
    }

    public String getUsername() {
        return usernameField.getText();
    }

    public String getPassword() {
        return new String(passwordField.getPassword());
    }

    public void failedLogin() {
        setErrorMessages(true);
    }

    public void setForgotPasswordButtonListener(ActionListener listener) {
        forgotPasswordButton.addActionListener(listener);
    }

    public void setLoginButtonListener(ActionListener listener) {
        loginButton.addActionListener(listener);
    }

    public void setSignupButtonListener(ActionListener listener) {
        signupButton.addActionListener(listener);
    }

    public void setFindRecommendedPathButtonListener(ActionListener listener) {
        findRecommendedPathButton.addActionListener(listener);
    }

    public void setGeneralResultButtonListener(ActionListener listener) {
        viewSurveyDataButton.addActionListener(listener);
    }
}