package controller;

import model.ResultMessage;
import model.Venue;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import service.ServiceFactory;

@CrossOrigin
@Controller
@RequestMapping("venue")
public class VenueController {

    @RequestMapping(value = "/register", method = RequestMethod.POST)
    @ResponseBody
    public ResultMessage Register(
            @RequestParam("account")String account,
            @RequestParam("password")String password) {
        return ServiceFactory.getVenueManageService().register(account,password);
    }

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    @ResponseBody public Venue Login(
            @RequestParam("account")String account,
            @RequestParam("password")String password) {
        return ServiceFactory.getVenueManageService().login(account,password);

    }

    @RequestMapping(value = "/getVenueById", method = RequestMethod.POST)
    @ResponseBody public Venue getVenueById(
            @RequestParam("venueId")String venueId){
        return ServiceFactory.getVenueManageService().getVenueById(Integer.parseInt(venueId));
    }

    @RequestMapping(value = "/updateInfo", method = RequestMethod.POST)
    @ResponseBody public ResultMessage applyForUpdate(
            @RequestParam("venueJson")String venueJson) {
        return ServiceFactory.getVenueManageService().applyForUpdate(venueJson);
    }

    @RequestMapping(value = "/approveApplication", method = RequestMethod.POST)
    @ResponseBody public ResultMessage approveApplication(
            @RequestParam("venueApplicationId")String venueApplicationId ) {
        return ServiceFactory.getVenueManageService().examineApplication(Integer.parseInt(venueApplicationId));
    }

    @RequestMapping(value = "/publishShow", method = RequestMethod.POST)
    @ResponseBody public ResultMessage publishShow(
            @RequestParam("showJson")String showJson) {
        return ServiceFactory.getVenueManageService().publishPlan(showJson);
    }




}
