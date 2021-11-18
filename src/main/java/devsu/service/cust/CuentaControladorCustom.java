package devsu.service.cust;

import devsu.model.Cuenta;

import java.util.List;

public interface CuentaControladorCustom {

    public Boolean crear(Cuenta cuenta) throws Exception;

    public Boolean actualizar(Cuenta cuenta) throws Exception;

    public List<Cuenta> listarTodos() throws Exception;

    public List<Cuenta> listarTodoPorPersona(Long idPersona) throws Exception;

    public List<Cuenta> listarTodoPorIdentificacion(String identificacion) throws Exception;
}
