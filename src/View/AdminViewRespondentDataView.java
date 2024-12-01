package View;

import java.awt.*;
import javax.swing.*;
import javax.swing.table.*;
import java.awt.event.ActionListener;

public class AdminViewRespondentDataView extends FrameCanvas {
    final private JButton backButton = new JButton("Back");

    public AdminViewRespondentDataView(String[] allNames, String[] allBirthdates, int[] allAges, String[] allEmails, String[] allUsernames, String[] allSQs, String[] allDateJoineds) {
        super();
        
        setTitle("Respondents Data");
        setLocationRelativeTo(null);

        JLabel titleLabel = new JLabel("Respondents Data", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Garamond", Font.BOLD, 30));
        add(titleLabel, BorderLayout.NORTH);

        String[] columnHeaders = {"Name", "Birthday", "Age", "Email", "Username", "Security Question", "Date Joined"};

        DefaultTableModel tableModel = new DefaultTableModel(columnHeaders, 0);

        for (int i = 0; i < allNames.length; i++) {
            Object[] row = {
                allNames[i],
                allBirthdates[i], 
                allAges[i],
                allEmails[i],
                allUsernames[i],
                allSQs[i],
                allDateJoineds[i] 
            };
            tableModel.addRow(row);
        }

        JTable table = new JTable(tableModel);
        table.setFillsViewportHeight(true);

        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(SwingConstants.CENTER);

        for (int col = 0; col < columnHeaders.length; col++) {
            table.getColumnModel().getColumn(col).setCellRenderer(centerRenderer);
        }

        TableColumn birthdayColumn = table.getColumnModel().getColumn(1); // Birthday
        birthdayColumn.setPreferredWidth(100);
        birthdayColumn.setMaxWidth(100);
        
        TableColumn ageColumn = table.getColumnModel().getColumn(2); // Age
        ageColumn.setPreferredWidth(50);
        ageColumn.setMaxWidth(50);

        TableColumn sqIdColumn = table.getColumnModel().getColumn(5); // Security Question
        sqIdColumn.setPreferredWidth(250);
        sqIdColumn.setMaxWidth(250);

        TableColumn dateJoinedColumn = table.getColumnModel().getColumn(6); // Date Joined
        dateJoinedColumn.setPreferredWidth(100);
        dateJoinedColumn.setMaxWidth(100);

        JPanel panel1 = new JPanel(new FlowLayout(FlowLayout.LEFT));
        panel1.add(backButton);
        add(panel1, BorderLayout.SOUTH);

        JPanel tablePanel = new JPanel(new BorderLayout());
        tablePanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        JScrollPane scrollPane = new JScrollPane(table);
        tablePanel.add(scrollPane, BorderLayout.CENTER);
        add(tablePanel, BorderLayout.CENTER);

        frameSetVisible();
    }   

    public void setBackButtonListener(ActionListener listener) {
        backButton.addActionListener(listener);
    }

}