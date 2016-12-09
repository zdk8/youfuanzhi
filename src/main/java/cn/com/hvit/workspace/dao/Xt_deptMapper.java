package cn.com.hvit.workspace.dao;


import cn.com.hvit.workspace.model.Xt_dept;

public interface Xt_deptMapper {
    int deleteByPrimaryKey(Short id);

    int insert(Xt_dept record);

    int insertSelective(Xt_dept record);

    Xt_dept selectByPrimaryKey(Short id);

    int updateByPrimaryKeySelective(Xt_dept record);

    int updateByPrimaryKey(Xt_dept record);
}