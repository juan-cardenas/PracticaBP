package devsu.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.List;

@Entity
@Table(name = "persona")
@JsonIgnoreProperties({ "hibernateLazyInitializer", "handler" })
@Getter
@Setter
public class Persona implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_persona")
    private Long idPersona;

    @NotNull
    @Column(name = "identificacion", length = 10)
    private String identificacion;

    @NotNull
    @Column(name = "nombres")
    private String nombres;

    @NotNull
    @Column(name = "apellidos")
    private String apellidos;

    @Column(name = "email")
    private String email;

    @NotNull
    @Column(name = "direccion")
    private String direccion;

    @Where(clause = "esta_activo = 'S' ")
    @OneToMany(fetch=FetchType.LAZY, mappedBy="persona")
    private List<Cuenta> lstCuentas;

    @NotNull
    @Column(name = "esta_activo", length = 1, nullable = false)
    private String estaActivo;

    public Persona limpiarRecursividad() {
        if (this.getLstCuentas() != null && !this.getLstCuentas().isEmpty()) {
            for (Cuenta cuenta : this.getLstCuentas()) {
                cuenta.limpiarRecursividad();
            }
        }

        return this;
    }

}
