package uz.expense.api.models;

import java.util.List;

public class DataList<T> {
    private Integer total;

    private List<T> rows;

    public DataList() {
    }

    public Integer getTotal() {
        return total;
    }

    public void setTotal(Integer total) {
        this.total = total;
    }

    public List<T> getRows() {
        return rows;
    }

    public void setRows(List<T> rows) {
        this.rows = rows;
    }
}
