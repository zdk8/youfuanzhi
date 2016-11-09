package cn.com.hvit.workspace.dao;

import cn.com.hvit.workspace.model.Station;
import java.math.BigDecimal;

public interface StationMapper {
    int deleteByPrimaryKey(BigDecimal stationid);

    int insert(Station record);

    int insertSelective(Station record);

    Station selectByPrimaryKey(BigDecimal stationid);

    int updateByPrimaryKeySelective(Station record);

    int updateByPrimaryKey(Station record);
}