import java.util.*;

public class KPopDMS {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        KPopManager manager = new KPopManager();

        while (true) {
            System.out.println("\nK-Pop Data Management System");
            System.out.println("1. Load Data from File");
            System.out.println("2. Add Group");
            System.out.println("3. View Groups");
            System.out.println("4. Update Group");
            System.out.println("5. Rank Groups");
            System.out.println("6. Delete Group");
            System.out.println("7. Exit");
            System.out.print("Choose an option: ");

            int choice = getValidInteger(scanner);

            switch (choice) {
                case 1:
                    System.out.print("Enter the file path to load data: ");
                    String filePath = scanner.nextLine().trim();
                    boolean loaded = manager.loadFromFile(filePath);
                    System.out.println(loaded ? "File loaded successfully!" : "Error: File not found.");
                    break;

                case 2:
                    KPopGroup newGroup = createGroup(scanner);
                    boolean added = manager.addGroup(newGroup);
                    System.out.println(added ? "Group added successfully!" : "Error: Group already exists.");
                    break;

                case 3:
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

                case 4:
                    System.out.print("Enter group name to update: ");
                    String updateName = scanner.nextLine();
                    KPopGroup updatedGroup = createGroup(scanner);
                    boolean updated = manager.updateGroup(updateName, updatedGroup);
                    System.out.println(updated ? "Updated successfully!" : "Error: Group not found.");
                    break;

                case 5:
                    List<KPopGroup> rankedGroups = manager.rankGroups();
                    if (rankedGroups.isEmpty()) {
                        System.out.println("No groups available to rank.");
                    } else {
                        System.out.println("\nTop K-Pop Groups by Popularity:");
                        for (int i = 0; i < rankedGroups.size(); i++) {
                            System.out.println((i + 1) + ". " + rankedGroups.get(i).name + " - Popularity Score: " + rankedGroups.get(i).popularityScore);
                        }
                    }
                    break;

                case 6:
                    System.out.print("Enter group name to delete: ");
                    String deleteName = scanner.nextLine();
                    boolean deleted = manager.deleteGroup(deleteName);
                    System.out.println(deleted ? "Group deleted successfully!" : "Error: Group not found.");
                    break;

                case 7:
                    System.out.println("Exiting...");
                    scanner.close();
                    return;

                default:
                    System.out.println("Invalid choice! Try again.");
            }
        }
    }

    // Gets user input and builds a KPopGroup object
    private static KPopGroup createGroup(Scanner scanner) {
        System.out.print("Enter group name: ");
        String name = scanner.nextLine().trim();

        System.out.print("Enter debut date (YYYY-MM-DD): ");
        String debutDate = scanner.nextLine().trim();

        System.out.print("Enter members (comma-separated): ");
        List<String> members = Arrays.asList(scanner.nextLine().split(","));

        System.out.print("Enter agency: ");
        String agency = scanner.nextLine().trim();

        System.out.print("Enter latest album: ");
        String latestAlbum = scanner.nextLine().trim();

        System.out.print("Enter status (active/disbanded/hiatus): ");
        String status = scanner.nextLine().trim().toLowerCase();

        int popularityScore = getValidInteger(scanner); // Ensures valid number input

        return new KPopGroup(name, debutDate, members, agency, latestAlbum, status, popularityScore);
    }

    // Loops until a valid number is entered
    private static int getValidInteger(Scanner scanner) {
        while (true) {
            try {
                return Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Invalid input! Enter a valid number.");
            }
        }
    }
}
