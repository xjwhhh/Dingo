package controller;

import model.*;
import net.sf.json.JSONArray;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import service.OrderManageService;
import service.bean.OrderManageServiceBean;
import service.bean.TicketManagerServiceBean;

import java.util.List;

@CrossOrigin
@Controller
@RequestMapping("ticketManager")
public class TicketManagerController {
    @Autowired
    TicketManagerServiceBean ticketManagerServiceBean;

    @Autowired
    OrderManageServiceBean orderManageServiceBean;

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    @ResponseBody
    public TicketManager Login(
            @RequestParam("account") String account,
            @RequestParam("password") String password) {
        TicketManager ticketManager = ticketManagerServiceBean.login(account, password);
        System.out.println(JSONArray.fromObject(ticketManager).toString());
        return ticketManager;
    }

    @RequestMapping(value = "/UnSettledShowEarning", method = RequestMethod.POST)
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

    @RequestMapping(value = "/ticketFinanceList", method = RequestMethod.POST)
    @ResponseBody
    public List<TicketFinance> getTicketFinanceList() {
        return ticketManagerServiceBean.getTicketFinanceList();
    }

    @RequestMapping(value = "/allocateTicket", method = RequestMethod.POST)
    @ResponseBody
    public ResultMessage allocateTicket() {
        return orderManageServiceBean.allocateTicket();
    }

}
