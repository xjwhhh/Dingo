package service.bean;

import dao.VenueDao;
import model.*;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import service.VenueManageService;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class VenueManageServiceBean implements VenueManageService {

    @Autowired
    VenueDao venueDao;

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

    public ResultMessage applyForUpdate(String venueJson, String one, String two, String three) {
        JSONObject jsonObject = JSONObject.fromObject(venueJson);
        Venue venue = (Venue) JSONObject.toBean(jsonObject, Venue.class);
        List<Seat> seatList = venue.getSeatList();
        seatList.clear();
        for (int i = 1; i <= Integer.parseInt(one); i++) {
            Seat seat = new Seat();
            seat.setLevel("一等座");
            seat.setDescription("一等座" + i + "号");
            seatList.add(seat);
        }
        for (int i = 1; i <= Integer.parseInt(two); i++) {
            Seat seat = new Seat();
            seat.setLevel("二等座");
            seat.setDescription("二等座" + i + "号");
            seatList.add(seat);
        }
        for (int i = 1; i <= Integer.parseInt(three); i++) {
            Seat seat = new Seat();
            seat.setLevel("三等座");
            seat.setDescription("三等座" + i + "号");
            seatList.add(seat);
        }
        venue.setSeatList(seatList);
        venueJson = JSONArray.fromObject(venue).toString();
        venueJson = venueJson.substring(1, venueJson.length() - 1);
        VenueApplication venueApplication = new VenueApplication();
        venueApplication.setVenueJson(venueJson);
        venueApplication.setVenueApplicationType(VenueApplicationType.UPDATE);
        return venueDao.saveVenueApplication(venueApplication);
    }

    public List<VenueApplication> getApplication(VenueApplicationType venueApplicationType) {
        return venueDao.findVenueApplicationByType(venueApplicationType);
    }

    public ResultMessage approveApplication(int venueApplicationId) {
        VenueApplication venueApplication = venueDao.findVenueApplicationById(venueApplicationId);
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("seatList", Seat.class);
        JSONObject jsonObject = JSONObject.fromObject(venueApplication.getVenueJson());
        Venue venue = (Venue) JSONObject.toBean(jsonObject, Venue.class, map);
        venueApplication.setApproved(true);
        venueApplication.setApproveTime(dateFormat(new Date()));
        venueDao.update(venueApplication);
        if (venueApplication.getVenueApplicationType() == VenueApplicationType.REGISTER) {
            return venueDao.save(venue);
        } else {
            deleteSeat(venue.getId());
            System.out.println(JSONArray.fromObject(venue).toString());
            int firstNumber = 0;
            int secondNumber = 0;
            int thirdNumber = 0;
            List<Seat> seatList = venue.getSeatList();
            for (int i = 0; i < seatList.size(); i++) {
                if (seatList.get(i).getLevel().equals("一等座")) {
                    firstNumber++;
                } else if (seatList.get(i).getLevel().equals("二等座")) {
                    secondNumber++;
                } else if (seatList.get(i).getLevel().equals("三等座")) {
                    thirdNumber++;
                }
            }
            seatList.clear();
            for (int i = 1; i <= firstNumber; i++) {
                Seat seat = new Seat();
                seat.setLevel("一等座");
                seat.setDescription("一等座" + i + "号");
                seatList.add(seat);
            }
            for (int i = 1; i <= secondNumber; i++) {
                Seat seat = new Seat();
                seat.setLevel("二等座");
                seat.setDescription("二等座" + i + "号");
                seatList.add(seat);
            }
            for (int i = 1; i <= thirdNumber; i++) {
                Seat seat = new Seat();
                seat.setLevel("三等座");
                seat.setDescription("三等座" + i + "号");
                seatList.add(seat);
            }
            venue.setSeatList(seatList);
            venueDao.update(venue);
            for (int i = 0; i < seatList.size(); i++) {
                Seat seat = seatList.get(i);
                seat.setVenue(venue);
                venueDao.saveSeat(seat);
            }
        }
        return ResultMessage.SUCCESS;
    }

    public List<Venue> getAllVenues() {
        return venueDao.findVenueList();
    }

    public List<VenueFinance> getVenueFinanceList(int venueId) {
        return venueDao.findVenueFinanceListByVenueId(venueId);
    }

    private String dateFormat(Date date) {
        SimpleDateFormat format = new SimpleDateFormat("yy/MM/dd HH:mm:ss");
        return format.format(date);
    }

    private void deleteSeat(int venueId) {
        List<Seat> seatList = venueDao.findSeatListByVenueId(venueId);
        for (int i = 0; i < seatList.size(); i++) {
            venueDao.delete(seatList.get(i));
        }
    }

    public static void main(String[] args) {
        VenueManageServiceBean venueManageServiceBean = new VenueManageServiceBean();
        venueManageServiceBean.deleteSeat(1);
    }
}
