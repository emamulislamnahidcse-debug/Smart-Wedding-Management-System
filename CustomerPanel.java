import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class CustomerPanel extends JPanel {

    private JTextField idField;
    private JTextField nameField;
    private JTextField phoneField;
    private JTextField addressField;
    private JTextField dateField;
    private JTextField searchField;

    private JTable table;
    private DefaultTableModel model;

    private CustomerManager manager;

    public CustomerPanel() {

        manager = new CustomerManager();

        setLayout(new BorderLayout(10, 10));

        createTopPanel();
        createTable();
        createButtonPanel();
    }

    private void createTopPanel() {

        JPanel topPanel = new JPanel(new BorderLayout());

        JPanel formPanel = new JPanel(new GridLayout(2, 5, 5, 5));

        idField = new JTextField();
        nameField = new JTextField();
        phoneField = new JTextField();
        addressField = new JTextField();
        dateField = new JTextField();

        formPanel.add(new JLabel("Customer ID"));
        formPanel.add(new JLabel("Name"));
        formPanel.add(new JLabel("Phone"));
        formPanel.add(new JLabel("Address"));
        formPanel.add(new JLabel("Wedding Date"));

        formPanel.add(idField);
        formPanel.add(nameField);
        formPanel.add(phoneField);
        formPanel.add(addressField);
        formPanel.add(dateField);

        topPanel.add(formPanel, BorderLayout.CENTER);

        JPanel searchPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));

        searchField = new JTextField(15);

        searchPanel.add(new JLabel("Search:"));
        searchPanel.add(searchField);

        topPanel.add(searchPanel, BorderLayout.SOUTH);

        add(topPanel, BorderLayout.NORTH);

        searchField.addKeyListener(new java.awt.event.KeyAdapter() {

            public void keyReleased(java.awt.event.KeyEvent e) {
                searchCustomer();
            }
        });
    }

    private void createTable() {

        String[] columns = {
                "Customer ID",
                "Name",
                "Phone",
                "Address",
                "Wedding Date"
        };

        model = new DefaultTableModel(columns, 0) {

            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        table = new JTable(model);

        table.setRowHeight(28);
        table.setAutoCreateRowSorter(true);

        add(new JScrollPane(table), BorderLayout.CENTER);

        table.getSelectionModel().addListSelectionListener(e -> {

            int row = table.getSelectedRow();

            if (row != -1) {

                idField.setText(model.getValueAt(row, 0).toString());
                nameField.setText(model.getValueAt(row, 1).toString());
                phoneField.setText(model.getValueAt(row, 2).toString());
                addressField.setText(model.getValueAt(row, 3).toString());
                dateField.setText(model.getValueAt(row, 4).toString());

                idField.setEditable(false);
            }
        });
    }

    private void createButtonPanel() {

        JPanel buttonPanel = new JPanel(new FlowLayout());

        JButton addButton = new JButton("Add");
        JButton updateButton = new JButton("Update");
        JButton deleteButton = new JButton("Delete");
        JButton clearButton = new JButton("Clear");

        buttonPanel.add(addButton);
        buttonPanel.add(updateButton);
        buttonPanel.add(deleteButton);
        buttonPanel.add(clearButton);

        add(buttonPanel, BorderLayout.SOUTH);

        addButton.addActionListener(e -> addCustomer());
        updateButton.addActionListener(e -> updateCustomer());
        deleteButton.addActionListener(e -> deleteCustomer());
        clearButton.addActionListener(e -> clearFields());
    }

    private void addCustomer() {

        if (!validateFields()) {
            return;
        }

        if (manager.findCustomer(idField.getText().trim()) != null) {

            JOptionPane.showMessageDialog(
                    this,
                    "Customer ID already exists!"
            );

            return;
        }

        Customer customer = new Customer(
                idField.getText().trim(),
                nameField.getText().trim(),
                phoneField.getText().trim(),
                addressField.getText().trim(),
                dateField.getText().trim()
        );

        manager.addCustomer(customer);

        refreshTable();

        clearFields();

        JOptionPane.showMessageDialog(
                this,
                "Customer added successfully!"
        );
    }

    private void updateCustomer() {

        String id = idField.getText().trim();

        Customer customer = manager.findCustomer(id);

        if (customer == null) {

            JOptionPane.showMessageDialog(
                    this,
                    "Customer not found!"
            );

            return;
        }

        customer.setName(nameField.getText().trim());
        customer.setPhone(phoneField.getText().trim());
        customer.setAddress(addressField.getText().trim());
        customer.setWeddingDate(dateField.getText().trim());

        refreshTable();

        JOptionPane.showMessageDialog(
                this,
                "Customer updated successfully!"
        );
    }

    private void deleteCustomer() {

        String id = idField.getText().trim();

        if (id.isEmpty()) {

            JOptionPane.showMessageDialog(
                    this,
                    "Select a customer first!"
            );

            return;
        }

        int result = JOptionPane.showConfirmDialog(
                this,
                "Are you sure you want to delete this customer?",
                "Confirm Delete",
                JOptionPane.YES_NO_OPTION
        );

        if (result == JOptionPane.YES_OPTION) {

            manager.deleteCustomer(id);

            refreshTable();
            clearFields();

            JOptionPane.showMessageDialog(
                    this,
                    "Customer deleted successfully!"
            );
        }
    }

    private void searchCustomer() {

        String text = searchField.getText().trim().toLowerCase();

        model.setRowCount(0);

        for (Customer customer : manager.getCustomers()) {

            if (customer.getCustomerId().toLowerCase().contains(text)
                    || customer.getName().toLowerCase().contains(text)
                    || customer.getPhone().toLowerCase().contains(text)) {

                model.addRow(new Object[]{
                        customer.getCustomerId(),
                        customer.getName(),
                        customer.getPhone(),
                        customer.getAddress(),
                        customer.getWeddingDate()
                });
            }
        }
    }

    private void refreshTable() {

        searchCustomer();
    }

    private boolean validateFields() {

        if (idField.getText().trim().isEmpty()
                || nameField.getText().trim().isEmpty()
                || phoneField.getText().trim().isEmpty()
                || addressField.getText().trim().isEmpty()
                || dateField.getText().trim().isEmpty()) {

            JOptionPane.showMessageDialog(
                    this,
                    "Please fill all fields!"
            );

            return false;
        }

        return true;
    }

    private void clearFields() {

        idField.setText("");
        nameField.setText("");
        phoneField.setText("");
        addressField.setText("");
        dateField.setText("");

        idField.setEditable(true);

        table.clearSelection();
    }
}