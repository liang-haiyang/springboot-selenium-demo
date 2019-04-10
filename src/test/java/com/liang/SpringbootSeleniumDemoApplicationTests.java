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
        browserService.openURL("http://e.chanjet.com/#home/customer");
        browserService.maximizeWindows();
        // 开始登陆
        browserService.click(By.id("loginBtnId"));
        LoginMessage loginMessage1 = new LoginMessage();
        loginMessage1.setUsername(By.id("login3loginNameIpt"), "13324050548");
        loginMessage1.setPassword(By.id("login3loginPwdIpt"), "123456");
        loginMessage1.setLoginButtonBy(By.id("login3Btn"));
        browserService.login(loginMessage1);
        // 去掉广告弹窗
        browserService.refresh();
        // 搜索公司名称
        browserService.sendKeys(By.xpath("//*[@id=\"cusSearchTxt\"]"), "沈阳诸葛企业\n");
        Thread.sleep(2000);
        //获取搜索到的第一行
        String searchResult = browserService.getInputText(By.xpath("//*[@id=\"assign_temp_wp\"]/table/tbody/tr/td[2]/div/span[2]"));
        if (!searchResult.contains("沈阳诸葛企业")) {
            return;
        }
        //进入资产负债表
        browserService.click(By.xpath("//*[@id=\"assign_temp_wp\"]/table/tbody/tr/td[9]/a[1]"));
        browserService.moveMouseToElement(By.xpath("//*[@id=\"app/vm/Header_0\"]/div[24]"));
        browserService.moveMouseToElement(By.xpath("//*[@id=\"costReportManage\"]/a/span[2]"));
        browserService.click(By.xpath("//*[@id=\"common/form/LeftCommandMenu_9\"]/div[1]/div/div/ul/li[1]/a"));
        //获取货币资金值
        String monetaryFund = browserService.getInputText(By.xpath("//*[@id=\"nav_balanceSheet\"]/div[2]/div[2]/div[2]/table/tbody/tr[2]/td[3]/span"));
        //打开一个新的标签
        browserService.openURLNewTab("https://etax.liaoning.chinatax.gov.cn/sword?ctrl=LoginCtrlTmp_logout#");
        // 进入登陆页面
        browserService.click(By.xpath("/html/body/div[2]/div/a"));
        // 开始登陆
        LoginMessage loginMessage2 = new LoginMessage();
        loginMessage2.setUsername(By.id("name2"), "91210103MA0XYDTP7T");
        loginMessage2.setPassword(By.id("pass2"), "ZZT3LW");
        String verifyCode = browserService.getVerifyCode(By.id("picimg"));
        loginMessage2.setVerifyCode(By.id("check_code"), verifyCode);
        loginMessage2.setLoginButtonBy(By.id("loginDW"));
        browserService.login(loginMessage2);
        // 去掉弹框
        browserService.refresh();
        browserService.click(By.xpath("//*[@id=\"cwgnlb\"]/li[3]/a"));
        //点击下一步
        // 对于一个新的iframe，需要将将driver重新定位到iframe
        browserService.switchToFrame("gndkiframe");
        browserService.click(By.xpath("//*[@id=\"sbToolBar\"]/div[4]/div[3]/div[2]"));
        browserService.switchToFrame(2);
        browserService.sendKeys(By.xpath("//*[@id=\"zcfzbxxForm_h1l2\"]"), monetaryFund);

        System.out.println("xxx");
    }


    @Test
    public void test123() throws InterruptedException {

    }


}
