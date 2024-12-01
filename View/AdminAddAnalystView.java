package View;

import java.awt.*;
import javax.swing.*;
import java.awt.event.*;

public class AdminAddAnalystView extends FrameCanvas {
    final private JButton addButton = new JButton("Add");
    final private JButton backButton = new JButton("Back");

    final private JTextField usernameField = new JTextField(15);
    final private JTextField passwordField = new JTextField(15);

    final private JLabel headerLabel = new JLabel("Add Analyst Manager");
    final private JLabel usernameLabel = new JLabel("Username");
    final private JLabel passwordLabel = new JLabel("Password");
    final private JLabel usernameErrorLabel = new JLabel("Username is already taken.");
    final private JLabel passwordErrorLabel = new JLabel("");

    public AdminAddAnalystView() {
        super(); 

        setTitle("Add Analyst Manager");

        // Add components to the panel
        addButton.setBounds(480, 550, 150, 41);
        panel.add(addButton);
        backButton.setBounds(85, 550, 100, 41);
        panel.add(backButton);

        usernameField.setBounds(390, 159, 410, 29);
        addPlaceholder(usernameField, " ex. juandelacruz");
        panel.add(usernameField);

        passwordField.setBounds(390, 236, 410, 29);
        addPlaceholder(passwordField, " It must contain a capital letter and special character");
        panel.add(passwordField);

        headerLabel.setBounds(370, 70, 453, 45);
        headerLabel.setFont(new Font("Garamond", Font.BOLD, 30));
        headerLabel.setHorizontalAlignment(SwingConstants.CENTER);
        panel.add(headerLabel);

        usernameLabel.setBounds(390, 138, 85, 18);
        panel.add(usernameLabel);
        passwordLabel.setBounds(390, 216, 134, 18);
        panel.add(passwordLabel);

        usernameErrorLabel.setBounds(403, 190, 165, 13);
        usernameErrorLabel.setForeground(Color.RED);
        usernameErrorLabel.setVisible(false);
        panel.add(usernameErrorLabel);

        passwordErrorLabel.setBounds(403, 268, 300, 13);
        passwordErrorLabel.setForeground(Color.RED);
        passwordErrorLabel.setVisible(false);
        panel.add(passwordErrorLabel);

        setErrorMessages(false);

        frameSetVisible();
    }

    public void setErrorMessages(boolean visible) {
        usernameErrorLabel.setVisible(visible);
        passwordErrorLabel.setText("");
    }

    public void notUniqueUsername() {
        usernameErrorLabel.setVisible(true);
    }

    public boolean validatePassword() {
        boolean valid = true;
        setErrorMessages(false);

        if (validateLength(passwordField.getText()) == false){
            passwordErrorLabel.setText("Password must be 8 to 25 characters long.");
            passwordErrorLabel.setVisible(true);
        }
        if (validatePasswordCharacters(passwordField.getText()) == false) {
            passwordErrorLabel.setText("Does not meet password requirements.");
            passwordErrorLabel.setVisible(true);
        }

        return valid;
    }

    public String getUsername() {
        return usernameField.getText();
    }

    public String getPassword() {
        return passwordField.getText();
    }

    public void setAddButtonListener(ActionListener listener) {
        addButton.addActionListener(listener);
    }

    public void setBackButtonListener(ActionListener listener) {
        backButton.addActionListener(listener);
    }
}
