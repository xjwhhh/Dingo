package service.bean;

import dao.TicketManagerDao;
import model.TicketManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import service.TicketManageService;

@Service
public class TicketManagerServiceBean implements TicketManageService {

    @Autowired
    TicketManagerDao ticketManagerDao;

    public TicketManager login(String account, String password) {
        return ticketManagerDao.find(account, password);
    }
}
