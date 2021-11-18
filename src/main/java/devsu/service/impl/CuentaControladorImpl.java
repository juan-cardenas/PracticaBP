package devsu.service.impl;

import devsu.model.Cuenta;
import devsu.model.Persona;
import devsu.model.Transaccion;
import devsu.model.enums.EstadoErrorEnum;
import devsu.model.enums.EstadoEstaActivoEnum;
import devsu.repository.CuentaControlador;
import devsu.repository.PersonaControlador;
import devsu.repository.TransaccionControlador;
import devsu.service.cust.CuentaControladorCustom;
import devsu.util.Validacion;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class CuentaControladorImpl implements CuentaControladorCustom {

    public Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    CuentaControlador cuentaControlador;

    @Autowired
    PersonaControlador personaControlador;

    @Autowired
    TransaccionControlador transaccionControlador;

    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = { Throwable.class })
    public Boolean crear(Cuenta cuenta) throws Exception {

        Cuenta cuentaSaved = cuentaControlador
                .findByCodigoAndEstaActivo(cuenta.getCodigo(), EstadoEstaActivoEnum.SI.getCodigo());
        if (cuentaSaved != null) {
            throw new Exception(
                    EstadoErrorEnum.VALIDACION.getCodigo() + " Ya existe una cuenta con el código ingresado.");
        }
        Validacion.validarDependenciasCuenta(cuenta);
        Validacion.validarTransaccionesPorCodigo(cuenta, transaccionControlador);

        Persona personaSaved = personaControlador.findByIdPersona(cuenta.getPersona().getIdPersona());
        if (personaSaved == null) {
            throw new Exception(
                    EstadoErrorEnum.VALIDACION.getCodigo() + " La persona ingresada no se encuentra registrada.");
        }
        cuenta.setPersona(personaSaved);
        cuenta.setEstaActivo(EstadoEstaActivoEnum.SI.getCodigo());
        cuenta.getLstTransacciones().forEach(transaccion ->
        {
            transaccion.setEstaActivo(EstadoEstaActivoEnum.SI.getCodigo());
        });
        List<Transaccion> lstTransacciones = cuenta.getLstTransacciones();
        cuenta.setLstTransacciones(null);
        cuentaSaved = cuentaControlador.save(cuenta);
        for (Transaccion transaccion: lstTransacciones) {
            transaccion.setCuenta(cuentaSaved);
        }
        transaccionControlador
                .saveAll(lstTransacciones);
//        cuenta.setLstTransacciones(lstTransacciones);
        return Boolean.TRUE;
    }


    @Override
    public Boolean actualizar(Cuenta cuenta) throws Exception {
        if (cuenta == null || cuenta.getIdCuenta() == null) {
            throw new Exception(
                    EstadoErrorEnum.VALIDACION.getCodigo() + " El idCuenta a actualizar es requerido");
        }

        Cuenta cuentaSaved = cuentaControlador
                .findByIdCuenta(cuenta.getIdCuenta());
        if (cuentaSaved == null) {
            throw new Exception(
                    EstadoErrorEnum.VALIDACION.getCodigo() + " No existe la cuenta");
        }
        Validacion.validarDependenciasCuenta(cuenta);

        Persona persona = personaControlador.findByIdPersona(cuenta.getPersona().getIdPersona());
        cuenta.setPersona(persona);

        BeanUtils.copyProperties(cuenta, cuentaSaved);

        cuentaControlador.save(cuentaSaved);
        return Boolean.TRUE;
    }

    @Override
    public List<Cuenta> listarTodos() throws Exception {
        List<Cuenta> lstCuentas = cuentaControlador.findAllByEstaActivo(EstadoEstaActivoEnum.SI.getCodigo());
        lstCuentas.stream().forEach(Cuenta::limpiarRecursividad);
        return lstCuentas;
    }

    @Override
    public List<Cuenta> listarTodoPorPersona(Long idPersona) throws Exception {
        Persona persona = personaControlador
                .findByIdPersonaAndEstaActivo(idPersona, EstadoEstaActivoEnum.SI.getCodigo());
        if (persona == null){
            throw new Exception(
                    EstadoErrorEnum.VALIDACION.getCodigo() + " No existe la persona o se encuentra inactiva");
        }
        List<Cuenta> lstCuentasPorPersona = cuentaControlador
                .findAllByIdPersonaAndEstaActivo(idPersona, EstadoEstaActivoEnum.SI.getCodigo());
        lstCuentasPorPersona.stream().forEach(Cuenta::limpiarRecursividad);
        return lstCuentasPorPersona;
    }

    @Override
    public List<Cuenta> listarTodoPorIdentificacion(String identificacion) throws Exception {
        Persona persona = personaControlador
                .findByIdentificacionAndEstaActivo(identificacion, EstadoEstaActivoEnum.SI.getCodigo());
        if (persona == null){
            throw new Exception(
                    EstadoErrorEnum.VALIDACION.getCodigo() + " No existe la persona o se encuentra inactiva");
        }
        List<Cuenta> lstCuentasPorPersona = cuentaControlador
                .findAllByIdentificacionAndEstaActivo(identificacion, EstadoEstaActivoEnum.SI.getCodigo());
        lstCuentasPorPersona.stream().forEach(Cuenta::limpiarRecursividad);
        return lstCuentasPorPersona;
    }
}
