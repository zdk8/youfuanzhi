package cn.com.hvit.workspace.service.impl;

import cn.com.hvit.framework.kon.util.PageHelper;
import cn.com.hvit.workspace.dao.Ls_LogMapper;
import cn.com.hvit.workspace.model.Ls_Log;
import cn.com.hvit.workspace.service.ILogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by Administrator on 2016/11/10.
 */
@Service
public class LogServiceImpl implements ILogService {

    @Autowired
    private Ls_LogMapper logMapper;

    @Override
    public void addLog(Ls_Log log) {
         logMapper.insertLog(log);
    }

    @Override
    public PageHelper.Page<Ls_Log> getLogs(int pageNumber, int pageSize) {
        PageHelper.startPage(pageNumber,pageSize);
        logMapper.getLogs();
        return PageHelper.endPage();
    }

}
