package uz.expense.api.models;

import uz.expense.api.db.beans.UserBean;

import java.util.Date;

public class AppSession extends BaseModel {
    private Date creationTime;
    private Date lastAccessedTime;
    private String webSessionId;
    private UserBean user;


    public AppSession() {
    }

    public Date getCreationTime() {
        return creationTime;
    }

    public void setCreationTime(Date creationTime) {
        this.creationTime = creationTime;
    }

    public Date getLastAccessedTime() {
        return lastAccessedTime;
    }

    public void setLastAccessedTime(Date lastAccessedTime) {
        this.lastAccessedTime = lastAccessedTime;
    }

    public String getWebSessionId() {
        return webSessionId;
    }

    public void setWebSessionId(String webSessionId) {
        this.webSessionId = webSessionId;
    }

    public UserBean getUser() {
        return user;
    }

    public void setUser(UserBean user) {
        this.user = user;
    }
}
