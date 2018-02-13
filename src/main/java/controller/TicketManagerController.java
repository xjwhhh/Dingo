package controller;

import dao.bean.TicketManagerDaoBean;
import model.TicketManager;
import model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import service.ServiceFactory;
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
            @RequestParam("account")String account,
            @RequestParam("password")String password) {
        return ticketManagerServiceBean.login(account,password);
    }

}
