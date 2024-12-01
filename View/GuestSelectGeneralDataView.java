package View;

import ServiceClassPackage.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;
import java.util.stream.*;
import javax.swing.*;

public class GuestSelectGeneralDataView extends FrameCanvas {
    final private JButton backButton;
    final private JButton submitButton;
    final private JTextField recommendationsField;
    final private JRadioButton bestToWorstRadio;
    final private JRadioButton worstToBestRadio;
    final private ButtonGroup rankingGroup = new ButtonGroup();

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
 
    final private JCheckBox[] placeCheckBoxes = new JCheckBox[Constants.MAX_PLACES];
    final private JCheckBox[] dayCheckBoxes = new JCheckBox[Constants.MAX_DAYS];
    final private JCheckBox[] timeCheckBoxes = new JCheckBox[Constants.MAX_TIMES];

    final private JLabel submitError = new JLabel("Select at least one place, day, or time.");

    public GuestSelectGeneralDataView() {
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

        // Add a new label to describe the purpose of the form
        JLabel descriptionLabel = new JLabel("Select the streets, days, and time categories for which you want to view data.", SwingConstants.LEFT);
        descriptionLabel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10)); // Add padding
        mainPanel.add(descriptionLabel, BorderLayout.NORTH);  // Add the label just below the main title

        // Create a Grid Layout with 1 row and 3 columns
        JPanel gridPanel = new JPanel(new GridLayout(1, 3, 20, 0));

        // Column 1: Place checkboxes with a scroll pane
        JPanel placePanel = new JPanel();
        placePanel.setLayout(new BoxLayout(placePanel, BoxLayout.Y_AXIS)); // Initialize here
        placePanel.setBorder(BorderFactory.createTitledBorder("Select Place"));

        for (int i = 0; i < Constants.MAX_PLACES; i++) {
            placeCheckBoxes[i] = new JCheckBox(locations[i]);
            placePanel.add(placeCheckBoxes[i]);
        }

        // Add the placePanel to a JScrollPane to make it scrollable
        JScrollPane placeScrollPane = new JScrollPane(placePanel);
        placeScrollPane.setPreferredSize(new Dimension(200, 300));  // Set the preferred size of the scroll pane
        gridPanel.add(placeScrollPane);

        // Column 2: Day and Time checkboxes
        JPanel dayTimePanel = new JPanel();
        dayTimePanel.setLayout(new BoxLayout(dayTimePanel, BoxLayout.Y_AXIS)); // Initialize here
        dayTimePanel.setBorder(BorderFactory.createTitledBorder("Select Day and Time"));

        // Day panel with title
        JPanel dayPanel = new JPanel();
        dayPanel.setLayout(new BoxLayout(dayPanel, BoxLayout.Y_AXIS));
        dayPanel.setBorder(BorderFactory.createTitledBorder("Select Day"));
        for (int i = 0; i < Constants.MAX_DAYS; i++) {
            dayCheckBoxes[i] = new JCheckBox(days[i]);
            dayPanel.add(dayCheckBoxes[i]);
        }

        // Time panel with title
        JPanel timePanel = new JPanel();
        timePanel.setLayout(new BoxLayout(timePanel, BoxLayout.Y_AXIS));
        timePanel.setBorder(BorderFactory.createTitledBorder("Select Time"));
        for (int i = 0; i < Constants.MAX_TIMES; i++) {
            timeCheckBoxes[i] = new JCheckBox(times[i]);
            timePanel.add(timeCheckBoxes[i]);
        }

        // Add day and time panels to the dayTimePanel
        dayTimePanel.add(dayPanel);  // Add the Day panel
        dayTimePanel.add(timePanel); // Add the Time panel
        gridPanel.add(dayTimePanel);

        // Column 3: Ranking options and recommendations field
        JPanel rankingPanel = new JPanel(new BorderLayout()); // Use BorderLayout for proper alignment
        rankingPanel.setBorder(BorderFactory.createTitledBorder("Ranking Options"));

        // Ranking label aligned to the left
        JLabel rankingLabel = new JLabel("<html>Choose whether to view the data ranked from worst to best or best to worst.</html>", SwingConstants.LEFT);

        // Create a vertical container for all ranking components
        JPanel rankingContentPanel = new JPanel();
        rankingContentPanel.setLayout(new BoxLayout(rankingContentPanel, BoxLayout.Y_AXIS)); // Stack components vertically
        rankingContentPanel.setAlignmentX(Component.LEFT_ALIGNMENT); // Ensure the content panel aligns to the left

        // Add ranking label to the content panel
        rankingLabel.setAlignmentX(Component.LEFT_ALIGNMENT); // Align the label to the left
        rankingContentPanel.add(rankingLabel);

        // Add a little vertical space between ranking label and radio buttons
        rankingContentPanel.add(Box.createVerticalStrut(5));

        // Ranking radio buttons
        bestToWorstRadio = new JRadioButton("Best to Worst");
        worstToBestRadio = new JRadioButton("Worst to Best");

        // Ensure radio buttons align to the left
        bestToWorstRadio.setAlignmentX(Component.LEFT_ALIGNMENT);
        worstToBestRadio.setAlignmentX(Component.LEFT_ALIGNMENT);

        rankingGroup.add(bestToWorstRadio);
        rankingGroup.add(worstToBestRadio);

        // Add radio buttons to the content panel
        rankingContentPanel.add(bestToWorstRadio);
        rankingContentPanel.add(worstToBestRadio);

        // Add a little vertical space between radio buttons and the recommendations field
        rankingContentPanel.add(Box.createVerticalStrut(10));

        // Number of recommendations text field and its label
        JPanel recommendationsPanel = new JPanel();
        recommendationsPanel.setLayout(new FlowLayout(FlowLayout.LEFT, 5, 5)); 
        recommendationsPanel.setAlignmentX(Component.LEFT_ALIGNMENT); // Align the panel to the left
        JLabel recommendationsLabel = new JLabel("Number of recommendations:");
        recommendationsField = new JTextField(5); // Smaller width for compactness
        recommendationsPanel.add(recommendationsLabel);
        recommendationsPanel.add(recommendationsField);
        rankingContentPanel.add(recommendationsPanel);

        // Add the content panel to the north of the ranking panel
        rankingPanel.add(rankingContentPanel, BorderLayout.NORTH);

        // Add the ranking panel to the gridPanel
        gridPanel.add(rankingPanel);

        // Add the grid panel to the main panel
        mainPanel.add(gridPanel, BorderLayout.CENTER);

        // Bottom panel for buttons
        JPanel bottomPanel = new JPanel(new BorderLayout());
        mainPanel.add(bottomPanel, BorderLayout.SOUTH); // Add the bottom panel to the main panel

        // Back button on the lower left
        backButton = new JButton("Back");
        bottomPanel.add(backButton, BorderLayout.WEST);

        // Submit button on the lower right
        submitButton = new JButton("Submit");
        bottomPanel.add(submitButton, BorderLayout.EAST);

        setErrorMessages(false);

        frameSetVisible();
    }

    public void setErrorMessages(boolean valid){
        submitError.setVisible(valid);
    }

    public boolean validateForms(){
        boolean valid = true;
        ArrayList<String> places = getPlaces();
        ArrayList<String> days = getDays();
        ArrayList<String> times = getTimes();

        if (places.isEmpty() || days.isEmpty() || times.isEmpty()){
            submitError.setVisible(true);
            valid = false;
        }

        return valid;
    }

    public ArrayList<String> getPlaces(){
        ArrayList<String> places = new ArrayList<>();

        for (int x = 0; x < Constants.MAX_PLACES; x++){
            if (placeCheckBoxes[x].isSelected())
                places.add(placeCheckBoxes[x].getText());
        }

        return places;
    }

    public ArrayList<String> getDays(){
        ArrayList<String> days = new ArrayList<>();
        
        for (int x = 0; x < Constants.MAX_DAYS; x++){
            if (dayCheckBoxes[x].isSelected())
                days.add(dayCheckBoxes[x].getText());
        }

        return days;
    }

    public ArrayList<String> getTimes(){
        ArrayList<String> times = new ArrayList<>();
        
        for (int x = 0; x < Constants.MAX_TIMES; x++){
            if (timeCheckBoxes[x].isSelected())
                times.add(timeCheckBoxes[x].getText());
        }

        return times;
    }

    public String getRecommendation(){
        String selected = "";

		ButtonModel selectedModel = rankingGroup.getSelection(); 			// Get the selected model for the row
        if (selectedModel != null) { 									    // Check if a button is selected
            if (bestToWorstRadio.getModel() == selectedModel)
                selected = bestToWorstRadio.getText();
            if (worstToBestRadio.getModel() == selectedModel)
                selected = worstToBestRadio.getText();
        }
        
		return selected;
    }

    public int getRecNum(){
        return Integer.parseInt(recommendationsField.getText());
    }

    public void setSubmitButtonListener(ActionListener listener) {
        submitButton.addActionListener(listener);
    }

    public void setBackButtonListener(ActionListener listener) {
        backButton.addActionListener(listener);
    }
}
