import java.util.List;

// Represents a K-Pop group and its key details
public class KPopGroup {
    public String name;
    public String debutDate;
    public List<String> members;
    public String agency;
    public String latestAlbum;
    public String status;
    public int popularityScore;

    // Initializes a group with all its info
    public KPopGroup(String name, String debutDate, List<String> members,
                     String agency, String latestAlbum, String status, int popularityScore) {
        this.name = name;
        this.debutDate = debutDate;
        this.members = members;
        this.agency = agency;
        this.latestAlbum = latestAlbum;
        this.status = status;
        this.popularityScore = popularityScore;
    }

    // Displays group details in a readable format
    @Override
    public String toString() {
        return "Name: " + name + "\n" +
                "Debut Date: " + debutDate + "\n" +
                "Members: " + String.join(", ", members) + "\n" +
                "Agency: " + agency + "\n" +
                "Latest Album: " + latestAlbum + "\n" +
                "Status: " + status + "\n" +
                "Popularity Score: " + popularityScore + "\n";
    }
}
