package ltd.newbee.mall.controller;

import ch.qos.logback.core.db.dialect.MySQLDialect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.CollectionUtils;
import org.springframework.util.MultiValueMap;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;
import org.springframework.web.multipart.support.StandardServletMultipartResolver;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

@Controller
public class UploadController {
    // 文件保存路径为 D 盘下的 upload 文件夹，可以按照自己的习惯来修改。D:\\upload\\
    // 设置的文件保存路径
    private final static String FILE_UPLOAD_PATH = "/Users/luf/Documents/upload-demo/";

    @Autowired
    private StandardServletMultipartResolver standardServletMultipartResolver;

    // @RequestParam 中文件名称属性需要与前端页面中 input 文件输入框设置的 name 属性一致。
    // 如果文件为空则返回上传失败，如果不为空则根据日期生成一个新的文件名，然后读取文件流程并写入指定的路径中，最后返回上传成功的提示信息。
    @RequestMapping(value = "/uploadFile", method = RequestMethod.POST)
    @ResponseBody
    public String uploadFile(@RequestParam("file") MultipartFile file) {
        if (file.isEmpty()) {
            return "上传失败";
        }

        String fileName = file.getOriginalFilename();
        String suffixName = fileName.substring(fileName.lastIndexOf("."));//获取文件后缀名称，从.开始

        //下面是生成文件名称的通用方法
        SimpleDateFormat sdf = new SimpleDateFormat("yyyymmdd_HHmmss");
        Random r = new Random();
        StringBuilder tempName = new StringBuilder();
        tempName.append(sdf.format(new Date())).append(r.nextInt(100)).append(suffixName);
        String newFileName = tempName.toString();

        // 下面进行读取文件
        try {
            byte[] bytes = file.getBytes();
            Path path = Paths.get(FILE_UPLOAD_PATH + newFileName);//完整路径，包含文件名及后缀
            Files.write(path, bytes);
        } catch (IOException e) {
            e.printStackTrace();
        }

        //原文件名称为fileName
        return "上传成功，原文件名称为：" + fileName + "，上传后的地址为：/upload/" + newFileName;
    }


    @RequestMapping(value = "/uploadFilesBySameName", method = RequestMethod.POST)
    @ResponseBody
    public String uploadFilesBySameName(@RequestPart MultipartFile[] files) {
        if (files == null || files.length == 0) {
            return "上传异常";
        }
        if (files.length > 5) {
            return "最多可一次上传 5 个文件";
        }

        String uploadResult = "上传成功，地址为：<br>";
        for (MultipartFile file : files) {

            String fileName = file.getOriginalFilename();
            if (StringUtils.isEmpty(fileName)) {
                //表示无文件信息，跳出本次循环
                continue;
            }

            String suffixName = fileName.substring(fileName.lastIndexOf("."));

            //生成文件名称通用方法
            SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd_HHmmss");
            Random r = new Random();
            StringBuilder tempName = new StringBuilder();
            tempName.append(sdf.format(new Date())).append(r.nextInt(100)).append(suffixName);
            String newFileName = tempName.toString();

            try {
                // 保存文件
                byte[] bytes = file.getBytes();
                Path path = Paths.get(FILE_UPLOAD_PATH + newFileName);
                Files.write(path, bytes);
                uploadResult += "/upload/" + newFileName + "<br>";
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return uploadResult;
    }

    @RequestMapping(value = "/uploadFilesByDifferentName", method = RequestMethod.POST)
    @ResponseBody
    public String uploadFilesByDifferentName(HttpServletRequest httpServletRequest) {

        List<MultipartFile> multipartFiles = new ArrayList<>();

        // 如果不是文件上传请求则不处理
        if (!standardServletMultipartResolver.isMultipart(httpServletRequest)) {
            return "请选择文件";
        }

        // 将 HttpServletRequest 对象转换为 MultipartHttpServletRequest 对象，之后读取文件
        MultipartHttpServletRequest multiRequest = (MultipartHttpServletRequest) httpServletRequest;
        Iterator<String> iterator = multiRequest.getFileNames();
        int total = 0;
        while (iterator.hasNext()) {
            if (total > 5) {
                return "最多上传 5 个文件";
            }
            total += 1;
            MultipartFile file = multiRequest.getFile(iterator.next());
            multipartFiles.add(file);
        }
        if (CollectionUtils.isEmpty(multipartFiles)) {
            return "请选择文件";
        }
        if (multipartFiles != null && multipartFiles.size() > 5) {
            return "最多可上传 5 个文件";
        }

        String uploadResult = "上传成功，地址为：<br>";
        for (int i = 0; i < multipartFiles.size(); i++) {
            String fileName = multipartFiles.get(i).getOriginalFilename();
            if (StringUtils.isEmpty(fileName)) {
                //表示无文件信息，跳出当前循环
                continue;
            }

            String suffixName = fileName.substring(fileName.lastIndexOf("."));
            //生成文件名称通用方法
            SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd_HHmmss");
            Random r = new Random();
            StringBuilder tempName = new StringBuilder();
            tempName.append(sdf.format(new Date())).append(r.nextInt(100)).append(suffixName);
            String newFileName = tempName.toString();

            try {
                // 保存文件
                byte[] bytes = multipartFiles.get(i).getBytes();
                Path path = Paths.get(FILE_UPLOAD_PATH + newFileName);
                Files.write(path, bytes);
                uploadResult += "/upload/" + newFileName + "<br>";
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return uploadResult;
    }

}