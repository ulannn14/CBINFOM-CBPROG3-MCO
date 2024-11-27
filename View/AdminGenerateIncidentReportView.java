package View;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;

public class AdminGenerateIncidentReportView extends JFrame {
    final private JLabel instanceLabel = new JLabel("Choose the instance you would like to make a report about.");
    final private JLabel toLabel = new JLabel("To: ");
    final private JLabel fromLabel = new JLabel("From: ");
    final private JLabel reportLabel = new JLabel("Write the report you would like to address to a government agency or official.");

    final private JLabel toBlankLabel = new JLabel("To field cannot be blank.");
    final private JLabel fromBlankLabel = new JLabel("From field cannot be blank.");
    final private JLabel reportBlankLabel = new JLabel("Report cannot be blank.");
	final private JLabel locationErrorLabel = new JLabel("Please select a valid location.");
    final private JLabel dayErrorLabel = new JLabel("Please select a valid day.");
    final private JLabel timeErrorLabel = new JLabel("Please select a valid time.");

    private JTextField toField = new JTextField(25);
    private JTextField fromField = new JTextField(25);
    private JTextArea reportField = new JTextArea(10, 50);

    private String[] locations = {
        "Location", "LOCATION 1", "LOCATION 2", "LOCATION 3", "LOCATION 4", "LOCATION 5"
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

        toLabel.setBounds(70, 121, 30, 18);
        panel.add(toLabel);

        fromLabel.setBounds(452, 121, 50, 18);
        panel.add(fromLabel);

        reportLabel.setBounds(70, 172, 500, 18);
        panel.add(reportLabel);

        toBlankLabel.setBounds(104, 145, 200, 13);
		toBlankLabel.setForeground(Color.RED);
        toBlankLabel.setVisible(false);
        panel.add(toBlankLabel);

        fromBlankLabel.setBounds(505, 145, 200, 13);
		fromBlankLabel.setForeground(Color.RED);
        fromBlankLabel.setVisible(false);
        panel.add(fromBlankLabel);

        reportBlankLabel.setBounds(70, 538, 200, 13);
		reportBlankLabel.setForeground(Color.RED);
        reportBlankLabel.setVisible(false);
        panel.add(reportBlankLabel);

        toField.setBounds(104, 118, 300, 23);
        panel.add(toField);

        fromField.setBounds(505, 118, 300, 23);
        panel.add(fromField);

        JScrollPane reportScrollPane = new JScrollPane(reportField);
		reportScrollPane.setBounds(70, 192, 1000, 343);

		reportField.setLineWrap(true); 
		reportField.setWrapStyleWord(true); 

		reportScrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		reportScrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);

		panel.add(reportScrollPane);

        locationDropdown.setBounds(70, 74, 200, 24);
        locationDropdown.setBackground(Color.WHITE);
        panel.add(locationDropdown);

        dayOfTheWeekDropdown.setBounds(315, 74, 200, 24);
        dayOfTheWeekDropdown.setBackground(Color.WHITE);
        panel.add(dayOfTheWeekDropdown);

        timeOfDayDropdown.setBounds(549, 74, 200, 24);
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

        dayErrorLabel.setBounds(315, 100, 200, 13);
        dayErrorLabel.setForeground(Color.RED);
        dayErrorLabel.setVisible(false);
        panel.add(dayErrorLabel);

        timeErrorLabel.setBounds(549, 100, 200, 13);
        timeErrorLabel.setForeground(Color.RED);
        timeErrorLabel.setVisible(false);
        panel.add(timeErrorLabel);

        addPlaceholder(toField, "                                                                    @gmail.com");
        addPlaceholder(fromField, "                                                                    @gmail.com");

        add(panel);
        setVisible(true);
    }

    private void addPlaceholder(JTextField field, String placeholder) {
        field.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                if (field.getText().equals(placeholder)) {
                    field.setText("");
                    field.setForeground(Color.BLACK);
                }
            }

            @Override
            public void focusLost(FocusEvent e) {
                if (field.getText().isEmpty()) {
                    field.setForeground(Color.GRAY);
                    field.setText(placeholder);
                }
            }
        });
        field.setForeground(Color.GRAY);
        field.setText(placeholder);
    }

    // Validate form fields and show appropriate error messages
    public boolean validateFields() {
		// Reset all error messages
		setErrorMessages(false);

		boolean valid = true;

		// Validate To field
		if (toField.getText().trim().isEmpty() || toField.getText().equals("                                                                  @gmail.com")) {
			toBlankLabel.setVisible(true);
			valid = false;
		}

		// Validate From field
		if (fromField.getText().trim().isEmpty() || fromField.getText().equals("                                                                  @gmail.com")) {
			fromBlankLabel.setVisible(true);
			valid = false;
		}

		// Validate Report field
		if (reportField.getText().trim().isEmpty()) {
			reportBlankLabel.setVisible(true);
			valid = false;
		}

		// Validate dropdowns
		if (locationDropdown.getSelectedItem().equals("Location")) {
			locationErrorLabel.setVisible(true);
			valid = false;
		}

		if (dayOfTheWeekDropdown.getSelectedItem().equals("Day of the Week")) {
			dayErrorLabel.setVisible(true);
			valid = false;
		}

		if (timeOfDayDropdown.getSelectedItem().equals("Time")) {
			timeErrorLabel.setVisible(true);
			valid = false;
		}

		return valid;
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

    public String getBodyField() {
        return reportField.getText();
    }

    public String getSelectedLocation() {
        return (String) locationDropdown.getSelectedItem();
    }

    public String getSelectedDay() {
        return (String) dayOfTheWeekDropdown.getSelectedItem();
    }

    public String getSelectedTime() {
        return (String) timeOfDayDropdown.getSelectedItem();
    }

    public int getIndexID() {
        //  todo
        return -1;
    }

}
