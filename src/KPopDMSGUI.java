import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;

/**
 * Graphical version of the K-Pop Data Management System.
 * Allows users to view, add, update, delete, and rank K-Pop groups using a Swing-based interface.
 */
public class KPopDMSGUI {
    private JFrame frame;
    private JTable table;
    private DefaultTableModel tableModel;
    private KPopDatabaseManager manager;

    /**
     * Launches the GUI, connects to the database, and builds the interface.
     */
    public KPopDMSGUI() {
        manager = new KPopDatabaseManager();

        String dbPath = JOptionPane.showInputDialog(null, "Enter path to SQLite database file:");
        if (dbPath == null || dbPath.trim().isEmpty() || !manager.connect(dbPath)) {
            JOptionPane.showMessageDialog(null, "Failed to connect to the database.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        frame = new JFrame("K-Pop Data Management System");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(700, 500);

        JPanel panel = new JPanel(new BorderLayout());

        String[] columnNames = {"Name", "Debut Date", "Members", "Agency", "Latest Album", "Status", "Popularity"};
        tableModel = new DefaultTableModel(columnNames, 0);
        table = new JTable(tableModel);
        panel.add(new JScrollPane(table), BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel();
        JButton addButton = new JButton("Add Group");
        JButton updateButton = new JButton("Update Group");
        JButton deleteButton = new JButton("Delete Group");
        JButton rankButton = new JButton("Rank Groups");

        buttonPanel.add(addButton);
        buttonPanel.add(updateButton);
        buttonPanel.add(deleteButton);
        buttonPanel.add(rankButton);
        panel.add(buttonPanel, BorderLayout.SOUTH);

        addButton.addActionListener(e -> addGroup());
        updateButton.addActionListener(e -> updateGroup());
        deleteButton.addActionListener(e -> deleteGroup());
        rankButton.addActionListener(e -> rankGroups());

        frame.add(panel);
        updateTable();
        frame.setVisible(true);
    }

    /**
     * Updates the table to display the current list of K-Pop groups.
     */
    private void updateTable() {
        tableModel.setRowCount(0);
        for (KPopGroup group : manager.getGroups()) {
            tableModel.addRow(new Object[]{
                    group.name,
                    group.debutDate,
                    String.join(", ", group.members),
                    group.agency,
                    group.latestAlbum,
                    group.status,
                    group.popularityScore
            });
        }
    }

    /**
     * Opens a form to collect new group data and adds the group to the database.
     */
    private void addGroup() {
        JTextField nameField = new JTextField();
        JTextField dateField = new JTextField();
        JTextField membersField = new JTextField();
        JTextField agencyField = new JTextField();
        JTextField albumField = new JTextField();
        JTextField statusField = new JTextField();
        JTextField popularityField = new JTextField();

        JPanel inputPanel = new JPanel(new GridLayout(7, 2));
        inputPanel.add(new JLabel("Name:")); inputPanel.add(nameField);
        inputPanel.add(new JLabel("Debut Date (YYYY-MM-DD):")); inputPanel.add(dateField);
        inputPanel.add(new JLabel("Members (comma-separated):")); inputPanel.add(membersField);
        inputPanel.add(new JLabel("Agency:")); inputPanel.add(agencyField);
        inputPanel.add(new JLabel("Latest Album:")); inputPanel.add(albumField);
        inputPanel.add(new JLabel("Status (active/disbanded/hiatus):")); inputPanel.add(statusField);
        inputPanel.add(new JLabel("Popularity Score:")); inputPanel.add(popularityField);

        int result = JOptionPane.showConfirmDialog(frame, inputPanel, "Add Group", JOptionPane.OK_CANCEL_OPTION);
        if (result == JOptionPane.OK_OPTION) {
            try {
                String name = nameField.getText().trim();
                String debutDate = dateField.getText().trim();
                if (!isValidDate(debutDate)) throw new Exception("Invalid date format.");
                String status = statusField.getText().trim().toLowerCase();
                if (!isValidStatus(status)) throw new Exception("Invalid status.");
                int popularity = Integer.parseInt(popularityField.getText().trim());
                List<String> members = List.of(membersField.getText().split(","));

                KPopGroup group = new KPopGroup(name, debutDate, members,
                        agencyField.getText().trim(),
                        albumField.getText().trim(),
                        status, popularity);

                if (manager.addGroup(group)) {
                    updateTable();
                } else {
                    showError("Failed to add group. It may already exist.");
                }
            } catch (Exception ex) {
                showError("Error: " + ex.getMessage());
            }
        }
    }

    /**
     * Opens a form to update an existing group's data in the database.
     */
    private void updateGroup() {
        String name = JOptionPane.showInputDialog(frame, "Enter name of group to update:");
        if (name == null || name.trim().isEmpty()) return;

        JTextField dateField = new JTextField();
        JTextField membersField = new JTextField();
        JTextField agencyField = new JTextField();
        JTextField albumField = new JTextField();
        JTextField statusField = new JTextField();
        JTextField popularityField = new JTextField();

        JPanel inputPanel = new JPanel(new GridLayout(6, 2));
        inputPanel.add(new JLabel("Debut Date (YYYY-MM-DD):")); inputPanel.add(dateField);
        inputPanel.add(new JLabel("Members (comma-separated):")); inputPanel.add(membersField);
        inputPanel.add(new JLabel("Agency:")); inputPanel.add(agencyField);
        inputPanel.add(new JLabel("Latest Album:")); inputPanel.add(albumField);
        inputPanel.add(new JLabel("Status (active/disbanded/hiatus):")); inputPanel.add(statusField);
        inputPanel.add(new JLabel("Popularity Score:")); inputPanel.add(popularityField);

        int result = JOptionPane.showConfirmDialog(frame, inputPanel, "Update Group", JOptionPane.OK_CANCEL_OPTION);
        if (result == JOptionPane.OK_OPTION) {
            try {
                String debutDate = dateField.getText().trim();
                if (!isValidDate(debutDate)) throw new Exception("Invalid date format.");
                String status = statusField.getText().trim().toLowerCase();
                if (!isValidStatus(status)) throw new Exception("Invalid status.");
                int popularity = Integer.parseInt(popularityField.getText().trim());
                List<String> members = List.of(membersField.getText().split(","));

                KPopGroup updated = new KPopGroup(name, debutDate, members,
                        agencyField.getText().trim(),
                        albumField.getText().trim(),
                        status, popularity);

                if (manager.updateGroup(name, updated)) {
                    updateTable();
                } else {
                    showError("Group not found or update failed.");
                }
            } catch (Exception ex) {
                showError("Error: " + ex.getMessage());
            }
        }
    }

    /**
     * Deletes a group from the database after asking for the group's name.
     */
    private void deleteGroup() {
        String name = JOptionPane.showInputDialog(frame, "Enter name of group to delete:");
        if (name != null && !name.trim().isEmpty()) {
            if (manager.deleteGroup(name.trim())) {
                updateTable();
            } else {
                showError("Group not found or failed to delete.");
            }
        }
    }

    /**
     * Displays the list of groups sorted by popularity.
     */
    private void rankGroups() {
        tableModel.setRowCount(0);
        for (KPopGroup group : manager.rankGroups()) {
            tableModel.addRow(new Object[]{
                    group.name,
                    group.debutDate,
                    String.join(", ", group.members),
                    group.agency,
                    group.latestAlbum,
                    group.status,
                    group.popularityScore
            });
        }
    }

    /**
     * Validates that a string is a valid date in YYYY-MM-DD format.
     *
     * @param dateStr the date string to check
     * @return true if valid, false otherwise
     */
    private boolean isValidDate(String dateStr) {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            sdf.setLenient(false);
            sdf.parse(dateStr);
            return true;
        } catch (ParseException e) {
            return false;
        }
    }

    /**
     * Checks if the given status is valid.
     *
     * @param status the status to validate
     * @return true if the status is active, disbanded, or hiatus
     */
    private boolean isValidStatus(String status) {
        return status.equals("active") || status.equals("disbanded") || status.equals("hiatus");
    }

    /**
     * Shows an error message popup with the provided message.
     *
     * @param msg the error message to show
     */
    private void showError(String msg) {
        JOptionPane.showMessageDialog(frame, msg, "Error", JOptionPane.ERROR_MESSAGE);
    }

    /**
     * Starts the GUI application.
     *
     * @param args unused command-line arguments
     */
    public static void main(String[] args) {
        SwingUtilities.invokeLater(KPopDMSGUI::new);
    }
}
