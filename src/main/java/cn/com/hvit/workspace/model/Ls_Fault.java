package cn.com.hvit.workspace.model;

import java.math.BigDecimal;
import java.util.Date;

public class Ls_Fault {
    private BigDecimal faultid;

    private String stationcode;

    private String faultphenomenon;

    private Date faultstarttime;

    private Date faultendtime;

    private Date repairtime;

    private String repaircontent;

    private String repairperson;

    private String repairresult;

    private Date guaranteetime;

    private Date watchresponsetime;

    private Date repairresponsetime;

    private Date scenerepairtime;

    private String faultreason;

    private String faulthandle;

    private Date faultrecoverytime;

    private String faultremarks;

    public BigDecimal getFaultid() {
        return faultid;
    }

    public void setFaultid(BigDecimal faultid) {
        this.faultid = faultid;
    }

    public String getStationcode() {
        return stationcode;
    }

    public void setStationcode(String stationcode) {
        this.stationcode = stationcode == null ? null : stationcode.trim();
    }

    public String getFaultphenomenon() {
        return faultphenomenon;
    }

    public void setFaultphenomenon(String faultphenomenon) {
        this.faultphenomenon = faultphenomenon == null ? null : faultphenomenon.trim();
    }

    public Date getFaultstarttime() {
        return faultstarttime;
    }

    public void setFaultstarttime(Date faultstarttime) {
        this.faultstarttime = faultstarttime;
    }

    public Date getFaultendtime() {
        return faultendtime;
    }

    public void setFaultendtime(Date faultendtime) {
        this.faultendtime = faultendtime;
    }

    public Date getRepairtime() {
        return repairtime;
    }

    public void setRepairtime(Date repairtime) {
        this.repairtime = repairtime;
    }

    public String getRepaircontent() {
        return repaircontent;
    }

    public void setRepaircontent(String repaircontent) {
        this.repaircontent = repaircontent == null ? null : repaircontent.trim();
    }

    public String getRepairperson() {
        return repairperson;
    }

    public void setRepairperson(String repairperson) {
        this.repairperson = repairperson == null ? null : repairperson.trim();
    }

    public String getRepairresult() {
        return repairresult;
    }

    public void setRepairresult(String repairresult) {
        this.repairresult = repairresult == null ? null : repairresult.trim();
    }

    public Date getGuaranteetime() {
        return guaranteetime;
    }

    public void setGuaranteetime(Date guaranteetime) {
        this.guaranteetime = guaranteetime;
    }

    public Date getWatchresponsetime() {
        return watchresponsetime;
    }

    public void setWatchresponsetime(Date watchresponsetime) {
        this.watchresponsetime = watchresponsetime;
    }

    public Date getRepairresponsetime() {
        return repairresponsetime;
    }

    public void setRepairresponsetime(Date repairresponsetime) {
        this.repairresponsetime = repairresponsetime;
    }

    public Date getScenerepairtime() {
        return scenerepairtime;
    }

    public void setScenerepairtime(Date scenerepairtime) {
        this.scenerepairtime = scenerepairtime;
    }

    public String getFaultreason() {
        return faultreason;
    }

    public void setFaultreason(String faultreason) {
        this.faultreason = faultreason == null ? null : faultreason.trim();
    }

    public String getFaulthandle() {
        return faulthandle;
    }

    public void setFaulthandle(String faulthandle) {
        this.faulthandle = faulthandle == null ? null : faulthandle.trim();
    }

    public Date getFaultrecoverytime() {
        return faultrecoverytime;
    }

    public void setFaultrecoverytime(Date faultrecoverytime) {
        this.faultrecoverytime = faultrecoverytime;
    }

    public String getFaultremarks() {
        return faultremarks;
    }

    public void setFaultremarks(String faultremarks) {
        this.faultremarks = faultremarks == null ? null : faultremarks.trim();
    }
}