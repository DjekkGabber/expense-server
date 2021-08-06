package uz.expense.api.db.beans;

import java.io.Serializable;

public class AddExpenseBean implements Serializable {
    private Integer type;
    private Double amount;
    private String description;

    public AddExpenseBean() {
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
