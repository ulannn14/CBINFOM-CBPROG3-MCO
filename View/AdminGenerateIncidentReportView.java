package View;

import Model.*;
import java.awt.*;
import java.awt.event.ActionListener;
import javax.swing.*;

public class AdminGenerateIncidentReportView extends ViewAbstract {
    private JLabel instanceLabel = new JLabel("Choose the instance you would like to make a report about.");
    private JLabel toLabel = new JLabel("To: ");
    private JLabel fromLabel = new JLabel("From: ");
    private JLabel reportLabel = new JLabel("Write the report you would like to address to a government agency or official.");

    private JLabel toBlankLabel = new JLabel("");
    private JLabel fromBlankLabel = new JLabel("");
    private JLabel reportBlankLabel = new JLabel("");
    private JLabel locationErrorLabel = new JLabel("Please select a valid location.");
    private JLabel dayErrorLabel = new JLabel("Please select a valid day.");
    private JLabel timeErrorLabel = new JLabel("Please select a valid time.");

    private JTextField toField = new JTextField(30);
    private JTextField fromField = new JTextField(30);
    private JTextArea reportField = new JTextArea(10, 50);
    
    private Place place = new Place();
    private String[] locations = {
        "Location", // Placeholder option
        place.fetchPlaceNames().toArray(new String[0])
    };  
    private String[] days = {
        "Day of the Week", "Sunday", "Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday"
    };
    private String[] times = {
        "Time",
        "Morning Rush Hour [6:00 to 8:59]",
        "Mid-morning [9:00 to 10:59]",
        "Lunchtime [11:00 to 12:59]",
        "Afternoon [13:00 to 16:59]",
        "Night Rush Hour [17:00 to 21:59]",
        "Rest of the Day [22:00 to 5:59]"
    };

    private JComboBox<String> locationDropdown = new JComboBox<>(locations);
    private JComboBox<String> dayOfTheWeekDropdown = new JComboBox<>(days);
    private JComboBox<String> timeOfDayDropdown = new JComboBox<>(times);

    private JButton submitButton = new JButton("Submit");
    private JButton backButton = new JButton("Back");

    private Image backgroundImage;

    public AdminGenerateIncidentReportView() {
        setTitle("Generate Incident Report");
        setSize(1200, 700);
        setResizable(false);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JLabel titleLabel = new JLabel("Generate Incident Report", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Garamond", Font.BOLD, 30));
        add(titleLabel, BorderLayout.NORTH);

        backgroundImage = new ImageIcon("fgenerateIncidentReport.png").getImage();

        JPanel panel = new JPanel(null) {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
            }
        };

        instanceLabel.setBounds(70, 50, 500, 18);
        panel.add(instanceLabel);

        toLabel.setBounds(70, 125, 30, 18);
        panel.add(toLabel);

        fromLabel.setBounds(452, 125, 50, 18);
        panel.add(fromLabel);

        reportLabel.setBounds(70, 172, 500, 18);
        panel.add(reportLabel);

        toBlankLabel.setBounds(104, 150, 250, 13);
        toBlankLabel.setForeground(Color.RED);
        toBlankLabel.setVisible(false);
        panel.add(toBlankLabel);

        fromBlankLabel.setBounds(505, 150, 250, 13);
        fromBlankLabel.setForeground(Color.RED);
        fromBlankLabel.setVisible(false);
        panel.add(fromBlankLabel);

        reportBlankLabel.setBounds(70, 538, 250, 13);
        reportBlankLabel.setForeground(Color.RED);
        reportBlankLabel.setVisible(false);
        panel.add(reportBlankLabel);

        toField.setBounds(104, 125, 300, 23);
        panel.add(toField);

        fromField.setBounds(505, 125, 300, 23);
        panel.add(fromField);

        JScrollPane reportScrollPane = new JScrollPane(reportField);
        reportScrollPane.setBounds(70, 192, 1000, 343);

        reportField.setLineWrap(true);
        reportField.setWrapStyleWord(true);

        reportScrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        reportScrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);

        panel.add(reportScrollPane);

        locationDropdown.setBounds(70, 74, 450, 24);
        locationDropdown.setBackground(Color.WHITE);
        panel.add(locationDropdown);

        dayOfTheWeekDropdown.setBounds(552, 74, 200, 24);
        dayOfTheWeekDropdown.setBackground(Color.WHITE);
        panel.add(dayOfTheWeekDropdown);

