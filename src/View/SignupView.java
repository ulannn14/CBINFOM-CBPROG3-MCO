package View;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class SignupView extends FrameCanvas {
    final private JButton signupButton = new JButton("Sign Up");
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
    final private String[] months = {"Select month...","01","02","03","04","05","06","07","08","09","10","11","12"};
    final private JComboBox<String> monthsDropdown = new JComboBox<>(months);
    final private JComboBox<String> securityQuestionsDropdown = new JComboBox<>(securityQuestions);
    final private String[] days = {"Select day...","01","02","03","04","05","06","07","08","09","10","11","12","13","14","15","16","17","18","19","20","21","22","23","24","25","26","27","28","29","30","31"};
    final private JComboBox<String> daysDropdown = new JComboBox<>(days);
    final private String[] years = {"Select year...","2024","2023","2022","2021","2020","2019","2018","2017","2016","2015","2014","2013","2012","2011","2010","2009","2008","2007","2006","2005","2004","2003","2002","2001","2000","1999","1998","1997","1996","1995","1994","1993","1992","1991","1990","1989","1988","1987","1986","1985","1984","1983","1982","1981","1980","1979","1978","1977","1976","1975","1974","1973","1972","1971","1970","1969","1968","1967","1966","1965","1964","1963","1962","1961","1960","1959","1958","1957","1956","1955","1954","1953","1952","1951","1950","1949","1948","1947","1946","1945","1944","1943","1942","1941","1940","1939","1938","1937","1936","1935","1934","1933","1932","1931","1930","1929","1928","1927","1926","1925"};
    final private JComboBox<String> yearsDropdown = new JComboBox<>(years);

    final private JTextField nameField = new JTextField(15);
    final private JTextField emailField = new JTextField(15);
    final private JTextField usernameField = new JTextField(15);
    final private JTextField passwordField = new JTextField(15);
    final private JTextField securityPasswordField = new JTextField(15);

    final private JLabel welcomeLabel = new JLabel("Welcome to ISSPa!");
    final private JLabel descriptionLabel = new JLabel("<html>Creating an account will allow you to participate in the<br>survey and help the visually-impaired community<br>navigate the streets safely.<br><br>By signing up, you agree to the storing, collecting, and<br>processing of the information provided, in compliance<br>with the Data Privacy Act of 2012. You also agree that<br>you are at least 16 years old.</html>");
    final private JLabel nameLabel = new JLabel("Name");
    final private JLabel emailLabel = new JLabel("Email");
    final private JLabel usernameLabel = new JLabel("Username");
    final private JLabel passwordLabel = new JLabel("Password");
    final private JLabel securityQuestionLabel = new JLabel("Security Question");
    final private JLabel securityPasswordLabel = new JLabel("Security Password");
    final private JLabel birthdayLabel = new JLabel("Birthday");

    final private JLabel nameErrorLabel = new JLabel("");
        // Name cannot be blank.
        // Name cannot be over 30 characters long.
    final private JLabel emailErrorLabel = new JLabel("");
        // Email cannot be blank.
        // Email cannot be over 50 characters long.
    final private JLabel usernameErrorLabel = new JLabel("");
        // Username is already taken.
        // Username must be 8 to 25 characters long.
    final private JLabel passwordErrorLabel = new JLabel("");
        // Does not meet password requirements.
        // Password must be 8 to 25 characters long.
    final private JLabel securityQuestionErrorLabel = new JLabel("Please select a valid security question.");
    final private JLabel securityPasswordErrorLabel = new JLabel("Security Password must be 8 to 25 characters long.");
    final private JLabel birthdayErrorLabel = new JLabel("Must be at least 16 years old.");
    
    /**
     * 
     */
    @SuppressWarnings("OverridableMethodCallInConstructor")
    public SignupView() {
        super();

        setTitle("Sign Up");

        signupButton.setBounds(595,590,132,41);
        panel.add(signupButton);
        backButton.setBounds(85,590,100,41);
        panel.add(backButton);

        securityQuestionsDropdown.setBounds(595,375,410,29);
        panel.add(securityQuestionsDropdown);
        monthsDropdown.setBounds(595,515,130,29);
        panel.add(monthsDropdown);
        daysDropdown.setBounds(735,515,130,29);
        panel.add(daysDropdown);
        yearsDropdown.setBounds(875,515,130,29);
        panel.add(yearsDropdown);

        nameField.setBounds(595,95,410,29);
        panel.add(nameField);
        emailField.setBounds(595,165,410,29);
        panel.add(emailField);
        usernameField.setBounds(595,235,410,29);
        panel.add(usernameField);
        passwordField.setBounds(595,305,410,29);
        panel.add(passwordField);
        securityPasswordField.setBounds(595,445,410,29);
        panel.add(securityPasswordField);

        welcomeLabel.setBounds(70,70,290,37);
        welcomeLabel.setFont(new Font("Garamond", Font.BOLD, 30));
        panel.add(welcomeLabel);
        descriptionLabel.setBounds(70,105,385,155);
        panel.add(descriptionLabel);
        nameLabel.setBounds(595,75,135,18);
        panel.add(nameLabel);
        emailLabel.setBounds(595,145,135,18);
        panel.add(emailLabel);
        usernameLabel.setBounds(595,215,135,18);
        panel.add(usernameLabel);
        passwordLabel.setBounds(595,285,135,18);
        panel.add(passwordLabel);
        securityQuestionLabel.setBounds(595,355,135,18);
        panel.add(securityQuestionLabel);
        securityPasswordLabel.setBounds(595,425,135,18);
        panel.add(securityPasswordLabel);
        birthdayLabel.setBounds(595,495,135,18);
        panel.add(birthdayLabel);

        nameErrorLabel.setBounds(608,125,280,13);
        nameErrorLabel.setForeground(Color.RED);
        panel.add(nameErrorLabel);
        emailErrorLabel.setBounds(608,195,280,13);
        emailErrorLabel.setForeground(Color.RED);
        panel.add(emailErrorLabel);
        usernameErrorLabel.setBounds(608,265,280,13);
        usernameErrorLabel.setForeground(Color.RED);
        panel.add(usernameErrorLabel);
        passwordErrorLabel.setBounds(608,335,280,13);
        passwordErrorLabel.setForeground(Color.RED);
        panel.add(passwordErrorLabel);
        securityQuestionErrorLabel.setBounds(608,405,280,13);
        securityQuestionErrorLabel.setForeground(Color.RED);
        panel.add(securityQuestionErrorLabel);
        securityPasswordErrorLabel.setBounds(608,475,300,13);
        securityPasswordErrorLabel.setForeground(Color.RED);
        panel.add(securityPasswordErrorLabel);
        birthdayErrorLabel.setBounds(608,545,280,13);
        birthdayErrorLabel.setForeground(Color.RED);
        panel.add(birthdayErrorLabel);

        setErrorMessages(false);

        frameSetVisible();
    }

    // Visibility of Error Messages
    public void setErrorMessages(boolean visible) {
        nameErrorLabel.setText("");
        emailErrorLabel.setText("");
        usernameErrorLabel.setText("");
        passwordErrorLabel.setText("");
        securityQuestionErrorLabel.setVisible(visible);
        securityPasswordErrorLabel.setVisible(visible);
        birthdayErrorLabel.setVisible(visible);
    }
    
    public void notUniqueUsername() {
        usernameErrorLabel.setText("Username is already taken.");
    }

    public boolean validateForm() {
        boolean valid = true;
        setErrorMessages(false); // Reset error visibility
        
        // Validate name
        if (nameField.getText().length() == 0) {
            nameErrorLabel.setText("Name cannot be blank.");
            valid = false;
        }
        if (nameField.getText().length() > 30) {
            nameErrorLabel.setText("Name cannot be over 30 characters long.");
            valid = false;
        }
        
        // Validate email
        if (emailField.getText().length() == 0) {
            emailErrorLabel.setText("Email cannot be blank.");
            valid = false;
        }
        if (emailField.getText().length() > 50) {
            emailErrorLabel.setText("Email cannot be over 50 characters long.");
            valid = false;
        }
        
        // Validate username
        if (validateLength(usernameField.getText()) == false) {
            usernameErrorLabel.setText("Username must be 8 to 25 characters long.");
            valid = false;
        }
        
        // Validate password
        if (validateLength(passwordField.getText()) == false) {
            passwordErrorLabel.setText("Password must be 8 to 25 characters long.");
            valid = false;
        }
        if (validatePasswordCharacters(passwordField.getText()) == false) {
            passwordErrorLabel.setText("Must contain uppercase and lowercase letters, symbols, and numbers.");
            valid = false;
        }
        
        // Validate security question
        if (securityQuestionsDropdown.getSelectedIndex() == 0) {
            securityQuestionErrorLabel.setVisible(true);
            valid = false;
        }
        
        // Validate birthday
        if (validateBirthday(getMonth(), getDay(), getYear()) == false){
            birthdayErrorLabel.setText("Must be at least 16 years old.");
            valid = false;
        }

        if (monthsDropdown.getSelectedIndex() == 0 || daysDropdown.getSelectedIndex() == 0 || yearsDropdown.getSelectedIndex() == 0) {
            birthdayErrorLabel.setText("Select an item in all dropdowns.");
            valid = false;
        }
        
        return valid;
    }

    // Getter methods for the form data
    @Override
    public String getName() {
        return nameField.getText();
    }

    public String getEmail() {
        return emailField.getText();
    }

    public String getUsername() {
        return usernameField.getText();
    }

    public String getPassword() {
        return passwordField.getText();
    }

    public String getSecurityQuestion() {
        return (String) securityQuestionsDropdown.getSelectedItem();
    }

    public int getMonth(){
        return (int) monthsDropdown.getSelectedItem();
    }

    public int getDay(){
        return (int) daysDropdown.getSelectedItem();
    }

    public int getYear(){
        return (int) yearsDropdown.getSelectedItem();
    }

    public String getSecurityPassword() {
        return securityPasswordField.getText();
    }

    // Action listener methods
    public void setSignupButtonListener(ActionListener listener) {
        signupButton.addActionListener(listener);
    }

    public void setBackButtonListener(ActionListener listener) {
        backButton.addActionListener(listener);
    }
}
