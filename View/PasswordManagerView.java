package View;

import java.awt.*;
import javax.swing.*;
import java.awt.event.*;

public class PasswordManagerView extends FrameCanvas{
    final private JButton changePasswordButton = new JButton("Change Password");
    final private JButton backButton = new JButton("Back");

    final private String[] securityQuestions = {
        "Select security question...",
        "What is your mother's maiden name?",
        "What is the name of your first pet?",
        "In what city were you born?",
        "What is the name of your favorite teacher?",
        "What is the model of your first car?",
        "What is the name of your favorite book?",
        "What is the name of your favorite movie?",
        "What is brand of your first mobile phone?",
        "What is the name of the street you grew up on?",
        "What is your favorite food?"
    };
    final private JComboBox<String> securityQuestionsDropdown = new JComboBox<>(securityQuestions);

    final private JTextField usernameField = new JTextField(15);
    final private JTextField securityPasswordField = new JTextField(15);
    final private JTextField newPasswordField = new JTextField(15);

    final private JLabel header = new JLabel("Password Manager");
    final private JLabel usernameLabel = new JLabel("Username");
    final private JLabel securityQuestionLabel = new JLabel("Security Question");
    final private JLabel securityPasswordLabel = new JLabel("Security Password");
    final private JLabel newPasswordLabel = new JLabel("New Password");

    final private JLabel usernameExistsLabel = new JLabel("Username doesn't exist.");
    final private JLabel mismatchedSecurityQuestionLabel = new JLabel("Mismatched security question.");
    final private JLabel incorrectSecurityPasswordLabel = new JLabel("Incorrect security password.");
    final private JLabel passwordErrorLabel = new JLabel("");
        // "Does not meet password requirements"
        // "Password must be 8 to 25 charactes long"

    @SuppressWarnings("OverridableMethodCallInConstructor")
    public PasswordManagerView(){
        super();

        setTitle("Password Manager");

        changePasswordButton.setBounds(494,550,211,41);
        panel.add(changePasswordButton);
        backButton.setBounds(85,550,100,41);
        panel.add(backButton);

        securityQuestionsDropdown.setBounds(412,236,374,29);
        panel.add(securityQuestionsDropdown);

        usernameField.setBounds(412,159,374,29);
        panel.add(usernameField);
        securityPasswordField.setBounds(412,309,374,29);
        panel.add(securityPasswordField);
        newPasswordField.setBounds(412,386,374,29);
        panel.add(newPasswordField);

        header.setBounds(373,70,453,45);
        header.setFont(new Font("Garamond", Font.BOLD, 40));
        header.setHorizontalAlignment(SwingConstants.CENTER);
        panel.add(header);
        usernameLabel.setBounds(412,138,85,18);
        panel.add(usernameLabel);
        securityQuestionLabel.setBounds(412,216,134,18);
        panel.add(securityQuestionLabel);
        securityPasswordLabel.setBounds(412,288,137,18);
        panel.add(securityPasswordLabel);
        newPasswordLabel.setBounds(412,366,137,18);
        panel.add(newPasswordLabel);

        usernameExistsLabel.setBounds(420,190,165,13);
        usernameExistsLabel.setForeground(Color.RED);
        panel.add(usernameExistsLabel);
        mismatchedSecurityQuestionLabel.setBounds(420,268,190,13);
        mismatchedSecurityQuestionLabel.setForeground(Color.RED);
        panel.add(mismatchedSecurityQuestionLabel);
        incorrectSecurityPasswordLabel.setBounds(420,340,209,13);
        incorrectSecurityPasswordLabel.setForeground(Color.RED);
        panel.add(incorrectSecurityPasswordLabel);
        passwordErrorLabel.setBounds(420,418,237,13);
        passwordErrorLabel.setForeground(Color.RED);
        panel.add(passwordErrorLabel);

        setErrorMessages(false);

        frameSetVisible();
    }

    public void setErrorMessages(boolean visible){
        usernameExistsLabel.setVisible(visible);
        mismatchedSecurityQuestionLabel.setVisible(visible);
        incorrectSecurityPasswordLabel.setVisible(visible);
        passwordErrorLabel.setText("");
    }

    public String getUsername() {
        return usernameField.getText();
    }

    public String getSecurityQuestion() {
        return (String) securityQuestionsDropdown.getSelectedItem();
    }

    public String getSecurityPassword() {
        return securityPasswordField.getText();
    }

    public String getPassword() {
        return newPasswordField.getText();
    }

    public void wrongUsername(){
        usernameExistsLabel.setVisible(true);
    }

    public void wrongSecurityQuestion() {
        mismatchedSecurityQuestionLabel.setVisible(true);
    }

    public void wrongSecurityPassword() {
        incorrectSecurityPasswordLabel.setVisible(true);
    }

    public boolean validatePassword(String password){
        boolean valid = true;

        if (validatePasswordCharacters(password) == false){
            passwordErrorLabel.setText("Does not meet password requirements.");
            valid = false;
        }

        if (validateLength(password) == false){
            passwordErrorLabel.setText("Password must be 8 to 25 charactes long.");
            valid = false;
        }

        return valid;
    }

    public void setChangePasswordButtonListener(ActionListener listener) {
        changePasswordButton.addActionListener(listener);
    }

    public void setBackButtonListener(ActionListener listener) {
        backButton.addActionListener(listener);
    }
}



