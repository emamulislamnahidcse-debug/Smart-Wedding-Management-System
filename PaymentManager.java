import java.util.ArrayList;

public class PaymentManager {

    private ArrayList<Payment> payments;

    public PaymentManager() {

        payments = new ArrayList<>();
    }

    public void addPayment(Payment payment) {

        payments.add(payment);
    }

    public ArrayList<Payment> getPayments() {

        return payments;
    }

    public Payment findPayment(String id) {

        for (Payment payment : payments) {

            if (payment.getPaymentId()
                    .equalsIgnoreCase(id)) {

                return payment;
            }
        }

        return null;
    }

    public boolean deletePayment(String id) {

        Payment payment = findPayment(id);

        if (payment != null) {

            payments.remove(payment);

            return true;
        }

        return false;
    }
}