package uz.expense.api.di.filters;

import jakarta.ws.rs.container.ContainerRequestContext;
import jakarta.ws.rs.container.ContainerRequestFilter;
import jakarta.ws.rs.container.ResourceInfo;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.ext.Provider;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import uz.expense.api.consts.ErrorEnum;
import uz.expense.api.models.AppSession;
import uz.expense.api.models.ErrorBuilder;

import javax.annotation.Priority;
import javax.annotation.security.DenyAll;
import javax.annotation.security.PermitAll;
import javax.inject.Inject;
import java.lang.reflect.Method;
import java.util.ResourceBundle;

@SuppressWarnings("Duplicates")
@Provider
@Priority(value = 1)
public class AuthenticationFilter implements ContainerRequestFilter {
    private static final Logger _logger = LogManager.getLogger(AuthenticationFilter.class);

    @Context
    ResourceInfo resourceInfo;

    @Inject
    ResourceBundle rb;
    @Inject
    AppSession appSession;

    @Override
    public void filter(ContainerRequestContext requestContext) {
        Class<?> cls = resourceInfo.getResourceClass();
        Method method = resourceInfo.getResourceMethod();

        if (cls.isAnnotationPresent(PermitAll.class) || method.isAnnotationPresent(PermitAll.class)) {
            return;
        }

        if (appSession.getUser() == null) {
            requestContext.abortWith(ErrorBuilder.newInstance(rb).UNAUTHORIZED(ErrorEnum.USER_NOT_FOUND).build());
            return;
        }

        if (method.isAnnotationPresent(DenyAll.class)) {
            requestContext.abortWith(ErrorBuilder.newInstance(rb).ERROR(ErrorEnum.ACCESS_DENIED).build());
            return;
        }
    }

}
