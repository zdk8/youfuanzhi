package cn.com.hvit.workspace.model;

import java.util.Date;

public class Xt_function {
    private String functionid;

    private String location;

    private String title;

    private String parent;

    private Short orderno;

    private String nodetype;

    private String type;

    private String description;

    private String log;

    private String developer;

    private String active;

    private String functiondesc;

    private String auflag;

    private String rbflag;

    private String param1;

    private String param2;

    private Date createdate;

    private String owner;

    private String bsdigest;

    private String urlpath;

    public String getFunctionid() {
        return functionid;
    }

    public void setFunctionid(String functionid) {
        this.functionid = functionid == null ? null : functionid.trim();
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location == null ? null : location.trim();
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title == null ? null : title.trim();
    }

    public String getParent() {
        return parent;
    }

    public void setParent(String parent) {
        this.parent = parent == null ? null : parent.trim();
    }

    public Short getOrderno() {
        return orderno;
    }

    public void setOrderno(Short orderno) {
        this.orderno = orderno;
    }

    public String getNodetype() {
        return nodetype;
    }

    public void setNodetype(String nodetype) {
        this.nodetype = nodetype == null ? null : nodetype.trim();
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type == null ? null : type.trim();
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description == null ? null : description.trim();
    }

    public String getLog() {
        return log;
    }

    public void setLog(String log) {
        this.log = log == null ? null : log.trim();
    }

    public String getDeveloper() {
        return developer;
    }

    public void setDeveloper(String developer) {
        this.developer = developer == null ? null : developer.trim();
    }

    public String getActive() {
        return active;
    }

    public void setActive(String active) {
        this.active = active == null ? null : active.trim();
    }

    public String getFunctiondesc() {
        return functiondesc;
    }

    public void setFunctiondesc(String functiondesc) {
        this.functiondesc = functiondesc == null ? null : functiondesc.trim();
    }

    public String getAuflag() {
        return auflag;
    }

    public void setAuflag(String auflag) {
        this.auflag = auflag == null ? null : auflag.trim();
    }

    public String getRbflag() {
        return rbflag;
    }

    public void setRbflag(String rbflag) {
        this.rbflag = rbflag == null ? null : rbflag.trim();
    }

    public String getParam1() {
        return param1;
    }

    public void setParam1(String param1) {
        this.param1 = param1 == null ? null : param1.trim();
    }

    public String getParam2() {
        return param2;
    }

    public void setParam2(String param2) {
        this.param2 = param2 == null ? null : param2.trim();
    }

    public Date getCreatedate() {
        return createdate;
    }

    public void setCreatedate(Date createdate) {
        this.createdate = createdate;
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner == null ? null : owner.trim();
    }

    public String getBsdigest() {
        return bsdigest;
    }

    public void setBsdigest(String bsdigest) {
        this.bsdigest = bsdigest == null ? null : bsdigest.trim();
    }

    public String getUrlpath() {
        return urlpath;
    }

    public void setUrlpath(String urlpath) {
        this.urlpath = urlpath == null ? null : urlpath.trim();
    }
}