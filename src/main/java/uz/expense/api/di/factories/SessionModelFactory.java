package uz.expense.api.di.factories;


import jakarta.ws.rs.core.Context;
import org.glassfish.hk2.api.Factory;
import uz.expense.api.models.AppSession;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

public class SessionModelFactory implements Factory<AppSession> {

    @Context
    HttpServletRequest request;

    @Override
    public AppSession provide() {
        HttpSession httpSession = request.getSession();
        return (AppSession) httpSession.getAttribute("app_session");
    }

    @Override
    public void dispose(AppSession sessionBean) {

    }
}
