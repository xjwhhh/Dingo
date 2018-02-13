package dao;

import model.ResultMessage;
import model.User;

public interface UserDao extends BaseDao{
    ResultMessage save(User user);

    User find(String account, String password);

    User findById(int userId);

    ResultMessage update(User user);

}
