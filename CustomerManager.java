import java.util.ArrayList;

public class CustomerManager {

    private ArrayList<Customer> customers;

    public CustomerManager() {
        customers = new ArrayList<>();
    }

    public void addCustomer(Customer customer) {
        customers.add(customer);
    }

    public ArrayList<Customer> getCustomers() {
        return customers;
    }

    public Customer findCustomer(String id) {

        for (Customer customer : customers) {

            if (customer.getCustomerId().equalsIgnoreCase(id)) {
                return customer;
            }
        }

        return null;
    }

    public boolean deleteCustomer(String id) {

        Customer customer = findCustomer(id);

        if (customer != null) {
            customers.remove(customer);
            return true;
        }

        return false;
    }
}
