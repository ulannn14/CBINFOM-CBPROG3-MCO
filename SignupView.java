// CHANGE!! SEE ANO PANG KULANG!!!

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

// MISSING SECURITY PASSWORD AND RELATED METHODS OF IT

public class SignupView extends JFrame {
    private JTextField nameField, emailField, usernameField;
    private JPasswordField passwordField;
    private JComboBox<String> securityQuestionDropdown, dayDropdown, monthDropdown, yearDropdown;
    private JLabel nameErrorLabel, emailErrorLabel, usernameErrorLabel, passwordErrorLabel, securityErrorLabel, birthdayErrorLabel;
    private JButton submitButton, cancelButton;
    
    public SignupView() {
        // Set up the frame
        setTitle("Signup Form");
        setSize(400, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new GridLayout(8, 2, 10, 10));
        
        // Initialize components
        nameField = new JTextField(30);
        emailField = new JTextField(30);
        usernameField = new JTextField(15);
        passwordField = new JPasswordField(15);
        
        // Security question dropdown
        String[] securityQuestions = { "Security Question", "What is your pet's name?", "What is your favorite color?", "What is your mother's maiden name?" };
        securityQuestionDropdown = new JComboBox<>(securityQuestions);
        
        // Birthday dropdowns (Day, Month, Year)
        String[] days = new String[31];
        for (int i = 0; i < 31; i++) days[i] = String.valueOf(i + 1);
        
        String[] months = { "January", "February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December" };
        
        String[] years = new String[101];
        for (int i = 0; i < 101; i++) years[i] = String.valueOf(1920 + i);
        
        dayDropdown = new JComboBox<>(days);
        monthDropdown = new JComboBox<>(months);
        yearDropdown = new JComboBox<>(years);
        
        // Initialize error labels
        nameErrorLabel = createErrorLabel("Name must be 1-30 characters.");
        emailErrorLabel = createErrorLabel("Email must be 1-30 characters.");
        usernameErrorLabel = createErrorLabel("Username must be unique and up to 15 characters.");
        passwordErrorLabel = createErrorLabel("Password must contain a capital letter and a special character.");
        securityErrorLabel = createErrorLabel("Please select a security question.");
        birthdayErrorLabel = createErrorLabel("Please select a valid birthday.");
        
        // Buttons
        submitButton = new JButton("Submit");
        cancelButton = new JButton("Cancel");
        
        // Add components to frame
        add(new JLabel("Name:"));
        add(nameField);
        add(nameErrorLabel);
        
        add(new JLabel("Email:"));
        add(emailField);
        add(emailErrorLabel);
        
        add(new JLabel("Username:"));
        add(usernameField);
        add(usernameErrorLabel);
        
        add(new JLabel("Password:"));
        add(passwordField);
        add(passwordErrorLabel);
        
        add(new JLabel("Security Question:"));
        add(securityQuestionDropdown);
        add(securityErrorLabel);
        
        add(new JLabel("Birthday:"));
        JPanel birthdayPanel = new JPanel();
        birthdayPanel.add(dayDropdown);
        birthdayPanel.add(monthDropdown);
        birthdayPanel.add(yearDropdown);
        add(birthdayPanel);
        add(birthdayErrorLabel);
        
        add(submitButton);
        add(cancelButton);
        
        // Make the error labels invisible initially
        setErrorLabelsVisible(false);
    }
    
    // Helper method to create an invisible error label
    private JLabel createErrorLabel(String message) {
        JLabel label = new JLabel(message);
        label.setForeground(Color.RED);
        label.setVisible(false); // Start as invisible
        return label;
    }

    // Method to show or hide error labels based on validation
    private void setErrorLabelsVisible(boolean visible) {
        nameErrorLabel.setVisible(visible);
        emailErrorLabel.setVisible(visible);
        usernameErrorLabel.setVisible(visible);
        passwordErrorLabel.setVisible(visible);
        securityErrorLabel.setVisible(visible);
        birthdayErrorLabel.setVisible(visible);
    }

    // Method to validate form
    public boolean validateForm() {
        boolean valid = true;
        setErrorLabelsVisible(false); // Reset error visibility
        
        // Validate name
        if (nameField.getText().length() == 0 || nameField.getText().length() > 30) {
            nameErrorLabel.setVisible(true);
            valid = false;
        }
        
        // Validate email
        if (emailField.getText().length() == 0 || emailField.getText().length() > 30) {
            emailErrorLabel.setVisible(true);
            valid = false;
        }
        
        // Validate username
        if (usernameField.getText().length() == 0 || usernameField.getText().length() > 15) {
            usernameErrorLabel.setVisible(true);
            valid = false;
        }
        
        // Validate password
        String password = new String(passwordField.getPassword());
        if (password.length() == 0 || !password.matches(".*[A-Z].*") || !password.matches(".*[!@#$%^&*(),.?\":{}|<>].*")) {
            passwordErrorLabel.setVisible(true);
            valid = false;
        }
        
        // Validate security question
        if (securityQuestionDropdown.getSelectedIndex() == 0) {
            securityErrorLabel.setVisible(true);
            valid = false;
        }
        
        // Validate birthday
        if (dayDropdown.getSelectedIndex() == -1 || monthDropdown.getSelectedIndex() == -1 || yearDropdown.getSelectedIndex() == -1) {
            birthdayErrorLabel.setVisible(true);
            valid = false;
        }
        
        return valid;
    }

    // Action listener methods
    public void setSubmitButtonListener(ActionListener listener) {
        submitButton.addActionListener(listener);
    }

    public void setCancelButtonListener(ActionListener listener) {
        cancelButton.addActionListener(listener);
    }

    // Getter methods for the form data
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
        return new String(passwordField.getPassword());
    }

    public String getSecurityQuestion() {
        return (String) securityQuestionDropdown.getSelectedItem();
    }

    public String getBirthday() {
        return dayDropdown.getSelectedItem() + " " + monthDropdown.getSelectedItem() + " " + yearDropdown.getSelectedItem();
    }
}
