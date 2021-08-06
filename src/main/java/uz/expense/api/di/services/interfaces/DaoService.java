package uz.expense.api.di.services.interfaces;

import org.apache.ibatis.exceptions.PersistenceException;
import uz.expense.api.models.DataList;

import java.util.Map;

public interface DaoService {

    <T> T getOne(String sql, Map<String, Object> params) throws PersistenceException;

    <T> DataList<T> getList(String sqlCnt, String sql, Map<String, Object> params) throws PersistenceException;

    <T> DataList<T> getListAll(String sql, Map<String, Object> params) throws PersistenceException;

    <T> int insert(String sqlInsert, T obj) throws PersistenceException;

    <T> int update(String sqlUpdate, T obj) throws PersistenceException;

    <T> int delete(String sqlDelete, T obj) throws PersistenceException;

    Map<String, Object> procedure(String sql, Map<String, Object> map) throws PersistenceException;
}
