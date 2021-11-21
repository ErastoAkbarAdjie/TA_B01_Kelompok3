package apap.tugasakhir.SIRETAIL.repository;

import apap.tugasakhir.SIRETAIL.model.UserModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

public interface UserDb extends JpaRepository<UserModel,Integer> {
    
}
