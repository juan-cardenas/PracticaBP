package devsu.repository;

import devsu.model.Cuenta;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CuentaControlador extends CrudRepository<Cuenta, Long> {

    Cuenta findByIdCuenta(Long idCuenta);

    Cuenta findByCodigoAndEstaActivo(String codigo, String codigo1);

    List<Cuenta> findAllByEstaActivo(String codigo);

    @Query(" FROM Cuenta c "
            + " WHERE c.persona.idPersona = :idPersona "
            + " AND c.estaActivo = :estaActivo")
    List<Cuenta> findAllByIdPersonaAndEstaActivo(@Param("idPersona") Long idPersona,
                                                 @Param("estaActivo") String estaActivo);

    @Query(" FROM Cuenta c "
            + " WHERE c.persona.identificacion = :identificacion "
            + " AND c.estaActivo = :estaActivo")
    List<Cuenta> findAllByIdentificacionAndEstaActivo(@Param("identificacion") String identificacion,
                                                      @Param("estaActivo") String estaActivo);
}
