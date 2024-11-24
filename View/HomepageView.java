import java.awt.*;
import java.awt.event.ActionListener;
import javax.swing.*;

public class HomepageView extends JFrame {

    private JButton forgotPasswordButton = new JButton("Forgot password?");
    private JButton loginButton = new JButton("Login");
    private JButton signupButton = new JButton("Sign up");
    // CHANGED 
    private JButton findRecommendedPath = new JButton("Find Recommended path");
    private JButton viewSurveyDataButton = new JButton("View Survey Data");

    private JTextField usernameField = new JTextField(15);
    private JPasswordField passwordField = new JPasswordField(15);

    private JLabel titleLabel = new JLabel("<html>ISSPa:<br>Intramuros Safety Survey & Pathfinder</html>");
    private JLabel usernameLabel = new JLabel("Username: ");
    private JLabel passwordLabel = new JLabel("Password: ");
    private JLable wrongCredentialsLabel = new JLabel("Wrong credentials");
    private JLabel invalidUsernamePasswordLabel = new JLabel("Invalid username or password");
    private JLabel signupLabel = new JLabel("No account yet?");
    private JLabel guestLabel = new JLabel("Continue as Guest?");
    private JLabel descriptionLabel = new JLabel("<html>ISSPa empowers visually-impaired<br>individuals to safely navigate Intramuros<br>by providing survey data on the safest<br>paths and times for walking.<br><br>Log in or sign up to create an account<br>and participate in the survey.</html>")

    private JPanel panel = new JPanel();

    public HomepageView() {
        setTitle("ISSPa: Intramuros Safety Survey & Pathfinder");
        setSize(1200, 700);
        setResizable(false);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        forgotPasswordButton.setBounds(728.6,397.1,154.3,18.8);
        panel.add(forgotPasswordButton);
        loginButton.setBounds(685.7,351.5,240,41.5);
        panel.add(loginButton);
        signupButton.setBounds(768,444.4,109.8,41.5);
        panel.add(signupButton);
        findShortestPathButton.setBounds(389.4,533.9,421.3,41.5);
        panel.add(findShortestPathButton);
        viewSurveyDataButton.setBounds(389.4,583.4,421.3,41.5);
        panel.add(viewSurveyDataButton);

        
        usernameField.setBounds(685.7,278.3,240,29.3);
        panel.add(usernameField);
        passwordField.setbBunds(685.7,311.5,240,29.3);
        panel.add(passwordField);

        titleLabel.setBounds(130.5,71,939.1,134);
        titleLabel.setFont(new Font("Garamond", Font.PLAIN, 42));
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
        titleLabel.setVerticalAlignment(SwingConstants.TOP);
        panel.add(titleLabel);
        usernameLabel.setBounds(600.1,283.5,80,18.8);
        panel.add(usernameLabel);
        passwordLabel.setBounds(600.1,316.8,76.6,18.8);
        panel.add(passwordLabel);
        guestLabel.setBounds(389.4,507.5,142.1,18.88);
        panel.add(guestLabel);
        signupLabel.setBounds(642,455,118,18);
        panel.add(signupLabel);
        descriptionLabel.setBounds(241.4,255.7,283.3,150.8);
        panel.add(descriptionLabel);

        setVisible(true);

        wrongCredentials.setBounds(728.6, 232.7, 142.3, 20.4);
        wrongCredentials.setForeground(Color.RED);
        wrongCredentials.setVisible(false);
        panel.add(wrongCredentials);
        
        invalidUsernamePasswordLabel.setBounds(699.8,253.1,211.9,18.8);
        invalidUsernamePasswordLabel.setForeground(Color.RED);
        invalidUsernamePasswordLabel.setVisible(false);
        panel.add(invalidUsernamePasswordLabel);

        
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

    // CHANGED
    public void setFindRecommendedPathButtonListener(ActionListener listener) {
        findShortestPathButton.addActionListener(listener);
    }

    public void setViewSurveyDataButtonListener(ActionListener listener) {
        viewSurveyDataButton.addActionListener(listener);
    }

    public String getUsername() {
        return usernameField.getText();
    }

    public String getPassword() {
        return new String(passwordField.getPassword());
    }

    public void failedLogin() {
        wrongCredentials.setVisible(true);
    }

}
