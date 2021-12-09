package apap.tugasakhir.SIRETAIL.service;

import apap.tugasakhir.SIRETAIL.model.CabangModel;
import apap.tugasakhir.SIRETAIL.model.UserModel;

import java.util.List;

public interface CabangService {
    void addCabang(CabangModel cabang);
    List<CabangModel> getListCabang();
    CabangModel getCabangByIdCabang(Integer idCabang);
    CabangModel updateCabang(CabangModel cabang);
    String deleteCabang(CabangModel cabang);
    List<CabangModel> getListCabangRequested( List<CabangModel> listCabang);
    String tolakCabang(CabangModel cabang);
}
