package cn.itweknow.sbelkstart.db.model;

import java.util.Date;

public class WebLog {
    private Integer id;

    private String operationName;

    private String request;

    private String response;

    private Boolean error;

    private Long taketime;

    private Date createTime;

    private String stack;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getOperationName() {
        return operationName;
    }

    public void setOperationName(String operationName) {
        this.operationName = operationName == null ? null : operationName.trim();
    }

    public String getRequest() {
        return request;
    }

    public void setRequest(String request) {
        this.request = request == null ? null : request.trim();
    }

    public String getResponse() {
        return response;
    }

    public void setResponse(String response) {
        this.response = response == null ? null : response.trim();
    }

    public Boolean getError() {
        return error;
    }

    public void setError(Boolean error) {
        this.error = error;
    }

    public Long getTaketime() {
        return taketime;
    }

    public void setTaketime(Long taketime) {
        this.taketime = taketime;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getStack() {
        return stack;
    }

    public void setStack(String stack) {
        this.stack = stack == null ? null : stack.trim();
    }
}