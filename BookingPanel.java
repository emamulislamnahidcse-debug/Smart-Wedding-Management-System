import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class BookingPanel extends JPanel {

    private JTextField idField;
    private JTextField customerField;
    private JTextField packageField;
    private JTextField venueField;
    private JTextField dateField;
    private JTextField guestField;
    private JTextField costField;
    private JTextField searchField;

    private JTable table;
    private DefaultTableModel model;

    private BookingManager manager;

    public BookingPanel() {

        manager = new BookingManager();

        setLayout(new BorderLayout(10, 10));

        createForm();
        createTable();
        createButtons();
    }

    private void createForm() {

        JPanel top = new JPanel(new BorderLayout());

        JPanel form = new JPanel(
                new GridLayout(2, 7, 5, 5)
        );

        idField = new JTextField();
        customerField = new JTextField();
        packageField = new JTextField();
        venueField = new JTextField();
        dateField = new JTextField();
        guestField = new JTextField();
        costField = new JTextField();

        form.add(new JLabel("Booking ID"));
        form.add(new JLabel("Customer"));
        form.add(new JLabel("Package"));
        form.add(new JLabel("Venue"));
        form.add(new JLabel("Wedding Date"));
        form.add(new JLabel("Guests"));
        form.add(new JLabel("Total Cost"));

        form.add(idField);
        form.add(customerField);
        form.add(packageField);
        form.add(venueField);
        form.add(dateField);
        form.add(guestField);
        form.add(costField);

        top.add(form, BorderLayout.CENTER);

        searchField = new JTextField(20);

        JPanel search = new JPanel(
                new FlowLayout(FlowLayout.RIGHT)
        );

        search.add(new JLabel("Search:"));
        search.add(searchField);

        top.add(search, BorderLayout.SOUTH);

        add(top, BorderLayout.NORTH);

        searchField.addKeyListener(
                new java.awt.event.KeyAdapter() {

                    public void keyReleased(
                            java.awt.event.KeyEvent e) {

                        searchBooking();
                    }
                }
        );
    }

    private void createTable() {

        String[] columns = {

                "Booking ID",
                "Customer",
                "Package",
                "Venue",
                "Wedding Date",
                "Guests",
                "Total Cost"
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

                        customerField.setText(
                                model.getValueAt(row, 1).toString()
                        );

                        packageField.setText(
                                model.getValueAt(row, 2).toString()
                        );

                        venueField.setText(
                                model.getValueAt(row, 3).toString()
                        );

                        dateField.setText(
                                model.getValueAt(row, 4).toString()
                        );

                        guestField.setText(
                                model.getValueAt(row, 5).toString()
                        );

                        costField.setText(
                                model.getValueAt(row, 6).toString()
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

        add.addActionListener(e -> addBooking());
        update.addActionListener(e -> updateBooking());
        delete.addActionListener(e -> deleteBooking());
        clear.addActionListener(e -> clearFields());
    }

    private void addBooking() {

        if (!validateFields()) {
            return;
        }

        if (manager.findBooking(
                idField.getText().trim()) != null) {

            JOptionPane.showMessageDialog(
                    this,
                    "Booking ID already exists!"
            );

            return;
        }

        try {

            int guests = Integer.parseInt(
                    guestField.getText().trim()
            );

            double cost = Double.parseDouble(
                    costField.getText().trim()
            );

            Booking booking = new Booking(

                    idField.getText().trim(),

                    customerField.getText().trim(),

                    packageField.getText().trim(),

                    venueField.getText().trim(),

                    dateField.getText().trim(),

                    guests,

                    cost
            );

            manager.addBooking(booking);

            refreshTable();

            clearFields();

        } catch (NumberFormatException e) {

            JOptionPane.showMessageDialog(
                    this,
                    "Guests and Total Cost must be numbers!"
            );
        }
    }

    private void updateBooking() {

        Booking booking = manager.findBooking(
                idField.getText().trim()
        );

        if (booking == null) {

            JOptionPane.showMessageDialog(
                    this,
                    "Booking not found!"
            );

            return;
        }

        try {

            booking.setCustomerName(
                    customerField.getText().trim()
            );

            booking.setPackageName(
                    packageField.getText().trim()
            );

            booking.setVenueName(
                    venueField.getText().trim()
            );

            booking.setWeddingDate(
                    dateField.getText().trim()
            );

            booking.setGuestNumber(
                    Integer.parseInt(
                            guestField.getText().trim()
                    )
            );

            booking.setTotalCost(
                    Double.parseDouble(
                            costField.getText().trim()
                    )
            );

            refreshTable();

        } catch (NumberFormatException e) {

            JOptionPane.showMessageDialog(
                    this,
                    "Invalid number!"
            );
        }
    }

    private void deleteBooking() {

        int result = JOptionPane.showConfirmDialog(
                this,
                "Delete this booking?",
                "Confirm Delete",
                JOptionPane.YES_NO_OPTION
        );

        if (result == JOptionPane.YES_OPTION) {

            manager.deleteBooking(
                    idField.getText().trim()
            );

            refreshTable();

            clearFields();
        }
    }

    private void searchBooking() {

        String search = searchField
                .getText()
                .trim()
                .toLowerCase();

        model.setRowCount(0);

        for (Booking booking : manager.getBookings()) {

            if (booking.getBookingId()
                    .toLowerCase()
                    .contains(search)

                    || booking.getCustomerName()
                    .toLowerCase()
                    .contains(search)

                    || booking.getVenueName()
                    .toLowerCase()
                    .contains(search)) {

                model.addRow(new Object[]{

                        booking.getBookingId(),
                        booking.getCustomerName(),
                        booking.getPackageName(),
                        booking.getVenueName(),
                        booking.getWeddingDate(),
                        booking.getGuestNumber(),
                        booking.getTotalCost()
                });
            }
        }
    }

    private boolean validateFields() {

        if (idField.getText().trim().isEmpty()
                || customerField.getText().trim().isEmpty()
                || packageField.getText().trim().isEmpty()
                || venueField.getText().trim().isEmpty()
                || dateField.getText().trim().isEmpty()
                || guestField.getText().trim().isEmpty()
                || costField.getText().trim().isEmpty()) {

            JOptionPane.showMessageDialog(
                    this,
                    "Please fill all fields!"
            );

            return false;
        }

        return true;
    }

    private void refreshTable() {

        searchBooking();
    }

    private void clearFields() {

        idField.setText("");
        customerField.setText("");
        packageField.setText("");
        venueField.setText("");
        dateField.setText("");
        guestField.setText("");
        costField.setText("");

        idField.setEditable(true);
    }
}