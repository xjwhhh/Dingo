package controller;

import model.ResultMessage;
import model.Venue;
import model.VenueApplication;
import model.VenueApplicationType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import service.bean.VenueManageServiceBean;

import java.util.List;

@CrossOrigin
@Controller
@RequestMapping("venue")
public class VenueController {

    @Autowired
    VenueManageServiceBean venueManageServiceBean;

    @RequestMapping(value = "/register", method = RequestMethod.POST)
    @ResponseBody
    public ResultMessage Register(
            @RequestParam("account") String account,
            @RequestParam("password") String password) {
        return venueManageServiceBean.register(account, password);
    }

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    @ResponseBody
    public Venue Login(
            @RequestParam("code") String code,
            @RequestParam("password") String password) {
        return venueManageServiceBean.login(code, password);

    }

    @RequestMapping(value = "/getVenueById", method = RequestMethod.POST)
    @ResponseBody
    public Venue getVenueById(
            @RequestParam("venueId") String venueId) {
        return venueManageServiceBean.getVenueById(Integer.parseInt(venueId));
    }

    @RequestMapping(value = "/updateInfo", method = RequestMethod.POST)
    @ResponseBody
    public ResultMessage applyForUpdate(
            @RequestParam("venueJson") String venueJson,
            @RequestParam("one") String one,
            @RequestParam("two") String two,
            @RequestParam("three") String three) {
        return venueManageServiceBean.applyForUpdate(venueJson, one, two, three);
    }

    @RequestMapping(value = "/getApplication", method = RequestMethod.POST)
    @ResponseBody
    public List<VenueApplication> getApplication(
            @RequestParam("type") String venueApplicationTypeString) {
        VenueApplicationType venueApplicationType = VenueApplicationType.class.getEnumConstants()[Integer.parseInt(venueApplicationTypeString)];
        return venueManageServiceBean.getApplication(venueApplicationType);
    }

    @RequestMapping(value = "/approveApplication", method = RequestMethod.POST)
    @ResponseBody
    public ResultMessage approveApplication(
            @RequestParam("venueApplicationId") String venueApplicationId) {
        return venueManageServiceBean.approveApplication(Integer.parseInt(venueApplicationId));
    }

    @RequestMapping(value = "/venueList", method = RequestMethod.POST)
    @ResponseBody
    public List<Venue> getVenueList() {
        return venueManageServiceBean.getAllVenues();
    }

}
