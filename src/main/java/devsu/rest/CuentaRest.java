package devsu.rest;


import devsu.model.Cuenta;
import devsu.model.enums.EstadoApiResponseEnum;
import devsu.model.enums.EstadoErrorEnum;
import devsu.service.cust.CuentaControladorCustom;
import devsu.util.ApiResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/cuenta")
public class CuentaRest {

    @Autowired
    CuentaControladorCustom cuentaControladorCustom;

    public Logger logger = LoggerFactory.getLogger(this.getClass());

    @CrossOrigin(origins = "*")
    @RequestMapping(value = "/crear", method = RequestMethod.POST)
    @ResponseBody
    public ApiResponse crear(@RequestBody Cuenta cuenta) {
        try {
            return new ApiResponse("OK",
                    cuentaControladorCustom.crear(cuenta));
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
    public ApiResponse actualizar(@RequestBody Cuenta cuenta) {
        try {
            return new ApiResponse(EstadoApiResponseEnum.OK.getCodigo(),
                    cuentaControladorCustom.actualizar(cuenta));
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
                    cuentaControladorCustom.listarTodos());
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
    @RequestMapping(value = "/listarTodoPorPersona/{idPersona}", method = RequestMethod.GET)
    @ResponseBody
    public ApiResponse listarTodoPorPersona(@PathVariable(value="idPersona") Long idPersona) {
        try {
            return new ApiResponse(EstadoApiResponseEnum.OK.getCodigo(),
                    cuentaControladorCustom.listarTodoPorPersona(idPersona));
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
    @RequestMapping(value = "/listarTodoPorIdentificacion/{identificacion}", method = RequestMethod.GET)
    @ResponseBody
    public ApiResponse listarTodoPorIdentificacion(@PathVariable(value="identificacion") String identificacion) {
        try {
            return new ApiResponse(EstadoApiResponseEnum.OK.getCodigo(),
                    cuentaControladorCustom.listarTodoPorIdentificacion(identificacion));
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
