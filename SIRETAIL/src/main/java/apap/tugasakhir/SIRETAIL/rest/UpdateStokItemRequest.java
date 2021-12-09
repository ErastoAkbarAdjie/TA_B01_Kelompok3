package apap.tugasakhir.SIRETAIL.rest;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@AllArgsConstructor
@Setter
@Getter
public class UpdateStokItemRequest {
    String idItem;
    Integer idKategori;
    Integer tambahanStok;
    LocalDate tanggalRequest;
    Integer idCabang;
}
