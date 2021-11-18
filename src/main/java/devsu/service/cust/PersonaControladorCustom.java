package devsu.service.cust;

import devsu.model.Persona;

import java.util.List;

public interface PersonaControladorCustom {

    public Boolean crear(Persona persona) throws Exception;

    public Boolean actualizar(Persona persona) throws Exception;

    public List<Persona> listarTodos() throws Exception;
}
