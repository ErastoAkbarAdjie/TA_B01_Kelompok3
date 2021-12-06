package apap.tugasakhir.SIRETAIL.repository;

import apap.tugasakhir.SIRETAIL.model.ItemCabangModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;


public interface ItemCabangDb extends JpaRepository<ItemCabangModel, Integer> {
    Optional<ItemCabangModel> findById(Integer id);
    Optional<ItemCabangModel> findByUuid(String uuid);
}
