package cn.com.hvit.workspace.model;

import java.math.BigDecimal;
import java.util.Date;

public class Ls_Article {
    private BigDecimal articleid;

    private BigDecimal userid;

    private String articletype;

    private String articletitle;

    private String articlecontent;

    private Date uploadtime;

    public BigDecimal getArticleid() {
        return articleid;
    }

    public void setArticleid(BigDecimal articleid) {
        this.articleid = articleid;
    }

    public BigDecimal getUserid() {
        return userid;
    }

    public void setUserid(BigDecimal userid) {
        this.userid = userid;
    }

    public String getArticletype() {
        return articletype;
    }

    public void setArticletype(String articletype) {
        this.articletype = articletype == null ? null : articletype.trim();
    }

    public String getArticletitle() {
        return articletitle;
    }

    public void setArticletitle(String articletitle) {
        this.articletitle = articletitle == null ? null : articletitle.trim();
    }

    public String getArticlecontent() {
        return articlecontent;
    }

    public void setArticlecontent(String articlecontent) {
        this.articlecontent = articlecontent == null ? null : articlecontent.trim();
    }

    public Date getUploadtime() {
        return uploadtime;
    }

    public void setUploadtime(Date uploadtime) {
        this.uploadtime = uploadtime;
    }
}