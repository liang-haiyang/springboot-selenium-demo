package com.liang.service;

import com.liang.model.LoginMessage;
import com.liang.utils.BrowserUtils;
import com.liang.utils.ImageUtils;
import com.liang.utils.ResourceUtils;
import com.liang.utils.Tess4JUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * @author lianghaiyang
 * @date 2019/04/01
 */
@Service
@Slf4j
public class BrowserService {
    private static final String TEMP_PATH = ResourceUtils.getResourceFileUrl("");
    @Resource
    private WebDriver webDriver;

    /**
     * 最大化窗口
     */
    public void maximizeWindows() {
        webDriver.manage().window().maximize();
    }

    /**
     * 移动到元素element对象的“顶端”与当前窗口的“顶部”对齐
     */
    public void scrollIntoView(By by) {
        ((JavascriptExecutor) webDriver).executeScript("arguments[0].scrollIntoView();", this.findElement(by));
    }

    /**
     * 在当前页面获取一个标签
     */
    public void openURL(String url) {
        // 打开并定位url所在页面
        webDriver.navigate().to(url);
    }

    /**
     * 在新的窗口打开一个标签
     */
    public void openURLNewTab(String url) {
        JavascriptExecutor javascriptExecutor = (JavascriptExecutor) webDriver;
        javascriptExecutor.executeScript("window.open('" + url + "')");
        BrowserUtils.getAnotherPage(webDriver);
    }

