package cn.com.hvit.workspace.model;

public class Xt_dept {
    private Short id;

    private String deptname;

    private Short parent;

    public Short getId() {
        return id;
    }

    public void setId(Short id) {
        this.id = id;
    }

    public String getDeptname() {
        return deptname;
    }

    public void setDeptname(String deptname) {
        this.deptname = deptname == null ? null : deptname.trim();
    }

    public Short getParent() {
        return parent;
    }

    public void setParent(Short parent) {
        this.parent = parent;
    }
}