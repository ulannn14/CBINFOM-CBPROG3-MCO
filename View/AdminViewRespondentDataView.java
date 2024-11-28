package View;

import Model.*;
import javax.swing.*;
import javax.swing.table.*;
import java.awt.*;
import java.util.ArrayList;
import java.awt.event.ActionListener;

public class AdminViewRespondentDataView extends JFrame {
    final private JButton backButton = new JButton("Back");
    final private Image backgroundImage;

    public AdminViewRespondentDataView(ArrayList<String> allNames, ArrayList<DateClass> allBirthdates, ArrayList<Integer> allAges, ArrayList<String> allEmails,
                                        ArrayList<String> allUsernames, ArrayList<String> allSQs, ArrayList<DateClass> allDateJoineds) {
        backgroundImage = new ImageIcon("fadminViewRespondentDataView.png").getImage();
        
        setTitle("Respondents Data");
        setSize(1200, 700);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JLabel titleLabel = new JLabel("Respondents Data", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Garamond", Font.BOLD, 30));
        add(titleLabel, BorderLayout.NORTH);

        String[] columnHeaders = {
            "Name", "Birthday", "Age", "Email", "Username", "Security Question", "Date Joined"
        };

        DefaultTableModel tableModel = new DefaultTableModel(columnHeaders, 0);

        for (int i = 0; i < allNames.size(); i++) {
            Object[] row = {
                allNames.get(i),
                allBirthdates.get(i), 
                allAges.get(i),
                allEmails.get(i),
                allUsernames.get(i),
                allSQs.get(i),
                allDateJoineds.get(i) 
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

        TableColumn birthdayColumn = table.getColumnModel().getColumn(2); // Birthday
        birthdayColumn.setPreferredWidth(100);
        birthdayColumn.setMaxWidth(100);
        
        TableColumn ageColumn = table.getColumnModel().getColumn(3); // Age
        ageColumn.setPreferredWidth(50);
        ageColumn.setMaxWidth(50);

        TableColumn sqIdColumn = table.getColumnModel().getColumn(6); // Security Question
        sqIdColumn.setPreferredWidth(150);
        sqIdColumn.setMaxWidth(200);

        TableColumn dateJoinedColumn = table.getColumnModel().getColumn(7); // Date Joined
        dateJoinedColumn.setPreferredWidth(100);
        dateJoinedColumn.setMaxWidth(100);

        JPanel panel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        panel.add(backButton);
        add(panel, BorderLayout.SOUTH);

        JPanel tablePanel = new JPanel(new BorderLayout());
        tablePanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        JScrollPane scrollPane = new JScrollPane(table);
        tablePanel.add(scrollPane, BorderLayout.CENTER);
        add(tablePanel, BorderLayout.CENTER);

        setVisible(true);
    }   

    public void setBackButtonListener(ActionListener listener) {
        backButton.addActionListener(listener);
    }

}
