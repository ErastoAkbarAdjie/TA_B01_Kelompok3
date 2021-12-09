package apap.tugasakhir.SIRETAIL.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Entity
@Table(name = "cabang")

public class CabangModel implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotNull
    @Size(max=30)
    @Column(name="nama", nullable = false)
    private String nama;

    @NotNull
    @Size(max=100)
    @Column(name="alamat", nullable = false)
    private String alamat;

    @NotNull
    @Column (name="ukuran", nullable = false)
    private Integer ukuran;

    @NotNull
    @Column (name="status", nullable = false)
    private Integer status;

    @NotNull
    @Size(max=20)
    @Column(name="no_telp", nullable = false)
    private String noTelp;

    //Relasi dengan ItemCabangModel
    @OneToMany(mappedBy = "cabang", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<ItemCabangModel> listItemCabang;

    //Relasi dengan UserModel
    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "penanggung_jawab", referencedColumnName = "id", nullable = true)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private UserModel user;
}
