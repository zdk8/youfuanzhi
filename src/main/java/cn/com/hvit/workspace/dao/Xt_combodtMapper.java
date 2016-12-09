package cn.com.hvit.workspace.dao;



import cn.com.hvit.workspace.model.Xt_combodt;

import java.util.List;

public interface Xt_combodtMapper {
    int insert(Xt_combodt record);

    int insertSelective(Xt_combodt record);

    List selectXt_combodts();
}