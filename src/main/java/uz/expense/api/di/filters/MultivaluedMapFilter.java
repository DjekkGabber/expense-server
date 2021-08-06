package uz.expense.api.di.filters;

import jakarta.ws.rs.container.ContainerRequestContext;
import jakarta.ws.rs.container.ContainerRequestFilter;
import jakarta.ws.rs.container.PreMatching;
import jakarta.ws.rs.core.UriBuilder;
import jakarta.ws.rs.ext.Provider;

import javax.annotation.Priority;

@Provider
@PreMatching
@Priority(value = 2)
public class MultivaluedMapFilter implements ContainerRequestFilter {


    @Override
    public void filter(ContainerRequestContext ctxRequest) {
        UriBuilder b = ctxRequest.getUriInfo().getRequestUriBuilder();
        ctxRequest.setRequestUri(b.build());
    }
}
