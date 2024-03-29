package service;

import model.ResultMessage;
import model.Show;
import model.ShowType;

import java.util.List;

public interface ShowManageService {
    ResultMessage publishShow(String showJson, String one, String two, String three);

    Show getShowById(int showId);

    List<Show> getPreSaleShowByType(String showType);

    ResultMessage distributeShowEarning(int showId);

    List<Show> getShowByVenueId(int venueId);

    List<Show> getShowByUserId(int userId);

}
