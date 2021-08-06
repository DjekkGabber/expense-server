package uz.expense.api.di.providers;


import jakarta.ws.rs.WebApplicationException;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;
import uz.expense.api.models.ErrorMsg;

@Provider
public class GenericExceptionProvider implements ExceptionMapper<Throwable> {

    @Override
    public Response toResponse(Throwable ex) {
        Response.StatusType type = getStatusType(ex);

        ErrorMsg error = new ErrorMsg(
                type.getStatusCode(),
                type.getReasonPhrase(),
                ex.getLocalizedMessage());

        return Response.status(error.getCode())
                .entity(error)
                .type(MediaType.APPLICATION_JSON)
                .build();
    }


    private Response.StatusType getStatusType(Throwable ex) {

        if (ex instanceof WebApplicationException) {
            return ((WebApplicationException) ex).getResponse().getStatusInfo();
        } else {
            return Response.Status.INTERNAL_SERVER_ERROR;
        }
    }
}
