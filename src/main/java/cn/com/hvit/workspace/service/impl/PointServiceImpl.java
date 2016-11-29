package cn.com.hvit.workspace.service.impl;

import cn.com.hvit.framework.kon.util.PageHelper;
import cn.com.hvit.workspace.dao.PointinfoMapper;
import cn.com.hvit.workspace.model.Pointinfo;
import cn.com.hvit.workspace.service.IPonitService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;

/**
 * Created by Administrator on 2016/11/29.
 */
@Service
public class PointServiceImpl implements IPonitService {
    @Autowired
    private PointinfoMapper pointinfoMapper;

    @Override
    public PageHelper.Page<Pointinfo> getPonitByconds(int page, int rows, HashMap<String, Object> condMap) {
        PageHelper.startPage(page,rows);
        pointinfoMapper.getPointByconds(condMap);
        return PageHelper.endPage();
    }

    @Override
    public List getPoints() {
        return pointinfoMapper.getPoints();
    }
}
