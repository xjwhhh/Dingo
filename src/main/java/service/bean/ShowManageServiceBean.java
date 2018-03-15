package service.bean;

import dao.ShowDao;
import dao.VenueDao;
import model.*;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import service.ShowManageService;

import javax.persistence.criteria.CriteriaBuilder;
import java.util.ArrayList;
import java.util.List;

@Service
public class ShowManageServiceBean implements ShowManageService {

    @Autowired
    ShowDao showDao;

    @Autowired
    VenueDao venueDao;

    public ResultMessage publishShow(String showJson, String one, String two, String three) {
        JSONObject jsonObject = JSONObject.fromObject(showJson);
        Show show = (Show) JSONObject.toBean(jsonObject, Show.class);
        showDao.save(show);
        List<Seat> seatList = venueDao.findSeatListByVenueId(show.getVenueId());
        List<ShowSeat> showSeatList = new ArrayList<ShowSeat>();
        List<Show> showList = showDao.findByVenueId(show.getVenueId());
        Show newShow = new Show();
        newShow.setId(-1);
        for (int i = 0; i < showList.size(); i++) {
            if (seatList.get(i).getId() > newShow.getId()) {
                newShow = showList.get(i);
            }
        }
        for (int i = 0; i < seatList.size(); i++) {
            ShowSeat showSeat = new ShowSeat();
            showSeat.setLevel(seatList.get(i).getLevel());
            showSeat.setDescription(seatList.get(i).getDescription());
            showSeat.setSeatId(seatList.get(i).getId());
            showSeat.setShow(newShow);
            showSeat.setBooked(false);
            if (seatList.get(i).getLevel().equals("一等座")) {
                showSeat.setCost(Integer.parseInt(one));
            } else if (seatList.get(i).getLevel().equals("二等座")) {
                showSeat.setCost(Integer.parseInt(two));
            } else if (seatList.get(i).getLevel().equals("三等座")) {
                showSeat.setCost(Integer.parseInt(three));
            }
            showSeatList.add(showSeat);
        }
        show.setSeatList(showSeatList);
        for (int i = 0; i < showSeatList.size(); i++) {
            showDao.saveShowSeat(showSeatList.get(i));
        }
        return ResultMessage.SUCCESS;
    }

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
