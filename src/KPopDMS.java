import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

// Command-line version of the K-Pop Data Management System
public class KPopDMS {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        KPopDatabaseManager manager = new KPopDatabaseManager();

        // Prompt user for the SQLite database path
        System.out.print("Enter path to SQLite database file: ");
        String dbPath = scanner.nextLine().trim();
        boolean connected = manager.connect(dbPath);
        if (!connected) {
            System.out.println("Failed to connect. Exiting...");
            scanner.close();
            return;
        }

        // Main menu loop
        while (true) {
            System.out.println("\nK-Pop Data Management System");
            System.out.println("1. Add Group");
            System.out.println("2. View Groups");
            System.out.println("3. Update Group");
            System.out.println("4. Rank Groups");
            System.out.println("5. Delete Group");
            System.out.println("6. Exit");
            System.out.print("Choose an option: ");

            int choice = getValidInteger(scanner);

            switch (choice) {
                case 1:
                    // Add new group to database
                    KPopGroup newGroup = createGroup(scanner);
                    boolean added = manager.addGroup(newGroup);
                    System.out.println(added ? "Group added successfully!" : "Error: Group already exists.");
                    break;

                case 2:
                    // Display all groups
                    List<KPopGroup> groups = manager.getGroups();
                    if (groups.isEmpty()) {
                        System.out.println("No groups available.");
                    } else {
                        for (KPopGroup group : groups) {
                            System.out.println(group);
                            System.out.println("------------------------");
                        }
                    }
                    break;

                case 3:
                    // Update an existing group by name
                    System.out.print("Enter group name to update: ");
                    String updateName = scanner.nextLine();
                    KPopGroup updatedGroup = createGroup(scanner);
                    boolean updated = manager.updateGroup(updateName, updatedGroup);
                    System.out.println(updated ? "Updated successfully!" : "Error: Group not found.");
                    break;

                case 4:
                    // Show ranked groups by popularity
                    List<KPopGroup> rankedGroups = manager.rankGroups();
                    if (rankedGroups.isEmpty()) {
                        System.out.println("No groups available to rank.");
                    } else {
                        System.out.println("\nTop K-Pop Groups by Popularity:");
                        for (int i = 0; i < rankedGroups.size(); i++) {
                            System.out.println((i + 1) + ". " + rankedGroups.get(i).name +
                                    " - Popularity Score: " + rankedGroups.get(i).popularityScore);
                        }
                    }
                    break;

                case 5:
                    // Delete a group by name
                    System.out.print("Enter group name to delete: ");
                    String deleteName = scanner.nextLine();
                    boolean deleted = manager.deleteGroup(deleteName);
                    System.out.println(deleted ? "Group deleted successfully!" : "Error: Group not found.");
                    break;

                case 6:
                    // Exit the application
                    System.out.println("Exiting...");
                    scanner.close();
                    return;

                default:
                    System.out.println("Invalid choice! Try again.");
            }
        }
    }

    // Prompts the user to enter all details and builds a KPopGroup object
    private static KPopGroup createGroup(Scanner scanner) {
        System.out.print("Enter group name: ");
        String name = scanner.nextLine().trim();

        // Validate proper date format
        String debutDate;
        while (true) {
            System.out.print("Enter debut date (YYYY-MM-DD): ");
            debutDate = scanner.nextLine().trim();
            if (isValidDate(debutDate)) break;
            System.out.println("Invalid date format! Please use YYYY-MM-DD.");
        }

        System.out.print("Enter members (comma-separated): ");
        List<String> members = Arrays.asList(scanner.nextLine().split(","));

        System.out.print("Enter agency: ");
        String agency = scanner.nextLine().trim();

        System.out.print("Enter latest album: ");
        String latestAlbum = scanner.nextLine().trim();

        // Ensure status is one of the accepted values
        String status;
        while (true) {
            System.out.print("Enter status (active/disbanded/hiatus): ");
            status = scanner.nextLine().trim().toLowerCase();
            if (status.equals("active") || status.equals("disbanded") || status.equals("hiatus")) break;
            System.out.println("Invalid status! Must be: active, disbanded, or hiatus.");
        }

        System.out.print("Enter popularity score (number): ");
        int popularityScore = getValidInteger(scanner);

        return new KPopGroup(name, debutDate, members, agency, latestAlbum, status, popularityScore);
    }

    // Ensures user inputs a valid integer (no letters, decimals, etc.)
    private static int getValidInteger(Scanner scanner) {
        while (true) {
            try {
                return Integer.parseInt(scanner.nextLine().trim());
            } catch (NumberFormatException e) {
                System.out.println("Invalid input! Enter a valid number.");
            }
        }
    }

    // Validates if input matches strict YYYY-MM-DD format
    private static boolean isValidDate(String dateStr) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        sdf.setLenient(false);
        try {
            sdf.parse(dateStr);
            return true;
        } catch (ParseException e) {
            return false;
        }
    }
}
