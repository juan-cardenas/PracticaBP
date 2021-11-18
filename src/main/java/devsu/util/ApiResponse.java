package devsu.util;

public class ApiResponse {

    private String status;
    private String message;
    private Object objecto;

    public ApiResponse(String status, String message) {
        this.status = status;
        this.message = message;
    }

    public ApiResponse(String status, Object objecto) {
        this.status = status;
        this.objecto = objecto;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Object getObjecto() {
        return objecto;
    }

    public void setObjecto(Object objecto) {
        this.objecto = objecto;
    }
}
