package com.Fifty50.seventh7day;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class wrapfuntion {
    private WebElement element;
    private WebDriver driver;

    public wrapfuntion(WebElement element, WebDriver driver) {
        this.element = element;
        this.driver = driver;
    }

    public void click() {
        element.click();
    }

    public String getText() {
        return element.getText();
    }

    public void sendKeys(String keys) {
        element.sendKeys(keys);
    }

    // Additional functionality can be added here
    public void hover() {
        Actions actions = new Actions(driver);
        actions.moveToElement(element).perform();
    }

    public void waitForVisibility() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        wait.until(ExpectedConditions.visibilityOf(element));
    }


    public static void main(String[] args) {
        WebDriver driver = new ChromeDriver();
        WebElement element = driver.findElement(By.xpath("//input[@id='username']"));
        wrapfuntion wrappedElement = new wrapfuntion(element, driver);

        wrappedElement.click();
        wrappedElement.sendKeys("username");
        wrappedElement.hover();
        wrappedElement.waitForVisibility();
    }

}
