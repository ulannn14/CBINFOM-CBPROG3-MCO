package View;

import java.awt.*;
import java.awt.event.*;
import java.time.*;
import javax.swing.*;


abstract class ViewAbstract extends JFrame{
    JPanel panel = new JPanel();

    public ViewAbstract(){
        setSize(1200, 700);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);
        add(panel);

        panel.setLayout(null);
    }

    protected void frameSetVisible(){
        setVisible(true);
    }

    protected static void addPlaceholder(JTextField textField, String placeholder) {
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

    protected static void addPasswordPlaceholder(JPasswordField passwordField, String placeholder) {
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

    protected boolean validateBirthday(int month, int day, int year){
        LocalDate today = LocalDate.now();
        int curryear = today.getYear();
        int currmonth = today.getMonthValue();
        int currday = today.getDayOfMonth();
        int age = curryear - year;
        boolean flag, valid;

        if (curryear >= year) {
            if (curryear == year) {
                if (currmonth >= month) {
                    if (currmonth == month) {
                        if (currday >= day)
                            flag = true;
                        else 
                            flag = false;
                    } else 
                        flag = true;
                } else 
                    flag = false;
            } else 
                flag = true;
        } else 
            flag = false;

        if (!flag) age -= 1;

        if (age >= 16) valid = true;
        else valid = false;

        return valid;
    }

    protected boolean validateLength(String input){
        boolean valid = true;

        if (input.length() < 8 || input.length() > 25)  // check if input is 8 to 25 characters long
            valid = false;

        return valid;
    }

    protected boolean validatePasswordCharacters(String password){
        int uppercaseCtr = 0, lowercaseCtr = 0, numberCtr = 0;
        boolean valid = true;

        for (int x = 0; x <= 9; x++){
            String number = Integer.toString(x);
            if (password.contains(number))
                numberCtr++;
        }
        if (numberCtr == 0){ // check if password contains at least one number
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
                valid = false;
            } else { // check if password contains at least one uppercase letter
                for (int x = 65; x <= 90; x++){
                    char ascii = (char)x;
                    String upper = Character.toString(ascii);
                    if (password.contains(upper))
                        uppercaseCtr++;
                }
                if (uppercaseCtr == 0){
                    valid = false;
                } else { // check if password contains at least one lowercase letter
                    for (int x = 97; x <= 122; x++){
                        char ascii = (char)x;
                        String lower = Character.toString(ascii);
                        if (password.contains(lower))
                            lowercaseCtr++;
                    }
                    if (lowercaseCtr == 0){
                        valid = false;
                    } else {
                        if (password.contains(" ")){ // check if password contains whitespaces
                            valid = false;
                        }
                    }
                }
            }
        }
        return valid;
    }
}