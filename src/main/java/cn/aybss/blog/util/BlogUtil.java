package cn.aybss.blog.util;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

public class BlogUtil {

    public static String parseSize(long size){
        if(size<1024){
            return String.valueOf(size)+"B";
        }else {
            size /= 1024;
        }
        if(size<1024){
            return String.valueOf(size)+"KB";
        }else {
            size /= 1024;
        }
        if(size<1024){
            size *= 100;
            return String.valueOf(size/100)+"." + String.valueOf(size % 100) + "MB";
        }else {
            size = size * 100 / 1024;
            return String.valueOf(size/100)+"." + String.valueOf(size % 100) + "GB";
        }
    }

    public static String getImageWh(File file){
        try {
            BufferedImage bi = ImageIO.read(new FileInputStream(file));
            return bi.getWidth()+"x"+bi.getHeight();
        } catch (IOException e) {
            e.printStackTrace();
            return "";
        }
    }

}
