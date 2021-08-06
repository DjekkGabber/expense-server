package uz.expense.api.consts;

import java.util.ResourceBundle;

public enum ErrorEnum {

    SYSTEM_ERROR(-1, "error.system"),
    USER_NOT_FOUND(-2, "error.auth.user_not_found"),
    ACCESS_DENIED(-3, "error.access.block_users"),
    ACCESS_DENIED_RESOURCE(-4, "error.access.resource"),
    LOGIN_PASSWORD_WRONG(-5, "error.auth.login_password_wrong"),
    USER_STATE_DISABLED(-6, "error.auth.state_disabled"),
    DB_NOT_AVAILABLE(-7, "error.db.cannot_connect"),
    PARAMETERS_NOT_FOUND(-8, "error.app.parameters.not_found"),
    RESOURCE_NOT_FOUND(-9, "error.app.resources.not_found"),
    ERROR_EXECUTION_PROCESS(10, "error.execution"),
    PASSWORD_IS_EMPTY(12, "error.password.empty"),
    LOGIN_IS_EMPTY(11, "error.login.empty");

    public static final int HTTP_STATUS_ERROR_CODE = 555;

    private int code;
    private String message;

    ErrorEnum(int code, String message) {
        this.code = code;
        this.message = message;
    }

    public static ErrorEnum getByCode(Integer code) {
        if (code == null) {
            return SYSTEM_ERROR;
        }
        for (ErrorEnum value : values()) {
            if (value.getCode() == code) {
                return value;
            }
        }
        return SYSTEM_ERROR;
    }

    public int getCode() {
        return code;
    }

    public String getMessage(ResourceBundle rb) {
        return rb.getString(message);
    }
}