        timeOfDayDropdown.setBounds(780, 74, 290, 24);
        timeOfDayDropdown.setBackground(Color.WHITE);
        panel.add(timeOfDayDropdown);

        backButton.setBounds(50, 560, 100, 40);
        panel.add(backButton);

        submitButton.setBounds(917, 560, 212, 41);
        panel.add(submitButton);

        locationErrorLabel.setBounds(70, 100, 200, 13);
        locationErrorLabel.setForeground(Color.RED);
        locationErrorLabel.setVisible(false);
        panel.add(locationErrorLabel);

        dayErrorLabel.setBounds(552, 100, 200, 13);
        dayErrorLabel.setForeground(Color.RED);
        dayErrorLabel.setVisible(false);
        panel.add(dayErrorLabel);

        timeErrorLabel.setBounds(780, 100, 200, 13);
        timeErrorLabel.setForeground(Color.RED);
        timeErrorLabel.setVisible(false);
        panel.add(timeErrorLabel);

        add(panel);
        setVisible(true);
    }

    public boolean validateFields() {
        setErrorMessages(false);
        boolean valid = true;

        if (!validateNameField(toField, toBlankLabel, "To field cannot be blank.")) {
            valid = false;
        }

        if (!validateNameField(fromField, fromBlankLabel, "From field cannot be blank.")) {
            valid = false;
        }

        if (reportField.getText().trim().isEmpty()) {
            showError(reportBlankLabel, "Report cannot be blank.");
            blankReportField();
            valid = false;
        } else if (reportField.getText().length() > 280) {
            showError(reportBlankLabel, "Report must be at most 280 characters.");
            blankReportField();
            valid = false;
        }

        if (locationDropdown.getSelectedItem().equals("Location")) {
            blankLocationDropdown();
            valid = false;
        }

        if (dayOfTheWeekDropdown.getSelectedItem().equals("Day of the Week")) {
            blankDayDropdown();
            valid = false;
        }

        if (timeOfDayDropdown.getSelectedItem().equals("Time")) {
            blankTimeDropdown();
            valid = false;
        }

        return valid;
    }

    public void showError(JLabel label, String errorMessage) {
        label.setText(errorMessage);
        label.setVisible(true);
    }

    public boolean validateNameField(JTextField field, JLabel errorLabel, String errorMessage) {
        if (field.getText().trim().isEmpty()) {
            showError(errorLabel, errorMessage);
            return false;
        } 
		else if (field.getText().length() > 30) {
            showError(errorLabel, "Name must be at most 30 characters.");
            return false;
        } 
		else if (field.getText().matches(".*[^a-zA-Z ].*")) {
            showError(errorLabel, "Name cannot have numbers or special characters.");
            return false;
        }
        return true;
    }

    public void blankToField() {
        toBlankLabel.setVisible(true);
    }

    public void blankFromField() {
        fromBlankLabel.setVisible(true);
    }

    public void blankReportField() {
        reportBlankLabel.setVisible(true);
    }

    public void blankLocationDropdown() {
        locationErrorLabel.setVisible(true);
    }

    public void blankDayDropdown() {
        dayErrorLabel.setVisible(true);
    }

    public void blankTimeDropdown() {
        timeErrorLabel.setVisible(true);
    }

    public void setErrorMessages(boolean visible) {
        toBlankLabel.setVisible(visible);
        fromBlankLabel.setVisible(visible);
        reportBlankLabel.setVisible(visible);
        locationErrorLabel.setVisible(visible);
        dayErrorLabel.setVisible(visible);
        timeErrorLabel.setVisible(visible);
    }

    public void setSubmitButtonListener(ActionListener listener) {
        submitButton.addActionListener(listener);
    }

    public void setBackButtonListener(ActionListener listener) {
        backButton.addActionListener(listener);
    }

    public String getToField() {
        return toField.getText();
    }

    public String getFromField() {
        return fromField.getText();
    }

    public String getReportField() {
        return reportField.getText();
    }

	public String getPlaceName() {
		return (String) locationDropdown.getSelectedItem();
	}


    public String getDayName() {
        return (String) dayOfTheWeekDropdown.getSelectedItem();
    }

    public String getTimeName() {
        return (String) timeOfDayDropdown.getSelectedItem();
    }

}
