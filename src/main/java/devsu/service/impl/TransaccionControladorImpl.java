package devsu.service.impl;

import devsu.model.Cuenta;
import devsu.model.Transaccion;
import devsu.model.Persona;
import devsu.model.enums.EstadoErrorEnum;
import devsu.model.enums.EstadoEstaActivoEnum;
import devsu.repository.CuentaControlador;
import devsu.repository.PersonaControlador;
import devsu.repository.TransaccionControlador;
import devsu.service.cust.CuentaControladorCustom;
import devsu.service.cust.TransaccionControladorCustom;
import devsu.util.Validacion;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

@Service
public class TransaccionControladorImpl implements TransaccionControladorCustom {

    public Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    CuentaControlador cuentaControlador;

    @Autowired
    PersonaControlador personaControlador;

    @Autowired
    TransaccionControlador transaccionControlador;

    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = { Throwable.class })
    public Boolean crear(List<Transaccion> transacciones) throws Exception {
        for (Transaccion transaccion: transacciones) {
            Transaccion transaccionSaved = transaccionControlador
                    .findByCodigoAndEstaActivo(transaccion.getCodigo(), EstadoEstaActivoEnum.SI.getCodigo());
            if (transaccionSaved != null) {
                throw new Exception(
                        EstadoErrorEnum.VALIDACION.getCodigo() + " Ya existe una transacción con el código ingresado.");
            }
            Validacion.validarDependenciasTransaccion(transaccion, transaccionControlador, cuentaControlador);

            Cuenta cuentaSaved = cuentaControlador.findByIdCuenta(transaccion.getCuenta().getIdCuenta());

            transaccion.setCuenta(cuentaSaved);
            transaccion.setEstaActivo(EstadoEstaActivoEnum.SI.getCodigo());
            transaccionControlador.save(transaccion);
        }
        return Boolean.TRUE;
    }


    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = { Throwable.class })
    public Boolean eliminar(Long idTransaccion) throws Exception {
        if (idTransaccion == null) {
            throw new Exception(
                    EstadoErrorEnum.VALIDACION.getCodigo() + " El idTransaccion a eliminar es requerido");
        }

        Transaccion transaccionSaved = transaccionControlador
                .findByIdTransaccion(idTransaccion);
        if (transaccionSaved == null) {
            throw new Exception(
                    EstadoErrorEnum.VALIDACION.getCodigo() + " No existe la transaccion");
        }
        Cuenta cuentaSaved = transaccionSaved.getCuenta();
        if (cuentaSaved.getLstTransacciones().size() == 1) {
            cuentaSaved.setEstaActivo(EstadoEstaActivoEnum.NO.getCodigo());
        }
        Cuenta cuentaUpdate = cuentaControlador.save(cuentaSaved);
        transaccionSaved.setCuenta(cuentaUpdate);
        transaccionSaved.setEstaActivo(EstadoEstaActivoEnum.NO.getCodigo());

        transaccionControlador.save(transaccionSaved);
        return Boolean.TRUE;
    }

    @Override
    public List<Transaccion> listarTodoPorRango(Date fechaInicio, Date fechaFin) throws Exception {
        List<Transaccion> lstTransacciones = transaccionControlador
                .buscarPorRangoFechas(fechaInicio, fechaFin, EstadoEstaActivoEnum.SI.getCodigo());
        lstTransacciones.stream().forEach(Transaccion::limpiarRecursividad);
        return lstTransacciones;
    }

}
