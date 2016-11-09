package cn.com.hvit.workspace.dao;

import cn.com.hvit.workspace.model.Ls_Station;

public interface Ls_StationMapper {
    int deleteByPrimaryKey(String stationcode);

    int insert(Ls_Station record);

    int insertSelective(Ls_Station record);

    Ls_Station selectByPrimaryKey(String stationcode);

    int updateByPrimaryKeySelective(Ls_Station record);

    int updateByPrimaryKey(Ls_Station record);
}