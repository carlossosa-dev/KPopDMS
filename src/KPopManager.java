import java.io.*;
import java.util.*;
import java.util.regex.Pattern;

public class KPopManager {
    private List<KPopGroup> groups = new ArrayList<>();

    // Checks if there are any groups in the list
    public boolean hasGroups() {
        return !groups.isEmpty();
    }

    // Reads K-Pop groups from a file and loads them into memory
    public void loadFromFile(String filePath) {
        File file = new File(filePath);
        if (!file.exists()) {
            System.out.println("Error: File not found. Please enter a valid file path.");
            return;
        }
        try (Scanner scanner = new Scanner(file)) {
            groups.clear();
            while (scanner.hasNextLine()) {
                String[] data = scanner.nextLine().split(",");
                if (data.length < 7) continue;
                List<String> members = Arrays.asList(data[2].split("\\|"));
                int popularityScore = Integer.parseInt(data[6]);
                groups.add(new KPopGroup(data[0], data[1], members, data[3], data[4], data[5], popularityScore));
            }
            System.out.println("File loaded successfully!");
        } catch (IOException e) {
            System.out.println("Error reading file: " + e.getMessage());
        }
    }

    // Adds a new K-Pop group after validating user input
    public void addGroup(Scanner scanner) {
        System.out.print("Enter group name: ");
        String name = scanner.nextLine().trim();
        for (KPopGroup existing : groups) {
            if (existing.name.equalsIgnoreCase(name)) {
                System.out.println("Error: Group already exists (Duplicate entry). Try again.");
                return;
            }
        }

        String debutDate;
        while (true) {
            System.out.print("Enter debut date (YYYY-MM-DD): ");
            debutDate = scanner.nextLine().trim();
            if (Pattern.matches("\\d{4}-\\d{2}-\\d{2}", debutDate)) break;
            System.out.println("Invalid date format. Please use YYYY-MM-DD.");
        }

        System.out.print("Enter members (comma-separated): ");
        List<String> members = Arrays.asList(scanner.nextLine().split(","));

        System.out.print("Enter agency: ");
        String agency = scanner.nextLine().trim();

        System.out.print("Enter latest album: ");
        String latestAlbum = scanner.nextLine().trim();

        String status;
        while (true) {
            System.out.print("Enter status (active/disbanded/hiatus): ");
            status = scanner.nextLine().trim().toLowerCase();
            if (status.equals("active") || status.equals("disbanded") || status.equals("hiatus")) break;
            System.out.println("Invalid status. Please enter 'active', 'disbanded', or 'hiatus'.");
        }

        int popularityScore;
        while (true) {
            System.out.print("Enter popularity score (0-100): ");
            try {
                popularityScore = Integer.parseInt(scanner.nextLine());
                if (popularityScore >= 0 && popularityScore <= 100) break;
            } catch (NumberFormatException ignored) {}
            System.out.println("Invalid score. Enter a number between 0 and 100.");
        }

        groups.add(new KPopGroup(name, debutDate, members, agency, latestAlbum, status, popularityScore));
        System.out.println("Group added successfully!");
    }

    // Displays all stored groups
    public void displayGroups() {
        if (groups.isEmpty()) {
            System.out.println("No groups available.");
            return;
        }
        for (KPopGroup group : groups) {
            System.out.println(group);
            System.out.println("------------------------");
        }
    }

    // Updates an existing group
    public void updateGroup(String name, Scanner scanner) {
        for (KPopGroup group : groups) {
            if (group.name.equalsIgnoreCase(name)) {
                System.out.println("Updating group: " + name);
                groups.remove(group);
                addGroup(scanner);
                System.out.println("Updated successfully!");
                return;
            }
        }
        System.out.println("Error: Group not found.");
    }

    // Deletes a group from the list
    public void deleteGroup(String name) {
        if (groups.isEmpty()) {
            System.out.println("Error: No groups available to delete.");
            return;
        }

        boolean removed = groups.removeIf(group -> group.name.equalsIgnoreCase(name));

        if (removed) {
            System.out.println("Group deleted successfully!");
        } else {
            System.out.println("Error: Group not found.");
        }
    }

    // Ranks groups by popularity score
    public void rankGroups() {
        if (groups.isEmpty()) {
            System.out.println("No groups available to rank.");
            return;
        }
        groups.sort((g1, g2) -> Integer.compare(g2.popularityScore, g1.popularityScore));
        System.out.println("\nTop K-Pop Groups by Popularity:");
        for (int i = 0; i < groups.size(); i++) {
            System.out.println((i + 1) + ". " + groups.get(i).name + " - Popularity Score: " + groups.get(i).popularityScore);
        }
    }
}
