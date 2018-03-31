package service.bean;

import dao.ShowDao;
import dao.VenueDao;
import model.*;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import service.ShowManageService;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
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
        try {
            Date startDate = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'").parse(show.getStartTime());
            Date endDate = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'").parse(show.getEndTime());
            String startStr = (new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")).format(startDate);
            String endStr = (new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")).format(endDate);
            show.setStartTime(startStr);
            show.setEndTime(endStr);
        }catch (ParseException e){
            e.printStackTrace();
        }
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

        ShowEarning showEarning=new ShowEarning();
        showEarning.setShowId(newShow.getId());
        showEarning.setSettled(false);
        showEarning.setOnlineEarning(0);
        showEarning.setOfflineEarning(0);
        showDao.save(showEarning);

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
        venue.setOnlineBalance(venue.getOnlineBalance() + show.getEarning() * 0.7);
        return venueDao.update(venue);
    }

    public List<Show> getPreSaleShowByVenueId(int venueId) {
        List<Show> showList=showDao.findByVenueId(venueId);
        List<Show> preSaleShowList=new ArrayList<Show>();
        for(int i=0;i<showList.size();i++){
            if(showList.get(i).getProgressType().equals("PRESALE")){
                preSaleShowList.add(showList.get(i));
            }
        }
        return preSaleShowList;
    }

    public List<Show> getShowByUserId(int userId) {
        return null;
    }
}
