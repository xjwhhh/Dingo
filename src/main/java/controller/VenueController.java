package controller;

import model.User;
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
    public Venue Register(
            @RequestParam("account")String account,
            @RequestParam("password")String password) {

        return null;
    }

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    @ResponseBody public Venue Login(
            @RequestParam("account")String account,
            @RequestParam("password")String password) {
        return null;
    }

}
