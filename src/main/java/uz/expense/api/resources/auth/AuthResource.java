package uz.expense.api.resources.auth;

import jakarta.ws.rs.*;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import uz.expense.api.consts.ErrorEnum;
import uz.expense.api.consts.urls.UriExpenseServer;
import uz.expense.api.db.beans.UserBean;
import uz.expense.api.models.AppSession;
import uz.expense.api.models.ErrorBuilder;
import uz.expense.api.utils.Utils;

import javax.annotation.security.PermitAll;
import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;

@PermitAll
@Path(UriExpenseServer.AUTH)
public class AuthResource {

    private static final Logger _logger = LogManager.getLogger(AuthResource.class);

    @Inject
    HttpSession httpSession;
    @Inject
    AppSession appSession;
    @Inject
    SqlSessionFactory db;
    @Inject
    ResourceBundle rb;

    @POST
    @Path("login")
    @Consumes({MediaType.APPLICATION_FORM_URLENCODED})
    @Produces({MediaType.APPLICATION_JSON})
    public Response login(
            @FormParam("login") String login,
            @FormParam("password") String password,
            @HeaderParam("user-agent") String userAgent,
            @Context HttpServletRequest request) {

        if (login == null || login.trim().isEmpty()) {
            return ErrorBuilder.newInstance(rb).ERROR(ErrorEnum.LOGIN_IS_EMPTY).build();
        }

        if (password == null || password.trim().isEmpty()) {
            return ErrorBuilder.newInstance(rb).ERROR(ErrorEnum.PASSWORD_IS_EMPTY).build();
        }

        try (SqlSession sqlSession = db.openSession()) {

            Map<String, Object> m = new HashMap<>();

            m.put("login", login);
            m.put("password", Utils.getStringDigest(password));

            List<UserBean> users = sqlSession.selectList("selectUsers", m);

            if (users == null || users.isEmpty()) {
                return ErrorBuilder.newInstance(rb).ERROR(ErrorEnum.LOGIN_PASSWORD_WRONG).build();
            }

            if (users.size() > 1) {
                return ErrorBuilder.newInstance(rb).ERROR(ErrorEnum.LOGIN_PASSWORD_WRONG).build();
            }

            UserBean user = users.get(0);

            if (user.getStatus() != 1) {
                return ErrorBuilder.newInstance(rb).ERROR(ErrorEnum.USER_STATE_DISABLED).build();
            }

            appSession.setUser(user);

            return Response.ok(user).build();

        } catch (Exception ex) {
            _logger.error(ex.getMessage(), ex);
            return ErrorBuilder.newInstance(rb).ERROR(ErrorEnum.DB_NOT_AVAILABLE).build();
        }
    }

    @POST
    @Path("logout")
    @Produces({MediaType.APPLICATION_JSON})
    public Response logout() {
        httpSession.invalidate();
        return Response.ok().build();
    }
}
