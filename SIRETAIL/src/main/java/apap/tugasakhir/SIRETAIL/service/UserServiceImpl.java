package apap.tugasakhir.SIRETAIL.service;

import apap.tugasakhir.SIRETAIL.model.UserModel;
import apap.tugasakhir.SIRETAIL.repository.UserDb;
import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Optional;

@Service
@Transactional
public class UserServiceImpl implements UserService {
    @Autowired
    private UserDb userDb;

    @Override
    public UserModel addUser(UserModel user) {
//        String pass = encrypt(user.getPassword());
//        user.setPassword(pass);
        return userDb.save(user);
    }

//    @Override
//    public String encrypt(String password) {
//        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
//        String passwordHash = passwordEncoder.encode(password);
//        return passwordHash;
//    }

    @Override
    public UserModel getUserByUsername(String username) {
        Optional<UserModel> user = userDb.findByUsername(username);
        if (user.isPresent()) return user.get();
        else return null;
    }
}
