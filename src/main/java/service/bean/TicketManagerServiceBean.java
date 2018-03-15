package service.bean;

import dao.ShowDao;
import dao.TicketManagerDao;
import model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import service.TicketManageService;

import java.util.List;

@Service
public class TicketManagerServiceBean implements TicketManageService {

    @Autowired
    TicketManagerDao ticketManagerDao;

    @Autowired
    ShowDao showDao;

    public TicketManager login(String account, String password) {
        return ticketManagerDao.find(account, password);
    }

    public List<ShowEarning> getUnSettledShowEarning() {
        return showDao.findUnSettledShowEarning();
    }

    public ResultMessage doSettle(int showEarningId) {
        ShowEarning showEarning = showDao.findShowEarningById(showEarningId);
        showEarning.setSettled(true);
        showDao.updateShowEarning(showEarning);

        Show show = showDao.findById(showEarning.getShowId());

        double earning = showEarning.getEarning();
        double venueEarning = earning * 0.7;
        double ticketEarning = earning * 0.3;

        VenueFinance venueFinance = new VenueFinance();
        venueFinance.setVenueId(show.getVenueId());
        venueFinance.setTicketManagerId(showEarning.getTicketManagerId());
        venueFinance.setCost(venueEarning);
        ticketManagerDao.save(venueFinance);

        TicketFinance ticketFinance = new TicketFinance();
        ticketFinance.setVenueId(show.getVenueId());
        ticketFinance.setTicketManagerId(showEarning.getTicketManagerId());
        ticketFinance.setCost(ticketEarning);
        ticketManagerDao.save(ticketFinance);

        return ResultMessage.SUCCESS;
    }

    public List<TicketFinance> getTicketFinanceList() {
        return ticketManagerDao.findTicketFinanceList();
    }
}
