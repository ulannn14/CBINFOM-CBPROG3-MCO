package View;

import javax.swing.*;
import java.awt.event.*;


public abstract class ViewAbstract extends JFrame{
    JPanel panel = new JPanel();

    public ViewAbstract(){
        setSize(1200, 700);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);
        add(panel);

        panel.setLayout(null);

        setVisible(true);
    }

    private static void addPlaceholder(JTextField textField, String placeholder) {
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

    private static void addPasswordPlaceholder(JPasswordField passwordField, String placeholder) {
        passwordField.setText(placeholder);
        passwordField.setEchoChar((char) 0); // Disable masking
        passwordField.setForeground(Color.GRAY);

        passwordField.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                if (String.valueOf(passwordField.getPassword()).equals(placeholder)) {
                    passwordField.setText("");
                    passwordField.setEchoChar('\u2022'); // Enable masking
                    passwordField.setForeground(Color.BLACK);
                }
            }

            @Override
            public void focusLost(FocusEvent e) {
                if (String.valueOf(passwordField.getPassword()).isEmpty()) {
                    passwordField.setText(placeholder);
                    passwordField.setEchoChar((char) 0); // Disable masking
                    passwordField.setForeground(Color.GRAY);
                }
            }
        });
    }
}