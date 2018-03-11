package service.bean;

import dao.ShowDao;
import dao.VenueDao;
import model.*;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import service.VenueManageService;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Service
public class VenueManageServiceBean implements VenueManageService {

    @Autowired
    VenueDao venueDao;

    @Autowired
    ShowDao showDao;

    public ResultMessage register(String account, String password) {
        Venue testVenue = venueDao.find(account, password);
        if (testVenue.getId() != -1) {
            return ResultMessage.FAIL;
        }
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

    public Venue login(String code, String password) {
        return venueDao.find(code, password);
    }

    public Venue getVenueById(int venueId) {
        return venueDao.findById(venueId);
    }

    public ResultMessage applyForUpdate(String venueJson,String one,String two,String three) {
        //todo 更新座位
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

    public List<VenueApplication> getApplication(VenueApplicationType venueApplicationType) {
        return venueDao.findVenueApplicationByType(venueApplicationType);
    }

    public ResultMessage approveApplication(int venueApplicationId) {
        VenueApplication venueApplication = venueDao.findVenueApplicationById(venueApplicationId);
        JSONObject jsonObject = JSONObject.fromObject(venueApplication.getVenueJson());
        Venue venue = (Venue) JSONObject.toBean(jsonObject, Venue.class);
        venueApplication.setApproved(true);
        venueApplication.setApproveTime(dateFormat(new Date()));
        venueDao.update(venueApplication);

        if (venueApplication.getVenueApplicationType() == VenueApplicationType.REGISTER) {
            return venueDao.save(venue);
        } else {
            return venueDao.update(venue);
        }
    }

    public List<Venue> getAllVenues() {
        return null;
    }

    private String dateFormat(Date date){
        SimpleDateFormat format=new SimpleDateFormat("yy/MM/dd HH:mm:ss");
        return  format.format(date);
    }
}
