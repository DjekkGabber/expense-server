package uz.expense.api.db.beans;

import java.io.Serializable;

public class ChartDataBean implements Serializable {
    private String type;
    private Double summary;

    public ChartDataBean() {
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Double getSummary() {
        return summary;
    }

    public void setSummary(Double summary) {
        this.summary = summary;
    }
}
