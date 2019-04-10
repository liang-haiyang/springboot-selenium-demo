package com.liang.common;

import org.apache.commons.lang3.StringUtils;

/**
 * @author lianghaiyang
 * @date 2019/04/01
 */
public enum BrowserTypeEnum {
    /**
     * 使用的浏览器种类
     */
    CHROME("chrome"),
    FIREFOX("firefox"),
    IE("ie"),
    EDGE("edge");

    private String name;

    public String getName() {
        return name;
    }

    BrowserTypeEnum(String name) {
        this.name = name;
    }

    public static BrowserTypeEnum getByBrowserTypeByName(String name) {
        if (StringUtils.isEmpty(name)) {
            return null;
        }
        BrowserTypeEnum[] values = BrowserTypeEnum.values();
        for (BrowserTypeEnum value : values) {
            if (name.equals(value.getName())) {
                return value;
            }
        }
        return null;
    }

}
