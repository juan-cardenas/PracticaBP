package devsu.util;



import com.google.gson.Gson;
import devsu.model.Cuenta;
import devsu.model.Transaccion;
import devsu.model.enums.EstadoErrorEnum;
import devsu.model.enums.EstadoEstaActivoEnum;
import devsu.repository.CuentaControlador;
import devsu.repository.TransaccionControlador;
import org.json.JSONObject;
import org.json.JSONTokener;
import org.springframework.beans.factory.annotation.Autowired;

import javax.ws.rs.client.*;
import javax.ws.rs.core.Response;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class Validacion {


    public static Boolean validadorDeCedula(String cedula) throws Exception {
        String res = "";
        String URLtext = "https://mocki.io/v1/2d5fc002-bc01-464e-a6d4-6958d96e0835";
        Boolean validacionCedula = false;
        try {

            java.net.URL url = new URL(URLtext);//your url i.e fetch data from .
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.setRequestProperty("Accept", "application/json");
            if (conn.getResponseCode() != 200) {
                throw new RuntimeException("Failed : HTTP Error code : "
                        + conn.getResponseCode());
            }
            InputStreamReader in = new InputStreamReader(conn.getInputStream());
            BufferedReader br = new BufferedReader(in);
            JSONTokener tokener = new JSONTokener(br);
            JSONObject json = new JSONObject(tokener);
            JSONObject jsonRegistroCivil = (JSONObject) json.get("registroCivil");
            validacionCedula = (Boolean) jsonRegistroCivil.get("validation");
//            System.out.println(json.get("registroCivil"));

            conn.disconnect();

        } catch (Exception e) {
            System.out.println("Exception in NetClientGet:- " + e);
        }
        return validacionCedula;
    }


    public static void validarDependenciasCuenta(Cuenta cuenta) throws Exception {
        if (cuenta.getPersona() == null){
            throw new Exception(
                    EstadoErrorEnum.VALIDACION.getCodigo() + " Debe asociar una persona a la cuenta.");
        }

        if (cuenta.getLstTransacciones() == null){
            throw new Exception(
                    EstadoErrorEnum.VALIDACION.getCodigo() + " Debe asociar una transaccion como minimo.");
        }

        if (cuenta.getLstTransacciones() != null && cuenta.getLstTransacciones().isEmpty()){
            throw new Exception(
                    EstadoErrorEnum.VALIDACION.getCodigo() + " Debe asociar una transaccion como minimo.");
        }

    }

    public static void validarTransaccionesPorCodigo(Cuenta cuenta, TransaccionControlador transaccionControlador) throws Exception {
        for (Transaccion transaccion: cuenta.getLstTransacciones()) {
            Transaccion transaccionSaved = transaccionControlador
                    .findByCodigoAndEstaActivo(transaccion.getCodigo(), EstadoEstaActivoEnum.SI.getCodigo());
            if (transaccionSaved != null) {
                throw new Exception(
                        EstadoErrorEnum.VALIDACION.getCodigo() + (" La transacción con codigo ")
                                .concat(transaccion.getCodigo()).concat(" ya existe."));
            }
        }
    }

    public static void validarDependenciasTransaccion(Transaccion transaccion, TransaccionControlador transaccionControlador, CuentaControlador cuentaControlador) throws Exception {
        if (transaccion.getCuenta() == null) {
            throw new Exception(
                    EstadoErrorEnum.VALIDACION.getCodigo() + "  Debe asociar una cuenta a la transacción.");
        }
        Cuenta cuentaSaved = cuentaControlador
                .findByIdCuenta(transaccion.getCuenta().getIdCuenta());
        if (cuentaSaved == null) {
            throw new Exception(
                    EstadoErrorEnum.VALIDACION.getCodigo() + " No existe la cuenta");
        }
        Transaccion transaccionSaved = transaccionControlador
                .findByCodigoAndEstaActivo(transaccion.getCodigo(), EstadoEstaActivoEnum.SI.getCodigo());
        if (transaccionSaved != null) {
            throw new Exception(
                    EstadoErrorEnum.VALIDACION.getCodigo() + (" La transacción con codigo ")
                            .concat(transaccion.getCodigo()).concat(" ya existe."));
        }
    }
}
