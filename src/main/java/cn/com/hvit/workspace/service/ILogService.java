package cn.com.hvit.workspace.service;

import cn.com.hvit.framework.kon.util.PageHelper;
import cn.com.hvit.workspace.model.Ls_Log;

/**
 * Created by Administrator on 2016/11/10.
 */
public interface ILogService {

    void addLog(Ls_Log log);

    PageHelper.Page<Ls_Log> getLogs(int pageNumber, int pageSize);
}
