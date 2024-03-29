package controller;

import model.ResultMessage;
import model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import service.bean.UserManageServiceBean;

import java.util.List;

@CrossOrigin
@Controller
@RequestMapping("user")
public class UserController {

    @Autowired
    UserManageServiceBean userManageServiceBean;

    @RequestMapping(value = "/register", method = RequestMethod.POST)
    @ResponseBody
    public ResultMessage Register(
            @RequestParam("account") String account,
            @RequestParam("password") String password) {
        return userManageServiceBean.register(account, password);

    }

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    @ResponseBody
    public User Login(
            @RequestParam("account") String account,
            @RequestParam("password") String password) {
        return userManageServiceBean.login(account, password);

    }

    @RequestMapping(value = "/getUserById", method = RequestMethod.POST)
    @ResponseBody
    public User getUserById(
            @RequestParam("userId") String userId) {
        return userManageServiceBean.getUserById(Integer.parseInt(userId));

    }

    @RequestMapping(value = "/update", method = RequestMethod.POST)
    @ResponseBody
    public ResultMessage update(
            @RequestParam("userJson") String userJson) {
        return userManageServiceBean.updateUserInfo(userJson);

    }

    @RequestMapping(value = "/emailConfirmation", method = RequestMethod.POST)
    @ResponseBody
    public ResultMessage emailConfirmation(
            @RequestParam("userId") String userId,
            @RequestParam("emailAddress") String emailAddress) {
        return userManageServiceBean.emailConfirmation(Integer.parseInt(userId), emailAddress);
    }

    @RequestMapping(value = "/emailReConfirmation", method = RequestMethod.GET)
    @ResponseBody
    public ResultMessage emailReConfirmation(
            @RequestParam("emailAddress") String emailAddress) {
//        return ResultMessage.SUCCESS;
        return userManageServiceBean.emailReConfirmation(emailAddress);

    }

    @RequestMapping(value = "/cancel", method = RequestMethod.POST)
    @ResponseBody
    public ResultMessage cancel(
            @RequestParam("userId") String userId) {
        return userManageServiceBean.cancel(Integer.parseInt(userId));

    }

    @RequestMapping(value = "/userList", method = RequestMethod.POST)
    @ResponseBody
    public List<User> getUserList() {
        return userManageServiceBean.getAllUsers();
    }

    @RequestMapping(value = "/exchangeCoupon", method = RequestMethod.POST)
    @ResponseBody
    public ResultMessage exchangeCoupon(
            @RequestParam("userId") String userId,
            @RequestParam("couponType") String couponType,
            @RequestParam("couponNumber") String couponNumber
    ) {
        return userManageServiceBean.exchangeCoupon(Integer.parseInt(userId), Integer.parseInt(couponType), Integer.parseInt(couponNumber));
    }

}
