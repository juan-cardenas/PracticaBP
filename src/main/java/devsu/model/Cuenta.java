package devsu.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "cuenta")
@JsonIgnoreProperties({ "hibernateLazyInitializer", "handler" })
@Getter
@Setter
public class Cuenta implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_cuenta")
    private Long idCuenta;

    @NotNull
    @Column(name = "codigo", unique = true)
    private String codigo;

    @NotNull
    @Column(name = "fecha", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date fecha;

    @NotNull
    @Column(name = "monto")
    private BigDecimal monto;

//    @JsonBackReference
    @JoinColumn(name = "id_persona", referencedColumnName = "id_persona",
            foreignKey = @ForeignKey(name = "fk_cuenta_persona"))
    @ManyToOne(fetch = FetchType.LAZY)
    private Persona persona;

    @Where(clause = "esta_activo = 'S' ")
    @OneToMany(fetch=FetchType.LAZY, mappedBy="cuenta")
    private List<Transaccion> lstTransacciones;

    @NotNull
    @Column(name = "esta_activo", length = 1, nullable = false)
    private String estaActivo;

    public Cuenta limpiarRecursividad() {
        if (this.getPersona() != null) {
            this.getPersona().setLstCuentas(null);
        }
        if (this.getLstTransacciones() != null) {
            for (Transaccion transaccion: this.getLstTransacciones()) {
                transaccion.setCuenta(null);
            }
        }
        return this;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Cuenta cuenta = (Cuenta) o;
        return idCuenta.equals(cuenta.idCuenta);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idCuenta);
    }
}
