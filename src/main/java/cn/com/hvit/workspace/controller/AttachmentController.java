package cn.com.hvit.workspace.controller;


import cn.com.hvit.framework.kon.util.AttachmentNameBean;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * 附件管理，接口只做上传和下载
 */
@Controller
public class AttachmentController {

    /**
     * 只做下载，对于实时生成的文件，可以采用先生成，再用重定向的方法
     * 提供的功能
     * 1、根据文件类型，返回支持浏览器浏览功能
     * 2、根据参数dlname,返回下载的流
     *
     * @param fileName
     * @param request
     * @return
     * @throws IOException
     */
    //@PathVariable出现点号"."时导致路径参数截断获取不全的解决办法
    @RequestMapping(value = "eers/attachment/{fileName:.+}", method = RequestMethod.GET)
    public ResponseEntity download(
            @PathVariable String fileName,
            HttpServletRequest request) throws IOException {

        File file = new File(AttachmentNameBean.getAbsolutePath(fileName));
        HttpHeaders headers = new HttpHeaders();
        if (request.getParameterMap().containsKey("dlname")) {
            String dlname = request.getParameter("dlname");
            if (dlname == null || dlname.trim().length() == 0) {
                dlname = fileName;
            }
            dlname = new String(dlname.getBytes("utf-8"), "iso8859-1");
            headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);//直接就下载了
            headers.setContentDispositionFormData("attachment", dlname);
        } else if (fileName.contains(".")) {
            String suffix = fileName.substring(fileName.lastIndexOf(".") + 1);
            switch (suffix.toUpperCase()) {
                case "JPG":
                case "JPEG":
                case "PNG":
                    headers.setContentType(MediaType.parseMediaType("image/"+suffix));
                    break;
                default:
                    headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);//直接就下载了
                    headers.setContentDispositionFormData("attachment",
                            new String(fileName.getBytes("utf-8"), "iso8859-1"));
                    break;
            }
        }

        System.out.println("文件路径BBBB"+fileName);
        return new ResponseEntity(FileCopyUtils.copyToByteArray(file), headers, HttpStatus.OK);
    }


    /**
     * 获取上传过的文件，封装为MultipartFile,使用请参考MultipartFile接口,附件的名称为myfile
     * 单个文件上传
     * 多个文件同时上传，开发中
     * 根据返回的json数据，自行保存相关信息
     *
     * @param myfile
     * @return 返回架构设定的视图
     */
/*    @ResponseBody
    @RequestMapping(value = "eers/attachment", method = RequestMethod.POST)
    public Map processUpload(@RequestParam("myfile") MultipartFile myfile) {
        String originName = myfile.getOriginalFilename();
        AttachmentNameBean nameBean = new AttachmentNameBean(originName);
        try {
            myfile.transferTo(new File(nameBean.getAbsolutePath()));
        } catch (IOException e) {
            e.printStackTrace();
        }

        Map resultMap = new HashMap();

        resultMap.put("newfilename", nameBean.getNewFileName());
        resultMap.put("url", "eers/attachment/" + nameBean.getNewFileNamePrefix() + nameBean.getNewFileName());
        resultMap.put("relativepath", nameBean.getRelativePath());
        resultMap.put("absolutepath", nameBean.getAbsolutePath());

        resultMap.put("success", true);
        return resultMap;
    }*/

/**
 * 上传接口测试:多个文件
 * clx
 */
    @ResponseBody
    @RequestMapping(value ="/eers/attachment", method = RequestMethod.POST)
    public List<Map<String,Object>> upload2(@RequestParam MultipartFile[] myfile) {
        List<Map<String,Object>> list=new ArrayList<Map<String,Object>>();
        for(MultipartFile file : myfile){
            if(file.isEmpty()){
                System.out.println("文件未上传");
            }else{
                String originName = file.getOriginalFilename();
                AttachmentNameBean nameBean = new AttachmentNameBean(originName);
                try {
                    file.transferTo(new File(nameBean.getAbsolutePath()));
                } catch (IOException e) {
                    e.printStackTrace();
                }

                Map resultMap = new HashMap();

                resultMap.put("newfilename", nameBean.getNewFileName());
                resultMap.put("url", "eers/attachment/" + nameBean.getNewFileNamePrefix() + nameBean.getNewFileName());
                resultMap.put("relativepath", nameBean.getRelativePath());
                resultMap.put("absolutepath", nameBean.getAbsolutePath());

                resultMap.put("oldfilename", originName);
                resultMap.put("size", file.getSize());

                resultMap.put("success", true);
                list.add(resultMap);
            }
        }


        return list;
    }

}
