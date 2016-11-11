package cn.com.hvit.workspace.service.impl;

import cn.com.hvit.framework.kon.util.PageHelper;
import cn.com.hvit.workspace.dao.Ls_BlastMapper;
import cn.com.hvit.workspace.model.Ls_Blast;
import cn.com.hvit.workspace.service.IBlastService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;

/**
 * Created by Administrator on 2016/11/11.
 */
@Service
public class BlastServiceImpl implements IBlastService{

    @Autowired
    private Ls_BlastMapper blastMapper;


    @Override
    public void addBlast(Ls_Blast blast) {
         blastMapper.addBlast(blast);
    }

    @Override
    public void updateBlast(Ls_Blast blast) {
         blastMapper.updateBlast(blast);
    }

    @Override
    public PageHelper.Page<Ls_Blast> getBlastByCond(int page, int rows, HashMap<String, Object> condMap) {
        PageHelper.startPage(page,rows);
        blastMapper.getBlastByCond(condMap);
        return PageHelper.endPage();
    }
}
