package com.liang.utils;

import java.net.URL;

/**
 * @author lianghaiyang
 * @date 2019/04/01
 */
public class ResourceUtils {
    public static String getResourceFileUrl(String fileName) {
        URL url = Thread.currentThread().getContextClassLoader().getResource(fileName);
        if (url == null) {
            // 如果为空采用：chromedriver.exe
            return "chromedriver.exe";
        }
        return url.getPath();
    }
}
