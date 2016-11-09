package cn.com.hvit.workspace.dao;

import cn.com.hvit.workspace.model.Ls_Special;
import java.math.BigDecimal;

public interface Ls_SpecialMapper {
    int deleteByPrimaryKey(BigDecimal specialid);

    int insert(Ls_Special record);

    int insertSelective(Ls_Special record);

    Ls_Special selectByPrimaryKey(BigDecimal specialid);

    int updateByPrimaryKeySelective(Ls_Special record);

    int updateByPrimaryKey(Ls_Special record);
}