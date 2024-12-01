package View;

import java.awt.*;
import java.util.*;
import javax.swing.*;
import java.awt.event.*;
import java.util.stream.*;

public class GuestChooseRecommendedPathView extends FrameCanvas {
    private final JButton backButton = new JButton("Back");
    private final JButton submitButton = new JButton("Submit");
    private final JComboBox<String> fromDropdown;
    private final JComboBox<String> toDropdown;
    private final JComboBox<String> dayDropdown;
    private final JComboBox<String> timeDropdown;

    private final JLabel fromErrorLabel = new JLabel("Invalid choice.");
    private final JLabel toErrorLabel = new JLabel("Invalid choice.");
    private final JLabel dayErrorLabel = new JLabel("Invalid choice.");
    private final JLabel timeErrorLabel = new JLabel("Invalid choice.");

    public GuestChooseRecommendedPathView(ArrayList<String> allNodesNames) {
        super();

        // Prepare data for dropdowns
        String[] locations = Stream.concat(
            Stream.of("Location"),
            allNodesNames.stream()
        ).toArray(String[]::new);
        String[] days = {"Day of the Week", "Sunday", "Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday"};
        String[] times = {
            "Time",
            "Morning Rush Hour [6:00 to 8:59]",
            "Mid-morning [9:00 to 10:59]",
            "Lunchtime [11:00 to 12:59]",
            "Afternoon [13:00 to 16:59]",
            "Night Rush Hour [17:00 to 21:59]",
            "Rest of the Day [22:00 to 5:59]"
        };

        fromDropdown = new JComboBox<>(locations);
        toDropdown = new JComboBox<>(locations);
        dayDropdown = new JComboBox<>(days);
        timeDropdown = new JComboBox<>(times);

        setupUI();

        setErrorMessages(false);

        frameSetVisible();
    }

    private void setupUI() {
        setTitle("Recommended Path");
        setLocationRelativeTo(null);

        // Main Panel
        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        add(mainPanel);

        // Title Label
        JLabel titleLabel = new JLabel("Recommended Path", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Garamond", Font.BOLD, 24));
        mainPanel.add(titleLabel, BorderLayout.NORTH);

        // Content Panel
        JPanel contentPanel = new JPanel(new BorderLayout());
        contentPanel.setBorder(BorderFactory.createEmptyBorder());

        // Dropdowns Panel
        JPanel dropdownsPanel = createDropdownsPanel();
        contentPanel.add(dropdownsPanel, BorderLayout.NORTH);

        // Map Placeholder
        JPanel mapPanel = new JPanel();
        mapPanel.setBackground(Color.LIGHT_GRAY);
        contentPanel.add(mapPanel, BorderLayout.CENTER);

        // Add Content Panel to Main Panel
        mainPanel.add(contentPanel, BorderLayout.CENTER);

        // Bottom Panel
        JPanel bottomPanel = new JPanel(new BorderLayout());
        bottomPanel.add(backButton, BorderLayout.WEST);
        bottomPanel.add(submitButton, BorderLayout.EAST);
        mainPanel.add(bottomPanel, BorderLayout.SOUTH);
    }

    private JPanel createDropdownsPanel() {
        JPanel dropdownsPanel = new JPanel();
        dropdownsPanel.setLayout(new BoxLayout(dropdownsPanel, BoxLayout.X_AXIS));
        dropdownsPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        addDropdownWithError(dropdownsPanel, "From:", fromDropdown, fromErrorLabel);
        addDropdownWithError(dropdownsPanel, "To:", toDropdown, toErrorLabel);
        addDropdownWithError(dropdownsPanel, "Day:", dayDropdown, dayErrorLabel);
        addDropdownWithError(dropdownsPanel, "Time:", timeDropdown, timeErrorLabel);

        return dropdownsPanel;
    }

    private void addDropdownWithError(JPanel panel, String label, JComboBox<String> dropdown, JLabel errorLabel) {
        JLabel jLabel = new JLabel(label);
        dropdown.setPreferredSize(new Dimension(200, 25));
        errorLabel.setForeground(Color.RED);
        errorLabel.setVisible(false);

        panel.add(jLabel);
        panel.add(Box.createRigidArea(new Dimension(5, 0)));
        panel.add(dropdown);
        panel.add(Box.createRigidArea(new Dimension(20, 0)));
    }

    public void setErrorMessages(boolean visible) {
        fromErrorLabel.setVisible(visible);
        toErrorLabel.setVisible(visible);
        dayErrorLabel.setVisible(visible);
        timeErrorLabel.setVisible(visible);
    }

    public boolean validateFields() {
        boolean valid = true;
        setErrorMessages(false);

        if (fromDropdown.getSelectedIndex() == 0) {
            fromErrorLabel.setVisible(true);
            valid = false;
        }

        if (toDropdown.getSelectedIndex() == 0) {
            toErrorLabel.setVisible(true);
            valid = false;
        }

        if (dayDropdown.getSelectedIndex() == 0) {
            dayErrorLabel.setVisible(true);
            valid = false;
        }

        if (timeDropdown.getSelectedIndex() == 0) {
            timeErrorLabel.setVisible(true);
            valid = false;
        }

        return valid;
    }

    public String getStartNode() {
        return (String) fromDropdown.getSelectedItem();
    }

    public String getGoalNode() {
        return (String) toDropdown.getSelectedItem();
    }

    public String getDay() {
        return (String) dayDropdown.getSelectedItem();
    }

    public String getTime() {
        return (String) timeDropdown.getSelectedItem();
    }

    public void setSubmitButtonListener(ActionListener listener) {
        submitButton.addActionListener(listener);
    }

    public void setBackButtonListener(ActionListener listener) {
        backButton.addActionListener(listener);
    }
}
