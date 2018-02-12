package service.bean;

import dao.DaoFactory;
import dao.ShowDao;
import dao.VenueDao;
import model.*;
import net.sf.json.JSONObject;
import service.VenueManageService;

import java.util.List;

public class VenueManageServiceBean implements VenueManageService {

    VenueDao venueDao = DaoFactory.getVenueDao();

    ShowDao showDao = DaoFactory.getShowDao();

    public ResultMessage register(String account, String password) {
        Venue venue = new Venue();
        venue.setAccount(account);
        venue.setPassword(password);
        JSONObject jsonObject = JSONObject.fromObject(venue);
        String venueJson = jsonObject.toString();
        VenueApplication venueApplication = new VenueApplication();
        venueApplication.setVenueJson(venueJson);
        venueApplication.setVenueApplicationType(VenueApplicationType.REGISTER);
        return venueDao.saveVenueApplication(venueApplication);
    }

    public Venue login(String account, String password) {
        return venueDao.find(account, password);
    }

    public Venue getVenueById(int venueId) {
        return venueDao.findById(venueId);
    }

    public ResultMessage applyForUpdate(String venueJson) {
        VenueApplication venueApplication = new VenueApplication();
        venueApplication.setVenueJson(venueJson);
        venueApplication.setVenueApplicationType(VenueApplicationType.UPDATE);
        return venueDao.saveVenueApplication(venueApplication);
    }

    public ResultMessage publishPlan(String showJson) {
        JSONObject jsonObject = JSONObject.fromObject(showJson);
        Show show = (Show) JSONObject.toBean(jsonObject, Show.class);
        return showDao.save(show);
    }

    public ResultMessage examineApplication(int venueApplicationId) {
        VenueApplication venueApplication = venueDao.findVenueApplicationById(venueApplicationId);
        JSONObject jsonObject = JSONObject.fromObject(venueApplication.getVenueJson());
        Venue venue = (Venue) JSONObject.toBean(jsonObject, Venue.class);
        if (venueApplication.getVenueApplicationType() == VenueApplicationType.REGISTER) {
            return venueDao.save(venue);
        } else {
            return venueDao.update(venue);
        }
    }

    public List<Venue> getAllVenues() {
        return null;
    }
}
