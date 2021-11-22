package apap.tugasakhir.SIRETAIL.repository;

import apap.tugasakhir.SIRETAIL.model.CouponModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


public interface CouponDb extends JpaRepository<CouponModel,Integer> {
    
}
