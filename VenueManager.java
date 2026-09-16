import java.util.ArrayList;

public class VenueManager {

    private ArrayList<Venue> venues;

    public VenueManager() {

        venues = new ArrayList<>();
    }

    public void addVenue(Venue venue) {

        venues.add(venue);
    }

    public ArrayList<Venue> getVenues() {

        return venues;
    }

    public Venue findVenue(String id) {

        for (Venue venue : venues) {

            if (venue.getVenueId()
                    .equalsIgnoreCase(id)) {

                return venue;
            }
        }

        return null;
    }

    public boolean deleteVenue(String id) {

        Venue venue = findVenue(id);

        if (venue != null) {

            venues.remove(venue);

            return true;
        }

        return false;
    }
}