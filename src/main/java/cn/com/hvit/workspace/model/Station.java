package cn.com.hvit.workspace.model;

import java.math.BigDecimal;
import java.util.Date;

public class Station {
    private BigDecimal stationid;

    private String stationname;

    private String stationcode;

    private String address;

    private String gateway;

    private String ip;

    private String flag;

    private Date bgtime;

    private Date endtime;

    private String linecode;

    private String linetype;

    private String linetime;

    private Double lat;

    private Double lon;

    private String storename;

    private String filename;

    public BigDecimal getStationid() {
        return stationid;
    }

    public void setStationid(BigDecimal stationid) {
        this.stationid = stationid;
    }

    public String getStationname() {
        return stationname;
    }

    public void setStationname(String stationname) {
        this.stationname = stationname == null ? null : stationname.trim();
    }

    public String getStationcode() {
        return stationcode;
    }

    public void setStationcode(String stationcode) {
        this.stationcode = stationcode == null ? null : stationcode.trim();
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address == null ? null : address.trim();
    }

    public String getGateway() {
        return gateway;
    }

    public void setGateway(String gateway) {
        this.gateway = gateway == null ? null : gateway.trim();
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip == null ? null : ip.trim();
    }

    public String getFlag() {
        return flag;
    }

    public void setFlag(String flag) {
        this.flag = flag == null ? null : flag.trim();
    }

    public Date getBgtime() {
        return bgtime;
    }

    public void setBgtime(Date bgtime) {
        this.bgtime = bgtime;
    }

    public Date getEndtime() {
        return endtime;
    }

    public void setEndtime(Date endtime) {
        this.endtime = endtime;
    }

    public String getLinecode() {
        return linecode;
    }

    public void setLinecode(String linecode) {
        this.linecode = linecode == null ? null : linecode.trim();
    }

    public String getLinetype() {
        return linetype;
    }

    public void setLinetype(String linetype) {
        this.linetype = linetype == null ? null : linetype.trim();
    }

    public String getLinetime() {
        return linetime;
    }

    public void setLinetime(String linetime) {
        this.linetime = linetime == null ? null : linetime.trim();
    }

    public Double getLat() {
        return lat;
    }

    public void setLat(Double lat) {
        this.lat = lat;
    }

    public Double getLon() {
        return lon;
    }

    public void setLon(Double lon) {
        this.lon = lon;
    }

    public String getStorename() {
        return storename;
    }

    public void setStorename(String storename) {
        this.storename = storename == null ? null : storename.trim();
    }

    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename == null ? null : filename.trim();
    }
}