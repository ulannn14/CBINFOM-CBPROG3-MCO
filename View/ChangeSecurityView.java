package View;

import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import javax.swing.*;

public class ChangeSecurityView extends JFrame{
	final private JButton changeSecurityDetailsButton = new JButton("Change Security Details");
    final private JButton backButton = new JButton("Back");

    private final String[] securityQuestions = {
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

    final private JPasswordField newSecurityPasswordField = new JPasswordField(15);
    final private JPasswordField accountPasswordField = new JPasswordField(15);

    final private JLabel header = new JLabel("Security Question and Password Manager");
    final private JLabel usernameLabel = new JLabel("Username: ");
    final private JLabel newSecurityQuestionLabel = new JLabel("New Security Question");
    final private JLabel newSecurityPasswordLabel = new JLabel("New Security Password");
    final private JLabel accountPasswordLabel = new JLabel("Account Password");
	
    final private JLabel blankNewSecQuesLabel = new JLabel("New Security Question cannot be blank.");
    final private JLabel incorrectAccountPasswordLabel = new JLabel("Incorrect password.");
    final private JLabel newSecPassErrorLabel = new JLabel("Does not meet password requirements.");
	
	private Image backgroundImage;
    
    public ChangeSecurityView(){
		JFrame frame = new JFrame("Change Security Question and Password");
		
		backgroundImage = new ImageIcon("fchangeSecurityView.png").getImage();
        
		JPanel panel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
            }
        }; 		
		
		frame.setSize(1200, 700);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false);
        frame.add(panel);

        panel.setLayout(null); 

        changeSecurityDetailsButton.setBounds(490, 550, 220, 40);
        panel.add(changeSecurityDetailsButton);

        backButton.setBounds(50, 550, 100, 40);
        panel.add(backButton);

        securityQuestionsDropdown.setBounds(400, 180, 400, 30);
		securityQuestionsDropdown.setBackground(Color.WHITE);
        panel.add(securityQuestionsDropdown);

        newSecurityPasswordField.setBounds(400, 260, 400, 30);
        panel.add(newSecurityPasswordField);
        addPasswordPlaceholder(newSecurityPasswordField, " It must be 8 to 25 characters long");

        accountPasswordField.setBounds(400, 340, 400, 30);
        panel.add(accountPasswordField);
        addPasswordPlaceholder(accountPasswordField, " Enter account password to verify");

        header.setBounds(150, 50, 900, 60);
        header.setFont(new Font("Garamond", Font.BOLD, 40));
        header.setHorizontalAlignment(SwingConstants.CENTER);
        panel.add(header);

        usernameLabel.setBounds(400, 128, 235, 20);
        panel.add(usernameLabel);

        newSecurityQuestionLabel.setBounds(400, 160, 200, 20);
        panel.add(newSecurityQuestionLabel);

        newSecurityPasswordLabel.setBounds(400, 240, 250, 20);
        panel.add(newSecurityPasswordLabel);

        accountPasswordLabel.setBounds(400, 320, 200, 20);
        panel.add(accountPasswordLabel);

        blankNewSecQuesLabel.setBounds(412, 210, 300, 20);
        blankNewSecQuesLabel.setForeground(Color.RED);
        blankNewSecQuesLabel.setVisible(false);
        panel.add(blankNewSecQuesLabel);

        incorrectAccountPasswordLabel.setBounds(412, 370, 300, 20);
        incorrectAccountPasswordLabel.setForeground(Color.RED);
        incorrectAccountPasswordLabel.setVisible(false);
        panel.add(incorrectAccountPasswordLabel);

        newSecPassErrorLabel.setBounds(412, 290, 300, 20);
        newSecPassErrorLabel.setForeground(Color.RED);
        newSecPassErrorLabel.setVisible(false);
        panel.add(newSecPassErrorLabel);

		
        frame.setVisible(true);
    }

    public void blankNewSecurityQuestion(){
        blankNewSecQuesLabel.setVisible(true);
    }

    public void incorrectAccountPassword() {
        incorrectAccountPasswordLabel.setVisible(true);
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

    public boolean validateNewSecurityPassword(String password){
        int uppercaseCtr = 0, lowercaseCtr = 0, numberCtr = 0;
        int length = password.length();
        boolean valid = true;

        if (length < 8 || length > 25){  // check if password is 8 to 25 characters long
            newSecPassErrorLabel.setText("Password must be 8 to 25 characters long.");
            valid = false;
        } else {  // check if password contains at least one number
            for (int x = 0; x <= 9; x++){
                String number = Integer.toString(x);
                if (password.contains(number))
                    numberCtr++;
            }
            if (numberCtr == 0){
                newSecPassErrorLabel.setText("Does not meet password requirements.");
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
                    newSecPassErrorLabel.setText("Does not meet password requirements.");
                    valid = false;
                } else { // check if password contains at least one uppercase letter
                    for (int x = 65; x <= 90; x++){
                        char ascii = (char)x;
                        String upper = Character.toString(ascii);
                        if (password.contains(upper))
                            uppercaseCtr++;
                    }
                    if (uppercaseCtr == 0){
                        newSecPassErrorLabel.setText("Does not meet password requirements.");
                        valid = false;
                    } else { // check if password contains at least one lowercase letter
                        for (int x = 97; x <= 122; x++){
                            char ascii = (char)x;
                            String lower = Character.toString(ascii);
                            if (password.contains(lower))
                                lowercaseCtr++;
                        }
                        if (lowercaseCtr == 0){
                            newSecPassErrorLabel.setText("Does not meet password requirements.");
                            valid = false;
                        } else {
                            if (password.contains(" ")){ // check if password contains whitespaces
                                newSecPassErrorLabel.setText("Does not meet password requirements.");
                                valid = false;
                            }
                        }
                    }
                }
            }
        }
        return valid;
    }
	
	public void setChangeSecurityDetailsButtonListener(ActionListener listener){
		changeSecurityDetailsButton.addActionListener(listener);
	}
	
	public void setBackButtonListener(ActionListener listener){
		backButton.addActionListener(listener);
	}
	
	public String getNewSecurityQuestion(){
		return (String) securityQuestionsDropdown.getSelectedItem();
	}
	
	public String getNewSecurityPasswordField(){
		return new String (newSecurityPasswordField.getPassword());
	}
	
	public String getAccountPasswordField(){
		return new String (accountPasswordField.getPassword());
	}

    public void setErrorMessages(boolean visible) {
        blankNewSecQuesLabel.setVisible(visible);
        incorrectAccountPasswordLabel.setVisible(visible);
        newSecPassErrorLabel.setVisible(visible);
    }	

    public boolean validateSecurityQuestion() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public boolean validateSecurityPassword() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public String getSecurityQuestion() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public String getSecurityPassword() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public String getPassword() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public void wrongPassword() {
        throw new UnsupportedOperationException("Not supported yet.");
    }
    
}