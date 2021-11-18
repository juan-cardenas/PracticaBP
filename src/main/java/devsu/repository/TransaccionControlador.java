package devsu.repository;

import devsu.model.Transaccion;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface TransaccionControlador extends CrudRepository<Transaccion, Long> {

    Transaccion findByIdTransaccion(Long idTransaccion);

    Transaccion findByCodigoAndEstaActivo(String codigo, String codigo1);

    @Query(" FROM Transaccion t "
            + " WHERE :fechaInicio <= t.fecha "
            + " AND :fechaFin >= t.fecha "
            + " AND t.estaActivo = :estaActivo")
    List<Transaccion> buscarPorRangoFechas(@Param("fechaInicio") Date fechaInicio,
                                           @Param("fechaFin") Date fechaFin,
                                           @Param("estaActivo") String estaActivo);
}
