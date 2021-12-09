package apap.tugasakhir.SIRETAIL.service;

import apap.tugasakhir.SIRETAIL.model.CabangModel;
import java.util.List;
import java.util.Map;

public interface CabangRestService {
    List<String> retrieveListCabang();
    List<CabangModel> getAllCabang();
    List<Map<String,Object>> getAllAlamat (List<CabangModel> allCabang);
}
