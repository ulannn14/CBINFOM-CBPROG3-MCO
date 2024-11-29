package View;

import java.awt.*;
import java.awt.event.ActionListener;
import javax.swing.*;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;

public class AdminAddAnalystView extends JFrame {
    final private JButton addButton = new JButton("Add");
    final private JButton backButton = new JButton("Back");

    final private JTextField usernameField = new JTextField(15);
    final private JTextField passwordField = new JTextField(15);

    final private JLabel headerLabel = new JLabel("Add Analyst Manager");
    final private JLabel usernameLabel = new JLabel("Username");
    final private JLabel passwordLabel = new JLabel("Password");
    final private JLabel usernameErrorLabel = new JLabel("Username is already taken.");
    final private JLabel passwordErrorLabel = new JLabel("");
    final private Image backgroundImage;

    public AdminAddAnalystView() {
        // Load the background image
        backgroundImage = new ImageIcon("adminAddAnalystView.png").getImage();

        // Set frame properties
        setTitle("Add New Analyst");
        setSize(1200, 700);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);

        // Create a custom JPanel
        JPanel panel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
            }
        };

        add(panel);
        panel.setLayout(null);

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

        setVisible(true);
    }

    private void addPlaceholder(JTextField textField, String placeholder) {
        textField.setForeground(Color.GRAY);
        textField.setText(placeholder);

        textField.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                if (textField.getText().equals(placeholder)) {
                    textField.setText("");
                    textField.setForeground(Color.BLACK);
                }
            }

            @Override
            public void focusLost(FocusEvent e) {
                if (textField.getText().isEmpty()) {
                    textField.setText(placeholder);
                    textField.setForeground(Color.GRAY);
                }
            }
        });
    }

    public boolean validatePassword() {
        String password = passwordField.getText();
        if (!password.matches(".*[A-Z].*") || !password.matches(".*[!@#$%^&*(),.?\":{}|<>].*")) {
            passwordErrorLabel.setText("Password must have an uppercase letter and special character.");
            passwordErrorLabel.setVisible(true);
            return false;
        } else if (password.length() < 8 || password.length() > 25) {
            passwordErrorLabel.setText("Password must be 8 to 25 characters long.");
            passwordErrorLabel.setVisible(true);
            return false;
        }
        passwordErrorLabel.setVisible(false);
        return true;
    }

    public void notUniqueUsername() {
        usernameErrorLabel.setVisible(true);
    }

    public void setErrorMessages(boolean visible) {
        usernameErrorLabel.setVisible(visible);
        passwordErrorLabel.setVisible(visible);
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
