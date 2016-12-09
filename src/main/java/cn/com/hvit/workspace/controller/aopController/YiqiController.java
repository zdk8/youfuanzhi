package cn.com.hvit.workspace.controller.aopController;

import cn.com.hvit.framework.kon.util.DataSourceContextHolder;
import cn.com.hvit.workspace.service.IYiqiService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * Created by Administrator on 2016/12/9.
 */
@Controller
public class YiqiController {

    @Autowired
    private IYiqiService yiqiService;

    @ResponseBody
    @RequestMapping(value = "/getyqquake" ,method = {RequestMethod.GET,RequestMethod.POST})
    public List getYqQuake(){
//        HashMap<String,Object> quakeMap = new HashMap<String,Object>();
//        HashMap<String,Object> condMap = new HashMap<String,Object>();
//        CommonCode code = new CommonCode();
//        condMap = code.condMap(request);
        DataSourceContextHolder.setDbType("lishuidataSource");
        List earthData = yiqiService.getEarthData();
//        quakeMap.put("total",orginfo.getTotal());
//        quakeMap.put("rows",orginfo.getResults());
        return earthData;
    }


    @ResponseBody
    @RequestMapping(value = "/getyqstation" ,method = {RequestMethod.GET,RequestMethod.POST})
    public List getYqStation(){
        DataSourceContextHolder.setDbType("lishuidataSource");
        List stationData = yiqiService.getStationData();
        return stationData;
    }
}
