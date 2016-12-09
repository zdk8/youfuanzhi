package cn.com.hvit.workspace.model;

public class XtRoleFunc {
    private Integer rolefuncid;

    private Integer roleid;

    private String functionid;

    public Integer getRolefuncid() {
        return rolefuncid;
    }

    public void setRolefuncid(Integer rolefuncid) {
        this.rolefuncid = rolefuncid;
    }

    public Integer getRoleid() {
        return roleid;
    }

    public void setRoleid(Integer roleid) {
        this.roleid = roleid;
    }

    public String getFunctionid() {
        return functionid;
    }

    public void setFunctionid(String functionid) {
        this.functionid = functionid == null ? null : functionid.trim();
    }
}