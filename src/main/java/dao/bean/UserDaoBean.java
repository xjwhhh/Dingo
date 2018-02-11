package dao.bean;

import dao.UserDao;
import model.User;

public class UserDaoBean implements UserDao {

    private static UserDaoBean userDao=new UserDaoBean();

    public static UserDaoBean getInstance(){
        return userDao;
    }

    public User save(User user) {
        return user;

    }

    public User find(String account, String password) {
        return null;
    }

    public User findById(int userId) {
        return null;
    }

    public User update(User user) {
        return null;
    }
}
