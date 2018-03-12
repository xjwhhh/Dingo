package dao;

import model.*;

import java.util.List;

public interface ShowDao extends BaseDao {
    ResultMessage save(Show show);

    Show findById(int showId);

    List<Show> findByType(String showType);

    ResultMessage update(Show show);

    List<Show> findByVenueId(int venueId);

    List<Show> findByUserId(int userId);

    ResultMessage saveShowSeat(ShowSeat showSeat);

}
