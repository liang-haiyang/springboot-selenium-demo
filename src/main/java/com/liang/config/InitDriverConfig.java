package com.liang.config;

import com.liang.common.BrowserTypeEnum;
import com.liang.utils.ResourceUtils;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.springframework.boot.autoconfigure.condition.ConditionalOnExpression;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.concurrent.TimeUnit;

/**
 * @author lianghaiyang
 * @date 2019/04/01
 */
@Component
public class InitDriverConfig {
    @Resource
    private AppConfig appConfig;

    /**
     * 获取浏览器驱动，根据配置文件配置，选择驱动类型
     */
    @Bean
    public WebDriver webDriver() {
        String browser = appConfig.getBrowser();
        BrowserTypeEnum browserTypeEnum = BrowserTypeEnum.valueOf(browser.toUpperCase());
        WebDriver webDriver;
        switch (browserTypeEnum) {
            case CHROME:
                WebDriverManager.chromedriver().setup();
                webDriver = new ChromeDriver();
                break;
            case IE:
                WebDriverManager.iedriver().setup();
                webDriver = new InternetExplorerDriver();
                break;
            case FIREFOX:
                WebDriverManager.firefoxdriver().setup();
                webDriver = new FirefoxDriver();
                break;
            case EDGE:
                WebDriverManager.edgedriver().setup();
                webDriver = new FirefoxDriver();
                break;
            default:
                WebDriverManager.chromedriver().setup();
                webDriver = new ChromeDriver();
                break;
        }
        //配置页面打开超时时间
        Integer timeout = appConfig.getTimeout();
        webDriver.manage().timeouts().pageLoadTimeout(timeout == null ? 20 : timeout, TimeUnit.SECONDS);
        return webDriver;
    }
}
