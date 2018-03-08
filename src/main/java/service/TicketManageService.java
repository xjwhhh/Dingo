package service;

import model.ResultMessage;
import model.ShowEarning;
import model.TicketManager;

import java.util.List;

public interface TicketManageService {
    TicketManager login(String account, String password);

    List<ShowEarning> getUnSettledShowEarning();

    ResultMessage doSettle(int showEarningId);

}
