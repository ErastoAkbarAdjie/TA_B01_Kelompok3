package apap.tugasakhir.SIRETAIL.rest;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.*;

@AllArgsConstructor
@Setter
@Getter
@ToString
public class itemDetail {
  String uuid;
  String nama;
  Integer harga;
  Integer stok;
  String kategori;
}
