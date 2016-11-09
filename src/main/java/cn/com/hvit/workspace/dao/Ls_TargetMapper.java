package cn.com.hvit.workspace.dao;

import cn.com.hvit.workspace.model.Ls_Target;
import java.math.BigDecimal;

public interface Ls_TargetMapper {
    int deleteByPrimaryKey(BigDecimal targetid);

    int insert(Ls_Target record);

    int insertSelective(Ls_Target record);

    Ls_Target selectByPrimaryKey(BigDecimal targetid);

    int updateByPrimaryKeySelective(Ls_Target record);

    int updateByPrimaryKey(Ls_Target record);
}