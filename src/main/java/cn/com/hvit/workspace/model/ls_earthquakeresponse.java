package cn.com.hvit.workspace.model;

import java.math.BigDecimal;

public class ls_earthquakeresponse {
    private BigDecimal yjid;

    private String yjname;

    private String yjpath;

    private String yjstatus;

    private String regionid;

    public String getRegionid() {
        return regionid;
    }

    public void setRegionid(String regionid) {
        this.regionid = regionid;
    }

    public BigDecimal getYjid() {
        return yjid;
    }

    public void setYjid(BigDecimal yjid) {
        this.yjid = yjid;
    }

    public String getYjname() {
        return yjname;
    }

    public void setYjname(String yjname) {
        this.yjname = yjname == null ? null : yjname.trim();
    }

    public String getYjpath() {
        return yjpath;
    }

    public void setYjpath(String yjpath) {
        this.yjpath = yjpath == null ? null : yjpath.trim();
    }

    public String getYjstatus() {
        return yjstatus;
    }

    public void setYjstatus(String yjstatus) {
        this.yjstatus = yjstatus;
    }
}