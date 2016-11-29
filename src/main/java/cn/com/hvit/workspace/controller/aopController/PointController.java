package cn.com.hvit.workspace.controller.aopController;

import cn.com.hvit.framework.kon.util.DataSourceContextHolder;
import cn.com.hvit.framework.kon.util.PageHelper;
import cn.com.hvit.workspace.model.Pointinfo;
import cn.com.hvit.workspace.service.IPonitService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.List;

/**
 * Created by Administrator on 2016/11/29.
 */
@Controller
public class PointController {
    @Autowired
    private IPonitService ponitService;

    /**
     * 点数据查询（分页）
     * @param page
     * @param rows
     * @param request
     * @param response
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/getponitbyconds", method = {RequestMethod.GET,RequestMethod.POST})
//    @SystemLog(module = "用户管理",methods = "查询爆破信息")
    public HashMap<String, Object> getPoint(@RequestParam int page, @RequestParam int rows, HttpServletRequest request, HttpServletResponse response){
        HashMap<String,Object> blastMap = new HashMap<String,Object>();
        HashMap<String,Object> condMap = new HashMap<String,Object>();
//        CommonCode code = new CommonCode();
//        condMap = code.condMap(request);
        DataSourceContextHolder.setDbType("yjxydataSource");
        PageHelper.Page<Pointinfo> orginfo = ponitService.getPonitByconds(page, rows,condMap);
        blastMap.put("total",orginfo.getTotal());
        blastMap.put("rows",orginfo.getResults());
        return blastMap;
    }

    /**
     * 查询所有点数据
     * @param request
     * @param response
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/getpoints" ,method = {RequestMethod.GET,RequestMethod.POST})
    public List getPointstest(HttpServletRequest request,HttpServletResponse response){
        DataSourceContextHolder.setDbType("yjxydataSource");
        List pointList = ponitService.getPoints();
        return pointList;
    }

}
