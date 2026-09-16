import javax.swing.*;
import java.awt.*;

public class Main {

    public static void main(String[] args) {

        SwingUtilities.invokeLater(() -> {

            JFrame frame = new JFrame("Smart Wedding Management System");

            frame.setSize(1100, 700);
            frame.setLayout(new BorderLayout());

            frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);

            frame.addWindowListener(new java.awt.event.WindowAdapter() {

                public void windowClosing(java.awt.event.WindowEvent e) {

                    int result = JOptionPane.showConfirmDialog(
                            frame,
                            "Do you want to exit?",
                            "Confirm Exit",
                            JOptionPane.YES_NO_OPTION
                    );

                    if (result == JOptionPane.YES_OPTION) {
                        System.exit(0);
                    }
                }
            });

            JTabbedPane tabs = new JTabbedPane();

            tabs.addTab("Customers", new CustomerPanel());
            tabs.addTab("Guests", new GuestPanel());
            tabs.addTab("Packages", new PackagePanel());
            tabs.addTab("Venues", new VenuePanel());
            tabs.addTab("Bookings", new BookingPanel());
            tabs.addTab("Payments", new Paymentpanel());

            frame.add(tabs, BorderLayout.CENTER);

            frame.setLocationRelativeTo(null);
            frame.setVisible(true);
        });
    }
}