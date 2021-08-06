package uz.expense.api.db.beans;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.io.Serializable;

public class TransactionTypeBean implements Serializable {
    private Integer id;
    private String name;
    @JsonIgnore
    private Integer ordering;

    public TransactionTypeBean() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getOrdering() {
        return ordering;
    }

    public void setOrdering(Integer ordering) {
        this.ordering = ordering;
    }
}
