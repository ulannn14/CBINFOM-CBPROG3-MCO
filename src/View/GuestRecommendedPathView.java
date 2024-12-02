package View;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class GuestRecommendedPathView extends FrameCanvas {
    private ArrayList<Integer> path;
    private int flag;
    private String day, time, start, goal;
    private JButton backButton;

    public GuestRecommendedPathView(ArrayList<Integer> path, int flag, String day, String time, String start, String goal) {
        super();

        this.path = path;
        this.flag = flag;
        this.day = day;
        this.time = time;
        this.start = start;
        //this.goal = goal;
        setTitle("Recommended Path");
        setLocationRelativeTo(null);

        // Main title
        JLabel titleLabel = new JLabel("Recommended Path", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Garamond", Font.BOLD, 30));
        add(titleLabel, BorderLayout.NORTH);

        // Create labels for From, To, Day, Time
        JPanel infoPanel = new JPanel(new GridLayout(4, 1));
        JLabel fromLabel = new JLabel("From: " + start);
        JLabel toLabel = new JLabel("To: " + goal);
        JLabel dayLabel = new JLabel("Day: " + day);
        JLabel timeLabel = new JLabel("Time: " + time);

        infoPanel.add(fromLabel);
        infoPanel.add(toLabel);
        infoPanel.add(dayLabel);
        infoPanel.add(timeLabel);

        // Create the map placeholder
        JPanel mapPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                // For illustration, this will simply draw a rectangle
                // Replace this with the actual map rendering logic
                g.setColor(Color.LIGHT_GRAY);
                g.fillRect(50, 50, 600, 600);
                // You could draw the path here, based on the `path` data
                g.setColor(Color.RED);
                // Example of drawing a path (you should adapt based on your actual path data)
                for (int i = 0; i < path.size(); i++) {
                    g.fillRect(50 + i * 10, 50 + i * 10, 5, 5);
                }
            }
        };
        mapPanel.setPreferredSize(new Dimension(600, 600));
        
        // Create the disclaimers based on the flag
        JPanel disclaimerPanel = new JPanel();
        String disclaimerText = flag == 0 ?
                "Disclaimer: There is no enough data about the safeness of the path generated." :
                "Disclaimer: The safeness of the path generated may not be up to date.";
        JLabel disclaimerLabel = new JLabel(disclaimerText);
        disclaimerPanel.add(disclaimerLabel);

        // Create the back button
        backButton = new JButton("Back");
        JPanel bottomPanel = new JPanel();
        bottomPanel.add(backButton, BorderLayout.WEST);

        // Assemble all components into the layout
        JPanel leftPanel = new JPanel();
        leftPanel.setLayout(new BoxLayout(leftPanel, BoxLayout.Y_AXIS));
        leftPanel.add(infoPanel);
        leftPanel.add(disclaimerPanel);


        // Add everything to the frame
        add(leftPanel, BorderLayout.WEST);
        add(mapPanel, BorderLayout.CENTER);
        add(bottomPanel, BorderLayout.SOUTH);
    }

    // Method to show the frame
    public void display() {
        setVisible(true);
    }

    public void setBackButtonActionListener(ActionListener listener) {
        backButton.addActionListener(listener);
    }
}
