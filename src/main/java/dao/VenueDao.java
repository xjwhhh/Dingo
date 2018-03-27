package dao;

import model.*;

import java.util.List;

public interface VenueDao extends BaseDao {
    ResultMessage save(Venue venue);

    Venue find(String code, String password);

    Venue findById(int venueId);

    ResultMessage update(Venue venue);

    ResultMessage saveVenueApplication(VenueApplication venueApplication);

    VenueApplication findVenueApplicationById(int venueApplicationId);

    List<VenueApplication> findVenueApplicationByType(VenueApplicationType venueApplicationType);

    List<Venue> findVenueList();

    List<Seat> findSeatListByVenueId(int venueId);

    ResultMessage saveSeat(Seat seat);

    List<VenueFinance> findVenueFinanceListByVenueId(int venueId);
}
