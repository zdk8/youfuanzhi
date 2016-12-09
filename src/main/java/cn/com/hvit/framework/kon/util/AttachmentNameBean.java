package cn.com.hvit.framework.kon.util;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

/**
 * Created by wp on 16-9-23.
 */
public class AttachmentNameBean {
    private  static String ATTACHMENTDIRNAME="/home/wp/Downloads/upload/";


    static {
        if(System.getProperty("os.name").contains("Windows")){
            ATTACHMENTDIRNAME="c:/Downloads/upload/";
        }else{
            ATTACHMENTDIRNAME=System.getProperty("user.home")+"/Downloads/upload/";
        }
    }

    private final static SimpleDateFormat fmt = new SimpleDateFormat("yyyy/MM/dd");

    private String originFileName;
    private String relativePath;
    private String absolutePath;
    private String newFileName;
    private String newFileNamePrefix;
    private String extName;

    public AttachmentNameBean() {
        this(UUID.randomUUID().toString());
    }

    public AttachmentNameBean(String fileName) {
        this.setOriginFileName(fileName);
    }

    /**
     * 根据文件名（有横线）,获取相对路径，其他就是把前两个-换成斜线
     * @param filename
     * @return
     */
    public static String getRelativePath(String filename) {
        return filename.replaceFirst("-", "/").replaceFirst("-", "/").replaceFirst("-", "/");
    }

    /**
     * 根据文件名（有横线）,获取绝对路径
     * @param filename
     * @return
     */
    public static String getAbsolutePath(String filename) {
        return ATTACHMENTDIRNAME + getRelativePath(filename);
    }



    /**
     * 根据文件名称，获取相去路径，如果文件名长度小于10,则用uuid,如果已经存在，则用uuid,否则用filename，
     * 然后根据当前时间得到文件路径的其他部分
     * @param fileName
     * @return
     */
    public static String createRelativePath(String fileName) {
        String yyyy_MM_dd=fmt.format(new Date());
        String extName = lastExtName(fileName);
        File fileDir = new File(ATTACHMENTDIRNAME + yyyy_MM_dd);
        if (!fileDir.exists()) {
            System.out.println("文件不存在：创建目录 "+ATTACHMENTDIRNAME + yyyy_MM_dd);
            fileDir.mkdirs();
        }
        if (fileName.length() < 10) {
            fileName = UUID.randomUUID().toString()+extName;
        }else{
            File file = new File(ATTACHMENTDIRNAME+yyyy_MM_dd+"/"+fileName);
            if (file.exists()) {
                fileName = UUID.randomUUID().toString()+extName;
            }
        }
        return yyyy_MM_dd + "/" + fileName;
    }

    private static String lastExtName(String fileName) {
        if (fileName.contains(".")) {
            return fileName.substring(fileName.lastIndexOf("."));
        }else{
            return "";
        }
    }
    /**
     * 根据当前时间和uuid生成相对文件路径
     * @return
     */
    public static String createRelativePath() {
        return createRelativePath(UUID.randomUUID().toString());
    }



    public String getOriginFileName() {
        return originFileName;
    }

    private void setOriginFileName(String originFileName) {
        this.originFileName = originFileName;
        this.relativePath= createRelativePath(originFileName);
        String tmpRP0 = this.relativePath.substring(this.relativePath.indexOf("/")+1);
        String tmpRP = tmpRP0.substring(tmpRP0.indexOf("/") + 1);
        this.newFileName = tmpRP.substring(tmpRP.indexOf("/")+1);
        this.newFileNamePrefix = this.relativePath.substring(0, "2016/01/01/".length()).replace("/","-");
        this.absolutePath = ATTACHMENTDIRNAME + relativePath;
    }

    public String getRelativePath() {
        return relativePath;
    }


    public String getAbsolutePath() {
        return absolutePath;
    }

    public String getNewFileName() {
        return newFileName;
    }

    public String getNewFileNamePrefix() {
        return newFileNamePrefix;
    }

}
