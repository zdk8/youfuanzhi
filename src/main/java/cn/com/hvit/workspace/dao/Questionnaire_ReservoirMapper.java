package cn.com.hvit.workspace.dao;

import cn.com.hvit.workspace.model.Questionnaire_Reservoir;

public interface Questionnaire_ReservoirMapper {
    int insert(Questionnaire_Reservoir record);

    int insertSelective(Questionnaire_Reservoir record);
}