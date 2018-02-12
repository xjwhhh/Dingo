package dao.bean;

import dao.TicketManagerDao;
import model.TicketManager;

public class TicketManagerDaoBean implements TicketManagerDao {

    private static TicketManagerDaoBean ticketManagerDao=new TicketManagerDaoBean();

    public static TicketManagerDaoBean getInstance(){
        return ticketManagerDao;
    }

    public TicketManager find(String account, String password) {
        return null;
    }
}
