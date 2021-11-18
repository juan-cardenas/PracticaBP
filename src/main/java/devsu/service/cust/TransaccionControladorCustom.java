package devsu.service.cust;

import devsu.model.Transaccion;

import java.util.Date;
import java.util.List;

public interface TransaccionControladorCustom {

    public Boolean crear(List<Transaccion> transacciones) throws Exception;

    public Boolean eliminar(Long idTransaccion) throws Exception;

    public List<Transaccion> listarTodoPorRango(Date fechaInicio, Date fechaFin) throws Exception;
}
