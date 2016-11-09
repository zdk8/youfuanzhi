package cn.com.hvit.workspace.model;

import java.math.BigDecimal;

public class TargetpushKey {
    private BigDecimal targetid;

    private BigDecimal pushid;

    public BigDecimal getTargetid() {
        return targetid;
    }

    public void setTargetid(BigDecimal targetid) {
        this.targetid = targetid;
    }

    public BigDecimal getPushid() {
        return pushid;
    }

    public void setPushid(BigDecimal pushid) {
        this.pushid = pushid;
    }
}