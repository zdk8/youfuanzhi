package cn.com.hvit.workspace.service.impl;

import cn.com.hvit.framework.kon.util.PageHelper;
import cn.com.hvit.workspace.dao.LS_filesMapper;
import cn.com.hvit.workspace.model.LS_files;
import cn.com.hvit.workspace.service.IFileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;

/**
 * Created by Administrator on 2016/11/23.
 */
@Service
public class FileServiceImpl implements IFileService {
    @Autowired
    private LS_filesMapper filesMapper;

    @Override
    public void addFile(LS_files files) {
       filesMapper.insertSelective(files);
    }

    @Override
    public PageHelper.Page<LS_files> getfilesbycond(int page, int rows, HashMap<String, Object> condMap) {
        PageHelper.startPage(page,rows);
        filesMapper.getfilesbycond(condMap);
        return PageHelper.endPage();
    }

    @Override
    public PageHelper.Page<LS_files> getprovincefilesbycond(int page, int rows, HashMap<String, Object> condMap) {
        PageHelper.startPage(page,rows);
        filesMapper.getprovincefilesbycond(condMap);
        return PageHelper.endPage();
    }
}
