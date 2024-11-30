package View;

import Model.*;
import java.awt.*;
import java.util.*;
import javax.swing.*;
import java.awt.event.*;
import javax.swing.table.*;

public class AdminViewAnalystDataView extends ViewAbstract {
    final private JButton backButton = new JButton("Back");

    public AdminViewAnalystDataView(ArrayList<String> allUsernames, ArrayList<String> allSQs, ArrayList<DateClass> allDateJoineds) {
        super();

        setTitle("Analysts Data");
        setLocationRelativeTo(null);

        JLabel titleLabel = new JLabel("Analysts Data", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Garamond", Font.BOLD, 30));
        add(titleLabel, BorderLayout.NORTH);

        String[] columnHeaders = {
            "Username", "Security Question", "Date Joined"
        };

        DefaultTableModel tableModel = new DefaultTableModel(columnHeaders, 0);

        for (int i = 0; i < allUsernames.size(); i++) {
            Object[] row = {
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

        frameSetVisible();
    }   

    public void setBackButtonListener(ActionListener listener) {
        backButton.addActionListener(listener);
    }
}
