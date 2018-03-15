package dao;

import model.TicketFinance;
import model.TicketManager;

import java.util.List;

public interface TicketManagerDao extends BaseDao {
    TicketManager find(String account, String password);

    List<TicketFinance> findTicketFinanceList();

}
