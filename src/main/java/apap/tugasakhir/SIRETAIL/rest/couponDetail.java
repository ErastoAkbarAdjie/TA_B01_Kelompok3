package apap.tugasakhir.SIRETAIL.rest;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.*;

import java.time.LocalDate;

@AllArgsConstructor
@Setter
@Getter
@ToString
public class couponDetail {
    Long id;
    String couponCode;
    String couponName;
    Double discountAmount;
    LocalDate expiryDate;
}
