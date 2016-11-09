package cn.com.hvit.workspace.dao;

import cn.com.hvit.workspace.model.Questionnaire_Risk;

public interface Questionnaire_RiskMapper {
    int insert(Questionnaire_Risk record);

    int insertSelective(Questionnaire_Risk record);
}