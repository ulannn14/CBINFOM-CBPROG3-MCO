package View;

import java.awt.*;
import java.awt.event.ItemEvent;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import javax.swing.*;

public class AnalystModifyTagsAndCommentsView extends FrameCanvas {

    final private JPanel tagsPanel;
    final private JPanel commentsPanel;
    final private JTextArea inputField;
    final private JButton backButton;
    final private JLabel errorLabel; // Error label for displaying messages
    final private ArrayList<JCheckBox> tagCheckBoxes = new ArrayList<>();
    final private ArrayList<JCheckBox> commentCheckBoxes = new ArrayList<>();
    final private Map<JCheckBox, Integer> tagIdMap = new HashMap<>();     // Map to store tag IDs
    final private Map<JCheckBox, Integer> commentIdMap = new HashMap<>(); // Map to store comment IDs
    final private JButton addTagButton;
    final private JButton addCommentSummaryButton;
    final private JButton deleteButton;

    public AnalystModifyTagsAndCommentsView(int instanceID, String[] summaries, String[] tags, int counterCS, int counterTag) {
        super();

        setTitle("Modify Tags and Comment Summaries");
        setLocationRelativeTo(null);

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
        tagsPanel.setBorder(BorderFactory.createTitledBorder("Tags"));
        populateTags(tags, counterTag); // Populate the tags
        JScrollPane tagsScrollPane = new JScrollPane(tagsPanel);
        contentPanel.add(tagsScrollPane);

        // Comments Panel
        commentsPanel = new JPanel();
        commentsPanel.setLayout(new BoxLayout(commentsPanel, BoxLayout.Y_AXIS));
        commentsPanel.setBorder(BorderFactory.createTitledBorder("Comment Summaries"));
        populateComments(summaries, counterCS); // Populate the comments
        JScrollPane commentsScrollPane = new JScrollPane(commentsPanel);
        contentPanel.add(commentsScrollPane);

        mainPanel.add(contentPanel, BorderLayout.CENTER);

        // Bottom Panel
        JPanel bottomPanel = new JPanel(new BorderLayout());
        mainPanel.add(bottomPanel, BorderLayout.SOUTH);

        // Input Panel
        JPanel inputPanel = new JPanel(new BorderLayout());
        inputPanel.add(new JLabel("Add New Entry"), BorderLayout.NORTH);
        inputField = new JTextArea(3, 35);
        inputField.setLineWrap(true);
        inputField.setWrapStyleWord(true);
        JScrollPane inputScrollPane = new JScrollPane(inputField);
        inputPanel.add(inputScrollPane, BorderLayout.CENTER);

        // Error Label
        errorLabel = new JLabel();
        errorLabel.setForeground(Color.RED);
        errorLabel.setVisible(false);
        inputPanel.add(errorLabel, BorderLayout.SOUTH);
        bottomPanel.add(inputPanel, BorderLayout.NORTH);

        // Button Panel
        JPanel buttonPanel = new JPanel(new FlowLayout());
        addTagButton = new JButton("Add Tag");
        addCommentSummaryButton = new JButton("Add Comment Summary");
        deleteButton = new JButton("Delete Selected");
        buttonPanel.add(addTagButton);
        buttonPanel.add(addCommentSummaryButton);
        buttonPanel.add(deleteButton);
        bottomPanel.add(buttonPanel, BorderLayout.CENTER);

        // Back Button
        backButton = new JButton("Back");
        bottomPanel.add(backButton, BorderLayout.WEST);

        frameSetVisible();
    }

    // Populate tags panel
    private void populateTags(String[] tags, int counterTag) {
        for (int i = 0; i < counterTag; i++) {
            String tag = tags[i];

            JCheckBox checkBox = new JCheckBox(tag);
            tagCheckBoxes.add(checkBox);
            tagIdMap.put(checkBox, i); // Using index as the ID, or update accordingly
            checkBox.addItemListener(e -> {
                if (e.getStateChange() == ItemEvent.SELECTED) {
                    int tagIdSelected = tagIdMap.get(checkBox);
                    System.out.println("Tag ID selected: " + tagIdSelected);
                }
            });
            tagsPanel.add(checkBox);
        }
    }

    // Populate comments panel
    private void populateComments(String[] summaries, int counterCS) {
        for (int i = 0; i < counterCS; i++) {
            String summary = summaries[i];

            JCheckBox checkBox = new JCheckBox(summary);
            commentCheckBoxes.add(checkBox);
            commentIdMap.put(checkBox, i); // Using index as the ID, or update accordingly
            checkBox.addItemListener(e -> {
                if (e.getStateChange() == ItemEvent.SELECTED) {
                    int commentIdSelected = commentIdMap.get(checkBox);
                    System.out.println("Comment ID selected: " + commentIdSelected);
                }
            });
            commentsPanel.add(checkBox);
        }
    }

    public void setDeleteButtonListener(java.awt.event.ActionListener listener) {
        deleteButton.addActionListener(listener);
    }

    public void setAddTagButtonListener(java.awt.event.ActionListener listener) {
        addTagButton.addActionListener(listener);
    }

    public void setAddSummaryButtonListener(java.awt.event.ActionListener listener) {
        addCommentSummaryButton.addActionListener(listener);
    }

    public void setBackButtonListener(java.awt.event.ActionListener listener) {
        backButton.addActionListener(listener);
    }

    public ArrayList<Integer> getTagIDsToDelete() {
        ArrayList<Integer> selectedTagIds = new ArrayList<>();
        for (JCheckBox checkBox : tagCheckBoxes) {
            if (checkBox.isSelected()) {
                selectedTagIds.add(tagIdMap.get(checkBox));
            }
        }
        return selectedTagIds;
    }

    public ArrayList<Integer> getSummariesIDsToDelete() {
        ArrayList<Integer> selectedCommentIds = new ArrayList<>();
        for (JCheckBox checkBox : commentCheckBoxes) {
            if (checkBox.isSelected()) {
                selectedCommentIds.add(commentIdMap.get(checkBox));
            }
        }
        return selectedCommentIds;
    }

    public String getNewTag() {
        return inputField.getText().trim();
    }

    public String getNewCS() {
        return inputField.getText().trim();
    }

    public boolean validateDelete() {
        if (!getTagIDsToDelete().isEmpty() || !getSummariesIDsToDelete().isEmpty()) {
            return true;
        } else {
            errorLabel.setText("Please select tags or comments to delete.");
            errorLabel.setVisible(true);
            return false;
        }
    }

    public boolean validateAddTag() {
        if (!getNewTag().isEmpty()) {
            return true;
        } else {
            errorLabel.setText("Please input a tag.");
            errorLabel.setVisible(true);
            return false;
        }
    }

    public boolean validateAddCS() {
        if (!getNewCS().isEmpty()) {
            return true;
        } else {
            errorLabel.setText("Please input a comment summary.");
            errorLabel.setVisible(true);
            return false;
        }
    }

    public void setErrorMessages(boolean visible) {
        errorLabel.setVisible(visible);
    }
}
