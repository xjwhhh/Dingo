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
        ResultMessage resultMessage=ServiceFactory.getVenueManageService().register(account,password);
        return resultMessage;
    }

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    @ResponseBody public Venue Login(
            @RequestParam("account")String account,
            @RequestParam("password")String password) {
        Venue venue=ServiceFactory.getVenueManageService().login(account,password);
        return venue;
    }

    @RequestMapping(value = "/getVenueById", method = RequestMethod.POST)
    @ResponseBody public Venue getVenueById(
            @RequestParam("venueId")String venueId){
        Venue venue=ServiceFactory.getVenueManageService().getVenueById(Integer.parseInt(venueId));
        return venue;
    }

    @RequestMapping(value = "/updateInfo", method = RequestMethod.POST)
    @ResponseBody public ResultMessage applyForUpdate(
            @RequestParam("venueJson")String venueJson) {
        ResultMessage resultMessage=ServiceFactory.getVenueManageService().applyForUpdate(venueJson);
        return resultMessage;
    }

    @RequestMapping(value = "/approveApplication", method = RequestMethod.POST)
    @ResponseBody public ResultMessage approveApplication(
            @RequestParam("venueApplicationId")String venueApplicationId ) {
        ResultMessage resultMessage=ServiceFactory.getVenueManageService().examineApplication(Integer.parseInt(venueApplicationId));
        return resultMessage;
    }

    @RequestMapping(value = "/publishShow", method = RequestMethod.POST)
    @ResponseBody public ResultMessage publishShow(
            @RequestParam("showJson")String showJson) {
        ResultMessage resultMessage=ServiceFactory.getVenueManageService().publishPlan(showJson);
        return resultMessage;
    }




}
