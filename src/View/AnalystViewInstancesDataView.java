package View;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class AnalystViewInstancesDataView extends FrameCanvas {
    final private JTextField instanceIdField;
    final private JButton backButton;
    final private JButton modifyButton;
    final private JLabel errorLabel;  // Label to display error messages

    @SuppressWarnings("OverridableMethodCallInConstructor")
    public AnalystViewInstancesDataView(int[] instanceIDs, String[] places, String[] days, String[] times, double[] zScores, int[] sampleSizes, double[] sampleMeans, String[] interpretations, String[][] commentSummaries, String[][] tags, Object[][][] surveyDetails, int counterCS, int counterTag) {
        super();

        setTitle("Instances Data");
        setLocationRelativeTo(null);

        // Main title
        JLabel titleLabel = new JLabel("Instances Data", SwingConstants.CENTER);
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
        for (int x = 0; x < instanceIDs.length; x++) {
            JPanel surveyDetailsPanel = createSurveyDetailsPanel(
                places[x],
                days[x],
                times[x],
                String.valueOf(instanceIDs[x]),
                String.valueOf(zScores[x]),
                String.valueOf(sampleSizes[x]),
                String.valueOf(sampleMeans[x]),
                interpretations[x],
                tags[x],
                commentSummaries[x],
                surveyDetails[x]
            );
            contentPanel.add(surveyDetailsPanel);
            contentPanel.add(Box.createRigidArea(new Dimension(0, 10))); // Add spacing
            contentPanel.add(new JSeparator()); // Add a line separator between sets
        }

        // Bottom panel for buttons and text field
        JPanel bottomPanel = new JPanel(new BorderLayout());
        mainPanel.add(bottomPanel, BorderLayout.SOUTH); // Add the bottom panel to the main panel

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
        errorLabel = new JLabel("Invalid Instance ID. Please try again.");
        errorLabel.setForeground(Color.RED); // Red color for error message
        errorPanel.add(errorLabel);  // Add error label to the error panel

        // Add the input panel and error panel to the rightPanel
        rightPanel.add(inputPanel);
        rightPanel.add(errorPanel);  // This is where the error message will appear on the same line

        bottomPanel.add(rightPanel, BorderLayout.EAST);

        setErrorMessages(false);

        frameSetVisible();
    }

    public JPanel createSurveyDetailsPanel(String place, String day, String time, String instanceID,
                                       String zScore, String sampleSize, String sampleMean, String interpretation,
                                       String[] tags, String[] commentSummary, Object[][] tableData) {
		JPanel panel1 = new JPanel();
		panel1.setLayout(new BoxLayout(panel1, BoxLayout.Y_AXIS));
		panel1.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

		// Survey details section
		JPanel detailsPanel = new JPanel(new GridLayout(1, 3, 20, 0));

		// Column 1
		JPanel col1 = new JPanel(new GridLayout(4, 1));
		col1.add(new JLabel("Instance ID: " + instanceID));
		col1.add(new JLabel("Place: " + place));
		col1.add(new JLabel("Day: " + day));
		col1.add(new JLabel("Time: " + time));

		// Column 2
		JPanel col2 = new JPanel(new GridLayout(4, 1));
		col2.add(new JLabel("Z-Score: " + zScore));
		col2.add(new JLabel("Sample Size: " + sampleSize));
		col2.add(new JLabel("Sample Mean: " + sampleMean));	
        col2.add(new JLabel("Interpretation: " + interpretation));

        // Convert the tags array to a single multiline String
		StringBuilder tagsText = new StringBuilder();
		for (String tag : tags) {
			tagsText.append(tag).append(", "); // Add each comment with a line break
		}	

        // Tag - Multiline
		JTextArea tagArea = new JTextArea("Tags: " + tagsText.toString());
		tagArea.setLineWrap(true); // Enable line wrapping
		tagArea.setWrapStyleWord(true); // Wrap at word boundaries
		tagArea.setEditable(false); // Non-editable
		tagArea.setOpaque(false); // Make it blend with the panel background
		tagArea.setFont(new JLabel().getFont()); // Match the font of other labels
		tagArea.setBorder(BorderFactory.createEmptyBorder()); // Remove border for a clean look

		// Add Comment Summary to col3
		col2.add(tagArea);

		// Column 3
		JPanel col3 = new JPanel(new GridLayout(1, 1)); // 2 rows for tag and comment summary

		// Convert the commentSummary array to a single multiline String
		StringBuilder commentSummaryText = new StringBuilder();
		for (String comment : commentSummary) {
			commentSummaryText.append(comment).append("\n"); // Add each comment with a line break
		}

		// Comment Summary - Multiline
		JTextArea commentSummaryArea = new JTextArea("Comment Summary: " + commentSummaryText.toString());
		commentSummaryArea.setLineWrap(true); // Enable line wrapping
		commentSummaryArea.setWrapStyleWord(true); // Wrap at word boundaries
		commentSummaryArea.setEditable(false); // Non-editable
		commentSummaryArea.setOpaque(false); // Make it blend with the panel background
		commentSummaryArea.setFont(new JLabel().getFont()); // Match the font of other labels
		commentSummaryArea.setBorder(BorderFactory.createEmptyBorder()); // Remove border for a clean look

		// Add Comment Summary to col3
		col3.add(commentSummaryArea);

		// Add columns to details panel
		detailsPanel.add(col1);
		detailsPanel.add(col2);
		detailsPanel.add(col3);

		panel1.add(detailsPanel);

		// Survey table
		JTable surveyDetailsTable = createSurveyDetailsTable(tableData);
		JScrollPane surveyDetailsScrollPane = new JScrollPane(surveyDetailsTable);

		// Disable horizontal scrollbar
		surveyDetailsScrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);

		// Adjust size to account for the borders
		surveyDetailsScrollPane.setPreferredSize(new Dimension(1100, 100));
		panel1.add(Box.createRigidArea(new Dimension(0, 10))); // Add spacing
		panel1.add(surveyDetailsScrollPane);

		return panel1;
	}


    @SuppressWarnings("unused")
	private JTable createSurveyDetailsTable(Object[][] tableData) {
		String[] columnNames = {"Survey Date", "Sum of Survey", "Username", "Comment"};
		JTable table = new JTable(tableData, columnNames) {
            @Override
			public boolean isCellEditable(int row, int column) {
				return false; // Make the table non-editable
			}
		};

		// Enable auto-resizing of columns
		table.setAutoResizeMode(JTable.AUTO_RESIZE_SUBSEQUENT_COLUMNS);

		// Set custom renderer for the "Comment" column to support multiline text
		table.getColumnModel().getColumn(3).setCellRenderer((table1, value, isSelected, hasFocus, row, column) -> {
			JTextArea textArea = new JTextArea();
			textArea.setLineWrap(true); // Enable line wrapping
			textArea.setWrapStyleWord(true); // Wrap at word boundaries
			textArea.setText(value != null ? value.toString() : "");

			// Adjust background and foreground for selection state
			if (isSelected) {
				textArea.setBackground(table1.getSelectionBackground());
				textArea.setForeground(table1.getSelectionForeground());
			} else {
				textArea.setBackground(table1.getBackground());
				textArea.setForeground(table1.getForeground());
			}

			// Adjust row height to fit multiline content
			textArea.setSize(table1.getColumnModel().getColumn(column).getWidth(), Integer.MAX_VALUE);
			int preferredHeight = textArea.getPreferredSize().height;
			if (table1.getRowHeight(row) != preferredHeight) {
				table1.setRowHeight(row, preferredHeight);
			}

			textArea.setOpaque(true); // Make the JTextArea background visible
			return textArea;
		});

		table.setFillsViewportHeight(true);

		// Adjust column widths to prevent unnecessary horizontal scrolling
		table.getColumnModel().getColumn(0).setPreferredWidth(70); // Survey Date
		table.getColumnModel().getColumn(1).setPreferredWidth(30);  // Sum of Survey
		table.getColumnModel().getColumn(2).setPreferredWidth(100);  // Username
		table.getColumnModel().getColumn(3).setPreferredWidth(500); // Comment (wider for multiline)

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

    public boolean validateInstanceID(int[] instanceIDs) {
        boolean valid = false;

        for (int instance : instanceIDs) {
            if (getInstanceID().equals(Integer.toString(instance))) {
                errorLabel.setVisible(false); // Hide the error message if valid
                valid = true;
            }
        }

        if (valid == false){
            errorLabel.setVisible(true); // Show the error message if invalid
        }
        
        return valid;
    }
	
	public void setErrorMessages(boolean visible) {
        errorLabel.setVisible(visible);
    }
}
