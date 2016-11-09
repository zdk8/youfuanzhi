package cn.com.hvit.workspace.model;

import java.util.Date;

public class Ls_Station {
    private String stationcode;

    private String stationlon;

    private Double stationlat;

    private String networkname;

    private String networkcode;

    private String address;

    private String gateway;

    private String communicationtype;

    private String linkman;

    private String linkmanphone;

    private String crossvalue;

    private String stationip;

    private Double elevation;

    private String stationname;

    private String flag;

    private Date bgtime;

    private Date endtime;

    public String getStationcode() {
        return stationcode;
    }

    public void setStationcode(String stationcode) {
        this.stationcode = stationcode == null ? null : stationcode.trim();
    }

    public String getStationlon() {
        return stationlon;
    }

    public void setStationlon(String stationlon) {
        this.stationlon = stationlon == null ? null : stationlon.trim();
    }

    public Double getStationlat() {
        return stationlat;
    }

    public void setStationlat(Double stationlat) {
        this.stationlat = stationlat;
    }

    public String getNetworkname() {
        return networkname;
    }

    public void setNetworkname(String networkname) {
        this.networkname = networkname == null ? null : networkname.trim();
    }

    public String getNetworkcode() {
        return networkcode;
    }

    public void setNetworkcode(String networkcode) {
        this.networkcode = networkcode == null ? null : networkcode.trim();
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

    public String getCommunicationtype() {
        return communicationtype;
    }

    public void setCommunicationtype(String communicationtype) {
        this.communicationtype = communicationtype == null ? null : communicationtype.trim();
    }

    public String getLinkman() {
        return linkman;
    }

    public void setLinkman(String linkman) {
        this.linkman = linkman == null ? null : linkman.trim();
    }

    public String getLinkmanphone() {
        return linkmanphone;
    }

    public void setLinkmanphone(String linkmanphone) {
        this.linkmanphone = linkmanphone == null ? null : linkmanphone.trim();
    }

    public String getCrossvalue() {
        return crossvalue;
    }

    public void setCrossvalue(String crossvalue) {
        this.crossvalue = crossvalue == null ? null : crossvalue.trim();
    }

    public String getStationip() {
        return stationip;
    }

    public void setStationip(String stationip) {
        this.stationip = stationip == null ? null : stationip.trim();
    }

    public Double getElevation() {
        return elevation;
    }

    public void setElevation(Double elevation) {
        this.elevation = elevation;
    }

    public String getStationname() {
        return stationname;
    }

    public void setStationname(String stationname) {
        this.stationname = stationname == null ? null : stationname.trim();
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
}