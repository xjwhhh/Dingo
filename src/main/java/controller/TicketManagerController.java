package controller;

import model.TicketManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import service.bean.TicketManagerServiceBean;

@CrossOrigin
@Controller
@RequestMapping("ticketManager")
public class TicketManagerController {
    @Autowired
    TicketManagerServiceBean ticketManagerServiceBean;

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    @ResponseBody
    public TicketManager Login(
            @RequestParam("account") String account,
            @RequestParam("password") String password) {
        return ticketManagerServiceBean.login(account, password);
    }

}
