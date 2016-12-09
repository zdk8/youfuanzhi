package cn.com.hvit.workspace.dao;


import cn.com.hvit.workspace.model.XtFunction;

import java.util.List;
import java.util.Map;

public interface XtFunctionMapper {
    int deleteByPrimaryKey(String functionid);

    int insert(XtFunction record);

    int insertSelective(XtFunction record);

    XtFunction selectByPrimaryKey(String functionid);

    int updateByPrimaryKeySelective(XtFunction record);

    int updateByPrimaryKey(XtFunction record);

    List selectAllFunctions();

    /**
     * 参数名
     * @param loginnameAndfunctionid
     * @return
     */
    List selectFunctionByLoginnameAndFunctionid(Map loginnameAndfunctionid);

    /**
     * 根据functionid查询子功能
     * @param functionid
     * @return
     */
    List selectSubFunctions(String functionid);
}