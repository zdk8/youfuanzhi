package cn.com.hvit.workspace.model;

import java.math.BigDecimal;
import java.util.Date;

public class Ls_Earth {
    private String earthid;

    private String location;

    private Double lon;

    private Double lat;

    private Date earthtime;

    private Double ml;

    private Double depth;

    private BigDecimal ishigh;

    public String getEarthid() {
        return earthid;
    }

    public void setEarthid(String earthid) {
        this.earthid = earthid == null ? null : earthid.trim();
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location == null ? null : location.trim();
    }

    public Double getLon() {
        return lon;
    }

    public void setLon(Double lon) {
        this.lon = lon;
    }

    public Double getLat() {
        return lat;
    }

    public void setLat(Double lat) {
        this.lat = lat;
    }

    public Date getEarthtime() {
        return earthtime;
    }

    public void setEarthtime(Date earthtime) {
        this.earthtime = earthtime;
    }

    public Double getMl() {
        return ml;
    }

    public void setMl(Double ml) {
        this.ml = ml;
    }

    public Double getDepth() {
        return depth;
    }

    public void setDepth(Double depth) {
        this.depth = depth;
    }

    public BigDecimal getIshigh() {
        return ishigh;
    }

    public void setIshigh(BigDecimal ishigh) {
        this.ishigh = ishigh;
    }
}