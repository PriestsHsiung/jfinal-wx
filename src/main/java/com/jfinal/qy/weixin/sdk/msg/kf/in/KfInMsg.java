package com.jfinal.qy.weixin.sdk.msg.kf.in;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by Administrator on 2016-10-23.
 */
public class KfInMsg {
    private String agentType;
    private String toUserName;
    private Integer itemCount;
    private String packageId;
    private List<KfInBaseMsg> itemList = new LinkedList<KfInBaseMsg>();

    public String getAgentType() {
        return agentType;
    }

    public void setAgentType(String agentType) {
        this.agentType = agentType;
    }

    public String getToUserName() {
        return toUserName;
    }

    public void setToUserName(String toUserName) {
        this.toUserName = toUserName;
    }

    public Integer getItemCount() {
        return itemCount;
    }

    public void setItemCount(Integer itemCount) {
        this.itemCount = itemCount;
    }

    public String getPackageId() {
        return packageId;
    }

    public void setPackageId(String packageId) {
        this.packageId = packageId;
    }

    public List<KfInBaseMsg> getItemList() {
        return itemList;
    }

    public void setItemList(List<KfInBaseMsg> itemList) {
        this.itemList = itemList;
    }
}
