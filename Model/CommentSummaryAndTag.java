package Model;

public class CommentSummaryAndTag {
    private String username;
    private String CSorTag;
    private int CSorTagID;

    public CommentSummaryAndTag(String username, String CSorTag) {
        this.username = username;
        this.CSorTag = CSorTag;
        
    }

    public void setUsername(String username) { this.username = username; }
    public void setCommentSummaryOrTag(String cs) { this.CSorTag = cs; } 
    public void CSorTagID(int CSorTagID) { this.CSorTagID = CSorTagID; } 
    
    public String getUsername() { return username; }
    public String getCSorTag() { return CSorTag; }
    public int getCSorTag() { return CSorTagID; }

}
