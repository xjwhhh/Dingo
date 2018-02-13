package service.bean;

import dao.DaoFactory;
import dao.ShowDao;
import model.Show;
import model.ShowType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import service.ShowManageService;

import java.util.List;

@Service
public class ShowManageServiceBean implements ShowManageService {


    @Autowired
    ShowDao showDao;


    public Show getShowById(int showId) {
        return showDao.findById(showId);
    }

    public List<Show> getShowByType(ShowType showType) {
        return showDao.findByType(showType);
    }
}
