import java.sql.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

// Handles all database operations for K-Pop group data
public class KPopDatabaseManager {
    private Connection connection; // Active connection to the SQLite database

    // Attempts to connect to the database using the given file path
    public boolean connect(String dbPath) {
        try {
            connection = DriverManager.getConnection("jdbc:sqlite:" + dbPath);
            return true;
        } catch (SQLException e) {
            System.out.println("Error connecting to database: " + e.getMessage());
            return false;
        }
    }

    // Retrieves all groups from the database
    public List<KPopGroup> getGroups() {
        List<KPopGroup> groups = new ArrayList<>();
        String sql = "SELECT * FROM groups";

        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                String name = rs.getString("name");
                String debutDate = rs.getString("debut_date");
                List<String> members = Arrays.asList(rs.getString("members").split(","));
                String agency = rs.getString("agency");
                String latestAlbum = rs.getString("latest_album");
                String status = rs.getString("status");
                int popularityScore = rs.getInt("popularity_score");

                groups.add(new KPopGroup(name, debutDate, members, agency, latestAlbum, status, popularityScore));
            }

        } catch (SQLException e) {
            System.out.println("Error loading groups: " + e.getMessage());
        }

        return groups;
    }

    // Inserts a new group into the database
    public boolean addGroup(KPopGroup group) {
        String sql = "INSERT INTO groups (name, debut_date, members, agency, latest_album, status, popularity_score) VALUES (?, ?, ?, ?, ?, ?, ?)";

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, group.name);
            stmt.setString(2, group.debutDate);
            stmt.setString(3, String.join(",", group.members));
            stmt.setString(4, group.agency);
            stmt.setString(5, group.latestAlbum);
            stmt.setString(6, group.status);
            stmt.setInt(7, group.popularityScore);
            stmt.executeUpdate();
            return true;
        } catch (SQLException e) {
            System.out.println("Error adding group: " + e.getMessage());
            return false;
        }
    }

    // Updates a group's details by name
    public boolean updateGroup(String name, KPopGroup updatedGroup) {
        String sql = "UPDATE groups SET debut_date = ?, members = ?, agency = ?, latest_album = ?, status = ?, popularity_score = ? WHERE name = ?";

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, updatedGroup.debutDate);
            stmt.setString(2, String.join(",", updatedGroup.members));
            stmt.setString(3, updatedGroup.agency);
            stmt.setString(4, updatedGroup.latestAlbum);
            stmt.setString(5, updatedGroup.status);
            stmt.setInt(6, updatedGroup.popularityScore);
            stmt.setString(7, name);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            System.out.println("Error updating group: " + e.getMessage());
            return false;
        }
    }

    // Deletes a group from the database by name
    public boolean deleteGroup(String name) {
        String sql = "DELETE FROM groups WHERE name = ?";

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, name);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            System.out.println("Error deleting group: " + e.getMessage());
            return false;
        }
    }

    // Returns groups sorted by popularity (highest first)
    public List<KPopGroup> rankGroups() {
        List<KPopGroup> ranked = new ArrayList<>();
        String sql = "SELECT * FROM groups ORDER BY popularity_score DESC";

        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                String name = rs.getString("name");
                String debutDate = rs.getString("debut_date");
                List<String> members = Arrays.asList(rs.getString("members").split(","));
                String agency = rs.getString("agency");
                String latestAlbum = rs.getString("latest_album");
                String status = rs.getString("status");
                int popularityScore = rs.getInt("popularity_score");

                ranked.add(new KPopGroup(name, debutDate, members, agency, latestAlbum, status, popularityScore));
            }

        } catch (SQLException e) {
            System.out.println("Error ranking groups: " + e.getMessage());
        }

        return ranked;
    }
}
