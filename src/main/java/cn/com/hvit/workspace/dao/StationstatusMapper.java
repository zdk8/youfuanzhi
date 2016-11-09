package cn.com.hvit.workspace.dao;

import cn.com.hvit.workspace.model.Stationstatus;

public interface StationstatusMapper {
    int insert(Stationstatus record);

    int insertSelective(Stationstatus record);
}