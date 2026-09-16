import java.util.ArrayList;

public class GuestManager {

    private ArrayList<Guest> guests;

    public GuestManager() {

        guests = new ArrayList<>();
    }

    public void addGuest(Guest guest) {

        guests.add(guest);
    }

    public ArrayList<Guest> getGuests() {

        return guests;
    }

    public Guest findGuestById(String id) {

        for (Guest guest : guests) {

            if (guest.getGuestId().equalsIgnoreCase(id)) {

                return guest;
            }
        }

        return null;
    }

    public Guest findGuestByName(String name) {

        for (Guest guest : guests) {

            if (guest.getGuestName().equalsIgnoreCase(name)) {

                return guest;
            }
        }

        return null;
    }

    public boolean deleteGuest(String id) {

        Guest guest = findGuestById(id);

        if (guest != null) {

            guests.remove(guest);

            return true;
        }

        return false;
    }
}