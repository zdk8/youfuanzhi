package cn.com.hvit.workspace.controller.aopController;

import cn.com.hvit.framework.kon.util.PageHelper;
import cn.com.hvit.workspace.model.ls_earthquakeresponse;
import cn.com.hvit.workspace.model.ls_responsemessage;
import cn.com.hvit.workspace.service.IResponseService;
import cn.com.hvit.workspace.util.CommonCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2016/11/24.
 */
@Controller
public class ResponseController {
    @Autowired
    IResponseService responseService;

    /**
     * 添加应急响应信息
     * @param earthquake
     * @param request
     * @param response
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/addearthquake",method = {RequestMethod.GET,RequestMethod.POST})
    public Map<String, Object> addResponse(ls_earthquakeresponse earthquake, HttpServletRequest request, HttpServletResponse response){
        Map<String,Object> userMap = new HashMap<String,Object>();
        responseService.addEarthquake(earthquake);
        userMap.put("success",true);
        userMap.put("message","应急响应信息新增成功");
        return userMap;
    }

    /**
     * 应急响应信息更新
     * @param earthquake
     * @param yjid
     * @param request
     * @param response
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/updateearthquake",method = {RequestMethod.GET,RequestMethod.POST})
    public Map<String, Object> updateResponse(ls_earthquakeresponse earthquake, @RequestParam int yjid, HttpServletRequest request, HttpServletResponse response){
        Map<String,Object> userMap = new HashMap<String,Object>();
        responseService.updateEarthquake(earthquake);
        userMap.put("success",true);
        userMap.put("message","应急响应信息更新成功");
        return userMap;
    }

    /**
     * 应急响应信息查询
     * @param page
     * @param rows
     * @param request
     * @param response
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/getearthquake",method = {RequestMethod.GET,RequestMethod.POST})
    public HashMap<String, Object> getBlast(@RequestParam int page, @RequestParam int rows, HttpServletRequest request, HttpServletResponse response){
        HashMap<String,Object> blastMap = new HashMap<String,Object>();
        HashMap<String,Object> condMap = new HashMap<String,Object>();
        CommonCode code = new CommonCode();
        condMap = code.condMap(request);
        PageHelper.Page<ls_earthquakeresponse> orginfo = responseService.getresponseByCond(page, rows,condMap);
        blastMap.put("total",orginfo.getTotal());
        blastMap.put("rows",orginfo.getResults());
        return blastMap;
    }

    /**
     * 应急响应应急措施新增
     * @param message
     * @param request
     * @param response
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/addearthmessage", method = {RequestMethod.GET,RequestMethod.POST})
    public Map<String,Object> addEarthMessage(ls_responsemessage message, HttpServletRequest request, HttpServletResponse response){
        Map<String,Object> userMap = new HashMap<String,Object>();
        responseService.addEarthMessage(message);
        userMap.put("success",true);
        userMap.put("message","应急响应措施新增成功");
        return userMap;
    }

    /**
     * 应急响应措施更新
     * @param message
     * @param request
     * @param response
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/updateearthmessage", method = {RequestMethod.GET,RequestMethod.POST})
    public Map<String,Object>  updateEarthMessage(ls_responsemessage message,HttpServletRequest request, HttpServletResponse response){
        Map<String,Object> userMap = new HashMap<String,Object>();
        responseService.updateEarthmessage(message);
        userMap.put("success",true);
        userMap.put("message","应急响应措施更新成功");
        return userMap;
    }

    /**
     * 应急响应信息查询（因只有一个应急预案，不以id查询）
     * @param page
     * @param rows
     * @param request
     * @param response
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/getearthmsg", method = {RequestMethod.GET,RequestMethod.POST})
    public HashMap<String,Object> getEarthmsgByyjid(@RequestParam int page, @RequestParam int rows,HttpServletRequest request,HttpServletResponse response){
        HashMap<String,Object> earthmsgMap = new HashMap<String,Object>();
        HashMap<String,Object> condMap = new HashMap<String ,Object>();
        CommonCode code = new CommonCode();
        condMap = code.condMap(request);
        PageHelper.Page<ls_responsemessage> msginfo = responseService.getEarthmsgByid(page,rows,condMap);
        earthmsgMap.put("total",msginfo.getTotal());
        earthmsgMap.put("rows",msginfo.getResults());
        return earthmsgMap;
    }

    /**
     * 应急响应启动
     * @param level
     * @param request
     * @param response
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/responsestart", method = {RequestMethod.GET,RequestMethod.POST})
    public Map<String,Object>  responseStart(@RequestParam String level,HttpServletRequest request, HttpServletResponse response){
        Map<String,Object> userMap = new HashMap<String,Object>();
        Map<String,Object> responseMap = new HashMap<String,Object>();
        responseMap.put("rmlevel",level);
        CommonCode code = new CommonCode();
        List<ls_responsemessage> message = responseService.getResponsebylevel(responseMap);              //根据应急id，应急等级level，查找应急相关的应急信息
        for (int i = 0 ; i<message.size(); i++) {
           if (message.get(i).getTelephone() != null){
               try {
                   if (message.get(i).getTelephone() == "18358158536") {                                   //测试阶段使用特定电话进行测试，发送一条后退出
                       code.sendmsg2(message.get(i).getTelephone(),"[丽水地震响应]" + message.get(i).getContact());
                       break;
                   }
               } catch (IOException e) {
                   e.printStackTrace();
               }
           }
        }
        responseService.setResponseStatus("start");             //更新预案启动状态
        userMap.put("success",true);
        userMap.put("message","应急响应措施启动成功");
        return userMap;
    }

    /**
     * 应急预案关闭
     * @param request
     * @param response
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/responsestop", method = {RequestMethod.GET,RequestMethod.POST})
    public Map<String,Object>  responseStop(HttpServletRequest request, HttpServletResponse response){
        Map<String,Object> stopMap = new HashMap<String,Object>();
        responseService.setResponseStatus("stop");
        stopMap.put("success",true);
        stopMap.put("message","应急响应措施关闭成功");
        return stopMap;
    }

}
