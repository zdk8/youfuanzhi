package cn.com.hvit.workspace.controller.aopController;

import cn.com.hvit.framework.kon.util.AttachmentNameBean;
import cn.com.hvit.framework.kon.util.PageHelper;
import cn.com.hvit.workspace.model.LS_files;
import cn.com.hvit.workspace.service.IFileService;
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
                AttachmentNameBean nameBean = new AttachmentNameBean();
                try {
                    file.transferTo(new File(nameBean.getAbsolutePath()));
                } catch (IOException e) {
                    e.printStackTrace();
                }

                LS_files files = new LS_files();

                files.setFilename( nameBean.getOriginFileName());
                files.setFilepath(nameBean.getNewFileNamePrefix() + nameBean.getNewFileName());
                files.setFilesize(String.valueOf(file.getSize()));
                fileService.addFile(files);
            }
        }
        userMap.put("success",true);
        userMap.put("message","爆破信息删除成功");
        return userMap;
    }

    @RequestMapping(value = "/filedownload", method = {RequestMethod.GET,RequestMethod.POST})
    public ResponseEntity download(@PathVariable String filename, HttpServletRequest request) throws IOException {
        File file = new File(AttachmentNameBean.getAbsolutePath(filename));
        HttpHeaders headers = new HttpHeaders();
        if (request.getParameterMap().containsKey("dlname")){
            String dlname = request.getParameter("dlname");
            if (dlname == null || dlname.trim().length() == 0){
                dlname = filename;
            }
            dlname = new String(dlname.getBytes("utf-8"),"iso8859-1");
            headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
            headers.setContentDispositionFormData("attachment",dlname);
        } else if (filename.contains(".")) {
            String suffix = filename.substring(filename.lastIndexOf(".") + 1);
            switch (suffix.toUpperCase()){
                case "JPG":
                case "JPEG":
                case "PNG":
                    headers.setContentType(MediaType.parseMediaType("image/" + suffix));
                    break;
                default:
                    headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
                    headers.setContentDispositionFormData("attachment",new String(filename.getBytes("utf-8"),"iso8859-1"));
                    break;
            }
        }
        return new ResponseEntity(FileCopyUtils.copyToByteArray(file),headers, HttpStatus.OK);
    }

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
}
