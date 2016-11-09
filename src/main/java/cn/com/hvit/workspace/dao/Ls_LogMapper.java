package cn.com.hvit.workspace.dao;

import cn.com.hvit.workspace.model.Ls_Log;
import java.math.BigDecimal;

public interface Ls_LogMapper {
    int deleteByPrimaryKey(BigDecimal logid);

    int insert(Ls_Log record);

    int insertSelective(Ls_Log record);

    Ls_Log selectByPrimaryKey(BigDecimal logid);

    int updateByPrimaryKeySelective(Ls_Log record);

    int updateByPrimaryKey(Ls_Log record);
}