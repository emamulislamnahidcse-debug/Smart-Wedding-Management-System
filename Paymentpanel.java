import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class Paymentpanel extends JPanel {

    private JTextField idField;
    private JTextField bookingField;
    private JTextField amountField;
    private JTextField searchField;

    private JComboBox<String> methodBox;
    private JComboBox<String> statusBox;

    private JTable table;
    private DefaultTableModel model;

    private PaymentManager manager;

    public Paymentpanel() {

        manager = new PaymentManager();

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
        bookingField = new JTextField();
        amountField = new JTextField();

        methodBox = new JComboBox<>(
                new String[]{
                        "Cash",
                        "Card",
                        "Bank",
                        "Mobile Banking"
                }
        );

        statusBox = new JComboBox<>(
                new String[]{
                        "Paid",
                        "Pending",
                        "Partial"
                }
        );

        form.add(new JLabel("Payment ID"));
        form.add(new JLabel("Booking ID"));
        form.add(new JLabel("Amount"));
        form.add(new JLabel("Payment Method"));
        form.add(new JLabel("Status"));

        form.add(idField);
        form.add(bookingField);
        form.add(amountField);
        form.add(methodBox);
        form.add(statusBox);

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

                        searchPayment();
                    }
                }
        );
    }

    private void createTable() {

        String[] columns = {

                "Payment ID",
                "Booking ID",
                "Amount",
                "Method",
                "Status"
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

                        bookingField.setText(
                                model.getValueAt(row, 1).toString()
                        );

                        amountField.setText(
                                model.getValueAt(row, 2).toString()
                        );

                        methodBox.setSelectedItem(
                                model.getValueAt(row, 3).toString()
                        );

                        statusBox.setSelectedItem(
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

        add.addActionListener(e -> addPayment());
        update.addActionListener(e -> updatePayment());
        delete.addActionListener(e -> deletePayment());
        clear.addActionListener(e -> clearFields());
    }

    private void addPayment() {

        if (!validateFields()) {
            return;
        }

        if (manager.findPayment(
                idField.getText().trim()) != null) {

            JOptionPane.showMessageDialog(
                    this,
                    "Payment ID already exists!"
            );

            return;
        }

        try {

            double amount = Double.parseDouble(
                    amountField.getText().trim()
            );

            Payment payment = new Payment(

                    idField.getText().trim(),

                    bookingField.getText().trim(),

                    amount,

                    methodBox.getSelectedItem()
                            .toString(),

                    statusBox.getSelectedItem()
                            .toString()
            );

            manager.addPayment(payment);

            refreshTable();

            clearFields();

        } catch (NumberFormatException e) {

            JOptionPane.showMessageDialog(
                    this,
                    "Amount must be a number!"
            );
        }
    }

    private void updatePayment() {

        Payment payment = manager.findPayment(
                idField.getText().trim()
        );

        if (payment == null) {

            JOptionPane.showMessageDialog(
                    this,
                    "Payment not found!"
            );

            return;
        }

        try {

            payment.setBookingId(
                    bookingField.getText().trim()
            );

            payment.setAmount(
                    Double.parseDouble(
                            amountField.getText().trim()
                    )
            );

            payment.setPaymentMethod(
                    methodBox.getSelectedItem()
                            .toString()
            );

            payment.setPaymentStatus(
                    statusBox.getSelectedItem()
                            .toString()
            );

            refreshTable();

        } catch (NumberFormatException e) {

            JOptionPane.showMessageDialog(
                    this,
                    "Amount must be a number!"
            );
        }
    }

    private void deletePayment() {

        int result = JOptionPane.showConfirmDialog(
                this,
                "Delete this payment?",
                "Confirm Delete",
                JOptionPane.YES_NO_OPTION
        );

        if (result == JOptionPane.YES_OPTION) {

            manager.deletePayment(
                    idField.getText().trim()
            );

            refreshTable();

            clearFields();
        }
    }

    private void searchPayment() {

        String search = searchField
                .getText()
                .trim()
                .toLowerCase();

        model.setRowCount(0);

        for (Payment payment : manager.getPayments()) {

            if (payment.getPaymentId()
                    .toLowerCase()
                    .contains(search)

                    || payment.getBookingId()
                    .toLowerCase()
                    .contains(search)

                    || payment.getPaymentStatus()
                    .toLowerCase()
                    .contains(search)) {

                model.addRow(new Object[]{

                        payment.getPaymentId(),
                        payment.getBookingId(),
                        payment.getAmount(),
                        payment.getPaymentMethod(),
                        payment.getPaymentStatus()
                });
            }
        }
    }

    private boolean validateFields() {

        if (idField.getText().trim().isEmpty()
                || bookingField.getText().trim().isEmpty()
                || amountField.getText().trim().isEmpty()) {

            JOptionPane.showMessageDialog(
                    this,
                    "Please fill all fields!"
            );

            return false;
        }

        return true;
    }

    private void refreshTable() {

        searchPayment();
    }

    private void clearFields() {

        idField.setText("");
        bookingField.setText("");
        amountField.setText("");

        methodBox.setSelectedIndex(0);
        statusBox.setSelectedIndex(0);

        idField.setEditable(true);
    }
}