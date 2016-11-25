package cn.com.hvit.workspace.service.impl;

import cn.com.hvit.workspace.dao.ls_earthquakeresponseMapper;
import cn.com.hvit.workspace.dao.ls_responsemessageMapper;
import cn.com.hvit.workspace.model.ls_earthquakeresponse;
import cn.com.hvit.workspace.model.ls_responsemessage;
import cn.com.hvit.workspace.service.IResponseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2016/11/24.
 */
@Service
public class ResponseServiceImpl implements IResponseService {
    @Autowired
    private ls_earthquakeresponseMapper earthquakeresponse;

    @Autowired
    private ls_responsemessageMapper responsemessage;

    @Override
    public void addEarthquake(ls_earthquakeresponse earthquake) {
         earthquakeresponse.insertSelective(earthquake);
    }

    @Override
    public void updateEarthquake(ls_earthquakeresponse earthquake) {
        earthquakeresponse.updateEarthquake(earthquake);
    }

    @Override
    public void addEarthMessage(ls_responsemessage message) {
        responsemessage.insertSelective(message);
    }

    @Override
    public void updateEarthmessage(ls_responsemessage message) {
        responsemessage.updatemessage(message);
    }

    @Override
    public List getResponsebyid(Map level) {
        return responsemessage.getMessagebyid(level);
    }
}
