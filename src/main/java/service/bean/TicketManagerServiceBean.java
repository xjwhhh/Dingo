package service.bean;

import dao.TicketManagerDao;
import model.ResultMessage;
import model.ShowEarning;
import model.TicketManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import service.TicketManageService;

import java.util.List;

@Service
public class TicketManagerServiceBean implements TicketManageService {

    @Autowired
    TicketManagerDao ticketManagerDao;

    public TicketManager login(String account, String password) {
        return ticketManagerDao.find(account, password);
    }

    public List<ShowEarning> getUnSettledShowEarning() {
        return ticketManagerDao.findUnSettledShowEarning();
    }

    public ResultMessage doSettle(int showEarningId) {
        ShowEarning showEarning=ticketManagerDao.findShowEarningById(showEarningId);
        showEarning.setSettled(true);
        return ticketManagerDao.updateShowEarning(showEarning);
    }
}
