package uz.expense.api.di.factories;

import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.glassfish.hk2.api.Factory;
import uz.expense.api.db.DBSqlSessionFactory;

import java.io.IOException;
import java.io.InputStream;

public class DBFactory implements Factory<SqlSessionFactory> {

    private static Logger logger = LogManager.getLogger(DBFactory.class);

    @Override
    public SqlSessionFactory provide() {
        try (InputStream in = getClass().getResourceAsStream("/db/MySQLConfig.xml")) {
            DBSqlSessionFactory.FactoryBuilder sb = new DBSqlSessionFactory.FactoryBuilder();
            return sb.build(in);
        } catch (IOException ex) {
            logger.error(ex.getMessage());
            throw new RuntimeException(ex);
        }
    }

    @Override
    public void dispose(SqlSessionFactory instance) {

    }
}
