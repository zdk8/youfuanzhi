package cn.com.hvit.workspace.controller;

import cn.com.hvit.workspace.model.Ls_Blast;
import cn.com.hvit.workspace.service.IBlastService;
import cn.com.hvit.workspace.util.CommonCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Administrator on 2016/11/11.
 */
@Controller
public class BlastController {

    @Autowired
    IBlastService blastService;


    @ResponseBody
    @RequestMapping(value = "/addblast" ,method = {RequestMethod.GET,RequestMethod.POST})
    public Map<String, Object> addBlast(Ls_Blast blast, HttpServletRequest request, HttpServletResponse response){
        Map<String,Object> userMap = new HashMap<String,Object>();
        blastService.addBlast(blast);
        CommonCode.addLog(request,"新增爆破信息");
        userMap.put("success",true);
        userMap.put("message","用户更新成功");
        return userMap;
    }
}
