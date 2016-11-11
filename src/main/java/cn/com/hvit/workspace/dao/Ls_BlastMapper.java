package cn.com.hvit.workspace.dao;

import cn.com.hvit.workspace.model.Ls_Blast;

import java.util.Map;
import java.util.List;

public interface Ls_BlastMapper {

    int addBlast(Ls_Blast blast);

    void updateBlast(Ls_Blast blast);

    List getBlastByCond(Map<String, Object> condMap);
}