import devsu.model.Persona;
import devsu.model.enums.EstadoEstaActivoEnum;
import devsu.rest.PersonaRest;
import devsu.service.cust.PersonaControladorCustom;
//import org.junit.Before;
//import org.junit.Test;
import devsu.util.Validacion;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
//import org.junit.runner.RunWith;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.Arrays;
import java.util.List;

@SpringBootTest(classes = PersonaRest.class)
public class PersonaTest {

    @MockBean
    private PersonaControladorCustom personaControladorCustom;


    @Test
    public void contextLoads() {
        Assertions.assertNotNull(personaControladorCustom);
    }

    @Test
    void testIdentificacion() throws Exception {
        Persona persona = new Persona();
        persona.setIdPersona(1L);
        persona.setIdentificacion("0105764062");
        persona.setNombres("Juan");
        persona.setApellidos("Cardenas");
        persona.setDireccion("Juan Montalvo");
        persona.setEmail("jc@gmail.com");
        persona.setEstaActivo(EstadoEstaActivoEnum.SI.getCodigo());
        Assertions.assertEquals(10, persona.getIdentificacion().length());
    }



}
