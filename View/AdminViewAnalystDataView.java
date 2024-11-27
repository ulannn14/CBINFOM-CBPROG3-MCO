package View;

import javax.swing.*;
import javax.swing.table.*;
import java.awt.*;
import java.util.ArrayList;
import java.awt.event.ActionListener;

public class AdminViewAnalystDataView extends JFrame {
    private JButton backButton = new JButton("Back");
    private Image backgroundImage;

    public AdminViewAnalystDataView(ArrayList<Analyst> allAnalysts) {
        backgroundImage = new ImageIcon("fadminViewAnalystDataView.png").getImage();
        
        setTitle("Analysts Data");
        setSize(1200, 700);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);
        setLocationRelativeTo(null);

        JLabel titleLabel = new JLabel("Analysts Data", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Garamond", Font.BOLD, 30));
        add(titleLabel, BorderLayout.NORTH);

        String[] columnHeaders = {
            "User ID", "Username", "S.Q. ID", "Date Joined"
        };

        DefaultTableModel tableModel = new DefaultTableModel(columnHeaders, 0);

        for (Analyst analyst : allAnalysts) {
            Object[] row = {
                analyst.getUserID(),
                analyst.getUsername(),
                analyst.getSecurityQuestionID(),
                analyst.getDateJoined()
            };
            tableModel.addRow(row);
        }

        JTable table = new JTable(tableModel);
        table.setFillsViewportHeight(true);

        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(SwingConstants.CENTER);

        table.getColumnModel().getColumn(0).setCellRenderer(centerRenderer); // User ID
        table.getColumnModel().getColumn(1).setCellRenderer(centerRenderer); // Username
        table.getColumnModel().getColumn(2).setCellRenderer(centerRenderer); // S.Q. ID
        table.getColumnModel().getColumn(3).setCellRenderer(centerRenderer); // Date Joined

        TableColumn userIdColumn = table.getColumnModel().getColumn(0); // User ID
        userIdColumn.setPreferredWidth(50);
        userIdColumn.setMaxWidth(50);

        TableColumn sqIdColumn = table.getColumnModel().getColumn(2); // S.Q. ID
        sqIdColumn.setPreferredWidth(50);
        sqIdColumn.setMaxWidth(50);

        TableColumn dateJoinedColumn = table.getColumnModel().getColumn(3); // Date Joined
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
