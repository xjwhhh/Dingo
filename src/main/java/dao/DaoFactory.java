package dao;

import dao.bean.ShowDaoBean;
import dao.bean.UserDaoBean;
import dao.bean.VenueDaoBean;

public class DaoFactory {
    public static UserDao getUserDao() {
        return UserDaoBean.getInstance();
    }

    public static ShowDao getShowDao() {
        return ShowDaoBean.getInstance();
    }

    public static VenueDao getVenueDao() {
        return VenueDaoBean.getInstance();
    }
}
