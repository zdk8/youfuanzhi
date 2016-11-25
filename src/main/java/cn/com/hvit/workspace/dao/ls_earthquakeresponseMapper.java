package cn.com.hvit.workspace.dao;

import cn.com.hvit.workspace.model.ls_earthquakeresponse;

public interface ls_earthquakeresponseMapper {
    int insert(ls_earthquakeresponse record);

    int insertSelective(ls_earthquakeresponse record);

    void updateEarthquake(ls_earthquakeresponse earthquake);
}