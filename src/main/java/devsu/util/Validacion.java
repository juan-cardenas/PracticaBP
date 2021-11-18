package devsu.util;


import devsu.model.Cuenta;
import devsu.model.Transaccion;
import devsu.model.enums.EstadoErrorEnum;
import devsu.model.enums.EstadoEstaActivoEnum;
import devsu.repository.CuentaControlador;
import devsu.repository.TransaccionControlador;
import org.springframework.beans.factory.annotation.Autowired;

public class Validacion {


    public static void validadorDeCedula(String cedula) throws Exception {
        boolean cedulaCorrecta = false;
        try {
            if (cedula.length() == 10) {
                int tercerDigito = Integer.parseInt(cedula.substring(2, 3));
                if (tercerDigito < 6) {
                        // Coeficientes de validación cédula
                    // El decimo digito se lo considera dígito verificador
                    int[] coefValCedula = {2, 1, 2, 1, 2, 1, 2, 1, 2};
                    int verificador = Integer.parseInt(cedula.substring(9, 10));
                    int suma = 0;
                    int digito = 0;
                    for (int i = 0; i < (cedula.length() - 1); i++) {
                        digito = Integer.parseInt(cedula.substring(i, i + 1)) * coefValCedula[i];
                        suma += ((digito % 10) + (digito / 10));
                    }
                    if ((suma % 10 == 0) && (suma % 10 == verificador)) {
                        cedulaCorrecta = true;
                    } else if ((10 - (suma % 10)) == verificador) {
                        cedulaCorrecta = true;
                    } else {
                        cedulaCorrecta = false;
                    }
                } else {
                    cedulaCorrecta = false;
                }
            } else {
                cedulaCorrecta = false;
            }
        } catch (NumberFormatException nfe) {
            cedulaCorrecta = false;
        } catch (Exception err) {
            cedulaCorrecta = false;
            throw new Exception(
                    EstadoErrorEnum.EJECUCION.getCodigo() + " Error en el proceso de validadcion");
        } finally {
            if (!cedulaCorrecta) {
                throw new Exception(
                        EstadoErrorEnum.VALIDACION.getCodigo() + " La Cédula ingresada es incorrecta");
            }
        }
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
