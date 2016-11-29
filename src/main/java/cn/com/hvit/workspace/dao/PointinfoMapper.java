package cn.com.hvit.workspace.dao;

import cn.com.hvit.workspace.model.Pointinfo;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;

public interface PointinfoMapper {
    int deleteByPrimaryKey(BigDecimal pointid);

    int insert(Pointinfo record);

    int insertSelective(Pointinfo record);

    Pointinfo selectByPrimaryKey(BigDecimal pointid);

    int updateByPrimaryKeySelective(Pointinfo record);

    int updateByPrimaryKey(Pointinfo record);

    List getPointByconds(HashMap conds);

    List getPoints();
}