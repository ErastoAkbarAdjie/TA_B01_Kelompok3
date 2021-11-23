package apap.tugasakhir.SIRETAIL.service;

import apap.tugasakhir.SIRETAIL.model.UserModel;

public interface UserService {

    UserModel addUser(UserModel user);
//    String encrypt(String password);
    UserModel getUserByUsername(String username);

}