package cn.com.hvit.workspace.dao;

import cn.com.hvit.workspace.model.Ls_Fault;
import java.math.BigDecimal;

public interface Ls_FaultMapper {
    int deleteByPrimaryKey(BigDecimal faultid);

    int insert(Ls_Fault record);

    int insertSelective(Ls_Fault record);

    Ls_Fault selectByPrimaryKey(BigDecimal faultid);

    int updateByPrimaryKeySelective(Ls_Fault record);

    int updateByPrimaryKey(Ls_Fault record);
}