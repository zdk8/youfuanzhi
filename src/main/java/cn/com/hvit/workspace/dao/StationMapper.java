package cn.com.hvit.workspace.dao;

import cn.com.hvit.workspace.model.Station;
import java.math.BigDecimal;
import java.util.List;

public interface StationMapper {

    Station selectByPrimaryKey(BigDecimal stationid);

    List getStation();
}