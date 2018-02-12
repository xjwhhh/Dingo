package dao;

import model.ResultMessage;
import model.Show;
import model.ShowType;

import java.util.List;

public interface ShowDao {
    ResultMessage save(Show show);

    Show findById(int showId);

    List<Show> findByType(ShowType showType);

    ResultMessage update(Show show);
}
