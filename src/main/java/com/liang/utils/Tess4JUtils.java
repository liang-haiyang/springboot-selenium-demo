package com.liang.utils;

import net.sourceforge.tess4j.ITesseract;
import net.sourceforge.tess4j.Tesseract;
import net.sourceforge.tess4j.TesseractException;

import java.io.File;

/**
 * @author lianghaiyang
 * @date 2019/04/02
 */
public class Tess4JUtils {

    /**
     * 默认只转换英文和数字，需要中文需要额外加入训练文件
     */
    public static String parseImg(String imgPath) {
        File imageFile = new File(imgPath);
        if (!imageFile.exists()) {
            return "图片不存在";
        }
        ITesseract tess = new Tesseract();
        tess.setDatapath("tessdata");
        tess.setLanguage("eng");
        String s = null;
        try {
            s = tess.doOCR(imageFile);
        } catch (TesseractException e) {
            e.printStackTrace();
        }
        return s;
    }
}
