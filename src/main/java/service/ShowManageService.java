package service;

import model.ResultMessage;
import model.Show;
import model.ShowType;

import java.util.List;

public interface ShowManageService {
    Show getShowById(int showId);

    List<Show> getShowByType(ShowType showType);

    ResultMessage distributeShowEarning(int showId);

    List<Show> getShowByVenueId(int venueId);

    List<Show> getShowByUserId(int userId);

}
