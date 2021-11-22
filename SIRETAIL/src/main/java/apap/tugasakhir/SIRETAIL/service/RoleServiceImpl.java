package apap.tugasakhir.SIRETAIL.service;

import apap.tugasakhir.SIRETAIL.model.RoleModel;
import apap.tugasakhir.SIRETAIL.repository.RoleDb;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@Transactional
public class RoleServiceImpl implements RoleService{

    @Autowired
    private RoleDb roleDb;

    @Override
    public List<RoleModel> getListRole() {
        return roleDb.findAll();
    }

}
