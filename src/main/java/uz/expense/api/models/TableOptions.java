package uz.expense.api.models;

import jakarta.ws.rs.MatrixParam;
import org.apache.ibatis.session.RowBounds;


public class TableOptions {

    @MatrixParam("sortBy")
    private String sortBy;

    @MatrixParam("descending")
    private Boolean descending;

    @MatrixParam("page")
    private Integer page;

    @MatrixParam("perPage")
    private Integer perPage;

    public TableOptions() {
    }

    public String getSortBy() {
        return sortBy;
    }

    public void setSortBy(String sortBy) {
        this.sortBy = sortBy;
    }


    public Integer getPerPage() {
        return perPage;
    }

    public void setPerPage(Integer perPage) {
        this.perPage = perPage;
    }

    public Boolean getDescending() {
        return descending;
    }

    public void setDescending(Boolean descending) {
        this.descending = descending;
    }

    public Integer getPage() {
        return page;
    }

    public void setPage(Integer page) {
        this.page = page;
    }

    public RowBounds getRowBounds() {
        return new RowBounds((this.page - 1) * this.perPage, this.perPage);
    }
}
