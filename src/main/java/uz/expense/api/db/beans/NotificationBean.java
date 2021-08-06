package uz.expense.api.db.beans;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.io.Serializable;

public class NotificationBean implements Serializable {
    private Integer id;
    @JsonIgnore
    private Integer users_id;
    @JsonIgnore
    private Integer is_read;
    private String creation_time;
    private String message;

    public NotificationBean() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getUsers_id() {
        return users_id;
    }

    public void setUsers_id(Integer users_id) {
        this.users_id = users_id;
    }

    public Integer getIs_read() {
        return is_read;
    }

    public void setIs_read(Integer is_read) {
        this.is_read = is_read;
    }

    public String getCreation_time() {
        return creation_time;
    }

    public void setCreation_time(String creation_time) {
        this.creation_time = creation_time;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
