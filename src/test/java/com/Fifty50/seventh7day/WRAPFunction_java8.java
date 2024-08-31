package com.Fifty50.seventh7day;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

import java.util.function.Function;

public class WRAPFunction_java8 {
    public static Function<WebElement, wrapfuntion> wrap(WebDriver driver) {
        return element -> new wrapfuntion(element, driver);
    }

/*
    public static void main(String[] args) {
        WebDriver driver = new ChromeDriver();
        WebElement element = driver.findElement(By.xpath("//input[@id='username']"));
        wrapfuntion wrappedElement = wrapfuntion.wrap(driver).apply(element);

        wrappedElement.click();
        wrappedElement.sendKeys("username");
        wrappedElement.hover();
        wrappedElement.waitForVisibility();
    }
*/

}
