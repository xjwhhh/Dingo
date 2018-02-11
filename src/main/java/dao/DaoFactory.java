package dao;

import dao.bean.UserDaoBean;

public class DaoFactory {
    public static UserDao getUserDao(){
        return UserDaoBean.getInstance();
    }
}
