import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;


public class GuestPanel extends JPanel {

    private JTextField idField;
    private JTextField nameField;
    private JTextField phoneField;
    private JTextField addressField;
    private JTextField spouseField;
    private JTextField giftDetailsField;
    private JTextField weddingIdField;

    private JComboBox<String> genderBox;
    private JComboBox<String> relationBox;
    private JComboBox<String> giftTypeBox;

    private JTextField searchField;

    private JTable table;
    private DefaultTableModel model;

    private GuestManager manager;

    public GuestPanel() {

        setBackground(new Color(255, 240, 245));

        manager = new GuestManager();

        setLayout(new BorderLayout(10, 10));

        createForm();
        createTable();
        createButtons();
    }

    private void createForm() {

        JPanel mainTop = new JPanel(new BorderLayout());

        JPanel form = new JPanel(new GridLayout(4, 4, 5, 5));

        idField = new JTextField();
        nameField = new JTextField();
        phoneField = new JTextField();
        addressField = new JTextField();
        spouseField = new JTextField();
        giftDetailsField = new JTextField();
        weddingIdField = new JTextField();

        genderBox = new JComboBox<>(
                new String[]{
                        "Male",
                        "Female",
                        "Other"
                }
        );

        relationBox = new JComboBox<>(
                new String[]{
                        "Husband",
                        "Wife",
                        "Father",
                        "Mother",
                        "Brother",
                        "Sister",
                        "Friend",
                        "Relative",
                        "Other"
                }
        );

        giftTypeBox = new JComboBox<>(
                new String[]{
                        "Nothing",
                        "Money",
                        "Gift",
                        "Both"
                }
        );

        form.add(new JLabel("Guest ID"));
        form.add(idField);

        form.add(new JLabel("Guest Name"));
        form.add(nameField);

        form.add(new JLabel("Phone"));
        form.add(phoneField);

        form.add(new JLabel("Address"));
        form.add(addressField);

        form.add(new JLabel("Gender"));
        form.add(genderBox);

        form.add(new JLabel("Relation"));
        form.add(relationBox);

        form.add(new JLabel("Spouse Name"));
        form.add(spouseField);

        form.add(new JLabel("Gift Type"));
        form.add(giftTypeBox);

        form.add(new JLabel("Gift Details"));
        form.add(giftDetailsField);

        form.add(new JLabel("Wedding ID"));
        form.add(weddingIdField);

        mainTop.add(form, BorderLayout.CENTER);

        JPanel searchPanel = new JPanel(
                new FlowLayout(FlowLayout.RIGHT)
        );

        searchField = new JTextField(20);

        searchPanel.add(new JLabel("Search Guest:"));
        searchPanel.add(searchField);

        mainTop.add(searchPanel, BorderLayout.SOUTH);

        add(mainTop, BorderLayout.NORTH);

        searchField.addKeyListener(
                new java.awt.event.KeyAdapter() {

                    public void keyReleased(
                            java.awt.event.KeyEvent e) {

                        searchGuest();
                    }
                }
        );
    }

    private void createTable() {

        String[] columns = {

                "Guest ID",
                "Name",
                "Phone",
                "Gender",
                "Relation",
                "Spouse",
                "Gift Type",
                "Gift Details",
                "Wedding ID"
        };

        model = new DefaultTableModel(columns, 0) {

            public boolean isCellEditable(
                    int row,
                    int column) {

                return false;
            }
        };

        table = new JTable(model);

        table.setRowHeight(28);

        table.setAutoCreateRowSorter(true);

        add(new JScrollPane(table), BorderLayout.CENTER);

        table.getSelectionModel()
                .addListSelectionListener(e -> {

                    int row = table.getSelectedRow();

                    if (row != -1) {

                        idField.setText(
                                model.getValueAt(row, 0).toString()
                        );

                        nameField.setText(
                                model.getValueAt(row, 1).toString()
                        );

                        phoneField.setText(
                                model.getValueAt(row, 2).toString()
                        );

                        genderBox.setSelectedItem(
                                model.getValueAt(row, 3).toString()
                        );

                        relationBox.setSelectedItem(
                                model.getValueAt(row, 4).toString()
                        );

                        spouseField.setText(
                                model.getValueAt(row, 5).toString()
                        );

                        giftTypeBox.setSelectedItem(
                                model.getValueAt(row, 6).toString()
                        );

                        giftDetailsField.setText(
                                model.getValueAt(row, 7).toString()
                        );

                        weddingIdField.setText(
                                model.getValueAt(row, 8).toString()
                        );

                        idField.setEditable(false);
                    }
                });
    }

    private void createButtons() {

        JPanel buttonPanel = new JPanel(
                new FlowLayout()
        );

        JButton addButton = new JButton("Add");
        JButton updateButton = new JButton("Update");
        JButton deleteButton = new JButton("Delete");
        JButton searchButton = new JButton("Search");
        JButton clearButton = new JButton("Clear");

        buttonPanel.add(addButton);
        buttonPanel.add(updateButton);
        buttonPanel.add(deleteButton);
        buttonPanel.add(searchButton);
        buttonPanel.add(clearButton);

        add(buttonPanel, BorderLayout.SOUTH);

        addButton.addActionListener(
                e -> addGuest()
        );

        updateButton.addActionListener(
                e -> updateGuest()
        );

        deleteButton.addActionListener(
                e -> deleteGuest()
        );

        searchButton.addActionListener(
                e -> searchGuest()
        );

        clearButton.addActionListener(
                e -> clearFields()
        );
    }

