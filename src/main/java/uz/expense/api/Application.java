package uz.expense.api;

import jakarta.ws.rs.ApplicationPath;
import org.glassfish.jersey.jackson.JacksonFeature;
import org.glassfish.jersey.media.multipart.MultiPartFeature;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.server.ServerProperties;
import uz.expense.api.di.AppBinder;
import uz.expense.api.di.filters.BaseFilter;
import uz.expense.api.di.providers.BaseProvider;
import uz.expense.api.resources.BaseResource;

@ApplicationPath("api")
public class Application extends ResourceConfig {
    public Application() {

        packages("uz.expense.api");
        property(ServerProperties.WADL_FEATURE_DISABLE, true);
        register(new AppBinder());
        packages(
                /* Resources */
                BaseResource.class.getPackage().getName(),

                /* Filters */
                BaseFilter.class.getPackage().getName(),

                /* Mappers */
                BaseProvider.class.getPackage().getName()
        );

        register(JacksonFeature.class);

        register(MultiPartFeature.class);
    }
}
