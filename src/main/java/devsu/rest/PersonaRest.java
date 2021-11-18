package devsu.rest;


import devsu.model.Persona;
import devsu.model.enums.EstadoApiResponseEnum;
import devsu.model.enums.EstadoErrorEnum;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import devsu.service.cust.PersonaControladorCustom;
import devsu.util.ApiResponse;

@RestController
@RequestMapping("/persona")
public class PersonaRest {

    @Autowired
    PersonaControladorCustom personaControladorCustom;

    public Logger logger = LoggerFactory.getLogger(this.getClass());

    @CrossOrigin(origins = "*")
    @RequestMapping(value = "/crear", method = RequestMethod.POST)
    @ResponseBody
    public ApiResponse crear(@RequestBody Persona persona) {
        try {
            return new ApiResponse("OK",
                    personaControladorCustom.crear(persona));
        } catch (Exception e) {
            String errorMensaje = "Error: " + this.getClass().getSimpleName() + "." + new Object() {
            }.getClass().getEnclosingMethod().getName() + ":";
            if (e.toString().contains(EstadoErrorEnum.EJECUCION.getCodigo())){
                errorMensaje += EstadoErrorEnum.EJECUCION.getCodigo();
            } else {
                errorMensaje += e.toString();
            }
            if (!errorMensaje.contains(EstadoErrorEnum.VALIDACION.getCodigo()))
                logger.error(errorMensaje, e);
            return new ApiResponse(EstadoApiResponseEnum.ERROR.getCodigo(),
                    errorMensaje);
        }
    }


    @CrossOrigin(origins = "*")
    @RequestMapping(value = "/actualizar", method = RequestMethod.POST)
    @ResponseBody
    public ApiResponse actualizar(@RequestBody Persona persona) {
        try {
            return new ApiResponse(EstadoApiResponseEnum.OK.getCodigo(),
                    personaControladorCustom.actualizar(persona));
        } catch (Exception e) {
            String errorMensaje = "Error: " + this.getClass().getSimpleName() + "." + new Object() {
            }.getClass().getEnclosingMethod().getName() + ":";
            if (e.toString().contains(EstadoErrorEnum.EJECUCION.getCodigo())){
                errorMensaje += EstadoErrorEnum.EJECUCION.getCodigo();
            } else {
                errorMensaje += e.toString();
            }
            if (!errorMensaje.contains(EstadoErrorEnum.VALIDACION.getCodigo()))
                logger.error(errorMensaje, e);
            return new ApiResponse(EstadoApiResponseEnum.ERROR.getCodigo(),
                    errorMensaje);
        }
    }


    @CrossOrigin(origins = "*")
    @RequestMapping(value = "/listarTodo", method = RequestMethod.GET)
    @ResponseBody
    public ApiResponse listarTodo() {
        try {
            return new ApiResponse(EstadoApiResponseEnum.OK.getCodigo(),
                    personaControladorCustom.listarTodos());
        } catch (Exception e) {
            String errorMensaje = "Error: " + this.getClass().getSimpleName() + "." + new Object() {
            }.getClass().getEnclosingMethod().getName() + ":";
            if (e.toString().contains(EstadoErrorEnum.EJECUCION.getCodigo())){
                errorMensaje += EstadoErrorEnum.EJECUCION.getCodigo();
            } else {
                errorMensaje += e.toString();
            }
            if (!errorMensaje.contains(EstadoErrorEnum.VALIDACION.getCodigo()))
                logger.error(errorMensaje, e);
            return new ApiResponse(EstadoApiResponseEnum.ERROR.getCodigo(),
                    errorMensaje);
        }
    }

}
