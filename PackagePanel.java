import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class PackagePanel extends JPanel {

    private JTextField idField;
    private JTextField nameField;
    private JTextField typeField;
    private JTextField priceField;
    private JTextField searchField;

    private JTable table;
    private DefaultTableModel model;

    private PackageManager manager;

    public PackagePanel() {

        manager = new PackageManager();

        setLayout(new BorderLayout(10, 10));

        createForm();
        createTable();
        createButtons();
    }

    private void createForm() {

        JPanel top = new JPanel(new BorderLayout());

        JPanel form = new JPanel(
                new GridLayout(2, 4, 5, 5)
        );

        idField = new JTextField();
        nameField = new JTextField();
        typeField = new JTextField();
        priceField = new JTextField();

        form.add(new JLabel("Package ID"));
        form.add(new JLabel("Package Name"));
        form.add(new JLabel("Package Type"));
        form.add(new JLabel("Price"));

        form.add(idField);
        form.add(nameField);
        form.add(typeField);
        form.add(priceField);

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

                        searchPackage();
                    }
                }
        );
    }

    private void createTable() {

        String[] columns = {
                "Package ID",
                "Package Name",
                "Package Type",
                "Price"
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

                        typeField.setText(
                                model.getValueAt(row, 2).toString()
                        );

                        priceField.setText(
                                model.getValueAt(row, 3).toString()
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

        add.addActionListener(e -> addPackage());
        update.addActionListener(e -> updatePackage());
        delete.addActionListener(e -> deletePackage());
        clear.addActionListener(e -> clearFields());
    }

    private void addPackage() {

        if (!validateFields()) {
            return;
        }

        String id = idField.getText().trim();

        if (manager.findPackage(id) != null) {

            JOptionPane.showMessageDialog(
                    this,
                    "Package ID already exists!"
            );

            return;
        }

        double price;

        try {

            price = Double.parseDouble(
                    priceField.getText().trim()
            );

        } catch (NumberFormatException e) {

            JOptionPane.showMessageDialog(
                    this,
                    "Price must be a number!"
            );

            return;
        }

        Package p = new Package(

                id,

                nameField.getText().trim(),

                typeField.getText().trim(),

                price
        );

        manager.addPackage(p);

        refreshTable();
        clearFields();

        JOptionPane.showMessageDialog(
                this,
                "Package added successfully!"
        );
    }

    private void updatePackage() {

        Package p = manager.findPackage(
                idField.getText().trim()
        );

        if (p == null) {

            JOptionPane.showMessageDialog(
                    this,
                    "Package not found!"
            );

            return;
        }

        try {

            double price = Double.parseDouble(
                    priceField.getText().trim()
            );

            p.setPackageName(
                    nameField.getText().trim()
            );

            p.setPackageType(
                    typeField.getText().trim()
            );

            p.setPrice(price);

            refreshTable();

            JOptionPane.showMessageDialog(
                    this,
                    "Package updated successfully!"
            );

        } catch (NumberFormatException e) {

            JOptionPane.showMessageDialog(
                    this,
                    "Price must be a number!"
            );
        }
    }

    private void deletePackage() {

        String id = idField.getText().trim();

        int result = JOptionPane.showConfirmDialog(
                this,
                "Delete this package?",
                "Confirm Delete",
                JOptionPane.YES_NO_OPTION
        );

        if (result == JOptionPane.YES_OPTION) {

            manager.deletePackage(id);

            refreshTable();
            clearFields();
        }
    }

    private void searchPackage() {

        String search = searchField
                .getText()
                .trim()
                .toLowerCase();

        model.setRowCount(0);

        for (Package p : manager.getPackages()) {

            if (p.getPackageId()
                    .toLowerCase()
                    .contains(search)

                    || p.getPackageName()
                    .toLowerCase()
                    .contains(search)

                    || p.getPackageType()
                    .toLowerCase()
                    .contains(search)) {

                model.addRow(new Object[]{

                        p.getPackageId(),
                        p.getPackageName(),
                        p.getPackageType(),
                        p.getPrice()
                });
            }
        }
    }

    private boolean validateFields() {

        if (idField.getText().trim().isEmpty()
                || nameField.getText().trim().isEmpty()
                || typeField.getText().trim().isEmpty()
                || priceField.getText().trim().isEmpty()) {

            JOptionPane.showMessageDialog(
                    this,
                    "Please fill all fields!"
            );

            return false;
        }

        return true;
    }

    private void refreshTable() {

        searchPackage();
    }

    private void clearFields() {

        idField.setText("");
        nameField.setText("");
        typeField.setText("");
        priceField.setText("");

        idField.setEditable(true);

        table.clearSelection();
    }
}