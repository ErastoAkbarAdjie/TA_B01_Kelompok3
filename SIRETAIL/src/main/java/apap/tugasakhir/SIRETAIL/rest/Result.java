package apap.tugasakhir.SIRETAIL.rest;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class Result<G> {
  String message;

  Integer status;

  G result;
}