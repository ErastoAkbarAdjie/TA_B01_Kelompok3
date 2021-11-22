package apap.tugasakhir.SIRETAIL.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Entity
@Table(name = "coupon")

public class CouponModel implements Serializable {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotNull
    @Size(max=20)
    @Column(name="coupon_nama", nullable = false)
    private String nama;

    @NotNull
    @Size(max=16)
    @Column(name="coupon_code", nullable = false)
    private String code;

    @NotNull
    @Column (name="discountAmount", nullable = false)
    private Float discount;

    @NotNull
    @Column(nullable = false)
    @DateTimeFormat(pattern="yyyy-MM-dd")
    private LocalDate ExpiryDate;

    @OneToOne(mappedBy = "coupon")
    private ItemCabangModel itemCabang;
}
