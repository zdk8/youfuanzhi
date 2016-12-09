package cn.com.hvit.workspace.model;

public class Division {
    private String dvcode;

    private String dvname;

    private Short dvrank;

    private String dvhigh;

    private Short dvflag;

    private String totalname;

    private Short isdel;

    public String getDvcode() {
        return dvcode;
    }

    public void setDvcode(String dvcode) {
        this.dvcode = dvcode == null ? null : dvcode.trim();
    }

    public String getDvname() {
        return dvname;
    }

    public void setDvname(String dvname) {
        this.dvname = dvname == null ? null : dvname.trim();
    }

    public Short getDvrank() {
        return dvrank;
    }

    public void setDvrank(Short dvrank) {
        this.dvrank = dvrank;
    }

    public String getDvhigh() {
        return dvhigh;
    }

    public void setDvhigh(String dvhigh) {
        this.dvhigh = dvhigh == null ? null : dvhigh.trim();
    }

    public Short getDvflag() {
        return dvflag;
    }

    public void setDvflag(Short dvflag) {
        this.dvflag = dvflag;
    }

    public String getTotalname() {
        return totalname;
    }

    public void setTotalname(String totalname) {
        this.totalname = totalname == null ? null : totalname.trim();
    }

    public Short getIsdel() {
        return isdel;
    }

    public void setIsdel(Short isdel) {
        this.isdel = isdel;
    }
}