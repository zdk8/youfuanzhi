package cn.com.hvit.workspace.model;

import java.math.BigDecimal;

public class ls_earthquakeresponse {
    private BigDecimal yjid;

    private String yjname;

    private String yjpath;

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
}