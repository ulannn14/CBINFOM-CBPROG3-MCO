package View;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class RespondentProfileView extends FrameCanvas{
    final private JButton updateButton = new JButton("Update Detials");
    final private JButton backButton = new JButton("Back");

    final private String[] months = {"Select month...","01","02","03","04","05","06","07","08","09","10","11","12"};
    final private JComboBox<String> monthsDropdown = new JComboBox<>(months);
    final private String[] days = {"Select day...","01","02","03","04","05","06","07","08","09","10","11","12","13","14","15","16","17","18","19","20","21","22","23","24","25","26","27","28","29","30","31"};
    final private JComboBox<String> daysDropdown = new JComboBox<>(days);
    final private String[] years = {"Select year...","2024","2023","2022","2021","2020","2019","2018","2017","2016","2015","2014","2013","2012","2011","2010","2009","2008","2007","2006","2005","2004","2003","2002","2001","2000","1999","1998","1997","1996","1995","1994","1993","1992","1991","1990","1989","1988","1987","1986","1985","1984","1983","1982","1981","1980","1979","1978","1977","1976","1975","1974","1973","1972","1971","1970","1969","1968","1967","1966","1965","1964","1963","1962","1961","1960","1959","1958","1957","1956","1955","1954","1953","1952","1951","1950","1949","1948","1947","1946","1945","1944","1943","1942","1941","1940","1939","1938","1937","1936","1935","1934","1933","1932","1931","1930","1929","1928","1927","1926","1925"};
    final private JComboBox<String> yearsDropdown = new JComboBox<>(years);

    final private JTextField nameField = new JTextField(15);
    final private JTextField emailField = new JTextField(15);

    final private JLabel headerLabel = new JLabel("Profile Details");
    final private JLabel profileLabel = new JLabel("Below are your account profile details.");
    final private JLabel nameHeaderLabel = new JLabel("Name");
    final private JLabel emailHeaderLabel = new JLabel("Email");
    final private JLabel usernameHeaderLabel = new JLabel("Username");
    final private JLabel birthdayHeaderLabel = new JLabel("Birthday");
    final private JLabel ageHeaderLabel = new JLabel("Age");
    final private JLabel dateJoinedHeaderLabel = new JLabel("Date Joined");
    final private JLabel editLabel = new JLabel("Edit your account profile below.");
    final private JLabel nameFieldLabel = new JLabel("Name");
    final private JLabel emailFieldLabel = new JLabel("Email");
    final private JLabel birthdayDropdownLabel = new JLabel("Birthday");

    final private JLabel usernameErrorLabel = new JLabel("");
        // Username is already taken.
        // Username must be 8 to 25 characters long.
    final private JLabel birthdayErrorLabel = new JLabel("");
        // Must be at least 16 years old.
        // Please choose a valid choice.
    final private JLabel updateErrorLabel = new JLabel("At least one field must be edited.");
    
    @SuppressWarnings("OverridableMethodCallInConstructor")
    public RespondentProfileView(String name, String email, String username, int[] birthday, int age, int[] dateJoined){
        super();

        setTitle("Respondent Profile");

        JLabel nameLabel = new JLabel(name);
        JLabel emailLabel = new JLabel(email);
        JLabel usernameLabel = new JLabel(username);
        JLabel birthdayLabel = new JLabel(String.format("%02d", birthday[0]).concat("/").concat(String.format("%02d", birthday[1])).concat("/").concat(String.format("%02d", birthday[2])));
        JLabel ageLabel = new JLabel(Integer.toString(age));
        JLabel dateJoinedLabel = new JLabel(String.format("%02d", dateJoined[0]).concat("/").concat(String.format("%02d", dateJoined[1])).concat("/").concat(String.format("%02d", dateJoined[2])));

        updateButton.setBounds(600,350,157,41);
        panel.add(updateButton);
        backButton.setBounds(70,590,100,41);
        panel.add(backButton);
        
        monthsDropdown.setBounds(600,300,130,29);
        panel.add(monthsDropdown);
        daysDropdown.setBounds(740,300,130,29);
        panel.add(daysDropdown);
        yearsDropdown.setBounds(880,300,130,29);
        panel.add(yearsDropdown);
        
        nameField.setBounds(600,160,410,29);
        addPlaceholder(nameField, " ex. Juan Dela Cruz");
        panel.add(nameField);
        emailField.setBounds(600,230,410,29);
        addPlaceholder(emailField, "                                                                                                              @gmail.com");
        panel.add(emailField);
        
        headerLabel.setBounds(70,70,500,37);
        headerLabel.setFont(new Font("Garamond", Font.BOLD, 30));
        panel.add(headerLabel);
        profileLabel.setBounds(70,115,324,18);
        panel.add(profileLabel);
        nameHeaderLabel.setBounds(70,140,85,20);
        panel.add(nameHeaderLabel);
        nameLabel.setBounds(85,160,308,18);
        nameLabel.setFont(new Font("Dialog", Font.PLAIN, 12));
        panel.add(nameLabel);
        emailHeaderLabel.setBounds(70,185,85,20);
        panel.add(emailHeaderLabel);
        emailLabel.setBounds(85,205,308,18);
        emailLabel.setFont(new Font("Dialog", Font.PLAIN, 12));
        panel.add(emailLabel);
        usernameHeaderLabel.setBounds(70,230,85,20);
        panel.add(usernameHeaderLabel);
        usernameLabel.setBounds(85,250,308,18);
        usernameLabel.setFont(new Font("Dialog", Font.PLAIN, 12));
        panel.add(usernameLabel);
        birthdayHeaderLabel.setBounds(70,275,85,20);
        panel.add(birthdayHeaderLabel);
        birthdayLabel.setBounds(85,295,308,18);
        birthdayLabel.setFont(new Font("Dialog", Font.PLAIN, 12));
        panel.add(birthdayLabel);
        ageHeaderLabel.setBounds(70,320,85,20);
        panel.add(ageHeaderLabel);
        ageLabel.setBounds(85,340,308,18);
        ageLabel.setFont(new Font("Dialog", Font.PLAIN, 12));
        panel.add(ageLabel);
        dateJoinedHeaderLabel.setBounds(70,365,85,20);
        panel.add(dateJoinedHeaderLabel);
        dateJoinedLabel.setBounds(85,385,308,18);
        dateJoinedLabel.setFont(new Font("Dialog", Font.PLAIN, 12));
        panel.add(dateJoinedLabel);

        editLabel.setBounds(600,115,324,18);
        panel.add(editLabel);
        nameFieldLabel.setBounds(600,140,85,18);
        panel.add(nameFieldLabel);
        emailFieldLabel.setBounds(600,210,85,20);
        panel.add(emailFieldLabel);
        birthdayDropdownLabel.setBounds(600,280,85,20);
        panel.add(birthdayDropdownLabel);

        usernameErrorLabel.setBounds(613,260,300,13);
        usernameErrorLabel.setForeground(Color.RED);
        panel.add(usernameErrorLabel);
        birthdayErrorLabel.setBounds(613,330,300,13);
        birthdayErrorLabel.setForeground(Color.RED);
        panel.add(birthdayErrorLabel);
        updateErrorLabel.setBounds(600,392,300,13);
        updateErrorLabel.setForeground(Color.RED);
        panel.add(updateErrorLabel);

        setErrorMessages(false);

        frameSetVisible();
    }

    public void setErrorMessages(boolean visible){
        birthdayErrorLabel.setText("");
        updateErrorLabel.setVisible(visible);
    }

    public String getNameField(){
        return nameField.getText();
    }

    public String getEmail(){
        return emailField.getText();
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

    public void emptyField(){
        updateErrorLabel.setVisible(true);
    }

    public boolean validateFields(){
        boolean valid = true;
        setErrorMessages(false);

        if (monthsDropdown.getSelectedIndex() == 0 || daysDropdown.getSelectedIndex() == 0 || yearsDropdown.getSelectedIndex() == 0){
            birthdayErrorLabel.setText("Please choose a valid choice.");
            valid = false;
        }

        if (validateBirthday(getMonth(), getDay(), getYear()) == false){
            birthdayErrorLabel.setText("Must be at least 16 years old.");
            valid = false;
        }

        return valid;
    }

    public void setUpdateDetailsButtonListener(ActionListener listener) {
        updateButton.addActionListener(listener);
    }

    public void setBackButtonListener(ActionListener listener) {
        backButton.addActionListener(listener);
    }
}