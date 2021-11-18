import devsu.Application;
import devsu.model.Cuenta;
import devsu.model.Persona;
import devsu.model.Transaccion;
import devsu.model.enums.EstadoEstaActivoEnum;
import devsu.repository.CuentaControlador;
import devsu.repository.TransaccionControlador;
import devsu.rest.CuentaRest;
import devsu.service.cust.CuentaControladorCustom;
import devsu.service.cust.PersonaControladorCustom;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.*;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doReturn;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;


@ExtendWith(SpringExtension.class)
@WebMvcTest(CuentaRest.class)
@ContextConfiguration(classes={Application.class})
public class CuentaServiceTest {


    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CuentaControladorCustom cuentaControladorCustom;

    @MockBean
    private CuentaControlador cuentaControlador;

    @MockBean
    private PersonaControladorCustom personaControladorCustom;

    @MockBean
    private TransaccionControlador transaccionControlador;

    @BeforeEach
    public void crearPersona() throws Exception {
        Persona persona = new Persona();
        persona.setIdPersona(1L);
        persona.setIdentificacion("0105764062");
        persona.setNombres("Juan");
        persona.setApellidos("Cardenas");
        persona.setDireccion("Juan Montalvo");
        persona.setEmail("jc@gmail.com");
        persona.setEstaActivo(EstadoEstaActivoEnum.SI.getCodigo());

        Cuenta cuenta = new Cuenta();
        cuenta.setIdCuenta(1L);
        cuenta.setPersona(persona);
        cuenta.setCodigo("C001-P001");
        cuenta.setFecha(new Date());
        cuenta.setMonto(new BigDecimal(10));
        cuenta.setEstaActivo(EstadoEstaActivoEnum.SI.getCodigo());
        Transaccion transaccion = new Transaccion();
        transaccion.setIdTransaccion(1L);
        transaccion.setCodigo("T001");
        transaccion.setTipo("1");
        transaccion.setFecha(new Date());
        transaccion.setMonto(new BigDecimal(10));
        transaccion.setEstaActivo(EstadoEstaActivoEnum.SI.getDescripcion());
        List<Transaccion> lstTransacciones = Arrays.asList(transaccion);
        cuenta.setLstTransacciones(lstTransacciones);
        Cuenta cuentaSaved = cuentaControlador.save(cuenta);

        ArgumentCaptor<Persona> captor = ArgumentCaptor.forClass(Persona.class);
        doReturn(Boolean.TRUE).when(personaControladorCustom).crear(any());
    }

    @Test
    public void testCrear() throws Exception {
        Persona persona = new Persona();
        persona.setIdPersona(1L);
        persona.setIdentificacion("0105764062");
        persona.setNombres("Juan");
        persona.setApellidos("Cardenas");
        persona.setDireccion("Juan Montalvo");
        persona.setEmail("jc@gmail.com");
        persona.setEstaActivo(EstadoEstaActivoEnum.SI.getCodigo());

        Cuenta cuenta = new Cuenta();
        cuenta.setIdCuenta(1L);
        cuenta.setPersona(persona);
        cuenta.setCodigo("C001-P001");
        cuenta.setFecha(new Date());
        cuenta.setMonto(new BigDecimal(10));
        cuenta.setEstaActivo(EstadoEstaActivoEnum.SI.getCodigo());
        Transaccion transaccion = new Transaccion();
        transaccion.setIdTransaccion(1L);
        transaccion.setCodigo("T001");
        transaccion.setTipo("1");
        transaccion.setFecha(new Date());
        transaccion.setMonto(new BigDecimal(10));
        transaccion.setEstaActivo(EstadoEstaActivoEnum.SI.getDescripcion());

        doReturn(transaccion).when(transaccionControlador).save(any());
        Transaccion transaccionSaved = transaccionControlador.save(transaccion);
        List<Transaccion> lstTransacciones = Arrays.asList(transaccionSaved);
        cuenta.setLstTransacciones(lstTransacciones);
        doReturn(cuenta).when(cuentaControlador).save(any());
        Cuenta cuentaSaved = cuentaControlador.save(cuenta);

        Assertions.assertNotNull(cuentaSaved);
        Assertions.assertNotNull(cuentaSaved.getIdCuenta());
        Assertions.assertNotNull(cuentaSaved.getCodigo());
        Assertions.assertNotNull(cuentaSaved.getPersona());
        Assertions.assertNotNull(cuentaSaved.getLstTransacciones());
        org.assertj.core.api.Assertions.assertThat(cuentaSaved)
                .extracting(Cuenta::getCodigo).isEqualTo("C001-P001");
        org.assertj.core.api.Assertions.assertThat(cuentaSaved)
                .extracting(Cuenta::getPersona).isInstanceOf(Persona.class);
        org.assertj.core.api.Assertions.assertThat(cuentaSaved)
                .extracting(Cuenta::getLstTransacciones).asList().size().isGreaterThan(0);
    }

    @Test
    public void testListarPorPersona() throws Exception {
        mockMvc.perform(get("/listarTodoPorPersona/1/")
                .header("token", "noTokenBP")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"status\":\"OK\",\"message\":\"null\",\"objecto\":\"**\"}"));
    }

}
