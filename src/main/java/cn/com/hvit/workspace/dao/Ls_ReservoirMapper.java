package cn.com.hvit.workspace.dao;

import cn.com.hvit.workspace.model.Ls_Reservoir;
import java.math.BigDecimal;

public interface Ls_ReservoirMapper {
    int deleteByPrimaryKey(BigDecimal reservoirid);

    int insert(Ls_Reservoir record);

    int insertSelective(Ls_Reservoir record);

    Ls_Reservoir selectByPrimaryKey(BigDecimal reservoirid);

    int updateByPrimaryKeySelective(Ls_Reservoir record);

    int updateByPrimaryKey(Ls_Reservoir record);
}