package cn.com.hvit.workspace.controller.aopController;

import cn.com.hvit.framework.kon.util.AttachmentNameBean;
import cn.com.hvit.framework.kon.util.PageHelper;
import cn.com.hvit.workspace.model.LS_files;
import cn.com.hvit.workspace.service.IFileService;
import cn.com.hvit.workspace.util.CommonCode;
import com.sun.scenario.effect.impl.sw.sse.SSEBlend_SRC_OUTPeer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.util.FileCopyUtils;
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
    @Autowired
    IFileService fileService;

    /**
     * 文件上传并保存文件信息
     * @param myfile
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/fileupload",method = {RequestMethod.GET,RequestMethod.POST})
    public Map<String, Object> fileupload(@RequestParam MultipartFile[] myfile){
        Map<String,Object> userMap = new HashMap<String,Object>();
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

                LS_files files = new LS_files();
                files.setOriginname(originName);
                files.setFilename( nameBean.getNewFileName());
                files.setFilepath("filedownload/" + nameBean.getNewFileNamePrefix() + nameBean.getNewFileName());
                files.setFilesize(String.valueOf(file.getSize()));
                fileService.addFile(files);
            }
        }
        userMap.put("success",true);
        userMap.put("message","爆破信息删除成功");
        return userMap;
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
//        CommonCode code = new CommonCode();
//        condMap = code.condMap(request);
        String filename = request.getParameter("filename");
        condMap.put("filename",filename);
        PageHelper.Page<LS_files> filesinfo = fileService.getfilesbycond(page, rows,condMap);
        fileMap.put("total",filesinfo.getTotal());
        fileMap.put("rows",filesinfo.getResults());
        return fileMap;
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
        String winPath = "\\\\192.168.1.142\\lishuigxtest\\"+filepath;
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
//        CommonCode code = new CommonCode();
//        condMap = code.condMap(request);
        String filename = request.getParameter("filename");
        condMap.put("filename",filename);
        PageHelper.Page<LS_files> filesinfo = fileService.getprovincefilesbycond(page, rows,condMap);
        fileMap.put("total",filesinfo.getTotal());
        fileMap.put("rows",filesinfo.getResults());
        return fileMap;
    }
}
