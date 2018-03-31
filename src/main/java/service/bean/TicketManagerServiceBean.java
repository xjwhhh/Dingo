package service.bean;

import dao.ShowDao;
import dao.TicketManagerDao;
import dao.VenueDao;
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
    VenueDao venueDao;

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

        double onlineEarning = showEarning.getOnlineEarning();
        double offlineEarning = showEarning.getOfflineEarning();
        double venueEarning = onlineEarning * 0.7;
        double ticketEarning = onlineEarning * 0.3;

        //venue线上收入记录
        VenueFinance onlineVenueFinance = new VenueFinance();
        onlineVenueFinance.setVenueId(show.getVenueId());
        onlineVenueFinance.setTicketManagerId(showEarning.getTicketManagerId());
        onlineVenueFinance.setCost(venueEarning);
        onlineVenueFinance.setOnLine(true);
        ticketManagerDao.save(onlineVenueFinance);

        //venue线下收入记录
        VenueFinance offlineVenueFinance = new VenueFinance();
        offlineVenueFinance.setVenueId(show.getVenueId());
        offlineVenueFinance.setTicketManagerId(showEarning.getTicketManagerId());
        offlineVenueFinance.setCost(offlineEarning);
        offlineVenueFinance.setOnLine(false);
        ticketManagerDao.save(offlineVenueFinance);

        //更新venue收入
        Venue venue = venueDao.findById(show.getVenueId());
        venue.setOnlineBalance(venue.getOnlineBalance() + venueEarning);
        venue.setOnlineBalance(venue.getOfflineBalance() + offlineEarning);
        venueDao.update(venue);

        //网站财务记录（只有线上）
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
