package uz.expense.api.models;

import jakarta.ws.rs.core.Response;
import uz.expense.api.consts.ErrorEnum;

import java.util.ResourceBundle;

public class ErrorBuilder {
    private int status;
    private int code;
    private String message;
    private String description;

    private ResourceBundle rb;

    private ErrorBuilder(ResourceBundle resourceBundle) {
        this.rb = resourceBundle;
    }

    public static ErrorBuilder newInstance(ResourceBundle resourceBundle) {
        return new ErrorBuilder(resourceBundle);
    }

    /**
     * This method use for any error
     */
    public ErrorBuilder ERROR(ErrorEnum errorEnum) {
        return ERROR(errorEnum, null);
    }

    public ErrorBuilder ERROR(ErrorEnum errorEnum, String description) {
        this.status = ErrorEnum.HTTP_STATUS_ERROR_CODE;
        this.code = errorEnum.getCode();
        this.message = errorEnum.getMessage(rb);
        this.description = description;
        return this;
    }

    public ErrorBuilder ERROR(Integer errorCode, String errorMsg) {
        this.status = ErrorEnum.HTTP_STATUS_ERROR_CODE;
        this.code = errorCode;
        this.message = errorMsg;
        this.description = null;
        return this;
    }

    /**
     * This method only for user not in session
     */
    public ErrorBuilder UNAUTHORIZED(ErrorEnum errorEnum) {
        return UNAUTHORIZED(errorEnum, null);
    }

    public ErrorBuilder UNAUTHORIZED(ErrorEnum errorEnum, String description) {
        return ANY_ERROR(Response.Status.UNAUTHORIZED.getStatusCode(), errorEnum, description);
    }

    /**
     * This is custom error
     * If you want to make custom error then use it, else
     * !!! don't use it
     */
    public ErrorBuilder ANY_ERROR(int status, ErrorEnum errorEnum) {
        return ANY_ERROR(status, errorEnum, null);
    }

    public ErrorBuilder ANY_ERROR(int status, ErrorEnum errorEnum, String description) {
        this.status = status;
        this.code = errorEnum.getCode();
        this.message = errorEnum.getMessage(rb);
        this.description = description;
        return this;
    }
    public ErrorBuilder PARAMETER_NOT_FOUND(ErrorEnum error) {
        this.status = Response.Status.OK.getStatusCode();
        this.code = error.getCode();
        this.message = error.getMessage(rb);
        this.description = null;
        return this;
    }

    public Response build() {
        ErrorMsg entity = new ErrorMsg(status, code, message, description);
        return Response.status(status).entity(entity).build();
    }
}
