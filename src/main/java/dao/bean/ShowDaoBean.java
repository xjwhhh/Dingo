package dao.bean;

import dao.ShowDao;
import model.ResultMessage;
import model.Show;
import model.ShowType;

import java.util.List;

public class ShowDaoBean implements ShowDao {

    private static ShowDaoBean showDao=new ShowDaoBean();

    public static ShowDaoBean getInstance(){
        return showDao;
    }

    public ResultMessage save(Show show) {
        return null;
    }

    public Show findById(int showId) {
        return null;
    }

    public List<Show> findByType(ShowType showType) {
        return null;
    }

    public ResultMessage update(Show show) {
        return null;
    }
}
