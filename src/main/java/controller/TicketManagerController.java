package controller;

import model.ResultMessage;
import model.ShowEarning;
import model.TicketManager;
import model.VenueApplication;
import net.sf.json.JSONArray;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import service.bean.TicketManagerServiceBean;

import java.util.List;

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
        TicketManager ticketManager=ticketManagerServiceBean.login(account, password);
        System.out.println(JSONArray.fromObject(ticketManager).toString());
        return ticketManager;
    }


    @RequestMapping(value = "/getUnSettledShowEarning", method = RequestMethod.POST)
    @ResponseBody
    public List<ShowEarning> getUnSettledShowEarning() {
        return ticketManagerServiceBean.getUnSettledShowEarning();
    }

    @RequestMapping(value = "/doSettle", method = RequestMethod.POST)
    @ResponseBody
    public ResultMessage doSettle(
            @RequestParam("showEarningId") String showWarningId) {
        return ticketManagerServiceBean.doSettle(Integer.parseInt(showWarningId));
    }



}
