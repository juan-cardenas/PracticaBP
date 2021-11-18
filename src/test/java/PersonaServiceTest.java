import devsu.Application;
import devsu.model.Cuenta;
import devsu.model.Persona;
import devsu.model.Transaccion;
import devsu.model.enums.EstadoEstaActivoEnum;
import devsu.repository.PersonaControlador;
import devsu.rest.PersonaRest;
import devsu.service.cust.PersonaControladorCustom;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;


import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.mockito.Mockito.doReturn;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;



@ExtendWith(SpringExtension.class)
@WebMvcTest(PersonaRest.class)
@ContextConfiguration(classes={Application.class})
public class PersonaServiceTest{


    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private PersonaControladorCustom personaControladorCustom;

    @MockBean
    private PersonaControlador personaControlador;

    @Test
    public void crear() throws Exception {
        Persona persona = crearPersona();

        ArgumentCaptor<Persona> captor = ArgumentCaptor.forClass(Persona.class);
        doReturn(Boolean.TRUE).when(personaControladorCustom).crear(any());
    }

    @Test
    void testSave() {
        Persona persona = crearPersona();

        doReturn(persona).when(personaControlador).save(any());
        Persona returnedPersona = personaControlador.save(persona);

        Assertions.assertNotNull(returnedPersona);
        Assertions.assertNotNull(returnedPersona.getIdPersona());
        Assertions.assertEquals(10, returnedPersona.getIdentificacion().length());
        org.assertj.core.api.Assertions.assertThat(returnedPersona)
                .extracting(Persona::getIdentificacion).isEqualTo("0105764062");
        Assertions.assertNotNull(returnedPersona.getNombres());
        Assertions.assertNotNull(returnedPersona.getApellidos());
        Assertions.assertNotNull(returnedPersona.getEmail());
        Assertions.assertNotNull(returnedPersona.getEstaActivo());
        Assertions.assertNull(returnedPersona.getLstCuentas());
    }

    @Test
    public void testListarTodos() throws Exception {
        mockMvc.perform(get("/listarTodos")
                .header("token", "noTokenBP")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"status\":\"OK\",\"message\":\"null\",\"objecto\":\"**\"}"));
    }

    private Persona crearPersona(){
        Persona persona = new Persona();
        persona.setIdPersona(1L);
        persona.setIdentificacion("0105764062");
        persona.setNombres("Juan");
        persona.setApellidos("Cardenas");
        persona.setDireccion("Juan Montalvo");
        persona.setEmail("jc@gmail.com");
        persona.setEstaActivo(EstadoEstaActivoEnum.SI.getCodigo());
        return persona;
    }
}
