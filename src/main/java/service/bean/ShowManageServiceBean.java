package service.bean;

import dao.DaoFactory;
import dao.ShowDao;
import model.Show;
import model.ShowType;
import service.ShowManageService;

import java.util.List;

public class ShowManageServiceBean implements ShowManageService {

    ShowDao showDao= DaoFactory.getShowDao();

    public Show getShowById(int showId) {
        return showDao.findById(showId);
    }

    public List<Show> getShowByType(ShowType showType) {
        return showDao.findByType(showType);
    }
}
