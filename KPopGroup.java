import java.util.*;

public class KPopGroup {
    // Stores basic details of a K-Pop group
    String name;
    String debutDate;
    List<String> members;
    String agency;
    String latestAlbum;
    String status;
    int popularityScore;

    public KPopGroup(String name, String debutDate, List<String> members, String agency, String latestAlbum, String status, int popularityScore) {
        this.name = name;
        this.debutDate = debutDate;
        this.members = members;
        this.agency = agency;
        this.latestAlbum = latestAlbum;
        this.status = status;
        this.popularityScore = popularityScore;
    }

    // Formats group details when printed
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
