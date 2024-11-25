package View;

import java.awt.*;
import java.awt.event.ActionListener;
import javax.swing.*;

public class PasswordManagerView extends JFrame{
    private JButton changePasswordButton = new JButton("Change Password");
    private JButton backButton = new JButton("Back");

    private String[] securityQuestions = {
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
    private JComboBox securityQuestionsDropdown = new JComboBox<String>(securityQuestions);

    private JTextField usernameField = new JTextField(15);
    private JTextField securityPasswordField = new JTextField(15);
    private JTextField newPasswordField = new JTextField(15);

    private JLabel header = new JLabel("Password Manager");
    private JLabel usernameLabel = new JLabel("Username");
    private JLabel securityQuestionLabel = new JLabel("Security Question");
    private JLabel securityPasswordLabel = new JLabel("Security Password");
    private JLabel newPasswordLabel = new JLabel("New Password");

    private JLabel usernameExistsLabel = new JLabel("Username doesn't exist.");
    private JLabel mismatchedSecurityQuestionLabel = new JLabel("Mismatched security question.");
    private JLabel incorrectSecurityPasswordLabel = new JLabel("Incorrect security password.");
    private JLabel passwordErrorLabel = new JLabel("");
        // "Does not meet password requirements"
        // "Password must be 8 to 25 charactes long"
    
    private JPanel panel = new JPanel();

    public PasswordManagerView(){
        setSize(1200, 700);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);
        add(panel);

        panel.setLayout(null); 

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
        usernameExistsLabel.setVisible(false);
        panel.add(usernameExistsLabel);
        mismatchedSecurityQuestionLabel.setBounds(420,268,190,13);
        mismatchedSecurityQuestionLabel.setForeground(Color.RED);
        mismatchedSecurityQuestionLabel.setVisible(false);
        panel.add(mismatchedSecurityQuestionLabel);
        incorrectSecurityPasswordLabel.setBounds(420,340,209,13);
        incorrectSecurityPasswordLabel.setForeground(Color.RED);
        incorrectSecurityPasswordLabel.setVisible(false);
        panel.add(incorrectSecurityPasswordLabel);
        passwordErrorLabel.setBounds(420,418,237,13);
        passwordErrorLabel.setForeground(Color.RED);
        panel.add(passwordErrorLabel);

        setVisible(true);
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
        int uppercaseCtr = 0, lowercaseCtr = 0, numberCtr = 0;
        int length = password.length();
        boolean valid = true;

        if (length < 8 || length > 25){  // check if password is 8 to 25 characters long
            passwordErrorLabel.setText("Password must be 8 to 25 charactes long.");
            valid = false;
        } else {  // check if password contains at least one number
            for (int x = 0; x <= 9; x++){
                String number = Integer.toString(x);
                if (password.contains(number))
                    numberCtr++;
            }
            if (numberCtr == 0){
                passwordErrorLabel.setText("Does not meet password requirements.");
                valid = false;
            } else { // check if password contains at least one symbol
                if (!(password.contains("@") || password.contains("#")
                        || password.contains("!") || password.contains("~")
                        || password.contains("$") || password.contains("%")
                        || password.contains("^") || password.contains("&")
                        || password.contains("*") || password.contains("(")
                        || password.contains(")") || password.contains("-")
                        || password.contains("+") || password.contains("/")
                        || password.contains(":") || password.contains(".")
                        || password.contains(",") || password.contains("<")
                        || password.contains(">") || password.contains("?")
                        || password.contains("|"))) {
                    passwordErrorLabel.setText("Does not meet password requirements.");
                    valid = false;
                } else { // check if password contains at least one uppercase letter
                    for (int x = 65; x <= 90; x++){
                        char ascii = (char)x;
                        String upper = Character.toString(ascii);
                        if (password.contains(upper))
                            uppercaseCtr++;
                    }
                    if (uppercaseCtr == 0){
                        passwordErrorLabel.setText("Does not meet password requirements.");
                        valid = false;
                    } else { // check if password contains at least one lowercase letter
                        for (int x = 97; x <= 122; x++){
                            char ascii = (char)x;
                            String lower = Character.toString(ascii);
                            if (password.contains(lower))
                                lowercaseCtr++;
                        }
                        if (lowercaseCtr == 0){
                            passwordErrorLabel.setText("Does not meet password requirements.");
                            valid = false;
                        } else {
                            if (password.contains(" ")){ // check if password contains whitespaces
                                passwordErrorLabel.setText("Does not meet password requirements.");
                                valid = false;
                            }
                        }
                    }
                }
            }
        }
        return valid;
    }
}



