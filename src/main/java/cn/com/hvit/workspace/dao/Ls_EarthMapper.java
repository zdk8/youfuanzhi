package cn.com.hvit.workspace.dao;

import cn.com.hvit.workspace.model.Ls_Earth;

public interface Ls_EarthMapper {
    int deleteByPrimaryKey(String earthid);

    int insert(Ls_Earth record);

    int insertSelective(Ls_Earth record);

    Ls_Earth selectByPrimaryKey(String earthid);

    int updateByPrimaryKeySelective(Ls_Earth record);

    int updateByPrimaryKey(Ls_Earth record);
}