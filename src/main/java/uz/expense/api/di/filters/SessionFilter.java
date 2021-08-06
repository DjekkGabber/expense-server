package uz.expense.api.di.filters;


import jakarta.ws.rs.container.ContainerRequestContext;
import jakarta.ws.rs.container.ContainerRequestFilter;
import jakarta.ws.rs.container.PreMatching;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.ext.Provider;
import uz.expense.api.models.AppSession;

import javax.annotation.Priority;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@Provider
@PreMatching
@Priority(value = 1)
public class SessionFilter implements ContainerRequestFilter {

    @Context
    HttpServletRequest request;

    public SessionFilter() {
    }

    @Override
    public void filter(ContainerRequestContext requestContext) {

        HttpSession httpSession = request.getSession();
        AppSession appSession = (AppSession) httpSession.getAttribute("app_session");
        if (appSession == null) {
            appSession = new AppSession();
            httpSession.setAttribute("app_session", appSession);
        }
    }
}
