package uz.expense.api.models;

import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.annotation.JsonTypeName;
import jakarta.ws.rs.core.Response;
import uz.expense.api.consts.ErrorEnum;

@JsonTypeName(value = "ERROR")
@JsonTypeInfo(include = JsonTypeInfo.As.WRAPPER_OBJECT, use = JsonTypeInfo.Id.NAME)
public class ErrorMsg extends BaseModel {

    private final int status;
    private final int code;
    private final String message;

    private String description;

    public ErrorMsg(int status, int code, String message, String description) {
        this.status = status;
        this.code = code;
        this.message = message;
        this.description = description;
    }

    public ErrorMsg(int code, String message, String description) {
        this.status = Response.Status.FORBIDDEN.getStatusCode();
        this.code = code;
        this.message = message;
        this.description = description;
    }

    public ErrorMsg(Response.Status status, String message) {
        this.status = status.getStatusCode();
        this.code = ErrorEnum.SYSTEM_ERROR.getCode();
        this.message = message;
    }

    public ErrorMsg(Response responseStringEntity) {
        this.status = responseStringEntity.getStatus();
        this.code = ErrorEnum.SYSTEM_ERROR.getCode();
        this.message = (String) responseStringEntity.getEntity();
        this.description = responseStringEntity.getStatusInfo().getReasonPhrase();
    }

    public int getStatus() {
        return status;
    }

    public int getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }

    public String getDescription() {
        return description;
    }

    public Response generateResponse() {
        return Response.status(this.code).entity(this).build();
    }

}
