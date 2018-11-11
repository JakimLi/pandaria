package com.github.jakimli.pandaria.utils;

import org.openqa.selenium.By;

public class SelectorUtil {
    public static By by(String selector) {
        if (selector.startsWith("#")) {
            return By.id(selector.substring(1));
        } else if (selector.startsWith(".")) {
            return By.className(selector.substring(1));
        }
        throw new RuntimeException("invalid selector");
    }
}
