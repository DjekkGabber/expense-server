package uz.expense.api.db.beans;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.io.Serializable;

public class TransactionBean implements Serializable {
    private Integer id;
    @JsonIgnore
    private Integer transaction_types_id;
    private Double amount;
    private String creation_date;
    private String description;
    private String type;
    @JsonIgnore
    private Integer users_id;

    public TransactionBean() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getTransaction_types_id() {
        return transaction_types_id;
    }

    public void setTransaction_types_id(Integer transaction_types_id) {
        this.transaction_types_id = transaction_types_id;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public String getCreation_date() {
        return creation_date;
    }

    public void setCreation_date(String creation_date) {
        this.creation_date = creation_date;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Integer getUsers_id() {
        return users_id;
    }

    public void setUsers_id(Integer users_id) {
        this.users_id = users_id;
    }
}
