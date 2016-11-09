package cn.com.hvit.workspace.dao;

import cn.com.hvit.workspace.model.Questionnaire_Hospital;

public interface Questionnaire_HospitalMapper {
    int insert(Questionnaire_Hospital record);

    int insertSelective(Questionnaire_Hospital record);
}