package cn.com.hvit.workspace.dao;

import cn.com.hvit.workspace.model.Questionnaire_Population;

public interface Questionnaire_PopulationMapper {
    int insert(Questionnaire_Population record);

    int insertSelective(Questionnaire_Population record);
}