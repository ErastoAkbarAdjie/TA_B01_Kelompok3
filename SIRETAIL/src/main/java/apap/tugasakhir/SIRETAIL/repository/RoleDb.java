package apap.tugasakhir.SIRETAIL.repository;

import apap.tugasakhir.SIRETAIL.model.RoleModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

public interface RoleDb extends JpaRepository<RoleModel,Integer> {
    
}