    private void addGuest() {

        if (!validateFields()) {
            return;
        }

        String id = idField.getText().trim();

        if (manager.findGuestById(id) != null) {

            JOptionPane.showMessageDialog(
                    this,
                    "Guest ID already exists!"
            );

            return;
        }

        Guest guest = new Guest(

                id,

                nameField.getText().trim(),

                phoneField.getText().trim(),

                addressField.getText().trim(),

                genderBox.getSelectedItem().toString(),

                relationBox.getSelectedItem().toString(),

                spouseField.getText().trim(),

                giftTypeBox.getSelectedItem().toString(),

                giftDetailsField.getText().trim(),

                weddingIdField.getText().trim()
        );

        manager.addGuest(guest);

        refreshTable();

        clearFields();

        JOptionPane.showMessageDialog(
                this,
                "Guest added successfully!"
        );
    }

    private void updateGuest() {

        String id = idField.getText().trim();

        Guest guest = manager.findGuestById(id);

        if (guest == null) {

            JOptionPane.showMessageDialog(
                    this,
                    "Guest not found!"
            );

            return;
        }

        guest.setGuestName(
                nameField.getText().trim()
        );

        guest.setPhone(
                phoneField.getText().trim()
        );

        guest.setAddress(
                addressField.getText().trim()
        );

        guest.setGender(
                genderBox.getSelectedItem().toString()
        );

        guest.setRelation(
                relationBox.getSelectedItem().toString()
        );

        guest.setSpouseName(
                spouseField.getText().trim()
        );

        guest.setGiftType(
                giftTypeBox.getSelectedItem().toString()
        );

        guest.setGiftDetails(
                giftDetailsField.getText().trim()
        );

        guest.setWeddingId(
                weddingIdField.getText().trim()
        );

        refreshTable();

        JOptionPane.showMessageDialog(
                this,
                "Guest updated successfully!"
        );
    }

    private void deleteGuest() {

        String id = idField.getText().trim();

        if (id.isEmpty()) {

            JOptionPane.showMessageDialog(
                    this,
                    "Select a guest first!"
            );

            return;
        }

        int result = JOptionPane.showConfirmDialog(

                this,

                "Are you sure you want to delete this guest?",

                "Confirm Delete",

                JOptionPane.YES_NO_OPTION
        );

        if (result == JOptionPane.YES_OPTION) {

            manager.deleteGuest(id);

            refreshTable();

            clearFields();

            JOptionPane.showMessageDialog(
                    this,
                    "Guest deleted successfully!"
            );
        }
    }

    // ==========================================
    // IMPORTANT SEARCH FEATURE
    // ==========================================

    private void searchGuest() {

        String search = searchField
                .getText()
                .trim()
                .toLowerCase();

        model.setRowCount(0);

        for (Guest guest : manager.getGuests()) {

            if (guest.getGuestId()
                    .toLowerCase()
                    .contains(search)

                    || guest.getGuestName()
                    .toLowerCase()
                    .contains(search)

                    || guest.getPhone()
                    .toLowerCase()
                    .contains(search)) {

                model.addRow(new Object[]{

                        guest.getGuestId(),

                        guest.getGuestName(),

                        guest.getPhone(),

                        guest.getGender(),

                        guest.getRelation(),

                        guest.getSpouseName(),

                        guest.getGiftType(),

                        guest.getGiftDetails(),

                        guest.getWeddingId()
                });
            }
        }

        // Show detailed information
        if (!search.isEmpty()) {

            showGuestInformation(search);
        }
    }

    private void showGuestInformation(String search) {

        Guest foundGuest = null;

        for (Guest guest : manager.getGuests()) {

            if (guest.getGuestName()
                    .toLowerCase()
                    .contains(search)

                    || guest.getGuestId()
                    .toLowerCase()
                    .contains(search)) {

                foundGuest = guest;

                break;
            }
        }

        if (foundGuest == null) {
            return;
        }

        String message =

                "Guest Information\n"
                + "-----------------------------\n"
                + "Guest Name : "
                + foundGuest.getGuestName()
                + "\n"

                + "Relation   : "
                + foundGuest.getRelation()
                + "\n"

                + "Spouse     : "
                + getSpouseText(foundGuest)
                + "\n"

                + "Gift Type  : "
                + foundGuest.getGiftType()
                + "\n"

                + "Gift/Amount: "
                + foundGuest.getGiftDetails()
                + "\n";

        JOptionPane.showMessageDialog(

                this,

                message,

                "Guest Details",

                JOptionPane.INFORMATION_MESSAGE
        );
    }

    private String getSpouseText(Guest guest) {

        if (guest.getSpouseName() == null
                || guest.getSpouseName().trim().isEmpty()) {

            return "Not Available";
        }

        Guest spouse = manager.findGuestByName(
                guest.getSpouseName()
        );

        if (spouse != null) {

            return spouse.getGuestName()
                    + " (" + spouse.getRelation() + ")";
        }

        return guest.getSpouseName();
    }

    private boolean validateFields() {

        if (idField.getText().trim().isEmpty()
                || nameField.getText().trim().isEmpty()
                || phoneField.getText().trim().isEmpty()
                || addressField.getText().trim().isEmpty()
                || spouseField.getText().trim().isEmpty()
                || weddingIdField.getText().trim().isEmpty()) {

            JOptionPane.showMessageDialog(

                    this,

                    "Please fill all required fields!"
            );

            return false;
        }

        return true;
    }

    private void refreshTable() {

        searchGuest();
    }

    private void clearFields() {

        idField.setText("");
        nameField.setText("");
        phoneField.setText("");
        addressField.setText("");
        spouseField.setText("");
        giftDetailsField.setText("");
        weddingIdField.setText("");

        genderBox.setSelectedIndex(0);
        relationBox.setSelectedIndex(0);
        giftTypeBox.setSelectedIndex(0);

        idField.setEditable(true);

        table.clearSelection();
    }
}