    /**
     * 下载图片到临时目录
     */
    public String downloadImg(By id) {
        try {
            WebElement ele = webDriver.findElement(id);
            File screenshot = ((TakesScreenshot) webDriver).getScreenshotAs(OutputType.FILE);
            // Get entire page screenshot
            BufferedImage fullImg = ImageIO.read(screenshot);
            // Get the location of element on the page
            Point point = ele.getLocation();
            // Get width and height of the element
            int eleWidth = ele.getSize().getWidth();
            int eleHeight = ele.getSize().getHeight();
            // Crop the entire page screenshot to get only element screenshot
            BufferedImage eleScreenshot = fullImg.getSubimage(point.getX(), point.getY(), eleWidth, eleHeight);
            ImageIO.write(eleScreenshot, "jpg", screenshot);
            // Copy the element screenshot to disk
            String path = TEMP_PATH + "image.jpg";
            File screenshotLocation = new File(path);
            FileUtils.copyFile(screenshot, screenshotLocation);
            return path;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 获取图片验证码
     */
    public String getVerifyCode(By by) throws IOException {
        // 验证码
        String imgPath = this.downloadImg(by);
        // 去噪点
        BufferedImage originImage = ImageIO.read(new File(imgPath));
        BufferedImage processedImage = ImageUtils.binaryImage(ImageUtils.grayImage(ImageUtils.clipImage(originImage)));
        ImageIO.write(processedImage, "JPEG", new File(imgPath));
        String verifyCode = Tess4JUtils.parseImg(imgPath);
        // 去除\n后，去除非字母数字，数字或字母个数小于4则重新识别验证码
        if (verifyCode.length() != 5) {
            this.click(by);
            verifyCode = getVerifyCode(by);
        }
        return verifyCode;
    }

    public void login(LoginMessage loginMessage) throws InterruptedException {
        //用户名
        this.sendKeys(loginMessage.getUsernameBy(), loginMessage.getUsername());
        // 密码
        this.sendKeys(loginMessage.getPasswordBy(), loginMessage.getPassword());
        // 如果有验证码
        if (StringUtils.isNotEmpty(loginMessage.getVerifyCode()) && loginMessage.getVerifyCodeInputBy() != null) {
            this.sendKeys(loginMessage.getVerifyCodeInputBy(), loginMessage.getVerifyCode());
            String verifyCode = loginMessage.getVerifyCode();
            this.sendKeys(loginMessage.getVerifyCodeInputBy(), verifyCode);
            this.click(loginMessage.getLoginButtonBy());
            boolean alerted = this.isAlerted();
            if (alerted) {
                Thread.sleep(200);
                login(loginMessage);
            }
            // 登陆完成后等待页面加载, 防止登陆后直接刷新，清除掉已经提交的表单
            Thread.sleep(1000);
            return;
        }
        this.click(loginMessage.getLoginButtonBy());
        // 登陆完成后等待页面加载, 防止登陆后直接刷新，清除掉已经提交的表单
        Thread.sleep(1000);
    }

    /**
     * 获取element，直到element加载出来之前一直等待
     */
    public WebElement findElement(By by) {
        return webDriver.findElement(by);
    }

    public List<WebElement> findElements(By by) {
        return webDriver.findElements(by);
    }

    /**
     * 切换到指定的iframe
     */
    public void switchToFrame(String nameOrId) {
        webDriver.switchTo().frame(nameOrId);
    }

    /**
     * 切换到指定的iframe
     */
    public void switchToFrame(By by) {
        WebElement element = this.findElement(by);
        webDriver.switchTo().frame(element);
    }

    /**
     * 切换到指定的iframe
     */
    public void switchToFrame(int index) {
        webDriver.switchTo().frame(index);
    }

    /**
     * 切换到上层frame
     */
    public void switchToParentFrame() {
        webDriver.switchTo().parentFrame();
    }

    /**
     * 切换到当前window
     */
    public void switchToCurrentWindow() {
        String currentUrl = webDriver.getCurrentUrl();
        this.openURL(currentUrl);
    }

    /**
     * 刷新当前页面
     */
    public void refresh() {
        webDriver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        webDriver.navigate().refresh();
    }

    /**
     * 找到element 并且点击
     */
    public void click(By by) {
        WebDriverWait wait = new WebDriverWait(webDriver, 20);
        // 直到元素可以点击开始点击
        wait.until(ExpectedConditions.elementToBeClickable(by));
        this.findElement(by).click();
    }

    /**
     * 填写input标签
     */
    public void sendKeys(By by, String value) {
        WebDriverWait wait = new WebDriverWait(webDriver, 20);
        // 直到元素被定位到，开始获取标签文本
        wait.until(ExpectedConditions.presenceOfElementLocated(by));
        // 首先清空原有内容
        this.findElement(by).clear();
        this.findElement(by).sendKeys(value);
    }

    /**
     * 获取input标签内容
     */
    public String getInputText(By by) {
        WebDriverWait wait = new WebDriverWait(webDriver, 20);
        // 直到元素被定位到，开始获取标签文本
        wait.until(ExpectedConditions.presenceOfElementLocated(by));
        return this.findElement(by).getText();
    }

    /**
     * 鼠标悬停事件
     */
    public void moveMouseToElement(By by) throws InterruptedException {
        WebDriverWait wait = new WebDriverWait(webDriver, 20);
        // 直到元素被定位到，开始获取标签文本
        wait.until(ExpectedConditions.presenceOfElementLocated(by));
        Actions action = new Actions(webDriver);
        action.moveToElement(this.findElement(by)).perform();
        // 停留1s，防止菜单获取不到
        Thread.sleep(1000);
    }


    /**
     * 如果存在弹窗则关闭, 不存在则不进行操作
     */
    public void closeAlert() {
        try {
            webDriver.switchTo().alert().accept();
        } catch (Exception e) {
            log.info("没有弹框");
        }
    }

    /**
     * 是否存在指定文本的弹窗
     */
    public boolean isAlerted(String alertText) {
        String text = webDriver.switchTo().alert().getText();
        return alertText.equals(text);
    }

    /**
     * 是否存在任意弹框
     */
    public boolean isAlerted() {
        try {
            Alert alert = webDriver.switchTo().alert();
            return alert != null;
        } catch (Exception e) {
            // 表示没有此弹框
            return false;
        }
    }

    public void closeBrowserTag() {
        webDriver.close();
    }

    public void closeAllBrowserTag() {
        webDriver.quit();
    }
}
