package cn.com.hvit.workspace.dao;

import cn.com.hvit.workspace.model.Ls_Article;
import java.math.BigDecimal;

public interface Ls_ArticleMapper {
    int deleteByPrimaryKey(BigDecimal articleid);

    int insert(Ls_Article record);

    int insertSelective(Ls_Article record);

    Ls_Article selectByPrimaryKey(BigDecimal articleid);

    int updateByPrimaryKeySelective(Ls_Article record);

    int updateByPrimaryKey(Ls_Article record);
}