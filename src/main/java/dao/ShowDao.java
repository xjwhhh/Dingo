package dao;

import model.*;

import java.util.List;

public interface ShowDao extends BaseDao {
    ResultMessage save(Show show);

    Show findById(int showId);

    List<Show> findByType(String showType);

    List<Show> findByState(String progressType);

    ResultMessage update(Show show);

    List<Show> findByVenueId(int venueId);

    List<Show> findByUserId(int userId);

    ResultMessage saveShowSeat(ShowSeat showSeat);

    ShowSeat findShowSeat(int showSeatId);
//
//    List<ShowSeat> findShowSeatListByShowId(int showId);
//
//    List<ShowSeat> findUnbookedShowSeatListByShowId(int showId);

    List<ShowEarning> findUnSettledShowEarning();

    ShowEarning findShowEarningById(int showEarningId);

    ResultMessage updateShowEarning(ShowEarning showEarning);

    ShowEarning findShowEarningByShowId(int showId);

}
