package dao;

import model.User;

public interface UserDao {
    void save(User user);

    User find(String account, String password);

    User findById(int userId);

    void update(User user);

}
