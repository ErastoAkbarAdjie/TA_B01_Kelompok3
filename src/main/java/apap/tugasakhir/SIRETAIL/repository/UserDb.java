package apap.tugasakhir.SIRETAIL.repository;

import apap.tugasakhir.SIRETAIL.model.UserModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;


public interface UserDb extends JpaRepository<UserModel, Long> {
    UserModel findByUsername(String username);
    UserModel findById(Integer id);
}