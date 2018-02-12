package service;

import model.Show;
import model.ShowType;

import java.util.List;

public interface ShowManageService {
    Show getShowById(int showId);

    List<Show> getShowByType(ShowType showType);


}
