public class Customer {

    private String customerId;
    private String name;
    private String phone;
    private String address;
    private String weddingDate;

    public Customer(String customerId, String name, String phone,
                    String address, String weddingDate) {

        this.customerId = customerId;
        this.name = name;
        this.phone = phone;
        this.address = address;
        this.weddingDate = weddingDate;
    }

    public String getCustomerId() {
        return customerId;
    }

    public String getName() {
        return name;
    }

    public String getPhone() {
        return phone;
    }

    public String getAddress() {
        return address;
    }

    public String getWeddingDate() {
        return weddingDate;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setWeddingDate(String weddingDate) {
        this.weddingDate = weddingDate;
    }
}