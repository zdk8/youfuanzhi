package cn.com.hvit.workspace.model;

import java.math.BigDecimal;

public class Ls_Push {
    private BigDecimal pushid;

    private BigDecimal userid;

    private String pushcontent;

    private String pushtime;

    public BigDecimal getPushid() {
        return pushid;
    }

    public void setPushid(BigDecimal pushid) {
        this.pushid = pushid;
    }

    public BigDecimal getUserid() {
        return userid;
    }

    public void setUserid(BigDecimal userid) {
        this.userid = userid;
    }

    public String getPushcontent() {
        return pushcontent;
    }

    public void setPushcontent(String pushcontent) {
        this.pushcontent = pushcontent == null ? null : pushcontent.trim();
    }

    public String getPushtime() {
        return pushtime;
    }

    public void setPushtime(String pushtime) {
        this.pushtime = pushtime == null ? null : pushtime.trim();
    }
}