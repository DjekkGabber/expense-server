package uz.expense.api.resources.common;

import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.apache.commons.lang.StringUtils;
import org.apache.ibatis.session.RowBounds;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import uz.expense.api.consts.ErrorEnum;
import uz.expense.api.consts.urls.UriExpenseServer;
import uz.expense.api.db.beans.*;
import uz.expense.api.models.AppSession;
import uz.expense.api.models.DataList;
import uz.expense.api.models.DataPagingList;
import uz.expense.api.models.ErrorBuilder;
import uz.expense.api.utils.Utils;

import javax.annotation.security.PermitAll;
import javax.inject.Inject;
import java.security.Principal;
import java.text.SimpleDateFormat;
import java.util.*;

@PermitAll
@Path(UriExpenseServer.COMMON)
public class CommonResource {
    private static final Logger _logger = LogManager.getLogger(CommonResource.class);
    @Inject
    SqlSessionFactory db;
    @Inject
    AppSession appSession;
    @Inject
    ResourceBundle rb;


    @GET
    @Path("date")
    @Produces({MediaType.APPLICATION_JSON})
    public Response getServerDate() {

        SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy HH:mm:ss");

        try (SqlSession sqlSession = db.openSession()) {

            String currentDateTime = sqlSession.selectOne("getCurDateTime");
            if (currentDateTime == null || currentDateTime.isEmpty()) {
                return Response.ok(sdf.format(new Date())).build();
            }

            return Response.ok(currentDateTime).build();

        } catch (Exception ex) {
            _logger.error(ex.getMessage(), ex);
            return Response.ok(sdf.format(new Date())).build();
        }
    }

    @GET
    @Path("notifications")
    @Produces({MediaType.APPLICATION_JSON})
    public Response getUnReadNotifications() {

        UserBean user = appSession.getUser();

        if (user == null) {
            return ErrorBuilder.newInstance(rb).UNAUTHORIZED(ErrorEnum.USER_NOT_FOUND).build();
        }

        try (SqlSession sqlSession = db.openSession()) {

            Map<String, Object> map = new HashMap<>();
            map.put("users_id", user.getId());
            map.put("is_read", 0);
            map.put("orderby_", "a.id desc");

            List<NotificationBean> notifications = sqlSession.selectList("selectNotifications", map);

            if (notifications == null) {
                notifications = new ArrayList<>();
            }

            DataList<NotificationBean> items = new DataList<>();
            items.setRows(notifications);
            items.setTotal(notifications.size());

            return Utils.toResponse(items);

        } catch (Exception ex) {
            _logger.error(ex.getMessage(), ex);
            return ErrorBuilder.newInstance(rb).ERROR(ErrorEnum.DB_NOT_AVAILABLE).build();
        }
    }

    @GET
    @Path("my-transactions")
    @Produces({MediaType.APPLICATION_JSON})
    public Response getMyTransactions(@QueryParam("page") Integer cur_page,
                                      @QueryParam("perPage") Integer per_page,
                                      @QueryParam("type") Integer type) {

        UserBean user = appSession.getUser();

        if (user == null) {
            return ErrorBuilder.newInstance(rb).UNAUTHORIZED(ErrorEnum.USER_NOT_FOUND).build();
        }

        try (SqlSession sqlSession = db.openSession()) {

            RowBounds rowbounds;
            Integer currentPage = cur_page != null ? cur_page : 1;
            Integer itemsPerPage = per_page != null ? per_page : 20;

            if (currentPage == 0) {
                rowbounds = new RowBounds();
            } else {
                rowbounds = new RowBounds((currentPage - 1) * itemsPerPage, itemsPerPage);
            }

            Map<String, Object> map = new HashMap<>();
            map.put("users_id", user.getId());
            map.put("orderby_", "a.created_date desc");

            if (type != null) {
                map.put("type", type);
            }

            List<TransactionBean> transactions = sqlSession.selectList("selectTransactions", map, rowbounds);
            Integer totalRows = sqlSession.selectOne("selectTransactionsCnt", map);

            if (transactions == null) {
                transactions = new ArrayList<>();
            }

            DataPagingList<TransactionBean> items = new DataPagingList<>();
            items.setRows(transactions);
            items.setRows(transactions);
            items.setTotalPages((int) Math.ceil((double) totalRows / itemsPerPage));
            items.setCurrentPage(cur_page != null ? cur_page : 1);
            items.setTotalRows(totalRows);

            return Utils.toResponse(items);

        } catch (Exception ex) {
            _logger.error(ex.getMessage(), ex);
            return ErrorBuilder.newInstance(rb).ERROR(ErrorEnum.DB_NOT_AVAILABLE).build();
        }
    }

