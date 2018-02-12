package controller;

import model.TicketManager;
import model.User;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import service.ServiceFactory;

@CrossOrigin
@Controller
@RequestMapping("ticketManager")
public class TicketManagerController {

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    @ResponseBody
    public TicketManager Login(
            @RequestParam("account")String account,
            @RequestParam("password")String password) {
        return ServiceFactory.getTicketManageService().login(account,password);
    }

}
