package cn.com.hvit.workspace.dao;

import cn.com.hvit.workspace.model.LS_files;

import java.util.HashMap;
import java.util.List;

public interface LS_filesMapper {
    int insert(LS_files record);

    int insertSelective(LS_files record);

    List getfilesbycond(HashMap<String, Object> condMap);

    List getprovincefilesbycond(HashMap<String, Object> condMap);
}