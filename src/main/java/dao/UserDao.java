package dao;

import model.User;

public interface UserDao {
    User save(User user);

    User find(String account, String password);

    User findById(int userId);

    User update(User user);

}
