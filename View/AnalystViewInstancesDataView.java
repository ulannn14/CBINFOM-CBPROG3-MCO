package View;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class AnalystViewInstancesDataView extends JFrame {
    private JTextField instanceIdField;
    private JButton backButton;
    private JButton modifyButton;
    private Instance[] instances;
    private JLabel errorLabel;  // Add a label to display error messages

    public AnalystViewInstancesDataView(Instance[] instances) {
        this.instances = instances; // Save instances for dynamic display
		setTitle("Instances Data");
        setSize(1200, 700);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);

        // Main title
        JLabel titleLabel = new JLabel("Instances Data", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Garamond", Font.BOLD, 30));
        add(titleLabel, BorderLayout.NORTH);

        // Scrollable panel to hold all content
        JPanel contentPanel = new JPanel();
        contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.Y_AXIS));

        JPanel tablePanel = new JPanel(new BorderLayout());
        tablePanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        JScrollPane scrollPane = new JScrollPane(contentPanel);
        tablePanel.add(scrollPane, BorderLayout.CENTER);
        add(contentPanel, BorderLayout.CENTER);

        // Add survey data sets dynamically
        for (Instance instance : instances) {
            JPanel surveyDetailsPanel = createSurveyDetailsPanel(
                instance.getPlace(),
                instance.getDay(),
                instance.getTime(),
                instance.getInstanceID(),
                String.valueOf(instance.getZScore()),
                String.valueOf(instance.getSampleSize()),
                String.valueOf(instance.getSampleMean()),
                instance.getTags(),
                instance.getCommentSummary(),
                instance.getSurveyDetails()
            );
            contentPanel.add(surveyDetailsPanel);
            contentPanel.add(Box.createRigidArea(new Dimension(0, 10))); // Add spacing
            contentPanel.add(new JSeparator()); // Add a line separator between sets
        }

        // Bottom panel for buttons and text field
        JPanel bottomPanel = new JPanel(new BorderLayout());
        // Back button on the lower left
        backButton = new JButton("Back");
        bottomPanel.add(backButton, BorderLayout.WEST);

        // Instance ID text field and Modify button on the lower right
        JPanel rightPanel = new JPanel();
        rightPanel.setLayout(new BoxLayout(rightPanel, BoxLayout.Y_AXIS)); // Vertical layout for stacking

        // Create a panel for the Instance ID input and Modify button
        JPanel inputPanel = new JPanel(new FlowLayout(FlowLayout.LEFT)); // Align to the left for the same line
        instanceIdField = new JTextField(15);
        modifyButton = new JButton("Modify");

        inputPanel.add(new JLabel("Instance ID:"));
        inputPanel.add(instanceIdField);
        inputPanel.add(modifyButton);

        // Create a new panel to hold the error label next to "Instance ID" label
        JPanel errorPanel = new JPanel(new FlowLayout(FlowLayout.LEFT)); // Horizontal alignment for same line
        errorLabel = new JLabel();
        errorLabel.setForeground(Color.RED); // Red color for error message
        errorLabel.setVisible(false);  // Start with the error label invisible
        errorPanel.add(errorLabel);  // Add error label to the error panel

        // Add the input panel and error panel to the rightPanel
        rightPanel.add(inputPanel);
        rightPanel.add(errorPanel);  // This is where the error message will appear on the same line

        bottomPanel.add(rightPanel, BorderLayout.EAST);
        add(bottomPanel, BorderLayout.SOUTH);

        setVisible(true);
    } // This was missing! The constructor is now properly closed.

    public JPanel createSurveyDetailsPanel(String place, String day, String time, String instanceID,
                                            String zScore, String sampleSize, String sampleMean,
                                            String tags, String commentSummary, Object[][] tableData) {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // Survey details section
        JPanel detailsPanel = new JPanel(new GridLayout(1, 3, 20, 0));

        // Column 1
        JPanel col1 = new JPanel(new GridLayout(4, 1));
        col1.add(new JLabel("Place: " + place));
        col1.add(new JLabel("Day: " + day));
        col1.add(new JLabel("Time: " + time));
        col1.add(new JLabel("Instance ID: " + instanceID));

        // Column 2
        JPanel col2 = new JPanel(new GridLayout(3, 1));
        col2.add(new JLabel("Z-Score: " + zScore));
        col2.add(new JLabel("Sample Size: " + sampleSize));
        col2.add(new JLabel("Sample Mean: " + sampleMean));

        // Column 3
        JPanel col3 = new JPanel(new GridLayout(2, 1));
        col3.add(new JLabel("Tag: " + tags));
        col3.add(new JLabel("Comment Summary: " + commentSummary));

        // Add columns to details panel
        detailsPanel.add(col1);
        detailsPanel.add(col2);
        detailsPanel.add(col3);

        panel.add(detailsPanel);

        // Survey table
        JTable surveyDetailsTable = createSurveyDetailsTable(tableData);
        JScrollPane surveyDetailsScrollPane = new JScrollPane(surveyDetailsTable);
        surveyDetailsScrollPane.setPreferredSize(new Dimension(1150, 100)); // Adjust size
        panel.add(Box.createRigidArea(new Dimension(0, 10))); // Add spacing
        panel.add(surveyDetailsScrollPane);

        return panel;
    }

    private JTable createSurveyDetailsTable(Object[][] tableData) {
        String[] columnNames = {"Survey Date", "Sum of Survey", "Username", "Comment"};
        JTable table = new JTable(tableData, columnNames);
        table.setFillsViewportHeight(true);
        table.setPreferredScrollableViewportSize(new Dimension(1150, 70));
        return table;
    }

    public void setBackButtonListener(ActionListener listener) {
        backButton.addActionListener(listener);
    }

    public void setModifyButtonListener(ActionListener listener) {
        modifyButton.addActionListener(listener);
    }

    public String getInstanceID() {
        return instanceIdField.getText().trim();
    }

    public boolean validateInstanceID() {
        String input = getInstanceID();
        for (Instance instance : instances) {
            if (instance.getInstanceID().equals(input)) {
                errorLabel.setVisible(false); // Hide the error message if valid
                return true;
            }
        }
        errorLabel.setText("Invalid Instance ID. Please try again."); // Set error message
        errorLabel.setVisible(true); // Show the error message if invalid
        return false;
    }
}
