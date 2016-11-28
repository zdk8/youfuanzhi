package cn.com.hvit.workspace.dao;

import cn.com.hvit.workspace.model.ls_responsemessage;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public interface ls_responsemessageMapper {
    int insert(ls_responsemessage record);

    int insertSelective(ls_responsemessage record);

    void updatemessage(ls_responsemessage message);

    List getMessagebylevel(Map level);

    List getEarthmsgByyjid(HashMap<String, Object> condMap);

    void deleteEarthMsg(HashMap<String, Object> condMap);
}