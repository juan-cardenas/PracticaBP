package devsu.model.enums;

public enum EstadoErrorEnum {

    VALIDACION("VALIDACION","Error de validacion"),
    EJECUCION("EJECUCION", "Error de ejecucion");

    private String codigo;
    private String descripcion;

    EstadoErrorEnum(String codigo, String descripcion) {
        this.codigo = codigo;
        this.descripcion = descripcion;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }
}
