import java.io.*;
import java.util.*;

public class KPopManager {
    private List<KPopGroup> groups = new ArrayList<>();

    // Loads group data from a file
    public boolean loadFromFile(String filePath) {
        File file = new File(filePath);
        if (!file.exists()) {
            return false;
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
            return true;
        } catch (IOException | NumberFormatException e) {
            return false;
        }
    }

    // Adds a new group if it doesn't already exist
    public boolean addGroup(KPopGroup newGroup) {
        for (KPopGroup existing : groups) {
            if (existing.name.equalsIgnoreCase(newGroup.name)) {
                return false;
            }
        }
        groups.add(newGroup);
        return true;
    }

    // Returns a list of all groups
    public List<KPopGroup> getGroups() {
        return new ArrayList<>(groups);
    }

    // Updates an existing group
    public boolean updateGroup(String name, KPopGroup updatedGroup) {
        for (int i = 0; i < groups.size(); i++) {
            if (groups.get(i).name.equalsIgnoreCase(name)) {
                groups.set(i, updatedGroup);
                return true;
            }
        }
        return false;
    }

    // Deletes a group if found
    public boolean deleteGroup(String name) {
        for (KPopGroup group : groups) {
            if (group.name.equalsIgnoreCase(name)) {
                groups.remove(group);
                return true;
            }
        }
        return false;
    }

    // Returns groups ranked by popularity (highest first)
    public List<KPopGroup> rankGroups() {
        if (groups.isEmpty()) return Collections.emptyList();
        List<KPopGroup> sortedGroups = new ArrayList<>(groups);
        sortedGroups.sort((g1, g2) -> Integer.compare(g2.popularityScore, g1.popularityScore));
        return sortedGroups;
    }
}
