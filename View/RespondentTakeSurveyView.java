package View;

import java.awt.*;
import java.util.*;
import javax.swing.*;
import java.awt.event.*;
import java.util.stream.*;
import ServiceClassPackage.*;

public class RespondentTakeSurveyView extends FrameCanvas {

	String[] locations = Stream.concat(
            Stream.of("Location"),
            Constants.fetchPlaceNames().stream()
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

    final private JComboBox<String> locationDropdown, dayDropdown, timeDropdown;
    final private JLabel locationError, dayError, timeError, questionsError, commentsError;
    final private ArrayList<JPanel> questionPanels;
    final private JTextArea commentField;
    final private JButton backButton, submitButton;
	final private JRadioButton[][] radioButton = new JRadioButton[20][5];
	final private ButtonGroup[] group = new ButtonGroup[20];

    public RespondentTakeSurveyView() {
		super();

        setTitle("Take Survey");
        setLocationRelativeTo(null);

        locationDropdown = new JComboBox<>(locations);
        dayDropdown = new JComboBox<>(days);
        timeDropdown = new JComboBox<>(times);

        // Main Panel
        panel.setLayout(new BorderLayout());
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        // Title
        JLabel titleLabel = new JLabel("Take Survey", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Garamond", Font.BOLD, 30));
        panel.add(titleLabel, BorderLayout.NORTH);

        // Content Panel (with ScrollPane)
        JPanel contentPanel = new JPanel();
        contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.Y_AXIS));
        JScrollPane scrollPane = new JScrollPane(contentPanel);
        scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        panel.add(scrollPane, BorderLayout.CENTER);

        // Dropdown Panel with GridBagLayout
		JPanel dropdownPanel = new JPanel(new GridBagLayout());
		dropdownPanel.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0));
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.insets = new Insets(10, 10, 10, 10);
		gbc.fill = GridBagConstraints.BOTH;

		// Location Panel
		JPanel locationPanel = new JPanel();
		locationPanel.setLayout(new BorderLayout());
		locationPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
		JLabel locationLabel = new JLabel("Location:");
		locationLabel.setHorizontalAlignment(SwingConstants.LEFT);
		locationPanel.add(locationLabel, BorderLayout.NORTH);
		locationPanel.add(locationDropdown, BorderLayout.CENTER);
		locationError = createErrorLabel("Please select a location.");
		locationPanel.add(locationError, BorderLayout.SOUTH);
		gbc.gridx = 0; // Column position
		gbc.weightx = 0.45; // 45% of total width
		gbc.gridy = 0;
		dropdownPanel.add(locationPanel, gbc);

		// Day Panel
		JPanel dayPanel = new JPanel();
		dayPanel.setLayout(new BorderLayout());
		dayPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
		JLabel dayLabel = new JLabel("Day of the Week:");
		dayLabel.setHorizontalAlignment(SwingConstants.LEFT);
		dayPanel.add(dayLabel, BorderLayout.NORTH);
		dayPanel.add(dayDropdown, BorderLayout.CENTER);
		dayError = createErrorLabel("Please select a day.");
		dayPanel.add(dayError, BorderLayout.SOUTH);
		gbc.gridx = 1; // Column position
		gbc.weightx = 0.3; // 30% of total width
		gbc.gridy = 0;
		dropdownPanel.add(dayPanel, gbc);

		// Time Panel
		JPanel timePanel = new JPanel();
		timePanel.setLayout(new BorderLayout());
		timePanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
		JLabel timeLabel = new JLabel("Time:");
		timeLabel.setHorizontalAlignment(SwingConstants.LEFT);
		timePanel.add(timeLabel, BorderLayout.NORTH);
		timePanel.add(timeDropdown, BorderLayout.CENTER);
		timeError = createErrorLabel("Please select a time.");
		timePanel.add(timeError, BorderLayout.SOUTH);
		gbc.gridx = 2; // Column position
		gbc.weightx = 0.25; // 25% of total width
		gbc.gridy = 0;
		dropdownPanel.add(timePanel, gbc);

		// Add Dropdown Panel to Content Panel
		contentPanel.add(dropdownPanel);

        // Survey Instructions Panel
		JPanel instructionsPanel = new JPanel(new BorderLayout());
		instructionsPanel.setBorder(BorderFactory.createEmptyBorder(0, 20, 0, 20));

		JLabel instructionsLabel = new JLabel(
			"<html>" +
			"Please answer the questions to rate the safety of the instance chosen.<br>" +
			"1 - Strongly Disagree&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;2 - Disagree&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;3 - Neutral&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;" +
			"4 - Agree&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;5 - Strongly Agree" +
			"</html>"
		);
		instructionsLabel.setHorizontalAlignment(SwingConstants.LEFT);
		instructionsPanel.add(instructionsLabel, BorderLayout.NORTH);

		// Add the Instructions Panel to the Content Panel
		contentPanel.add(instructionsPanel);


        // Survey Questions
        questionPanels = new ArrayList<>();
        addSurveySection(contentPanel, "Route Efficiency", new String[]{
            "The different types of flooring or paving surfaces (tactile, cement, tiles, bricks) are not safe to walk on.",
            "There are unexpected holes in the ground that could trip you over.",
            "There are poles around or near the line guide.",
            "There are vehicular roads around the line guide.",
            "There are a lot of vehicular activities around the area.",
            "There are frequent and loud vehicle noises that could be disruptive.",
            "There are no pedestrian lanes available in the area.",
            "There is a need to pass in footbridges.",
            "There is no indicator for the blind to cross the street, e.g. sound, traffic enforcer.",
            "The crossing time is not enough for a blind person."
        });

        addSurveySection(contentPanel, "Activity Level", new String[]{
            "The place is crowded with people.",
            "The pace people are walking in is just right (1 for slow, 5 for fast).",
            "There are people biking around the area.",
            "There are entrance or exit points near or at the side of the line guide."
        });

        addSurveySection(contentPanel, "Environmental Factors", new String[]{
            "There are many plants and trees around the area.",
            "There are ongoing constructions in the area.",
            "There are difficult terrain or inaccessible places for the blind.",
            "The width of the sidewalk (1 for narrow, 5 for wide).",
            "There are a lot of road humps/rocks in the area."
        });
		
		questionsError = createErrorLabel("Some questions are unanswered.");
		contentPanel.add(questionsError, BorderLayout.SOUTH);

        // Comment Section
        JPanel commentPanel = new JPanel(new BorderLayout());
        commentPanel.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
        JLabel commentLabel = new JLabel("Additional Comments:");
        commentField = new JTextArea(5, 40);
        commentField.setLineWrap(true);
        commentField.setWrapStyleWord(true);
		addPlaceholder(commentField, " Type here...");
        JScrollPane commentScrollPane = new JScrollPane(commentField);

        commentPanel.add(commentLabel, BorderLayout.NORTH);
        commentPanel.add(commentScrollPane, BorderLayout.CENTER);
        contentPanel.add(commentPanel);

		commentsError = createErrorLabel("Comment cannot be over 280 characters long.");
		commentsError.add(commentsError, BorderLayout.SOUTH);

        // Bottom Panel
        JPanel bottomPanel = new JPanel(new BorderLayout());
        backButton = new JButton("Back");
        submitButton = new JButton("Submit");
        bottomPanel.add(backButton, BorderLayout.WEST);
        bottomPanel.add(submitButton, BorderLayout.EAST);
        panel.add(bottomPanel, BorderLayout.SOUTH);

		setErrorMessages(false);

        frameSetVisible();
    }

    // Helper method to create error labels
    private JLabel createErrorLabel(String text) {
        JLabel errorLabel = new JLabel(text);
        errorLabel.setForeground(Color.RED);
        return errorLabel;
    }

	public void setErrorMessages(boolean visible){
		locationError.setVisible(visible);
		dayError.setVisible(visible);
		timeError.setVisible(visible);
		questionsError.setVisible(visible);
		commentsError.setVisible(visible);
	}

    // Helper method to add survey sections with border around each question section
	private void addSurveySection(JPanel contentPanel, String sectionTitle, String[] questions) {
		// Create a panel for each section with 20px padding around the section
		JPanel sectionPanel = new JPanel();
		sectionPanel.setLayout(new BoxLayout(sectionPanel, BoxLayout.Y_AXIS));

		// Section Title (Center-aligned)
		JLabel sectionLabel = new JLabel(sectionTitle);
		sectionLabel.setFont(new Font("Arial", Font.BOLD, 14));
		sectionLabel.setHorizontalAlignment(SwingConstants.LEFT); // Align the title to the center
		sectionLabel.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0)); // Padding below the title
		sectionPanel.add(sectionLabel);

		// Create a panel for questions with a border around it
		JPanel questionsPanel = new JPanel();
		questionsPanel.setLayout(new BoxLayout(questionsPanel, BoxLayout.Y_AXIS));
		questionsPanel.setBorder(BorderFactory.createEmptyBorder(20, 15, 20, 0)); // Padding

		// Loop through each question and add radio buttons
		for (int x = 0; x < 20; x++) {
			JPanel questionPanel = new JPanel(new FlowLayout(FlowLayout.LEFT)); // FlowLayout for question label

			// Question Label
			JLabel questionLabel = new JLabel(questions[x]);
			questionLabel.setPreferredSize(new Dimension(900, 20)); // Set a fixed width for the question label
			questionPanel.add(questionLabel);

			// Panel for radio buttons (aligned to the right of the panel)
			JPanel radioPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT)); // FlowLayout with RIGHT alignment
			group[x] = new ButtonGroup();
			for (int i = 1; i <= 5; i++) {
				radioButton[x][i] = new JRadioButton(String.valueOf(i));
				group[x].add(radioButton[x][i]);
				radioPanel.add(radioButton[x][i]);
			}

			// Add the radio button panel to the question panel
			questionPanel.add(radioPanel);

			// Add the question panel (with label and radio buttons) to the questions panel
			questionsPanel.add(questionPanel);
		}

		// Add the questions panel (with border) to the section panel
		sectionPanel.add(questionsPanel);

		// Add the completed section panel to the content panel
		contentPanel.add(sectionPanel);
		questionPanels.add(sectionPanel);
	}

	public String getPlaceName(){
		return (String) locationDropdown.getSelectedItem();
	}

	public String getPlaceDay(){
		return (String) dayDropdown.getSelectedItem();
	}

	public String getPlaceTime(){
		return (String) timeDropdown.getSelectedItem();
	}

	public boolean validateAnswers(int[] answers){
		boolean valid = true, stop;
		int idx = 0;

		for (int x = 0; x < group.length && valid == true; x++) { 			// Loop through each ButtonGroup (row)
			ButtonModel selectedModel = group[x].getSelection(); 			// Get the selected model for the row
			if (selectedModel != null) { 									// Check if a button is selected
				stop = false;
				for (int i = 1; i <= 5 && stop == false; i++) { 			// Loop through each button in the row
					if (radioButton[x][i].getModel() == selectedModel) { 	// Match the model
						answers[idx] = Integer.parseInt(radioButton[x][i].getText());
						stop = true;
					}
				}
			} else {
				valid = false;
			}
		}

		return valid;
	}

	public boolean validateForm(int[] answers){
		boolean valid = true;

		if (validateAnswers(answers) == false){
			questionsError.setVisible(true);
			valid = false;
		}

		if (commentField.getText().length() > 280){
			commentsError.setVisible(true);
			valid = false;
		}

		if (locationDropdown.getSelectedIndex() == 0){
			locationError.setVisible(true);
			valid = false;
		}

		if (dayDropdown.getSelectedIndex() == 0){
			dayError.setVisible(true);
			valid = false;
		}

		if (timeDropdown.getSelectedIndex() == 0){
			timeError.setVisible(true);
			valid = false;
		}

		return valid;
	}

	public String getComment(){
		return commentField.getText();
	}

	public void setSubmitButtonListener(ActionListener listener) {
        submitButton.addActionListener(listener);
    } 

	public void setBackButtonListener(ActionListener listener) {
        backButton.addActionListener(listener);
    } 
}
