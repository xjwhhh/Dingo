package dao;

import dao.bean.*;

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

    public static TicketManagerDao getTicketManagerDao() {
        return TicketManagerDaoBean.getInstance();
    }

    public static OrderDao getOrderDao() {
        return OrderDaoBean.getInstance();
    }
}
