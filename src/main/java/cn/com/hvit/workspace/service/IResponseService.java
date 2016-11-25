package cn.com.hvit.workspace.service;

import cn.com.hvit.workspace.model.ls_earthquakeresponse;
import cn.com.hvit.workspace.model.ls_responsemessage;

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

    List getResponsebyid(Map responseMap);
}
