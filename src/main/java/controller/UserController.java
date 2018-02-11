package controller;

import model.User;
import net.sf.json.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import service.ServiceFactory;

@CrossOrigin
@Controller
@RequestMapping("user")
public class UserController {

    @RequestMapping(value = "/register", method = RequestMethod.POST)
    @ResponseBody public User Register(
            @RequestParam("account")String account,
            @RequestParam("password")String password) {
        User user= ServiceFactory.getUserManageService().register(account,password);
        return user;
    }

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    @ResponseBody public User Login(
            @RequestParam("account")String account,
            @RequestParam("password")String password) {
        User user= ServiceFactory.getUserManageService().login(account,password);
        return user;
    }

    @RequestMapping(value = "/getUserById", method = RequestMethod.POST)
    @ResponseBody public User getUserById(
            @RequestParam("userId")String userId) {
        User user= ServiceFactory.getUserManageService().getUserById(Integer.parseInt(userId));
        return user;
    }

    @RequestMapping(value = "/update", method = RequestMethod.POST)
    @ResponseBody public User update(
            @RequestParam("userJson")String userJson) {
        User user= ServiceFactory.getUserManageService().updateUserInfo(userJson);
        return user;
    }




}
