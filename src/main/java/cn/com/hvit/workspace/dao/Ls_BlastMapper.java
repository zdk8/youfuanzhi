package cn.com.hvit.workspace.dao;

import cn.com.hvit.workspace.model.Ls_Blast;

public interface Ls_BlastMapper {
    int insert(Ls_Blast record);

    int insertSelective(Ls_Blast record);
}