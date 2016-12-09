package cn.com.hvit.workspace.model;

public class Xt_rolefunc {
    private Short rolefuncid;

    private Short roleid;

    private String functionid;

    public Short getRolefuncid() {
        return rolefuncid;
    }

    public void setRolefuncid(Short rolefuncid) {
        this.rolefuncid = rolefuncid;
    }

    public Short getRoleid() {
        return roleid;
    }

    public void setRoleid(Short roleid) {
        this.roleid = roleid;
    }

    public String getFunctionid() {
        return functionid;
    }

    public void setFunctionid(String functionid) {
        this.functionid = functionid == null ? null : functionid.trim();
    }
}