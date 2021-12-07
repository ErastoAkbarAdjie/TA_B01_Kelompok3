package apap.tugasakhir.SIRETAIL.rest;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Setter
@Getter
public class UpdateStokItemPayload {
    String itemId;
    Integer stok;
}
