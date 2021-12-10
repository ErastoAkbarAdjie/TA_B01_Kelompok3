package apap.tugasakhir.SIRETAIL.service;

import apap.tugasakhir.SIRETAIL.rest.itemDetail;
import org.springframework.http.HttpStatus;

public interface UpdateStokItemService {
    HttpStatus createRequest(itemDetail item, Integer tambahanStok, Integer idCabang);
}
