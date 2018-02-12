package dao.bean;

import dao.VenueDao;
import model.ResultMessage;
import model.Venue;
import model.VenueApplication;

public class VenueDaoBean implements VenueDao {

    private static VenueDaoBean venueDao=new VenueDaoBean();

    public static VenueDaoBean getInstance(){
        return venueDao;
    }

    public ResultMessage save(Venue venue) {
        return null;
    }

    public Venue find(String account, String password) {
        return null;
    }

    public Venue findById(int venueId) {
        return null;
    }

    public ResultMessage update(Venue venue) {
        return null;
    }

    public ResultMessage saveVenueApplication(VenueApplication venueApplication) {
        return null;
    }

    public VenueApplication findVenueApplicationById(int venueApplicationId) {
        return null;
    }
}
