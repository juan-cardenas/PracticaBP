package devsu.repository;

import devsu.model.Persona;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;


import java.util.List;

@Repository
public interface PersonaControlador extends CrudRepository<Persona, Long> {

    Persona findByIdPersona(Long idPersona);

    @Query(" FROM Persona p "
            + " WHERE p.estaActivo = :estaActivo")
    List<Persona> findAllByEstaActivo(@Param("estaActivo") String estaActivo);

    Persona findByIdentificacion(String identificacion);

    Persona findByIdentificacionAndEstaActivo(String identificacion, String codigo);

    Persona findByIdPersonaAndEstaActivo(Long idPersona, String codigo);

    @Query(value = " SELECT * FROM Persona p LIMIT 1", nativeQuery = true)
    Persona findOne();
}
