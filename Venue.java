public class Venue {

    private String venueId;
    private String venueName;
    private String location;
    private int capacity;
    private double rent;

    public Venue(String venueId,
                 String venueName,
                 String location,
                 int capacity,
                 double rent) {

        this.venueId = venueId;
        this.venueName = venueName;
        this.location = location;
        this.capacity = capacity;
        this.rent = rent;
    }

    public String getVenueId() {
        return venueId;
    }

    public String getVenueName() {
        return venueName;
    }

    public String getLocation() {
        return location;
    }

    public int getCapacity() {
        return capacity;
    }

    public double getRent() {
        return rent;
    }

    public void setVenueName(String venueName) {
        this.venueName = venueName;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    public void setRent(double rent) {
        this.rent = rent;
    }
}