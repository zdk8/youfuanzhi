package cn.com.hvit.workspace.controller.aopController;

import cn.com.hvit.framework.kon.util.AttachmentNameBean;
import cn.com.hvit.workspace.util.CommonCode;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2016/11/23.
 */
@Controller
public class FileController {


    /**
     * 文件上传并保存文件信息
     * @param myfile
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/fileupload",method = {RequestMethod.GET,RequestMethod.POST})
    public Map<String, Object> fileupload(@RequestParam MultipartFile[] myfile){
        Map<String,Object> resultMap = new HashMap<String,Object>();
        List<Map<String,Object>> list = new ArrayList<Map<String,Object>>();
        for (MultipartFile file : myfile){
            if (file.isEmpty()){
                System.out.println("文件未上传");
            }else {
                String originName = file.getOriginalFilename();
                AttachmentNameBean nameBean = new AttachmentNameBean(originName);
                try {
                    file.transferTo(new File(nameBean.getAbsolutePath()));
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
        }
        resultMap.put("success",true);
        resultMap.put("message","文件上传成功");
        return resultMap;
    }

    /**
     * 文件下载
     * @param filename
     * @param request
     * @return
     * @throws IOException
     */
    @RequestMapping(value = "/filedownload/{filename:.+}", method = {RequestMethod.GET,RequestMethod.POST})
    public ResponseEntity download(@PathVariable String filename, HttpServletRequest request) throws IOException {
        CommonCode code = new CommonCode();
        return code.filedownload(filename,request);
    }

    /**
     * 文件信息查询
     * @param page
     * @param rows
     * @param request
     * @param response
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/getfiles" ,method = {RequestMethod.GET,RequestMethod.POST})
    public HashMap<String, Object> getFiles(@RequestParam int page, @RequestParam int rows, HttpServletRequest request, HttpServletResponse response){
        HashMap<String,Object> fileMap = new HashMap<String,Object>();
        HashMap<String,Object> condMap = new HashMap<String,Object>();
        CommonCode code = new CommonCode();
        condMap = code.condMap(request);
//        String filename = request.getParameter("filename");
//        condMap.put("filename",filename);
        return fileMap;
    }

    @ResponseBody
    @RequestMapping(value = "/delete-ziliao", method = {RequestMethod.GET,RequestMethod.POST})
    public HashMap<String, Object> deleteFiles(@RequestParam int fid, HttpServletRequest request, HttpServletResponse response){
        HashMap<String,Object> resultMap = new HashMap<String,Object>();
        HashMap<String,Object> condMap = new HashMap<String,Object>();
        condMap.put("fid",fid);
        resultMap.put("success",true);
        resultMap.put("message","文件删除成功");
        return  resultMap;
    }

    /**
     * 文件下载（服务器共享文件夹）
     * @param filename
     * @param request
     * @return
     * @throws IOException
     */
    @RequestMapping(value = "/{filename:.+}", method = {RequestMethod.GET,RequestMethod.POST})
    public ResponseEntity windownload(@PathVariable String filename,@RequestParam String filepath, HttpServletRequest request) throws IOException {
//        String winPath = "\\\\192.168.1.142\\lishuigxtest\\"+filepath;
        String winPath = "\\\\10.33.253.40\\upload\\"+filepath;
        CommonCode code = new CommonCode();
        return code.filedownload2(filename,request,winPath);
    }

    /**
     * 文件查询（省厅，通过数据库表同步，查本地数据库表）
     * @param page
     * @param rows
     * @param request
     * @param response
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/getprovincefiles" ,method = {RequestMethod.GET,RequestMethod.POST})
    public HashMap<String, Object> getShengFiles(@RequestParam int page, @RequestParam int rows, HttpServletRequest request, HttpServletResponse response){
        HashMap<String,Object> fileMap = new HashMap<String,Object>();
        HashMap<String,Object> condMap = new HashMap<String,Object>();
        CommonCode code = new CommonCode();
        condMap = code.condMap(request);
//        String filename = request.getParameter("filename");
//        condMap.put("filename",filename);
        return fileMap;
    }
}
