package View;

import java.awt.*;
import javax.swing.*;
import java.awt.event.*;

public class AdminViewCommentSummaryReports extends ViewAbstract {
    private JButton backButton;
    private Instance[] instances;

    ///// papalitan pa paramssss
    public AdminViewCommentSummaryReports(Instance[] instances) {
        this.instances = instances;

        setTitle("Comment Summary Reports");
        setLocationRelativeTo(null);
        setResizable(false);

        // Main title
        JLabel titleLabel = new JLabel("Comment Summary Reports", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Garamond", Font.BOLD, 30));
        add(titleLabel, BorderLayout.NORTH);

        // Main panel with a 20-pixel margin
        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        add(mainPanel, BorderLayout.CENTER);

        // Scrollable panel to hold all content
        JPanel contentPanel = new JPanel();
        contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.Y_AXIS));

        JScrollPane scrollPane = new JScrollPane(contentPanel);
        mainPanel.add(scrollPane, BorderLayout.CENTER);

        // Add survey data sets dynamically
        for (Instance instance : instances) {
            JPanel instanceInfo = createSurveyDetailsPanel(
                instance.getInstanceID(),
                instance.getPlace(),
                instance.getDay(),
                instance.getTime(),
                instance.getInterpretation(),
                instance.getCommentSummary()
            );
            contentPanel.add(instanceInfo);
            contentPanel.add(Box.createRigidArea(new Dimension(0, 10))); // Add spacing
            contentPanel.add(new JSeparator()); // Add a line separator between sets
        }

        // Ensure the JScrollPane starts at the top
        SwingUtilities.invokeLater(() -> {
            JScrollBar vertical = scrollPane.getVerticalScrollBar();
            vertical.setValue(0);  // Set scroll position to top
        });

        // Bottom panel for button
        JPanel bottomPanel = new JPanel(new BorderLayout());
        mainPanel.add(bottomPanel, BorderLayout.SOUTH); // Add the bottom panel to the main panel

        // Back button on the lower left
        backButton = new JButton("Back");
        bottomPanel.add(backButton, BorderLayout.WEST);

        // Action listener for the back button
        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Add logic to navigate back (e.g., close this window and show a previous view)
                System.out.println("Back button clicked");
            }
        });

        frameSetVisible();
    }

    
    public JPanel createSurveyDetailsPanel(String instanceID, String place, String day, 
        String time, String interpretation, String[] commentSummary) {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // Survey details section
        JPanel detailsPanel = new JPanel(new GridLayout(1, 3, 20, 0));

        // Column 1
        JPanel col1 = new JPanel(new GridLayout(1, 1));
        col1.add(new JLabel("Instance ID: " + instanceID));

        // Column 2
        JPanel col2 = new JPanel(new GridLayout(1, 1));
        col2.add(new JLabel("Place: " + place));

        // Column 3
        JPanel col3 = new JPanel(new GridLayout(1, 1));
        col3.add(new JLabel("Day: " + day));

        // Column 4
        JPanel col4 = new JPanel(new GridLayout(1, 1));
        col4.add(new JLabel("Time: " + time));

        // Column 5
        JPanel col5 = new JPanel(new GridLayout(1, 1));
        col5.add(new JLabel("Interpretation: " + interpretation));

        // Add columns to details panel
        detailsPanel.add(col1);
        detailsPanel.add(col2);
        detailsPanel.add(col3);
        detailsPanel.add(col4);
        detailsPanel.add(col5);

        panel.add(detailsPanel);

        // Comment Summary - Display each comment below the header
        JPanel commentSummaryPanel = new JPanel();
        commentSummaryPanel.setLayout(new FlowLayout(FlowLayout.LEFT));  // Use FlowLayout to left-align the label
        JLabel commentSummaryLabel = new JLabel("Comment Summary: ");
        commentSummaryPanel.add(commentSummaryLabel);
        panel.add(commentSummaryPanel);

        // Loop through comment summary and add each comment to the panel
        for (String comment : commentSummary) {
            JTextArea commentArea = new JTextArea(comment);
            commentArea.setEditable(false);
            commentArea.setOpaque(false); // Transparent background
            commentArea.setFont(new Font("Arial", Font.PLAIN, 12));
            commentArea.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5)); // Padding around text
            commentArea.setWrapStyleWord(true); // Wrap at word boundaries
            commentArea.setLineWrap(true); // Enable line wrapping
            panel.add(commentArea);
        }

        return panel;
    }

///// kulang pa methods hehe
}
