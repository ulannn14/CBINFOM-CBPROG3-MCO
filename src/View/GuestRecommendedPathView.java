package View;
/*
public class GuestRecommendedPathView extends FrameCanvas {

    private int disclaimerFlag = 0; // 0 or 1 to toggle disclaimer text
    private String from = "Location A"; // Placeholder location for "From"
    private String to = "Location B"; // Placeholder location for "To"
    private String day = "Monday"; // Placeholder day
    private String time = "10:00 AM"; // Placeholder time

    public GuestRecommendedPathView(ArrayList<Integer> path, int flag, String day, String time, String start, String goal) {
        super();

        if (flag == 0)
            setTitle("Shortest Path");
        else 
            setTitle("Safest Path");

        panel.setLayout(new BorderLayout());

        // Top panel
        JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        topPanel.add(new JLabel("From: " + from)); // Displaying actual "From"
        topPanel.add(new JLabel("To: " + to)); // Displaying actual "To"
        topPanel.add(new JLabel("Day: " + day)); // Displaying actual "Day"
        topPanel.add(new JLabel("Time: " + time)); // Displaying actual "Time"

        panel.add(topPanel, BorderLayout.NORTH);

        // Disclaimer panel on the left
        JPanel disclaimerPanel = new JPanel();
        disclaimerPanel.setLayout(new BoxLayout(disclaimerPanel, BoxLayout.Y_AXIS));
        JLabel disclaimerLabel = new JLabel(getDisclaimerText());
        disclaimerLabel.setForeground(Color.RED);
        disclaimerLabel.setHorizontalAlignment(SwingConstants.LEFT);
        disclaimerPanel.add(disclaimerLabel);
        panel.add(disclaimerPanel, BorderLayout.WEST);

        // Map panel in the center (no scrollable map)
        panel.add(drawMap(lineData), BorderLayout.CENTER);

        // Bottom panel
        JPanel bottomPanel = new JPanel(new BorderLayout());
        JButton backButton = new JButton("Back");
        bottomPanel.add(backButton, BorderLayout.WEST);
        panel.add(bottomPanel, BorderLayout.SOUTH);

        frameSetVisible();
    }

    private String getDisclaimerText() {
        return disclaimerFlag == 0
                ? "Disclaimer: There is no enough data about the safeness of the path generated."
                : "Disclaimer: The safeness of the path generated may not be up to date.";
    }

    private JPanel drawMap(Object[][] lineData) {
        JPanel mapPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                ImageIcon icon = new ImageIcon("C:\\Users\\Axl Roel\\Documents\\CBPROG3 MP\\MAP\\maps.png");
                g.drawImage(icon.getImage(), 0, 0, getWidth(), getHeight(), this);
                Graphics2D g2d = (Graphics2D) g;

                // Enable anti-aliasing
                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

                // Draw lines with labels from lineData
                for (int i = 0; i < lineData.length; i++) {
                    int x1 = (int) lineData[i][0];
                    int y1 = (int) lineData[i][1];
                    int x2 = (int) lineData[i][2];
                    int y2 = (int) lineData[i][3];
                    String name = (String) lineData[i][4];

                    // Draw the line
                    g2d.setStroke(new BasicStroke(5));
                    g2d.setColor(Color.BLACK);
                    g2d.drawLine(x1, y1, x2, y2);

                    // Midpoint of the line for label placement
                    int midX = (x1 + x2) / 2;
                    int midY = (y1 + y2) / 2;

                    // Text width and height
                    FontMetrics metrics = g2d.getFontMetrics();
                    int textWidth = metrics.stringWidth(name);
                    int textHeight = metrics.getHeight();

                    // Position the label
                    int textX = midX - (textWidth / 2);
                    int textY = midY + (textHeight / 4);

                    // Rotate text to align with the line
                    double angle = Math.atan2(y2 - y1, x2 - x1);
                    g2d.setColor(Color.RED);
                    g2d.rotate(angle, midX, midY);
                    g2d.drawString(name, textX, textY);
                    g2d.rotate(-angle, midX, midY); // Reset rotation
                }
            }
        };

        // Set preferred size of the panel to fit the map
        mapPanel.setMaximumSize(new Dimension(400, 400));
		mapPanel.setMinimumSize(new Dimension(400, 400));
		mapPanel.setPreferredSize(new Dimension(400, 400));
        mapPanel.setBackground(Color.WHITE);

        return mapPanel;
    }
}

}*/