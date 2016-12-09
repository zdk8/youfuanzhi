package cn.com.hvit.workspace.service;

import cn.com.hvit.framework.kon.util.PageHelper;
import cn.com.hvit.workspace.model.Ls_Earth;

import java.util.HashMap;
import java.util.List;

/**
 * Created by Administrator on 2016/12/9.
 */
public interface IYiqiService {

    List getEarthData();

    List getStationData();
}
