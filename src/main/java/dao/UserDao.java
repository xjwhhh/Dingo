package dao;

import model.ResultMessage;
import model.User;

public interface UserDao extends BaseDao {
    ResultMessage save(User user);

    User find(String account, String password);

    User findById(int userId);

    User findByEmail(String emailAddress);

    ResultMessage update(User user);

}
