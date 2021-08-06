package uz.expense.api.di.factories;

import org.glassfish.hk2.api.Factory;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import java.util.ResourceBundle;

public class ResourceBundleFactory implements Factory<ResourceBundle> {

    private final HttpServletRequest request;

    @Inject
    public ResourceBundleFactory(HttpServletRequest request) {
        this.request = request;
    }

    @Override
    public ResourceBundle provide() {
        return ResourceBundle.getBundle("Application");
    }

    @Override
    public void dispose(ResourceBundle instance) {

    }
}
