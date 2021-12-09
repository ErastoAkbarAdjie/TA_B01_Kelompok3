package apap.tugasakhir.SIRETAIL.service;


import apap.tugasakhir.SIRETAIL.model.CabangModel;
import apap.tugasakhir.SIRETAIL.model.ItemCabangModel;
import apap.tugasakhir.SIRETAIL.model.UserModel;
import apap.tugasakhir.SIRETAIL.repository.CabangDb;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
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
    public List<CabangModel> getListCabangRequested( List<CabangModel> listCabang) {
        List<CabangModel> listRequested = new ArrayList<>();
        for (int i=0; i<listCabang.size();i++) {
            if (listCabang.get(i).getStatus()==0) {
                listRequested.add(listCabang.get(i));
            }
        }
        return (listRequested);
    }
    @Override
    public CabangModel getCabangByIdCabang(Integer idCabang) {
        Optional<CabangModel> cabang = cabangDb.findById(idCabang);
        if (cabang.isPresent()) return cabang.get();
        else return null;
    }

    @Override
    public CabangModel updateCabang(CabangModel cabang) {
        cabangDb.save(cabang);
        return cabang;
    }
    
    @Override
    public String deleteCabang(CabangModel cabang) {
        List <ItemCabangModel> itemcabang = cabang.getListItemCabang();
        if (itemcabang.size() == 0) {
            cabangDb.delete(cabang);
            return "Cabang berhasil dihapus";
        }
        else if (cabang.getStatus() ==0) {
            cabangDb.delete(cabang);
            return "Cabang berhasil dihapus";
        }
        else {
            return "Masih terdapat item di dalam cabang";
        }
    }

    @Override
    public String tolakCabang(CabangModel cabang) {
        String namaCabang = cabang.getNama();
        cabangDb.delete(cabang);
        return ("Cabang " + namaCabang + " berhasil di tolak ");

    }
}