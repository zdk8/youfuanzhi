package cn.com.hvit.workspace.controller.aopController;

import cn.com.hvit.framework.kon.util.DataSourceContextHolder;
import cn.com.hvit.framework.kon.util.PageHelper;
import cn.com.hvit.workspace.model.Ls_Blast;
import cn.com.hvit.workspace.model.XtUser;
import cn.com.hvit.workspace.service.IBlastService;
import cn.com.hvit.workspace.util.CommonCode;
import cn.com.hvit.workspace.util.log.SystemLog;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Administrator on 2016/11/11.
 */
@Controller
public class BlastController {

    @Autowired
    IBlastService blastService;

    @Autowired
    private SimpMessagingTemplate template;

    /**
     * 爆破信息的增加
     * @param blast
     * @param request
     * @param response
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/addblast" ,method = {RequestMethod.GET,RequestMethod.POST})
//    @SystemLog(module = "用户管理",methods = "新增爆破信息")
    public Map<String, Object> addBlast(Ls_Blast blast, HttpServletRequest request, HttpServletResponse response){
        Map<String,Object> userMap = new HashMap<String,Object>();
        //将用户信息的行政区划id添加到爆破信息中
        XtUser user = (XtUser) request.getSession().getAttribute("user");
        if(user != null){
            blast.setRegionid(user.getRegionid());
            blast.setReviewstatus("1");                            //保存数据状态为1
            DataSourceContextHolder.setDbType("frameworkdataSource");
            blastService.addBlast(blast);
//        CommonCode.addLog(request,"新增爆破信息");             //日志信息增加
            userMap.put("success",true);
            userMap.put("message","爆破信息新增成功");
        }else {
            userMap.put("success",false);
            userMap.put("message","用户未登录");
        }
        return userMap;
    }

    /**
     * 爆破信息的更新，需要传递更新的爆破信息的id
     * @param blast
     * @param request
     * @param response
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/updateblast", method = {RequestMethod.GET,RequestMethod.POST})
//    @SystemLog(module = "用户管理",methods = "更新爆破信息")
    public Map<String, Object> updateBlast(Ls_Blast blast,@RequestParam int b_id, HttpServletRequest request, HttpServletResponse response){
        Map<String,Object> userMap = new HashMap<String,Object>();
        blast.setbId(BigDecimal.valueOf(b_id));
        DataSourceContextHolder.setDbType("frameworkdataSource");
        blastService.updateBlast(blast);
//        CommonCode.addLog(request,"更新爆破信息");         //日志信息增加
        userMap.put("success",true);
        userMap.put("message","爆破信息更新成功");
        return userMap;
    }

    /**
     * 爆破信息的删除，实质为更新爆破的删除标识
     * @param b_id
     * @param request
     * @param response
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/delblast", method = {RequestMethod.GET,RequestMethod.POST})
//    @SystemLog(module = "用户管理",methods = "删除爆破信息")
    public Map<String, Object> deleteBlast(@RequestParam int b_id, HttpServletRequest request, HttpServletResponse response){
        Map<String,Object> blastMap = new HashMap<String,Object>();
        Ls_Blast blast = new Ls_Blast();
        blast.setIsdel("1");
        blast.setbId(BigDecimal.valueOf(b_id));
        DataSourceContextHolder.setDbType("frameworkdataSource");
        blastService.updateBlast(blast);                  //更新爆破删除标识信息
//        CommonCode.addLog(request,"删除爆破信息");         //日志信息增加
        blastMap.put("success",true);
        blastMap.put("message","爆破信息删除成功");
        return blastMap;
    }

    /**
     * 爆破信息的查询，传递rows，page进行分页查询
     * @param page
     * @param rows
     * @param request
     * @param response
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/getblast", method = {RequestMethod.GET,RequestMethod.POST})
//    @SystemLog(module = "用户管理",methods = "查询爆破信息")
    public HashMap<String, Object> getBlast(@RequestParam int page, @RequestParam int rows, HttpServletRequest request, HttpServletResponse response){
        HashMap<String,Object> blastMap = new HashMap<String,Object>();
        HashMap<String,Object> condMap = new HashMap<String,Object>();
        XtUser user = (XtUser) request.getSession().getAttribute("user");
        CommonCode code = new CommonCode();
        condMap = code.condMap(request);
        if(user != null){
            if ("331100".equals(user.getRegionid())){                //去掉丽水市行政区划的0
                condMap.put("regionid","3311");
            }else{
                condMap.put("regionid",user.getRegionid());
            }

        }else {
            condMap.put("regionid","未登录");
        }
        DataSourceContextHolder.setDbType("frameworkdataSource");
        PageHelper.Page<Ls_Blast> orginfo = blastService.getBlastByCond(page, rows,condMap);
        blastMap.put("total",orginfo.getTotal());
        blastMap.put("rows",orginfo.getResults());
        return blastMap;
    }

    /**
     * 爆破信息审核
     * @param blast
     * @param issuccess
     * @param request
     * @param response
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/blastreview", method = {RequestMethod.GET,RequestMethod.POST})
    public HashMap<String, Object> BlastReview(Ls_Blast blast,@RequestParam int b_id,@RequestParam String issuccess,HttpServletRequest request,HttpServletResponse response){
        HashMap<String,Object> blastMap = new HashMap<String,Object>();
        CommonCode code = new CommonCode();
        blast.setbId(BigDecimal.valueOf(b_id));
        if ("1".equals(issuccess)) {
            blast.setReviewstatus("3");             //审核通过，审核状态为y
            blastMap.put("success",true);
            blastMap.put("message","爆破信息审核通过");
            DataSourceContextHolder.setDbType("frameworkdataSource");
            blastService.updateBlast(blast);
            code.sendClientMessafe(template,"爆破信息审核通过");
        }else if ("0".equals(issuccess)){
            blast.setReviewstatus("4");            //审核不通过，审核状态为n
            blastMap.put("success",true);
            blastMap.put("message","爆破信息审核不通过");
            DataSourceContextHolder.setDbType("frameworkdataSource");
            blastService.updateBlast(blast);
        }else{                                     //未提交审核标记，不做任何操作
            blastMap.put("success",false);
            blastMap.put("message","审核参数传递失败");
        }
        return blastMap;
    }

    /**
     * 爆破信息提交
     * @param b_id
     * @param request
     * @param response
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/blastcommit",method = {RequestMethod.GET,RequestMethod.POST})
    public HashMap<String,Object > BlastCommit(@RequestParam int b_id,HttpServletRequest request,HttpServletResponse response){
        HashMap<String,Object> blastMap = new HashMap<String,Object>();
        Ls_Blast blast = new Ls_Blast();
        blast.setbId(BigDecimal.valueOf(b_id));
        blast.setReviewstatus("2");    //提交时状态更改为2
        DataSourceContextHolder.setDbType("frameworkdataSource");
        blastService.updateBlast(blast);
        blastMap.put("success",false);
        blastMap.put("message","爆破信息提交成功");
        return blastMap;
    }

}
