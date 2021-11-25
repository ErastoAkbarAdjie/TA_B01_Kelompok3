package apap.tugasakhir.SIRETAIL.service;

import apap.tugasakhir.SIRETAIL.model.CabangModel;

import java.util.List;

public interface CabangService {
    void addCabang(CabangModel cabang);
    List<CabangModel> getListCabang();
    CabangModel getCabangByIdCabang(Long idCabang);
}
