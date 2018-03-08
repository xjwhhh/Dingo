package service;

import model.ResultMessage;
import model.User;

import java.util.List;

public interface UserManageService {

    ResultMessage register(String account, String password);

    User login(String account, String password);

    User getUserById(int userId);

    ResultMessage updateUserInfo(String userJson);

    ResultMessage emailConfirmation(int userId, String emailAddress);

    ResultMessage emailReConfirmation(String emailAddress);

    List<User> getAllUsers();

    ResultMessage cancel(int userId);

}
