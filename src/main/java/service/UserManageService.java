package service;

import model.User;

import java.util.List;

public interface UserManageService {

    void register(String account, String password);

    User login(String account, String password);

    User getUserById(int userId);

    void emailConfirmation();

    List<User> getAllUsers();

}
