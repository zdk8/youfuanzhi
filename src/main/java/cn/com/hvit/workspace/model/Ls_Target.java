package cn.com.hvit.workspace.model;

import java.math.BigDecimal;

public class Ls_Target {
    private BigDecimal targetid;

    private String targetname;

    private String targettel;

    private String targetduty;

    private String targetcompany;

    private String isdel;

    private String mydept;

    public BigDecimal getTargetid() {
        return targetid;
    }

    public void setTargetid(BigDecimal targetid) {
        this.targetid = targetid;
    }

    public String getTargetname() {
        return targetname;
    }

    public void setTargetname(String targetname) {
        this.targetname = targetname == null ? null : targetname.trim();
    }

    public String getTargettel() {
        return targettel;
    }

    public void setTargettel(String targettel) {
        this.targettel = targettel == null ? null : targettel.trim();
    }

    public String getTargetduty() {
        return targetduty;
    }

    public void setTargetduty(String targetduty) {
        this.targetduty = targetduty == null ? null : targetduty.trim();
    }

    public String getTargetcompany() {
        return targetcompany;
    }

    public void setTargetcompany(String targetcompany) {
        this.targetcompany = targetcompany == null ? null : targetcompany.trim();
    }

    public String getIsdel() {
        return isdel;
    }

    public void setIsdel(String isdel) {
        this.isdel = isdel == null ? null : isdel.trim();
    }

    public String getMydept() {
        return mydept;
    }

    public void setMydept(String mydept) {
        this.mydept = mydept == null ? null : mydept.trim();
    }
}