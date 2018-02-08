package controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@CrossOrigin
@Controller
@RequestMapping("user")
public class UserController {

    @RequestMapping(value = "/register", method = RequestMethod.GET)
    @ResponseBody public String getRegister() {
        return "register";
    }
}
