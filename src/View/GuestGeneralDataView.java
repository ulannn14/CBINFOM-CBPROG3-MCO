package View;

import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import ServiceClassPackage.*;

public class GuestGeneralDataView extends FrameCanvas {
    
    final private JButton backButton = new JButton("Back");

    public GuestGeneralDataView(String[] places, String[] days, String[] times, String[] interpretations, String[][] commentSummaries, int counterCS, String[][] tags, int counterTag) {
        super();

        setTitle("General Data");
        setLocationRelativeTo(null);

        // Main title
        JLabel titleLabel = new JLabel("General Data", SwingConstants.CENTER);
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
        for (int x = 0; x < Constants.MAX_INSTANCE; x++) {
        // Pass both tags and commentSummary to the createSurveyDetailsPanel method
        @SuppressWarnings("OverridableMethodCallInConstructor")
        JPanel instanceInfo = createSurveyDetailsPanel(
            places[x],
            days[x],
            times[x],
            interpretations[x],
            tags[x],
            commentSummaries[x]
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
        bottomPanel.add(backButton, BorderLayout.WEST);

        frameSetVisible();
    }

    
    public JPanel createSurveyDetailsPanel(String place, String day, String time, String interpretation, String[] tags, String[] commentSummary) {
        JPanel panel1 = new JPanel();
        panel1.setLayout(new BoxLayout(panel1, BoxLayout.Y_AXIS));
        panel1.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // Survey details section
        JPanel detailsPanel = new JPanel(new GridLayout(1, 3, 20, 0));

        // Column 1
        JPanel col1 = new JPanel(new GridLayout(1, 1));
        col1.add(new JLabel(""));

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

        panel1.add(detailsPanel);

        // Tags Section (added above the comment summary)
        JPanel tagPanel = new JPanel();
        tagPanel.setLayout(new FlowLayout(FlowLayout.LEFT)); // Left-aligned layout
        JLabel tagLabel = new JLabel("Tags: ");
        tagPanel.add(tagLabel);

        // Display tags as a comma-separated list or individual labels
        if (tags != null && tags.length > 0) {
            StringBuilder tagString = new StringBuilder();
            for (String tag : tags) {
                tagString.append(tag).append(", ");
            }
            // Remove the trailing comma and space
            String tagText = tagString.substring(0, tagString.length() - 2);
            JLabel tagValues = new JLabel(tagText);
            tagPanel.add(tagValues);
        } else {
            JLabel noTagsLabel = new JLabel("None");
            tagPanel.add(noTagsLabel);
        }

        panel1.add(tagPanel);

        // Comment Summary Section
        JPanel commentSummaryPanel = new JPanel();
        commentSummaryPanel.setLayout(new FlowLayout(FlowLayout.LEFT));  // Use FlowLayout to left-align the label
        JLabel commentSummaryLabel = new JLabel("Comment Summary: ");
        commentSummaryPanel.add(commentSummaryLabel);
        panel1.add(commentSummaryPanel);

        // Loop through comment summary and add each comment to the panel
        for (String comment : commentSummary) {
            JTextArea commentArea = new JTextArea(comment);
            commentArea.setEditable(false);
            commentArea.setOpaque(false); // Transparent background
            commentArea.setFont(new Font("Arial", Font.PLAIN, 12));
            commentArea.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5)); // Padding around text
            commentArea.setWrapStyleWord(true); // Wrap at word boundaries
            commentArea.setLineWrap(true); // Enable line wrapping
            panel1.add(commentArea);
        }

        return panel1;
    }
    
    public void setBackButtonListener(ActionListener listener) {
        backButton.addActionListener(listener);
    }
}