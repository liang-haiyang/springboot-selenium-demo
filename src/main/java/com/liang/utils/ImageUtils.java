package com.liang.utils;

import java.awt.image.BufferedImage;

/**
 * @author lianghaiyang
 * @date 2019/04/02
 */
public class ImageUtils {
    /**
     * 裁剪图片：去掉黑边
     */
    public static BufferedImage clipImage(BufferedImage srcImage) {
        return srcImage.getSubimage(1, 1, srcImage.getWidth() - 2, srcImage.getHeight() - 2);
    }

    /**
     * 灰度化
     */
    public static BufferedImage grayImage(BufferedImage srcImage) {
        return copyImage(srcImage, new BufferedImage(srcImage.getWidth(), srcImage.getHeight(), BufferedImage.TYPE_BYTE_GRAY));
    }

    /**
     * 二值化
     */
    public static BufferedImage binaryImage(BufferedImage srcImage) {
        return copyImage(srcImage, new BufferedImage(srcImage.getWidth(), srcImage.getHeight(), BufferedImage.TYPE_BYTE_BINARY));
    }


    public static BufferedImage copyImage(BufferedImage srcImage, BufferedImage destImage) {
        for (int y = 0; y < srcImage.getHeight(); y++) {
            for (int x = 0; x < srcImage.getWidth(); x++) {
                destImage.setRGB(x, y, srcImage.getRGB(x, y));
            }
        }
        return destImage;
    }
}
