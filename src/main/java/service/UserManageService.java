package service;

import model.User;

import java.util.List;

public interface UserManageService {

    User register(String account, String password);

    User login(String account, String password);

    User getUserById(int userId);

    User updateUserInfo(String userJson);

    void emailConfirmation();

    List<User> getAllUsers();

}
