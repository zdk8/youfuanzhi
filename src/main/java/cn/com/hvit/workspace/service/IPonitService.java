package cn.com.hvit.workspace.service;

import cn.com.hvit.framework.kon.util.PageHelper;
import cn.com.hvit.workspace.model.Pointinfo;

import java.util.HashMap;
import java.util.List;

/**
 * Created by Administrator on 2016/11/29.
 */
public interface IPonitService {
    PageHelper.Page<Pointinfo> getPonitByconds(int page, int rows, HashMap<String, Object> condMap);

    List getPoints();
}