    @GET
    @Path("transaction-types")
    @Produces({MediaType.APPLICATION_JSON})
    public Response getTransactionTypes() {


        try (SqlSession sqlSession = db.openSession()) {

            List<TransactionTypeBean> types = sqlSession.selectList("selectTransactionTypes");

            if (types == null) {
                types = new ArrayList<>();
            }

            DataList<TransactionTypeBean> items = new DataList<>();
            items.setRows(types);
            items.setTotal(types.size());

            return Utils.toResponse(items);

        } catch (Exception ex) {
            _logger.error(ex.getMessage(), ex);
            return ErrorBuilder.newInstance(rb).ERROR(ErrorEnum.DB_NOT_AVAILABLE).build();
        }
    }

    @GET
    @Path("chart-data")
    @Produces({MediaType.APPLICATION_JSON})
    public Response getChartData() {

        UserBean user = appSession.getUser();

        if (user == null) {
            return ErrorBuilder.newInstance(rb).UNAUTHORIZED(ErrorEnum.USER_NOT_FOUND).build();
        }

        try (SqlSession sqlSession = db.openSession()) {

            List<ChartDataBean> data = sqlSession.selectList("getChartData");

            if (data == null) {
                data = new ArrayList<>();
            }

            DataList<ChartDataBean> items = new DataList<>();
            items.setRows(data);
            items.setTotal(data.size());

            return Utils.toResponse(items);

        } catch (Exception ex) {
            _logger.error(ex.getMessage(), ex);
            return ErrorBuilder.newInstance(rb).ERROR(ErrorEnum.DB_NOT_AVAILABLE).build();
        }
    }

    @POST
    @Path("my-transactions/add")
    @Consumes({MediaType.APPLICATION_JSON})
    @Produces({MediaType.APPLICATION_JSON})
    public Response createPaymentBack(AddExpenseBean bean) {

        UserBean user = appSession.getUser();

        if (user == null) {
            return ErrorBuilder.newInstance(rb).UNAUTHORIZED(ErrorEnum.USER_NOT_FOUND).build();
        }
        if (bean == null) {
            return ErrorBuilder.newInstance(rb).ERROR(ErrorEnum.PARAMETERS_NOT_FOUND).build();
        }
        if (bean.getType() == null) {
            return ErrorBuilder.newInstance(rb).ERROR(ErrorEnum.PARAMETERS_NOT_FOUND).build();
        }
        if (bean.getAmount() == null) {
            return ErrorBuilder.newInstance(rb).ERROR(ErrorEnum.PARAMETERS_NOT_FOUND).build();
        }
        if (bean.getDescription() == null || bean.getDescription().trim().isEmpty()) {
            return ErrorBuilder.newInstance(rb).ERROR(ErrorEnum.PARAMETERS_NOT_FOUND).build();
        }

        TransactionBean transact = new TransactionBean();
        transact.setUsers_id(user.getId());
        transact.setAmount(bean.getAmount());
        transact.setTransaction_types_id(bean.getType());
        transact.setDescription(bean.getDescription());


        try (SqlSession sqlSession = db.openSession()) {

            int inserted = sqlSession.insert("insertTransaction", transact);

            if (inserted <= 0) {
                return ErrorBuilder.newInstance(rb).ERROR(ErrorEnum.DB_NOT_AVAILABLE).build();
            }

            return Response.ok(true).build();

        } catch (Exception ex) {
            _logger.error(ex.getMessage(), ex);
            return ErrorBuilder.newInstance(rb).ERROR(ErrorEnum.DB_NOT_AVAILABLE).build();
        }
    }
}

