package cn.com.hvit.workspace.model;

import java.util.Date;

public class Pointinfo {
    private String pointid;

    private String earthid;

    private String pointlocation;

    private Double pointlon;

    private Double pointlat;

    private String pointname;

    private Date pointtime;

    private String pointgroup;

    private String pointperson;

    private String pointintensity;

    private String pointcontent;

    private String msgtel;

    private String msgcontent;

    public String getPointid() {
        return pointid;
    }

    public void setPointid(String pointid) {
        this.pointid = pointid == null ? null : pointid.trim();
    }

    public String getEarthid() {
        return earthid;
    }

    public void setEarthid(String earthid) {
        this.earthid = earthid == null ? null : earthid.trim();
    }

    public String getPointlocation() {
        return pointlocation;
    }

    public void setPointlocation(String pointlocation) {
        this.pointlocation = pointlocation == null ? null : pointlocation.trim();
    }

    public Double getPointlon() {
        return pointlon;
    }

    public void setPointlon(Double pointlon) {
        this.pointlon = pointlon;
    }

    public Double getPointlat() {
        return pointlat;
    }

    public void setPointlat(Double pointlat) {
        this.pointlat = pointlat;
    }

    public String getPointname() {
        return pointname;
    }

    public void setPointname(String pointname) {
        this.pointname = pointname == null ? null : pointname.trim();
    }

    public Date getPointtime() {
        return pointtime;
    }

    public void setPointtime(Date pointtime) {
        this.pointtime = pointtime;
    }

    public String getPointgroup() {
        return pointgroup;
    }

    public void setPointgroup(String pointgroup) {
        this.pointgroup = pointgroup == null ? null : pointgroup.trim();
    }

    public String getPointperson() {
        return pointperson;
    }

    public void setPointperson(String pointperson) {
        this.pointperson = pointperson == null ? null : pointperson.trim();
    }

    public String getPointintensity() {
        return pointintensity;
    }

    public void setPointintensity(String pointintensity) {
        this.pointintensity = pointintensity == null ? null : pointintensity.trim();
    }

    public String getPointcontent() {
        return pointcontent;
    }

    public void setPointcontent(String pointcontent) {
        this.pointcontent = pointcontent == null ? null : pointcontent.trim();
    }

    public String getMsgtel() {
        return msgtel;
    }

    public void setMsgtel(String msgtel) {
        this.msgtel = msgtel == null ? null : msgtel.trim();
    }

    public String getMsgcontent() {
        return msgcontent;
    }

    public void setMsgcontent(String msgcontent) {
        this.msgcontent = msgcontent == null ? null : msgcontent.trim();
    }
}