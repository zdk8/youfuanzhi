package cn.com.hvit.workspace.dao;

import cn.com.hvit.workspace.model.Ls_Push;
import java.math.BigDecimal;

public interface Ls_PushMapper {
    int deleteByPrimaryKey(BigDecimal pushid);

    int insert(Ls_Push record);

    int insertSelective(Ls_Push record);

    Ls_Push selectByPrimaryKey(BigDecimal pushid);

    int updateByPrimaryKeySelective(Ls_Push record);

    int updateByPrimaryKey(Ls_Push record);
}