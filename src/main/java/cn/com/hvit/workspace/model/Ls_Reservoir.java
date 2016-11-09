package cn.com.hvit.workspace.model;

import java.math.BigDecimal;

public class Ls_Reservoir {
    private BigDecimal reservoirid;

    private String reservoirname;

    private String reservoiraddress;

    private String damposition;

    private Double reservoirarea;

    private String reservoirtime;

    private Double damheight;

    private Double damlong;

    private Double designcapacity;

    private Double maxcapacity;

    private String damtype;

    private Double designwaterlevel;

    private Double maxwaterlevel;

    private Double designintensity;

    private Double reservoirintensity;

    public BigDecimal getReservoirid() {
        return reservoirid;
    }

    public void setReservoirid(BigDecimal reservoirid) {
        this.reservoirid = reservoirid;
    }

    public String getReservoirname() {
        return reservoirname;
    }

    public void setReservoirname(String reservoirname) {
        this.reservoirname = reservoirname == null ? null : reservoirname.trim();
    }

    public String getReservoiraddress() {
        return reservoiraddress;
    }

    public void setReservoiraddress(String reservoiraddress) {
        this.reservoiraddress = reservoiraddress == null ? null : reservoiraddress.trim();
    }

    public String getDamposition() {
        return damposition;
    }

    public void setDamposition(String damposition) {
        this.damposition = damposition == null ? null : damposition.trim();
    }

    public Double getReservoirarea() {
        return reservoirarea;
    }

    public void setReservoirarea(Double reservoirarea) {
        this.reservoirarea = reservoirarea;
    }

    public String getReservoirtime() {
        return reservoirtime;
    }

    public void setReservoirtime(String reservoirtime) {
        this.reservoirtime = reservoirtime == null ? null : reservoirtime.trim();
    }

    public Double getDamheight() {
        return damheight;
    }

    public void setDamheight(Double damheight) {
        this.damheight = damheight;
    }

    public Double getDamlong() {
        return damlong;
    }

    public void setDamlong(Double damlong) {
        this.damlong = damlong;
    }

    public Double getDesigncapacity() {
        return designcapacity;
    }

    public void setDesigncapacity(Double designcapacity) {
        this.designcapacity = designcapacity;
    }

    public Double getMaxcapacity() {
        return maxcapacity;
    }

    public void setMaxcapacity(Double maxcapacity) {
        this.maxcapacity = maxcapacity;
    }

    public String getDamtype() {
        return damtype;
    }

    public void setDamtype(String damtype) {
        this.damtype = damtype == null ? null : damtype.trim();
    }

    public Double getDesignwaterlevel() {
        return designwaterlevel;
    }

    public void setDesignwaterlevel(Double designwaterlevel) {
        this.designwaterlevel = designwaterlevel;
    }

    public Double getMaxwaterlevel() {
        return maxwaterlevel;
    }

    public void setMaxwaterlevel(Double maxwaterlevel) {
        this.maxwaterlevel = maxwaterlevel;
    }

    public Double getDesignintensity() {
        return designintensity;
    }

    public void setDesignintensity(Double designintensity) {
        this.designintensity = designintensity;
    }

    public Double getReservoirintensity() {
        return reservoirintensity;
    }

    public void setReservoirintensity(Double reservoirintensity) {
        this.reservoirintensity = reservoirintensity;
    }
}