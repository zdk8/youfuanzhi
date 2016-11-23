package cn.com.hvit.workspace.dao;

import cn.com.hvit.workspace.model.LS_files;

import java.util.HashMap;

public interface LS_filesMapper {
    int insert(LS_files record);

    int insertSelective(LS_files record);

    void getfilesbycond(HashMap<String, Object> condMap);

}