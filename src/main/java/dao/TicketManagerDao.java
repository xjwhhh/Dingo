package dao;

import model.TicketManager;

public interface TicketManagerDao {
    TicketManager find(String account, String password);
}
