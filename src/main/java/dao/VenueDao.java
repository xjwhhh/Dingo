package dao;

import model.ResultMessage;
import model.Venue;
import model.VenueApplication;

public interface VenueDao {
    ResultMessage save(Venue venue);

    Venue find(String account, String password);

    Venue findById(int venueId);

    ResultMessage update(Venue venue);

    ResultMessage saveVenueApplication(VenueApplication venueApplication);

    VenueApplication findVenueApplicationById(int venueApplicationId);


}
