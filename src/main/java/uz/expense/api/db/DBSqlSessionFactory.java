package uz.expense.api.db;

import org.apache.ibatis.builder.xml.XMLConfigBuilder;
import org.apache.ibatis.exceptions.ExceptionFactory;
import org.apache.ibatis.executor.ErrorContext;
import org.apache.ibatis.session.Configuration;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.defaults.DefaultSqlSessionFactory;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class DBSqlSessionFactory extends DefaultSqlSessionFactory {

    public DBSqlSessionFactory(Configuration configuration) {
        super(configuration);
    }

    @Override
    public SqlSession openSession() {
        return super.openSession(true);
    }


    public static class FactoryBuilder {

        public SqlSessionFactory build(InputStream inputStream) {
            return build(inputStream, null, null);
        }


        public SqlSessionFactory build(InputStream inputStream, String environment, Properties properties) {
            try {
                XMLConfigBuilder parser = new XMLConfigBuilder(inputStream, environment, properties);
                return build(parser.parse());
            } catch (Exception e) {
                throw ExceptionFactory.wrapException("Error building SqlSession.", e);
            } finally {
                ErrorContext.instance().reset();
                try {
                    inputStream.close();
                } catch (IOException e) {
                    // Intentionally ignore. Prefer previous ERROR.
                }
            }
        }

        public SqlSessionFactory build(Configuration config) {
            return new DBSqlSessionFactory(config);
        }
    }
}
