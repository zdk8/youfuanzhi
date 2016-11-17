package cn.com.hvit.workspace.model;

import org.springframework.format.annotation.DateTimeFormat;

import java.math.BigDecimal;
import java.util.Date;

public class Ls_Blast {
    private BigDecimal bId;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date blasttime;

    private BigDecimal distance;

    private String blastway;

    private String description;

    private String applyunit;

    private String contactway;

    private BigDecimal weight;

    private String applicant;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date formdate;

    private String isdel;

    public BigDecimal getbId() {
        return bId;
    }

    public void setbId(BigDecimal bId) {
        this.bId = bId;
    }

    public Date getBlasttime() {
        return blasttime;
    }

    public void setBlasttime(Date blasttime) {
        this.blasttime = blasttime;
    }

    public BigDecimal getDistance() {
        return distance;
    }

    public void setDistance(BigDecimal distance) {
        this.distance = distance;
    }

    public String getBlastway() {
        return blastway;
    }

    public void setBlastway(String blastway) {
        this.blastway = blastway == null ? null : blastway.trim();
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description == null ? null : description.trim();
    }

    public String getApplyunit() {
        return applyunit;
    }

    public void setApplyunit(String applyunit) {
        this.applyunit = applyunit == null ? null : applyunit.trim();
    }

    public String getContactway() {
        return contactway;
    }

    public void setContactway(String contactway) {
        this.contactway = contactway == null ? null : contactway.trim();
    }

    public BigDecimal getWeight() {
        return weight;
    }

    public void setWeight(BigDecimal weight) {
        this.weight = weight;
    }

    public String getApplicant() {
        return applicant;
    }

    public void setApplicant(String applicant) {
        this.applicant = applicant == null ? null : applicant.trim();
    }

    public Date getFormdate() {
        return formdate;
    }

    public void setFormdate(Date formdate) {
        this.formdate = formdate;
    }

    public String getIsdel() {
        return isdel;
    }

    public void setIsdel(String isdel) {
        this.isdel = isdel;
    }
}