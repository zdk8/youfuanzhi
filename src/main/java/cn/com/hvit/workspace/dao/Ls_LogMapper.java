package cn.com.hvit.workspace.dao;


import cn.com.hvit.workspace.model.Ls_Log;

import java.util.List;

public interface Ls_LogMapper {
    int insertLog(Ls_Log ls_log);

    List getLogs();
}