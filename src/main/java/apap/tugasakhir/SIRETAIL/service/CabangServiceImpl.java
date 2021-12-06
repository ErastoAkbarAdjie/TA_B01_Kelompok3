package apap.tugasakhir.SIRETAIL.service;


import apap.tugasakhir.SIRETAIL.model.CabangModel;
import apap.tugasakhir.SIRETAIL.repository.CabangDb;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class CabangServiceImpl implements CabangService {

    @Autowired
    CabangDb cabangDb;

    @Override
    public void addCabang(CabangModel cabang) {
        cabangDb.save(cabang);
    }

    @Override
    public List<CabangModel> getListCabang() {
        return cabangDb.findAll();
    }

    @Override
    public CabangModel getCabangByIdCabang(Integer idCabang) {
        Optional<CabangModel> cabang = cabangDb.findById(idCabang);
        if (cabang.isPresent()) return cabang.get();
        else return null;
    }
}