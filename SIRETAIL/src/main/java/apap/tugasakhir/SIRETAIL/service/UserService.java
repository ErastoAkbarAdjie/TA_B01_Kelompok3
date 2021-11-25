package apap.tugasakhir.SIRETAIL.service;

import apap.tugasakhir.SIRETAIL.model.UserModel;

import java.util.List;

public interface UserService {

    UserModel addUser(UserModel user);
    String encrypt(String password);
    UserModel getUserByUsername(String username);
    List<UserModel> getListUser();
}