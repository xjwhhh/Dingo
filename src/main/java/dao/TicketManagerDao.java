package dao;

import model.ResultMessage;
import model.ShowEarning;
import model.TicketManager;

import java.util.List;

public interface TicketManagerDao extends BaseDao{
    TicketManager find(String account, String password);

    List<ShowEarning> findUnSettledShowEarning();

    ShowEarning findShowEarningById(int showEarningId);

    ResultMessage updateShowEarning(ShowEarning showEarning);

}
