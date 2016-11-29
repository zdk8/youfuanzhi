package cn.com.hvit.workspace.service;


import cn.com.hvit.framework.kon.util.PageHelper;
import cn.com.hvit.workspace.model.LS_files;

import java.util.HashMap;

/**
 * Created by Administrator on 2016/11/23.
 */
public interface IFileService {


    void addFile(LS_files files);

    PageHelper.Page<LS_files> getfilesbycond(int page, int rows, HashMap<String, Object> condMap);

    PageHelper.Page<LS_files> getprovincefilesbycond(int page, int rows, HashMap<String, Object> condMap);
}
