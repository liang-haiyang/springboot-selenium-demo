package com.liang;

import com.liang.utils.ResourceUtils;
import org.junit.Test;
import java.net.URL;

/**
 * @author lianghaiyang
 * @date 2019/04/02
 */
public class SimpleTest {

    @Test
    public void test1() {
        String resourceFileUrl = ResourceUtils.getResourceFileUrl("");
        URL resource = Thread.currentThread().getContextClassLoader().getResource("");
        System.out.println(resource.getPath());
        System.out.println(resourceFileUrl);
    }

    @Test
    public void test2() {
        int recursion = recursion();
        System.out.println(recursion);
    }

    private int recursion() {
        int s = Double.valueOf(Math.random() * 10).intValue();
        if (s != 5) {
            s= recursion();
        }
        return s;
    }

}
