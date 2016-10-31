package com.heyuo.qy.event;

/**
 * Created by Administrator on 2016-10-31.
 */
public class ClientConsultEvent {
    private String customerServiceAgentId;
    private Boolean forbidden;
    private Object msg;

    public String getCustomerServiceAgentId() {
        return customerServiceAgentId;
    }

    public void setCustomerServiceAgentId(String customerServiceAgentId) {
        this.customerServiceAgentId = customerServiceAgentId;
    }

    public Boolean getForbidden() {
        return forbidden;
    }

    public void setForbidden(Boolean forbidden) {
        this.forbidden = forbidden;
    }

    public Object getMsg() {
        return msg;
    }

    public void setMsg(Object msg) {
        this.msg = msg;
    }
}
