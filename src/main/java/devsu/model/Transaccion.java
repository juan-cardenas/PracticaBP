package devsu.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.Objects;

@Entity
@Table(name = "transaccion")
@JsonIgnoreProperties({ "hibernateLazyInitializer", "handler" })
@Getter
@Setter
public class Transaccion implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_transaccion")
    private Long idTransaccion;

    @NotNull
    @Column(name = "codigo", nullable = false, unique = true)
    private String codigo;

    @NotNull
    @Column(name = "fecha", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date fecha;

    @NotNull
    @Column(name = "tipo", length = 1)
    private String tipo;//1/0 suma o resta

    @NotNull
    @Column(name = "monto")
    private BigDecimal monto;

    //@JsonBackReference
    @JoinColumn(name = "id_cuenta", referencedColumnName = "id_cuenta",
            foreignKey = @ForeignKey(name = "fk_transaccion_cuenta"))
    @ManyToOne(fetch = FetchType.LAZY)
    private Cuenta cuenta;


    /* Caracter que representa el estado del registro y permite determinar si está eliminado lógicamente
	 * S=Activo / N=Inactivo (Eliminado)
	 */
    @NotNull
    @Column(name = "esta_activo", length = 1, nullable = false)
    private String estaActivo;

    public Transaccion limpiarRecursividad() {
        if (this.getCuenta() != null) {
            this.getCuenta().limpiarRecursividad();
        }
        return this;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Transaccion that = (Transaccion) o;
        return idTransaccion.equals(that.idTransaccion);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idTransaccion);
    }
}
