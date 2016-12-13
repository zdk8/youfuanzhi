package cn.com.hvit.workspace.service;

import cn.com.hvit.framework.kon.util.PageHelper;
import cn.com.hvit.workspace.model.ls_earthquakeresponse;
import cn.com.hvit.workspace.model.ls_responsemessage;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2016/11/24.
 */
public interface IResponseService {
    void addEarthquake(ls_earthquakeresponse earthquake);

    void updateEarthquake(ls_earthquakeresponse earthquake);

    void addEarthMessage(ls_responsemessage message);

    void updateEarthmessage(ls_responsemessage message);

    List getResponsebylevel(Map responseMap);

    PageHelper.Page<ls_earthquakeresponse> getresponseByCond(int page, int rows, HashMap<String, Object> condMap);

    PageHelper.Page<ls_responsemessage> getEarthmsgByid(int page, int rows, HashMap<String, Object> condMap);

    void setResponseStatus(String start,int yjid);

    List getEarthResponse(HashMap<String, Object> quakeMap);

    void deleteEarthMsg(HashMap<String, Object> condMap);

    ls_earthquakeresponse getResponseByid(int yjid);

}
