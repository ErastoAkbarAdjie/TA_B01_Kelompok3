package apap.tugasakhir.SIRETAIL.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Entity
@Table(name = "item_cabang")

public class ItemCabangModel implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @GeneratedValue(generator = "system-uuid")
    @GenericGenerator(name = "system-uuid", strategy = "uuid")
    private String uuid;

    @NotNull
    @Size(max=50)
    @Column(name="nama", nullable = false)
    private String nama;

    @NotNull
    @Column (name="harga", nullable = false)
    private Integer harga;

    @NotNull
    @Column (name="stok", nullable = false)
    private Integer stok;

    @NotNull
    @Size(max=500)
    @Column(name="kategori", nullable = false)
    private String kategori;

    //Relasi dengan CabangModel
    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "id_cabang", referencedColumnName = "id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private CabangModel cabang;

    //Relasi dengan Kupon (SI-BUSINESS)
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "id_coupon", referencedColumnName = "id")
    private CouponModel coupon;
}
