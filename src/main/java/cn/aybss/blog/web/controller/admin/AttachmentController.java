package cn.aybss.blog.web.controller.admin;

import cn.aybss.blog.model.domain.Attachment;
import cn.aybss.blog.model.domain.Log;
import cn.aybss.blog.model.dto.JsonResult;
import cn.aybss.blog.model.dto.LogConstant;
import cn.aybss.blog.model.enums.BlogEnum;
import cn.aybss.blog.service.AttachmentService;
import cn.aybss.blog.service.LogService;
import cn.aybss.blog.util.BlogUtil;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.RandomUtil;
import cn.hutool.extra.servlet.ServletUtil;
import com.github.pagehelper.PageInfo;
import net.coobird.thumbnailator.Thumbnails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

@Controller
@RequestMapping(value = "/admin/attachment")
public class AttachmentController extends BaseController {

    @Autowired
    private AttachmentService attachmentService;
    @Autowired
    private LogService logService;

    @GetMapping
    public String attachment(Model model, @RequestParam(value = "page",defaultValue = "1")int page,@RequestParam(value = "limit",defaultValue = "18")int limit){
        PageInfo<Attachment> pageInfo = attachmentService.getAttachment(page,limit);
        model.addAttribute("info",pageInfo);
        return "admin/admin_attachment";
    }
    @RequestMapping(value = "/upload")
    @ResponseBody
    public JsonResult upload(@RequestParam(value = "file")MultipartFile file,HttpServletRequest request){
        return uploadAttachment(file,request);
    }



    public JsonResult uploadAttachment(@RequestParam(value = "file")MultipartFile file, HttpServletRequest request) {
        if(!file.isEmpty()){
            try {
                //获取用户目录
                String userPath = System.getProperties().getProperty("user.home")+"/blog/";
                //保存目录
                StringBuffer hold = new StringBuffer("upload/");
                //获取时间，以年运创建目录
                Date date = DateUtil.date();
                hold.append(DateUtil.thisYear()).append("/").append(DateUtil.thisMonth()+1).append("/");
                File mediaPath = new File(userPath,hold.toString());
                //如果没有该目录则创建
                if(!mediaPath.exists()){
                    mediaPath.mkdirs();
                }
                System.out.println("路径++++++" + mediaPath);
                SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
                //生成文件名
                String nameSuffix = file.getOriginalFilename().substring(0,file.getOriginalFilename().lastIndexOf("."))
                        .replaceAll(" ","_").replaceAll(",","")+sdf.format(DateUtil.date())+ new Random().nextInt(1000);
                //文件后缀
                String fileSuffix = file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf(".")+1);
                String fileName = nameSuffix+"."+fileSuffix;
                //转存文件
                file.transferTo(new File(mediaPath.toString(),fileName));
                //原图片路径
                StringBuffer originalPath = new StringBuffer();
                originalPath.append(mediaPath.getAbsolutePath()).append("/").append(fileName);
                //压缩图片路径
                StringBuffer compressPath = new StringBuffer();
                compressPath.append(mediaPath.getAbsolutePath()).append("/").append(nameSuffix).append("_small.").append(fileSuffix);
                //压缩图片
                Thumbnails.of(originalPath.toString()).size(256,256).keepAspectRatio(false).toFile(compressPath.toString());
                //原图数据库路径
                StringBuffer originalDataPath = new StringBuffer();
                originalDataPath.append("/").append(hold).append(fileName);
                //压缩图数据库路劲
                StringBuffer compressDataPath = new StringBuffer();
                compressDataPath.append("/").append(hold).append(nameSuffix).append("_small.").append(fileSuffix);
                //添加数据库
                Attachment attachment = new Attachment();
                attachment.setPictureName(fileName);
                attachment.setPicturePath(originalDataPath.toString());
                attachment.setPictureType(file.getContentType());
                attachment.setPictureCreateDate(DateUtil.date());
                attachment.setPictureSuffix(new StringBuffer().append(".").append(fileSuffix).toString());
                attachment.setPictureSmallPath(compressDataPath.toString());
                attachment.setPictureWh(BlogUtil.getImageWh(new File(mediaPath.toString()+"/"+fileName)));
                attachment.setPictureSize(BlogUtil.parseSize(new File(mediaPath.toString()+"/"+fileName).length()));
                attachmentService.save(attachment);
                //添加日志
                logService.save(new Log(LogConstant.UPLOAD_ATTACHMENT,LogConstant.UPLOAD_SUCCESS, ServletUtil.getClientIP(request),DateUtil.date()));
            }catch (Exception e){
                e.printStackTrace();
                return new JsonResult(false,"未知错误");
            }

        }else {
            return new JsonResult(false,"文件不能为空");
        }
        return new JsonResult(true,"上传成功");
    }

    @GetMapping(value = "viewDetails/{id}")
    public String viewDetails(Model model, @PathVariable int id){
        Attachment attachment = attachmentService.findById(id);
        model.addAttribute("attachment",attachment);
        return "admin/part/view_details";
    }

    @PostMapping(value = "deleteAttachment")
    @ResponseBody
    public JsonResult deleteAttachment(int id,HttpServletRequest request){
        Attachment attachment = attachmentService.findById(id);
        try {
            String basePath = System.getProperties().getProperty("user.home")+"/blog";
            String attachmentPath = attachment.getPicturePath();
            String smallAttachmentPath = attachment.getPictureSmallPath();
            File attachmentFile = new File(new StringBuffer().append(basePath).append(attachmentPath).toString());
            File smallAttachmentFile = new File(new StringBuffer().append(basePath).append(smallAttachmentPath).toString());
            if(attachmentFile.isFile()&&attachmentFile.exists()){
                System.gc();
                if(attachmentFile.delete()&&smallAttachmentFile.delete()){
                    attachmentService.delete(id);
                    logService.save(new Log(LogConstant.DELETE_ATTACHMENT,LogConstant.DELETE_SUCCESS,ServletUtil.getClientIP(request),DateUtil.date()));
                }else {
                    return new JsonResult(BlogEnum.OPERATION_ERROR.isFlag(),BlogEnum.OPERATION_ERROR.getMessage());
                }
            }
        }catch (Exception e){
            return new JsonResult(BlogEnum.ERROR.isFlag(),BlogEnum.ERROR.getMessage());
        }
        return new JsonResult(BlogEnum.OPERATION_SUCCESS.isFlag(),BlogEnum.PRESERVE_SUCCESS.getMessage());
    }

}
