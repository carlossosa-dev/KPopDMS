import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class KPopDMSGUI {
    private JFrame frame;
    private JTable table;
    private DefaultTableModel tableModel;
    private KPopManager manager;

    public KPopDMSGUI() {
        manager = new KPopManager();
        frame = new JFrame("K-Pop Data Management System");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(600, 400);

        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());

        String[] columnNames = {"Name", "Debut Date", "Members", "Agency", "Latest Album", "Status", "Popularity Score"};
        tableModel = new DefaultTableModel(columnNames, 0);
        table = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(table);
        panel.add(scrollPane, BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel();
        JButton addButton = new JButton("Add Group");
        JButton deleteButton = new JButton("Delete Group");
        JButton rankButton = new JButton("Rank Groups");
        JButton loadButton = new JButton("Load Data");
        JButton updateButton = new JButton("Update Group");

        buttonPanel.add(addButton);
        buttonPanel.add(deleteButton);
        buttonPanel.add(rankButton);
        buttonPanel.add(loadButton);
        buttonPanel.add(updateButton);
        panel.add(buttonPanel, BorderLayout.SOUTH);

        addButton.addActionListener(e -> addGroup());
        deleteButton.addActionListener(e -> deleteGroup());
        rankButton.addActionListener(e -> rankGroups());
        loadButton.addActionListener(e -> loadData());
        updateButton.addActionListener(e -> updateGroup());

        frame.add(panel);
        frame.setVisible(true);
    }

    private void addGroup() {
        try {
            JTextField nameField = new JTextField();
            JTextField dateField = new JTextField();
            JTextField membersField = new JTextField();
            JTextField agencyField = new JTextField();
            JTextField albumField = new JTextField();
            JTextField statusField = new JTextField();
            JTextField popularityField = new JTextField();

            JPanel panel = new JPanel(new GridLayout(7, 2));
            panel.add(new JLabel("Name:"));
            panel.add(nameField);
            panel.add(new JLabel("Debut Date:"));
            panel.add(dateField);
            panel.add(new JLabel("Members (comma-separated):"));
            panel.add(membersField);
            panel.add(new JLabel("Agency:"));
            panel.add(agencyField);
            panel.add(new JLabel("Latest Album:"));
            panel.add(albumField);
            panel.add(new JLabel("Status:"));
            panel.add(statusField);
            panel.add(new JLabel("Popularity Score:"));
            panel.add(popularityField);

            int result = JOptionPane.showConfirmDialog(frame, panel, "Add K-Pop Group", JOptionPane.OK_CANCEL_OPTION);
            if (result == JOptionPane.OK_OPTION) {
                String name = nameField.getText();
                String debutDate = dateField.getText();
                List<String> members = List.of(membersField.getText().split(","));
                String agency = agencyField.getText();
                String latestAlbum = albumField.getText();
                String status = statusField.getText();
                int popularityScore = Integer.parseInt(popularityField.getText());

                KPopGroup group = new KPopGroup(name, debutDate, members, agency, latestAlbum, status, popularityScore);
                manager.addGroup(group);
                updateTable();
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(frame, "Invalid popularity score! Please enter a number.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void deleteGroup() {
        String name = JOptionPane.showInputDialog(frame, "Enter group name to delete:");
        if (name != null) {
            manager.deleteGroup(name);
            updateTable();
        }
    }

    private void updateGroup() {
        String name = JOptionPane.showInputDialog(frame, "Enter the group name to update:");
        if (name != null && !name.isEmpty()) {
            JTextField dateField = new JTextField();
            JTextField membersField = new JTextField();
            JTextField agencyField = new JTextField();
            JTextField albumField = new JTextField();
            JTextField statusField = new JTextField();
            JTextField popularityField = new JTextField();

            JPanel panel = new JPanel(new GridLayout(6, 2));
            panel.add(new JLabel("Debut Date:"));
            panel.add(dateField);
            panel.add(new JLabel("Members (comma-separated):"));
            panel.add(membersField);
            panel.add(new JLabel("Agency:"));
            panel.add(agencyField);
            panel.add(new JLabel("Latest Album:"));
            panel.add(albumField);
            panel.add(new JLabel("Status:"));
            panel.add(statusField);
            panel.add(new JLabel("Popularity Score:"));
            panel.add(popularityField);

            int result = JOptionPane.showConfirmDialog(frame, panel, "Update K-Pop Group", JOptionPane.OK_CANCEL_OPTION);
            if (result == JOptionPane.OK_OPTION) {
                try {
                    List<String> members = List.of(membersField.getText().split(","));
                    int popularityScore = Integer.parseInt(popularityField.getText());
                    KPopGroup updatedGroup = new KPopGroup(name, dateField.getText(), members, agencyField.getText(), albumField.getText(), statusField.getText(), popularityScore);
                    manager.updateGroup(name, updatedGroup);
                    updateTable();
                } catch (NumberFormatException e) {
                    JOptionPane.showMessageDialog(frame, "Invalid popularity score! Please enter a number.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        }
    }

    private void rankGroups() {
        List<KPopGroup> rankedGroups = manager.rankGroups();
        tableModel.setRowCount(0);
        for (KPopGroup group : rankedGroups) {
            tableModel.addRow(new Object[]{group.name, group.debutDate, String.join(", ", group.members), group.agency, group.latestAlbum, group.status, group.popularityScore});
        }
    }

    private void loadData() {
        String filePath = JOptionPane.showInputDialog(frame, "Enter file path:");
        if (filePath != null && !filePath.trim().isEmpty()) {
            if (manager.loadFromFile(filePath)) {
                updateTable();
            } else {
                JOptionPane.showMessageDialog(frame, "Error loading file! Check the file path.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void updateTable() {
        tableModel.setRowCount(0);
        for (KPopGroup group : manager.getGroups()) {
            tableModel.addRow(new Object[]{group.name, group.debutDate, String.join(", ", group.members), group.agency, group.latestAlbum, group.status, group.popularityScore});
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(KPopDMSGUI::new);
    }
}
