import java.util.List;

/**
 * Represents a K-Pop group and its key information.
 * This class holds all relevant data for a group such as debut date, members, agency, and popularity.
 */
public class KPopGroup {
    public String name;
    public String debutDate;
    public List<String> members;
    public String agency;
    public String latestAlbum;
    public String status;
    public int popularityScore;

    /**
     * Creates a new KPopGroup object with all the necessary group info.
     *
     * @param name the name of the group
     * @param debutDate the debut date in YYYY-MM-DD format
     * @param members list of member names
     * @param agency the agency managing the group
     * @param latestAlbum the latest album released by the group
     * @param status current status of the group (active, disbanded, hiatus)
     * @param popularityScore a number representing how popular the group is
     */
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

    /**
     * Returns a formatted string showing all group details.
     *
     * @return string with group info
     */
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
