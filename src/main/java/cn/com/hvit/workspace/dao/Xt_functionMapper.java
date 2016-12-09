package cn.com.hvit.workspace.dao;


import cn.com.hvit.workspace.model.Xt_function;

public interface Xt_functionMapper {
    int deleteByPrimaryKey(String functionid);

    int insert(Xt_function record);

    int insertSelective(Xt_function record);

    Xt_function selectByPrimaryKey(String functionid);

    int updateByPrimaryKeySelective(Xt_function record);

    int updateByPrimaryKey(Xt_function record);
}