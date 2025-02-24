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

            int choice;
            try {
                choice = Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Invalid input! Enter a number.");
                continue;
            }

            switch (choice) {
                case 1:
                    System.out.print("Enter the file path to load data: ");
                    String filePath = scanner.nextLine().trim();
                    manager.loadFromFile(filePath);
                    break;
                case 2:
                    manager.addGroup(scanner);
                    break;
                case 3:
                    manager.displayGroups();
                    break;
                case 4:
                    System.out.print("Enter group name to update: ");
                    String updateName = scanner.nextLine();
                    manager.updateGroup(updateName, scanner);
                    break;
                case 5:
                    manager.rankGroups();
                    break;
                case 6:
                    System.out.print("Enter group name to delete: ");
                    String deleteName = scanner.nextLine();
                    manager.deleteGroup(deleteName);
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
}
