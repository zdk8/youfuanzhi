package cn.com.hvit.workspace.model;

import java.math.BigDecimal;

public class Ls_Special {
    private BigDecimal specialid;

    private String specialtype;

    private Double speciallon;

    private Double speciallat;

    public BigDecimal getSpecialid() {
        return specialid;
    }

    public void setSpecialid(BigDecimal specialid) {
        this.specialid = specialid;
    }

    public String getSpecialtype() {
        return specialtype;
    }

    public void setSpecialtype(String specialtype) {
        this.specialtype = specialtype == null ? null : specialtype.trim();
    }

    public Double getSpeciallon() {
        return speciallon;
    }

    public void setSpeciallon(Double speciallon) {
        this.speciallon = speciallon;
    }

    public Double getSpeciallat() {
        return speciallat;
    }

    public void setSpeciallat(Double speciallat) {
        this.speciallat = speciallat;
    }
}