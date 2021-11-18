package devsu.rest;


import devsu.model.Transaccion;
import devsu.model.enums.EstadoApiResponseEnum;
import devsu.model.enums.EstadoErrorEnum;
import devsu.service.cust.TransaccionControladorCustom;
import devsu.util.ApiResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/transaccion")
public class TransaccionRest {

    @Autowired
    TransaccionControladorCustom transaccionControladorCustom;

    public Logger logger = LoggerFactory.getLogger(this.getClass());

    @CrossOrigin(origins = "*")
    @RequestMapping(value = "/crear", method = RequestMethod.POST)
    @ResponseBody
    public ApiResponse crear(@RequestBody List<Transaccion> transacciones) {
        try {
            return new ApiResponse("OK",
                    transaccionControladorCustom.crear(transacciones));
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
    @RequestMapping(value = "/eliminar/{idTransaccion}", method = RequestMethod.GET)
    @ResponseBody
    public ApiResponse eliminar(@PathVariable(value="idTransaccion") Long idTransaccion) {
        try {
            return new ApiResponse(EstadoApiResponseEnum.OK.getCodigo(),
                    transaccionControladorCustom.eliminar(idTransaccion));
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
    @RequestMapping(value = "/listarTodoPorRango/{fechaInicio}/{fechaFin}", method = RequestMethod.GET)
    @ResponseBody
    public ApiResponse listarTodoPorRango(@PathVariable(value="fechaInicio") @DateTimeFormat(pattern="dd-MM-yyyy HH:mm") Date fechaInicio,
                                          @PathVariable(value="fechaFin") @DateTimeFormat(pattern="dd-MM-yyyy HH:mm") Date fechaFin) {
        try {
            return new ApiResponse(EstadoApiResponseEnum.OK.getCodigo(),
                    transaccionControladorCustom.listarTodoPorRango(fechaInicio, fechaFin));
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
