package com.liang;

import com.liang.model.LoginMessage;
import com.liang.service.BrowserService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.openqa.selenium.By;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.io.IOException;

@RunWith(SpringRunner.class)
@SpringBootTest
public class SpringbootSeleniumDemoApplicationTests {

    @Resource
    private BrowserService browserService;

    @Test
    public void contextLoads() throws IOException, InterruptedException {
        browserService.openURL("xxx");
        browserService.maximizeWindows();
        // 无验证码登录  -》 开始登陆
        browserService.click(By.id("login entrance id"));
        LoginMessage loginMessage1 = new LoginMessage();
        loginMessage1.setUsername(By.id("username id"), "username");
        loginMessage1.setPassword(By.id("password id"), "password");
        loginMessage1.setLoginButtonBy(By.id("login button id"));
        browserService.login(loginMessage1);
        // 刷新浏览器，可以去掉一些广告弹窗
        browserService.refresh();
        // 搜索公司名称, 注意xpath在不同浏览器可能不同
        browserService.sendKeys(By.xpath("xpath"), "xxxx");
        Thread.sleep(2000);
        //获取文本框
        String searchResult = browserService.getInputText(By.xpath("input xpath"));
        // 鼠标悬浮事件
        browserService.moveMouseToElement(By.xpath("xxx"));
        //获取货币资金值
        //在浏览器打开一个新的标签
        browserService.openURLNewTab("new url");
        // 进入登陆页面
        browserService.click(By.xpath("xxxxx"));
        // 开始登陆
        LoginMessage loginMessage2 = new LoginMessage();
        loginMessage2.setUsername(By.id("username input id"), "username");
        loginMessage2.setPassword(By.id("username input id"), "password");
        String verifyCode = browserService.getVerifyCode(By.id("verify code image id"));
        loginMessage2.setVerifyCode(By.id("verify input "), verifyCode);
        loginMessage2.setLoginButtonBy(By.id("login button"));
        browserService.login(loginMessage2);

        // 对于一个新的iframe，需要将将driver重新定位到iframe
        browserService.switchToFrame("gndkiframe");
        browserService.switchToFrame(2);

        System.out.println("xxx");
    }


    @Test
    public void test123() throws InterruptedException {

    }


}
