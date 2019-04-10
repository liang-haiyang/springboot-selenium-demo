package com.liang.utils;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.Set;

/**
 * @author lianghaiyang
 * @date 2019/04/01
 */
public class BrowserUtils {

    /**
     * java 代码调用js
     *
     * @param driver：传入的浏览器对象
     */
    public static void inputStrByJS(WebDriver driver, String id, String value) {
        //1、js代码，用id来得到 input 标签，并且对该标签赋值
        String setValueJS = "document.getElementById('" + id + "').value='" + value + "';";
        //2、使用driver执行该js代码
        ((JavascriptExecutor) driver).executeScript(setValueJS);

    }

    /**
     * 这个方法是当有两个标签页时切换到另一个标签页的方法
     *
     * @param driver 浏览器驱动
     */
    public static void getAnotherPage(WebDriver driver) {
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        //1、首先获得当前标签页的 handle
        String currentPageHandle = driver.getWindowHandle();

        //2、获得所有标签页的 handle
        Set<String> totalPageHandle = driver.getWindowHandles();

        //3、遍历handle，如果不是当前的 handle 那么切换到另一个handle的标签页
        for (String handleStr : totalPageHandle) {
            if (!handleStr.equals(currentPageHandle)) {
                //跳转到另一个标签页，这个时候你就可以用driver控制当前页面了
                driver.switchTo().window(handleStr);
            }
        }
    }

    /**
     * 这个方法是当有两个标签页时切换到另一个标签页的方法，并且关闭之前的页面
     *
     * @param driver 获取另一个标签，关闭当前标签
     */
    public static void getAnotherPageAndCloseCurrentPage(WebDriver driver) {
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        //1、首先获得当前标签页的 handle
        String currentPageHandle = driver.getWindowHandle();

        //2、关闭当前页面
        driver.close();

        //3、获得所有标签页的 handle
        Set<String> totalPageHandle = driver.getWindowHandles();

        //4、遍历handle，如果不是当前的 handle 那么切换到另一个handle的标签页
        for (String handleStr : totalPageHandle) {
            if (!handleStr.equals(currentPageHandle)) {
                //跳转到另一个标签页，这个时候你就可以用driver控制当前页面了
                driver.switchTo().window(handleStr);
            }
        }

    }

    /**
     * 根据传入的元素滑动到对应的元素，然后点击这个元素，可以防止点击不到的情况
     *
     * @param element : 传入的元素，注意这里的元素必须是可以点击的
     *                例如a标签中有href，或者某个元素中有个onclick事件
     * @param driver  : 传入个driver，可以当作传入个浏览器对象，因为对浏览器的 操作都需要这个对象，因此必须传入它
     */
    public static void scrollToElementAndClick(WebElement element, WebDriver driver) {
        //得到传入元素的y轴坐标
        int yScrollPosition = element.getLocation().getY() - 100;
        //得到传入元素的x轴坐标
        int xScrollPosition = element.getLocation().getX();
        //将浏览器对象强制转为可以执行js的对象
        JavascriptExecutor executor = (JavascriptExecutor) driver;
        //页面上执行js代码，用来操控页面，该js代码为：window.scroll(xScrollPosition ,  yScrollPosition );  意思是窗口跳转到x，y的位置，可以防止窗口没到那里点击不到的情况
        executor.executeScript("window.scroll(" + xScrollPosition + ", " + yScrollPosition + ");");
        element.click();
    }

    /**
     * 模拟鼠标操作
     */
    public static void rightClickMouse(WebDriver driver,By by) {
        driver.get("http://www.baidu.com");
        Actions action = new Actions(driver);
        action.contextClick(driver.findElement(by)).perform();
    }

    /**
     * 截屏
     */
    public static void testScreenShot(WebDriver driver) throws Exception {
        driver.get("http://www.baidu.com");
        File srcFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
        FileUtils.copyFile(srcFile, new File("c:\\1.png"));
    }

}
