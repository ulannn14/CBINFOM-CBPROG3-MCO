package Model;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class IncidentReport {

    String sender;
    String recipient;
    String body;
    String wholeText;
    DateClass dateCreated;

    public void setDateCreated(DateClass dateCreated) {
        this.dateCreated = new dateCreated;
    }


    public IncidentReport(String recipient, String sender, String body) {
        this.sender = sender;
        this.recipient = recipient;
        this.body = body;

        wholeText = "Dear " + recipient + "\n\n" + "          " + body + "\n\n" + "Sincerely,\n" + sender + "\n";
    }

    public String getSender() { return sender; } 
    public void setSender(String sender) { this.sender = sender; }
    public String getRecipient() { return recipient; } 
    public void setRecipient(String recipient) { this.recipient = recipient; }
    public String getBody() { return body; } 
    public void setBody(String body) { this.body = body; }
    public String getWholeText() { return wholeText; } 
    public void setWholeText(String wholeText) { this.wholeText = wholeText; }


    // Method to save the wholeText to a file
    public void saveToFile(String place, String day, String time, int count) {
        String fileName = "Incident Report for " + place + " " + day + " " + time + " " + count;
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(new File(fileName)))) {
            writer.write(wholeText);
            System.out.println("Incident report saved to " + fileName);
        } catch (IOException e) {
            System.out.println("An error occurred while saving the report.");
            e.printStackTrace();
        }
    }

}
