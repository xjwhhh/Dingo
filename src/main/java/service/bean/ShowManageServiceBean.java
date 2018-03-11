package service.bean;

import dao.ShowDao;
import dao.VenueDao;
import model.ResultMessage;
import model.Show;
import model.ShowType;
import model.Venue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import service.ShowManageService;

import java.util.List;

@Service
public class ShowManageServiceBean implements ShowManageService {

    @Autowired
    ShowDao showDao;

    @Autowired
    VenueDao venueDao;

    public Show getShowById(int showId) {
        return showDao.findById(showId);
    }

    public List<Show> getShowByType(String showType) {
        return showDao.findByType(showType);
    }

    public ResultMessage distributeShowEarning(int showId) {
        Show show = showDao.findById(showId);
        Venue venue = venueDao.findById(show.getVenueId());
        venue.setBalance(venue.getBalance() + show.getEarning() * 0.7);
        return venueDao.update(venue);
    }

    public List<Show> getShowByVenueId(int venueId) {
        return null;
    }

    public List<Show> getShowByUserId(int userId) {
        return null;
    }
}
