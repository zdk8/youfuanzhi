package cn.com.hvit.workspace.model;

import java.math.BigDecimal;

public class ls_responsemessage {
    private BigDecimal rmid;

    private String department;

    private String dutycontent;

    private String contact;

    private String position;

    private String phone;

    private String telephone;

    private String rmlevel;

    private BigDecimal yjid;

    public BigDecimal getRmid() {
        return rmid;
    }

    public void setRmid(BigDecimal rmid) {
        this.rmid = rmid;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department == null ? null : department.trim();
    }

    public String getDutycontent() {
        return dutycontent;
    }

    public void setDutycontent(String dutycontent) {
        this.dutycontent = dutycontent == null ? null : dutycontent.trim();
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact == null ? null : contact.trim();
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position == null ? null : position.trim();
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone == null ? null : phone.trim();
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone == null ? null : telephone.trim();
    }

    public String getRmlevel() {
        return rmlevel;
    }

    public void setRmlevel(String rmlevel) {
        this.rmlevel = rmlevel == null ? null : rmlevel.trim();
    }

    public BigDecimal getYjid() {
        return yjid;
    }

    public void setYjid(BigDecimal yjid) {
        this.yjid = yjid;
    }
}