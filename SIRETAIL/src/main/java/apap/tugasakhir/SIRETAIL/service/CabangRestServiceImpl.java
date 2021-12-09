package apap.tugasakhir.SIRETAIL.service;

import apap.tugasakhir.SIRETAIL.model.CabangModel;
import apap.tugasakhir.SIRETAIL.repository.CabangDb;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import javax.transaction.Transactional;
import java.time.LocalTime;
import java.util.*;


@Service
@Transactional

public class CabangRestServiceImpl implements CabangRestService {
    @Autowired
    private CabangDb cabangDb;

    @Override
    public List<String> retrieveListCabang(){
        List<String> allAlamat = new ArrayList<>();
        for (int i = 0 ; i< cabangDb.findAll().size(); i++) {
            allAlamat.add(cabangDb.findAll().get(i).getAlamat());
        }
        return allAlamat;
    }

    @Override
    public List<Map<String,Object>> getAllAlamat (List<CabangModel> allCabang) {
        List<Map<String,Object>> listAlamat = new ArrayList<>(allCabang.size());


        for (int i = 0 ; i<cabangDb.findAll().size();i++) {
            HashMap<String,Object> isi=new HashMap<>();
            isi.put("id", cabangDb.findAll().get(i).getId());
            isi.put("alamat", cabangDb.findAll().get(i).getAlamat());
            listAlamat.add(isi);
        }
        return listAlamat;
    }

    @Override
    public List<CabangModel> getAllCabang() {
        return cabangDb.findAll();
    }

}
