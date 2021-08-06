package uz.expense.api.di.providers;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.ext.ContextResolver;
import jakarta.ws.rs.ext.Provider;

import java.text.SimpleDateFormat;

@Provider
@Produces("application/json")
public class JacksonConfigurator implements ContextResolver<ObjectMapper> {
    private ObjectMapper mapper = new ObjectMapper();


    public JacksonConfigurator() {
        mapper.setDateFormat(new SimpleDateFormat("dd.MM.yyyy"));
    }

    @Override
    public ObjectMapper getContext(Class<?> arg0) {
        return mapper;
    }
}
