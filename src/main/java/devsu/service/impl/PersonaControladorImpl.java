package devsu.service.impl;

import devsu.model.Persona;
import devsu.model.enums.EstadoErrorEnum;
import devsu.model.enums.EstadoEstaActivoEnum;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import devsu.repository.PersonaControlador;
import devsu.service.cust.PersonaControladorCustom;
import devsu.util.Validacion;

import java.util.List;

@Service
public class PersonaControladorImpl implements PersonaControladorCustom {

    public Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    PersonaControlador personaControlador;

    @Override
    public Boolean crear(Persona persona) throws Exception {
        Validacion.validadorDeCedula(persona.getIdentificacion());
        Persona personaSaved = personaControlador
                .findByIdentificacionAndEstaActivo(persona.getIdentificacion(),
                        EstadoEstaActivoEnum.SI.getCodigo());
        if (personaSaved != null) {
            throw new Exception(
                    EstadoErrorEnum.VALIDACION.getCodigo() + " Ya existe una persona con la cédula ingresada.");
        }
        persona.setEstaActivo("S");
        personaControlador.save(persona);
        return Boolean.TRUE;
    }


    @Override
    public Boolean actualizar(Persona persona) throws Exception {
        if (persona == null || persona.getIdPersona() == null) {
            throw new Exception(
                    EstadoErrorEnum.VALIDACION.getCodigo() + " El idPersona a actualizar es requerido");
        }

        Persona personaSaved = personaControlador
                .findByIdPersona(persona.getIdPersona());
        if (personaSaved == null) {
            throw new Exception(
                    EstadoErrorEnum.VALIDACION.getCodigo() + " No existe la persona");
        }
        Validacion.validadorDeCedula(persona.getIdentificacion());
        BeanUtils.copyProperties(persona, personaSaved);

        personaControlador.save(personaSaved);
        return Boolean.TRUE;
    }

    @Override
    public List<Persona> listarTodos() throws Exception {
        List<Persona> lstPersonas = personaControlador.findAllByEstaActivo(EstadoEstaActivoEnum.SI.getCodigo());
        lstPersonas.stream().forEach(Persona::limpiarRecursividad);
        return lstPersonas;
    }
}
