import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class VenuePanel extends JPanel {

    private JTextField idField;
    private JTextField nameField;
    private JTextField locationField;
    private JTextField capacityField;
    private JTextField rentField;
    private JTextField searchField;

    private JTable table;
    private DefaultTableModel model;

    private VenueManager manager;

    public VenuePanel() {

        manager = new VenueManager();

        setLayout(new BorderLayout(10, 10));

        createForm();
        createTable();
        createButtons();
    }

    private void createForm() {

        JPanel top = new JPanel(new BorderLayout());

        JPanel form = new JPanel(
                new GridLayout(2, 5, 5, 5)
        );

        idField = new JTextField();
        nameField = new JTextField();
        locationField = new JTextField();
        capacityField = new JTextField();
        rentField = new JTextField();

        form.add(new JLabel("Venue ID"));
        form.add(new JLabel("Venue Name"));
        form.add(new JLabel("Location"));
        form.add(new JLabel("Capacity"));
        form.add(new JLabel("Rent"));

        form.add(idField);
        form.add(nameField);
        form.add(locationField);
        form.add(capacityField);
        form.add(rentField);

        top.add(form, BorderLayout.CENTER);

        JPanel search = new JPanel(
                new FlowLayout(FlowLayout.RIGHT)
        );

        searchField = new JTextField(20);

        search.add(new JLabel("Search:"));
        search.add(searchField);

        top.add(search, BorderLayout.SOUTH);

        add(top, BorderLayout.NORTH);

        searchField.addKeyListener(
                new java.awt.event.KeyAdapter() {

                    public void keyReleased(
                            java.awt.event.KeyEvent e) {

                        searchVenue();
                    }
                }
        );
    }

    private void createTable() {

        String[] columns = {
                "Venue ID",
                "Venue Name",
                "Location",
                "Capacity",
                "Rent"
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

                        locationField.setText(
                                model.getValueAt(row, 2).toString()
                        );

                        capacityField.setText(
                                model.getValueAt(row, 3).toString()
                        );

                        rentField.setText(
                                model.getValueAt(row, 4).toString()
                        );

                        idField.setEditable(false);
                    }
                });
    }

    private void createButtons() {

        JPanel buttons = new JPanel();

        JButton add = new JButton("Add");
        JButton update = new JButton("Update");
        JButton delete = new JButton("Delete");
        JButton clear = new JButton("Clear");

        buttons.add(add);
        buttons.add(update);
        buttons.add(delete);
        buttons.add(clear);

        add(buttons, BorderLayout.SOUTH);

        add.addActionListener(e -> addVenue());
        update.addActionListener(e -> updateVenue());
        delete.addActionListener(e -> deleteVenue());
        clear.addActionListener(e -> clearFields());
    }

    private void addVenue() {

        if (!validateFields()) {
            return;
        }

        if (manager.findVenue(
                idField.getText().trim()) != null) {

            JOptionPane.showMessageDialog(
                    this,
                    "Venue ID already exists!"
            );

            return;
        }

        try {

            int capacity = Integer.parseInt(
                    capacityField.getText().trim()
            );

            double rent = Double.parseDouble(
                    rentField.getText().trim()
            );

            Venue venue = new Venue(

                    idField.getText().trim(),

                    nameField.getText().trim(),

                    locationField.getText().trim(),

                    capacity,

                    rent
            );

            manager.addVenue(venue);

            refreshTable();
            clearFields();

        } catch (NumberFormatException e) {

            JOptionPane.showMessageDialog(
                    this,
                    "Capacity and Rent must be numbers!"
            );
        }
    }

    private void updateVenue() {

        Venue venue = manager.findVenue(
                idField.getText().trim()
        );

        if (venue == null) {

            JOptionPane.showMessageDialog(
                    this,
                    "Venue not found!"
            );

            return;
        }

        try {

            int capacity = Integer.parseInt(
                    capacityField.getText().trim()
            );

            double rent = Double.parseDouble(
                    rentField.getText().trim()
            );

            venue.setVenueName(
                    nameField.getText().trim()
            );

            venue.setLocation(
                    locationField.getText().trim()
            );

            venue.setCapacity(capacity);

            venue.setRent(rent);

            refreshTable();

        } catch (NumberFormatException e) {

            JOptionPane.showMessageDialog(
                    this,
                    "Invalid number!"
            );
        }
    }

    private void deleteVenue() {

        String id = idField.getText().trim();

        int result = JOptionPane.showConfirmDialog(
                this,
                "Delete this venue?",
                "Confirm Delete",
                JOptionPane.YES_NO_OPTION
        );

        if (result == JOptionPane.YES_OPTION) {

            manager.deleteVenue(id);

            refreshTable();
            clearFields();
        }
    }

    private void searchVenue() {

        String search = searchField
                .getText()
                .trim()
                .toLowerCase();

        model.setRowCount(0);

        for (Venue venue : manager.getVenues()) {

            if (venue.getVenueId()
                    .toLowerCase()
                    .contains(search)

                    || venue.getVenueName()
                    .toLowerCase()
                    .contains(search)

                    || venue.getLocation()
                    .toLowerCase()
                    .contains(search)) {

                model.addRow(new Object[]{

                        venue.getVenueId(),
                        venue.getVenueName(),
                        venue.getLocation(),
                        venue.getCapacity(),
                        venue.getRent()
                });
            }
        }
    }

    private boolean validateFields() {

        if (idField.getText().trim().isEmpty()
                || nameField.getText().trim().isEmpty()
                || locationField.getText().trim().isEmpty()
                || capacityField.getText().trim().isEmpty()
                || rentField.getText().trim().isEmpty()) {

            JOptionPane.showMessageDialog(
                    this,
                    "Please fill all fields!"
            );

            return false;
        }

        return true;
    }

    private void refreshTable() {

        searchVenue();
    }

    private void clearFields() {

        idField.setText("");
        nameField.setText("");
        locationField.setText("");
        capacityField.setText("");
        rentField.setText("");

        idField.setEditable(true);
    }
}