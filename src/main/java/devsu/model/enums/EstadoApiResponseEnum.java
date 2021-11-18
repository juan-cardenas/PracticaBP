package devsu.model.enums;

public enum EstadoApiResponseEnum {
    OK("OK","Ok"),
    ERROR("ERROR", "Error");

    private String codigo;
    private String descripcion;

    EstadoApiResponseEnum(String codigo, String descripcion) {
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
