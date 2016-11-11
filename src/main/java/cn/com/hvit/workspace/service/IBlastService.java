package cn.com.hvit.workspace.service;

import cn.com.hvit.framework.kon.util.PageHelper;
import cn.com.hvit.workspace.model.Ls_Blast;

import java.util.HashMap;

/**
 * Created by Administrator on 2016/11/11.
 */
public interface IBlastService {
    void addBlast(Ls_Blast blast);

    void updateBlast(Ls_Blast blast);

    PageHelper.Page<Ls_Blast> getBlastByCond(int page, int rows, HashMap<String, Object> condMap);
}
