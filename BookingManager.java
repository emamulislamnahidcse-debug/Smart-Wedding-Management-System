import java.util.ArrayList;

public class BookingManager {

    private ArrayList<Booking> bookings;

    public BookingManager() {

        bookings = new ArrayList<>();
    }

    public void addBooking(Booking booking) {

        bookings.add(booking);
    }

    public ArrayList<Booking> getBookings() {

        return bookings;
    }

    public Booking findBooking(String id) {

        for (Booking booking : bookings) {

            if (booking.getBookingId()
                    .equalsIgnoreCase(id)) {

                return booking;
            }
        }

        return null;
    }

    public boolean deleteBooking(String id) {

        Booking booking = findBooking(id);

        if (booking != null) {

            bookings.remove(booking);

            return true;
        }

        return false;
    }
}