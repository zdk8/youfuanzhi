package cn.com.hvit.workspace.dao;

import cn.com.hvit.workspace.model.ls_earthquakeresponse;

import java.util.HashMap;
import java.util.List;

public interface ls_earthquakeresponseMapper {
    int insert(ls_earthquakeresponse record);

    int insertSelective(ls_earthquakeresponse record);

    void updateEarthquake(ls_earthquakeresponse earthquake);

    List getResponseByCond(HashMap<String, Object> condMap);

    List getEarthResponse();

}