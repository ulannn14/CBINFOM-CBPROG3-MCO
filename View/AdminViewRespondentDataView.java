package View;

import javax.swing.*;
import javax.swing.table.*;
import java.awt.*;
import java.util.ArrayList;
import java.awt.event.ActionListener;

public class AdminViewRespondentDataView extends JFrame {
    private JButton backButton = new JButton("Back");
    private Image backgroundImage;

    public AdminViewRespondentDataView(ArrayList<Respondent> allRespondents) {
        backgroundImage = new ImageIcon("fadminViewRespondentDataView.png").getImage();
        
        setTitle("Respondents Data");
        setSize(1200, 700);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JLabel titleLabel = new JLabel("Respondents Data", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Garamond", Font.BOLD, 30));
        add(titleLabel, BorderLayout.NORTH);

        String[] columnHeaders = {
            "User ID", "Name", "Birthday", "Age",
            "Email", "Username", "S.Q. ID", "Date Joined"
        };

        DefaultTableModel tableModel = new DefaultTableModel(columnHeaders, 0);

        for (Respondent respondent : allRespondents) {
            Object[] row = {
                respondent.getUserID(),
                respondent.getName(),
                respondent.getBirthday(),
                respondent.getAge(),
                respondent.getEmail(),
                respondent.getUsername(),
                respondent.getSecurityQuestionID(),
                respondent.getDateJoined()
            };
            tableModel.addRow(row);
        }

        JTable table = new JTable(tableModel);
        table.setFillsViewportHeight(true);

        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(SwingConstants.CENTER);

        table.getColumnModel().getColumn(0).setCellRenderer(centerRenderer); // User ID
        table.getColumnModel().getColumn(1).setCellRenderer(centerRenderer); // Name
        table.getColumnModel().getColumn(2).setCellRenderer(centerRenderer); // Birthday
        table.getColumnModel().getColumn(3).setCellRenderer(centerRenderer); // Age
        table.getColumnModel().getColumn(4).setCellRenderer(centerRenderer); // Email
        table.getColumnModel().getColumn(5).setCellRenderer(centerRenderer); // Username
        table.getColumnModel().getColumn(6).setCellRenderer(centerRenderer); // S.Q. ID
        table.getColumnModel().getColumn(7).setCellRenderer(centerRenderer); // Date Joined

        TableColumn userIdColumn = table.getColumnModel().getColumn(0); // User ID
        userIdColumn.setPreferredWidth(50);
        userIdColumn.setMaxWidth(50);

        TableColumn birthdayColumn = table.getColumnModel().getColumn(2); // Birthday
        birthdayColumn.setPreferredWidth(100);
        birthdayColumn.setMaxWidth(100);
        
        TableColumn ageColumn = table.getColumnModel().getColumn(3); // Age
        ageColumn.setPreferredWidth(50);
        ageColumn.setMaxWidth(50);

        TableColumn sqIdColumn = table.getColumnModel().getColumn(6); // S.Q. ID
        sqIdColumn.setPreferredWidth(100);
        sqIdColumn.setMaxWidth(100);

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
