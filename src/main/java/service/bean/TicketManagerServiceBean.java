package service.bean;

import dao.DaoFactory;
import dao.TicketManagerDao;
import model.TicketManager;
import service.TicketManageService;

public class TicketManagerServiceBean implements TicketManageService {

    TicketManagerDao ticketManagerDao= DaoFactory.getTicketManagerDao();

    public TicketManager login(String account, String password) {
        return ticketManagerDao.find(account,password);
    }
}
