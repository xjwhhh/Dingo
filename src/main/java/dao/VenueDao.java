package dao;

import model.ResultMessage;
import model.Venue;
import model.VenueApplication;
import model.VenueApplicationType;

import java.util.List;

public interface VenueDao extends BaseDao {
    ResultMessage save(Venue venue);

    Venue find(String account, String password);

    Venue findById(int venueId);

    ResultMessage update(Venue venue);

    ResultMessage saveVenueApplication(VenueApplication venueApplication);

    VenueApplication findVenueApplicationById(int venueApplicationId);

    List<VenueApplication> findVenueApplicationByType(VenueApplicationType venueApplicationType);

}
