package View;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.table.*;

public class RespondentViewHistoryView extends FrameCanvas {
    final private JPanel contentPanel;  // Panel to hold all survey sets
    final private JButton backButton = new JButton("Back");

    // Modified constructor to accept answers and metadata
    public RespondentViewHistoryView(int[][] answers, String[] places, String[] days, String[] times, String[] dateTakens) {
		super();

        setTitle("Survey History");
        setLocationRelativeTo(null);

        // Main Panel
        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        add(mainPanel);

        // Title
        JLabel titleLabel = new JLabel("Survey History", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Garamond", Font.BOLD, 30));
        mainPanel.add(titleLabel, BorderLayout.NORTH);

        // Content Panel (with ScrollPane)
        contentPanel = new JPanel();
        contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.Y_AXIS));
        JScrollPane scrollPane = new JScrollPane(contentPanel);
        scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        mainPanel.add(scrollPane, BorderLayout.CENTER);

        // Add Survey History
        displaySurveyHistory(answers, places, days, times, dateTakens);

        // Bottom Panel (Back Button)
        JPanel bottomPanel = new JPanel(new BorderLayout());
        bottomPanel.add(backButton, BorderLayout.WEST);  // Back button in the West part of the bottom panel
        mainPanel.add(bottomPanel, BorderLayout.SOUTH);

        frameSetVisible();
    }

    // Method to display all survey history
    private void displaySurveyHistory(int[][] answers, String[] places, String[] days, String[] times, String[] dateTakens) {
        int surveyCounter = answers.length;
        
        for (int i = 0; i < surveyCounter; i++) {
            JPanel surveyPanel = new JPanel();
            surveyPanel.setLayout(new BoxLayout(surveyPanel, BoxLayout.Y_AXIS));
            surveyPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

            // Survey Header (Location, Day, Time, Date Taken)
            JPanel headerPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
            JLabel locationLabel = new JLabel("Location: " + places[i]);
            JLabel dayLabel = new JLabel("Day: " + days[i]);
            JLabel timeLabel = new JLabel("Time: " + times[i]);
            JLabel dateTakenLabel = new JLabel("Date Taken: " + dateTakens[i]);

            headerPanel.add(locationLabel);
            headerPanel.add(dayLabel);
            headerPanel.add(timeLabel);
            headerPanel.add(dateTakenLabel);

            surveyPanel.add(headerPanel);

            // Survey Sections (Route Efficiency, Activity Level, Environmental Factors)
            displaySurveySection(surveyPanel, "Route Efficiency", answers[i], 0);
            displaySurveySection(surveyPanel, "Activity Level", answers[i], 10);  // Assuming 10 questions for Activity Level
            displaySurveySection(surveyPanel, "Environmental Factors", answers[i], 15);  // Assuming 5 questions for Environmental Factors

            // Separator Line for Each Survey Set
            JSeparator separator = new JSeparator();
            separator.setOrientation(SwingConstants.HORIZONTAL);
            surveyPanel.add(separator);

            // Add survey panel to content panel
            contentPanel.add(surveyPanel);
        }
    }

    // Display Survey Section
    private void displaySurveySection(JPanel surveyPanel, String sectionLabel, int[] answers, int startIndex) {
        JPanel sectionPanel = new JPanel();
        sectionPanel.setLayout(new BoxLayout(sectionPanel, BoxLayout.Y_AXIS));

        // Left-align section title
        JLabel sectionTitleLabel = new JLabel(sectionLabel);
        sectionTitleLabel.setFont(new Font("Arial", Font.BOLD, 18));
        sectionTitleLabel.setAlignmentX(Component.LEFT_ALIGNMENT);  // Align to the left
        sectionPanel.add(sectionTitleLabel);

        // Table Layout for Questions
        String[] columnNames = {"Score", "Survey Question"};
        String[][] rowData = new String[10][2];  // Assuming 10 questions per section

        for (int i = 0; i < 10; i++) {
            rowData[i][0] = String.valueOf(answers[startIndex + i]);
            rowData[i][1] = "Question " + (startIndex + i + 1);  // Placeholder text for questions
        }

        JTable table = new JTable(rowData, columnNames);
        table.setRowHeight(25);
        table.setFillsViewportHeight(true);
        table.setEnabled(false);  // Disable editing of table cells
        table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

        // Set column width
        table.getColumnModel().getColumn(0).setPreferredWidth(50);
        table.getColumnModel().getColumn(1).setPreferredWidth(1020);

        // Center align the "Score" column
        DefaultTableCellRenderer scoreRenderer = new DefaultTableCellRenderer();
        scoreRenderer.setHorizontalAlignment(JLabel.CENTER);
        table.getColumnModel().getColumn(0).setCellRenderer(scoreRenderer);

        JScrollPane tableScrollPane = new JScrollPane(table);
        tableScrollPane.setPreferredSize(new Dimension(600, 100));
        sectionPanel.add(tableScrollPane);

        // Add section panel to survey panel
        surveyPanel.add(sectionPanel);
    }

    public void setBackButtonListener(ActionListener listener) {
        backButton.addActionListener(listener);
    } 
}
