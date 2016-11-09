package cn.com.hvit.workspace.dao;

import cn.com.hvit.workspace.model.TargetpushKey;

public interface TargetpushMapper {
    int deleteByPrimaryKey(TargetpushKey key);

    int insert(TargetpushKey record);

    int insertSelective(TargetpushKey record);
}