package cn.com.hvit.workspace.dao;


import cn.com.hvit.workspace.model.Division;

import java.util.List;

public interface DivisionMapper {
    int deleteByPrimaryKey(String dvcode);

    int insert(Division record);

    int insertSelective(Division record);

    Division selectByPrimaryKey(String dvcode);

    int updateByPrimaryKeySelective(Division record);

    int updateByPrimaryKey(Division record);

    List selectSubDivision(String dvhigh);
}