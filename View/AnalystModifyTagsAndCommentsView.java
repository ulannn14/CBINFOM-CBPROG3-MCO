import javax.swing.*;
import java.awt.*;
import java.awt.event.ItemEvent;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class AnalystModifyTagsAndCommentsView extends JFrame {

    private JPanel tagsPanel;
    private JPanel commentsPanel;
    private JTextArea inputField;
    private JButton backButton;
    private JLabel errorLabel; // Error label for displaying messages
    private ArrayList<JCheckBox> tagCheckBoxes = new ArrayList<>();
    private ArrayList<JCheckBox> commentCheckBoxes = new ArrayList<>();
    private Map<JCheckBox, Integer> tagIdMap = new HashMap<>();     // Map to store tag IDs
    private Map<JCheckBox, Integer> commentIdMap = new HashMap<>(); // Map to store comment IDs
    private int instanceID;
    private String[] summaries;
    private String[] tags;

    public AnalystModifyTagsAndCommentsView(int instanceID, String[][] summaries, String[][] tags,
                                            int counterCS, int counterTag) {
        this.instanceID = instanceID;

        setTitle("Modify Tags and Comment Summaries");
        setSize(1200, 700);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);

        // Title Label
        JLabel titleLabel = new JLabel("Modify Tags and Comment Summaries", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Garamond", Font.BOLD, 30));
        add(titleLabel, BorderLayout.NORTH);

        // Main Panel
        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        add(mainPanel, BorderLayout.CENTER);

        // Tags and Comments Panel
        JPanel contentPanel = new JPanel(new GridLayout(1, 2, 20, 0)); // Two columns: Tags and Comments

        // Tags Panel
        tagsPanel = new JPanel();
        tagsPanel.setLayout(new BoxLayout(tagsPanel, BoxLayout.Y_AXIS));
        tagsPanel.add(new JLabel("Tags"));
        tagsPanel.setBorder(BorderFactory.createEmptyBorder());
        populateTags(tags); // Populate the tags
        JScrollPane tagsScrollPane = new JScrollPane(tagsPanel);
        contentPanel.add(tagsScrollPane);

        // Comments Panel
        commentsPanel = new JPanel();
        commentsPanel.setLayout(new BoxLayout(commentsPanel, BoxLayout.Y_AXIS));
        commentsPanel.add(new JLabel("Comment Summaries"));
        commentsPanel.setBorder(BorderFactory.createEmptyBorder());
        populateComments(summaries); // Populate the comments
        JScrollPane commentsScrollPane = new JScrollPane(commentsPanel);
        contentPanel.add(commentsScrollPane);

        mainPanel.add(contentPanel, BorderLayout.CENTER);

        // Bottom Panel
        JPanel bottomPanel = new JPanel(new BorderLayout());
        mainPanel.add(bottomPanel, BorderLayout.SOUTH);

        // Input Panel
        JPanel inputPanel = new JPanel(new BorderLayout());
        inputPanel.add(new JLabel("Add New Entry"), BorderLayout.NORTH);
        inputPanel.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0));
        inputField = new JTextArea(3, 35);
        inputField.setLineWrap(true);
        inputField.setWrapStyleWord(true);
        JScrollPane inputScrollPane = new JScrollPane(inputField);
        inputPanel.add(inputScrollPane, BorderLayout.CENTER);

        // Error Label
        errorLabel = new JLabel();
        errorLabel.setForeground(Color.RED); // Red for errors
        errorLabel.setVisible(false); // Initially hidden
        inputPanel.add(errorLabel, BorderLayout.SOUTH);

        bottomPanel.add(inputPanel, BorderLayout.NORTH);

        // Button Panel
        JPanel buttonPanel = new JPanel(new FlowLayout());
        JButton addTagButton = new JButton("Add Tag");
        JButton addCommentSummaryButton = new JButton("Add Comment Summary");
        JButton deleteButton = new JButton("Delete Selected");
        buttonPanel.add(addTagButton);
        buttonPanel.add(addCommentSummaryButton);
        buttonPanel.add(deleteButton);

        bottomPanel.add(buttonPanel, BorderLayout.CENTER);

        // Back Button
        backButton = new JButton("Back");
        bottomPanel.add(backButton, BorderLayout.WEST);

        // Button Actions
        addTagButton.addActionListener(e -> addTag());
        addCommentSummaryButton.addActionListener(e -> addComment());
        deleteButton.addActionListener(e -> deleteSelectedItems());

        setVisible(true);
    }

    // Populate tags panel
    private void populateTags(String[][] tags) {
        for (int i = 0; i < tags.length; i++) {
            String tagId = tags[i][0];
            String tagName = tags[i][1];
            String username = tags[i][2];
            String displayText = String.format("ID: %s | Tag: %s | User: %s", tagId, tagName, username);

            JCheckBox checkBox = new JCheckBox(displayText);
            tagCheckBoxes.add(checkBox);
            tagIdMap.put(checkBox, Integer.parseInt(tagId)); // Map the checkbox to the Tag ID
            checkBox.addItemListener(e -> {
                if (e.getStateChange() == ItemEvent.SELECTED) {
                    int tagIdSelected = tagIdMap.get(checkBox);
                    System.out.println("Tag ID selected: " + tagIdSelected); // Do something with the Tag ID
                }
            });
            tagsPanel.add(checkBox);
        }
    }

    // Populate comments panel
    private void populateComments(String[][] summaries) {
        for (int i = 0; i < summaries.length; i++) {
            String commentId = summaries[i][0];
            String commentText = summaries[i][1];
            String username = summaries[i][2];
            String displayText = String.format("ID: %s | Comment: %s | User: %s", commentId, commentText, username);

            JCheckBox checkBox = new JCheckBox(displayText);
            commentCheckBoxes.add(checkBox);
            commentIdMap.put(checkBox, Integer.parseInt(commentId)); // Map the checkbox to the Comment ID
            checkBox.addItemListener(e -> {
                if (e.getStateChange() == ItemEvent.SELECTED) {
                    int commentIdSelected = commentIdMap.get(checkBox);
                    System.out.println("Comment ID selected: " + commentIdSelected); // Do something with the Comment ID
                }
            });
            commentsPanel.add(checkBox);
        }
    }

    private void addTag() {
        // Implementation for adding a new tag
    }

    private void addComment() {
        // Implementation for adding a new comment
    }

    private void deleteSelectedItems() {
        // Implementation for deleting selected items
    }

    public static void main(String[] args) {
        String[][] summaries = {
            {"1", "Heavy traffic on route 1", "AnalystA"},
            {"2", "Road construction causing delays", "AnalystB"},
            {"3", "Long comment summary example", "AnalystC"}
        };
        String[][] tags = {
            {"1", "Traffic", "AnalystA"},
            {"2", "Construction", "AnalystB"}
        };
        new AnalystModifyTagsAndCommentsView(123, summaries, tags, summaries.length, tags.length);
    }
}
