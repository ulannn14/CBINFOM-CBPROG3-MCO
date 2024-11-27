package Model;

public class CommentSummary {
    private String username;
    private String commentSummary;

    public CommentSummary(String username, String commentSummary) {
        this.username = username;
        this.commentSummary = commentSummary;
    }

    public void setUsername(String username) { this.username = username; }
    public void setCommentSummary(String cs) { this.commentSummary = cs; } 

    public String getUsername() { return username; }
    public String getCommentSummary() { return commentSummary; }

}
