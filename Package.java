public class Package {

    private String packageId;
    private String packageName;
    private String packageType;
    private double price;

    public Package(String packageId,
                   String packageName,
                   String packageType,
                   double price) {

        this.packageId = packageId;
        this.packageName = packageName;
        this.packageType = packageType;
        this.price = price;
    }

    public String getPackageId() {
        return packageId;
    }

    public String getPackageName() {
        return packageName;
    }

    public String getPackageType() {
        return packageType;
    }

    public double getPrice() {
        return price;
    }

    public void setPackageName(String packageName) {
        this.packageName = packageName;
    }

    public void setPackageType(String packageType) {
        this.packageType = packageType;
    }

    public void setPrice(double price) {
        this.price = price;
    }
}