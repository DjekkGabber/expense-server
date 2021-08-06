package uz.expense.api.di;

import org.apache.ibatis.session.SqlSessionFactory;
import org.glassfish.hk2.utilities.binding.AbstractBinder;
import org.glassfish.jersey.process.internal.RequestScoped;
import uz.expense.api.di.factories.DBFactory;
import uz.expense.api.di.factories.HttpSessionFactory;
import uz.expense.api.di.factories.ResourceBundleFactory;
import uz.expense.api.di.factories.SessionModelFactory;
import uz.expense.api.di.services.DaoServiceImpl;
import uz.expense.api.di.services.interfaces.DaoService;
import uz.expense.api.models.AppSession;

import javax.inject.Singleton;
import javax.servlet.http.HttpSession;
import java.util.ResourceBundle;

public class AppBinder extends AbstractBinder {

    /**
     * https://docs.huihoo.com/jersey/2.13/ioc.html
     */
    @Override
    protected void configure() {

        /*
        Factories
         */
        bindFactory(HttpSessionFactory.class).to(HttpSession.class).proxy(true).proxyForSameScope(false).in(RequestScoped.class);
        bindFactory(SessionModelFactory.class).to(AppSession.class).proxy(true).proxyForSameScope(false).in(RequestScoped.class);
        bindFactory(ResourceBundleFactory.class).to(ResourceBundle.class).proxy(true).proxyForSameScope(false).in(RequestScoped.class);
        bindFactory(DBFactory.class).to(SqlSessionFactory.class).proxy(true).proxyForSameScope(false).in(Singleton.class);

        /*
        Services
         */
        bind(DaoServiceImpl.class).to(DaoService.class).proxy(true).proxyForSameScope(false).in(Singleton.class);
    }
}
