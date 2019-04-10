package com.liang.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.openqa.selenium.By;

/**
 * @author lianghaiyang
 * @date 2019/04/10
 */
@Setter
@Getter
@ToString
public class LoginMessage {
    private String username;
    private String password;
    private String verifyCode;
    private By usernameBy;
    private By passwordBy;
    private By verifyCodeInputBy;
    private By loginButtonBy;

    public void setUsername(By usernameBy, String username) {
        this.usernameBy = usernameBy;
        this.username = username;
    }

    public void setPassword(By passwordBy, String password) {
        this.passwordBy = passwordBy;
        this.password = password;
    }

    public void setVerifyCode(By verifyCodeInputBy, String verifyCode) {
        this.verifyCodeInputBy = verifyCodeInputBy;
        this.verifyCode = verifyCode;
    }
}
