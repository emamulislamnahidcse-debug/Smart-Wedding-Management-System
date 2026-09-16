public class Booking {

    private String bookingId;
    private String customerName;
    private String packageName;
    private String venueName;
    private String weddingDate;
    private int guestNumber;
    private double totalCost;

    public Booking(String bookingId,
                   String customerName,
                   String packageName,
                   String venueName,
                   String weddingDate,
                   int guestNumber,
                   double totalCost) {

        this.bookingId = bookingId;
        this.customerName = customerName;
        this.packageName = packageName;
        this.venueName = venueName;
        this.weddingDate = weddingDate;
        this.guestNumber = guestNumber;
        this.totalCost = totalCost;
    }

    public String getBookingId() {
        return bookingId;
    }

    public String getCustomerName() {
        return customerName;
    }

    public String getPackageName() {
        return packageName;
    }

    public String getVenueName() {
        return venueName;
    }

    public String getWeddingDate() {
        return weddingDate;
    }

    public int getGuestNumber() {
        return guestNumber;
    }

    public double getTotalCost() {
        return totalCost;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public void setPackageName(String packageName) {
        this.packageName = packageName;
    }

    public void setVenueName(String venueName) {
        this.venueName = venueName;
    }

    public void setWeddingDate(String weddingDate) {
        this.weddingDate = weddingDate;
    }

    public void setGuestNumber(int guestNumber) {
        this.guestNumber = guestNumber;
    }

    public void setTotalCost(double totalCost) {
        this.totalCost = totalCost;
    }
}