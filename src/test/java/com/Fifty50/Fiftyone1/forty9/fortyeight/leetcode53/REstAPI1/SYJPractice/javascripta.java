package com.Fifty50.Fiftyone1.forty9.fortyeight.leetcode53.REstAPI1.SYJPractice;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;

import java.io.File;
import java.io.IOException;
import java.time.Duration;

public class javascripta {

    public WebDriver driver;

    public void launch(){
        WebDriverManager.chromedriver().setup();
        driver= new ChromeDriver();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(2));
        driver.get("http://localhost/login.do");
        driver.manage().window().maximize();
    }
//To highlight the elements using JS
    public void highlightJS(){
        launch();
        WebElement button= driver.findElement(By.xpath("//a[@id='loginButton']"));
        highligh(button, driver);
       String tit= gettitle(driver);
       System.out.println(tit);
        clickbyJS(button, driver);
        alertByJS(driver, "Dont give UP");
       Scroolpagedown(driver);
       ScroolpageUp(driver);
        flash(button,driver);

    }

//Take a screenshot
    public void takescreenshot() throws IOException {
        launch();
        WebElement button= driver.findElement(By.xpath("//a[@id='loginButton']"));
        highligh(button, driver);

        TakesScreenshot getscre=(TakesScreenshot)driver;
        File src= getscre.getScreenshotAs(OutputType.FILE);
        File trg= new File("E:\\CPAYFW\\Screenshots\\test123_1.png");
        FileUtils.copyFile(src,trg);
    }
//To capture screenshot and highlight the button using Java script
    public static void highligh(WebElement element, WebDriver driver){
        JavascriptExecutor js=(JavascriptExecutor) driver;
        js.executeScript("arguments[0].style.border='3px solid red'", element);
    }

//To get the current title
    public static String gettitle(WebDriver driver) {
        JavascriptExecutor js = (JavascriptExecutor) driver;
        String title = js.executeScript("return document.title;").toString();
        return title;
    }
// To click on the webelement by using javascript
    public static void clickbyJS(WebElement element,WebDriver driver){
        JavascriptExecutor js= (JavascriptExecutor) driver;
        js.executeScript("arguments[0].click();",element);
    }

//To genrate alert by using JS
public static void alertByJS(WebDriver driver, String messagge){
        JavascriptExecutor js=(JavascriptExecutor) driver;
        js.executeScript("alert('"+messagge+"')");
}

    //To genrate pagedown by using JS
    public static void Scroolpagedown(WebDriver driver){
        JavascriptExecutor js=(JavascriptExecutor) driver;
        js.executeScript("window.scroll(0,document.body.scrollHeight)");
    }

    //To genrate pagedown by using JS
    public static void ScroolpageUp(WebDriver driver){
        JavascriptExecutor js=(JavascriptExecutor) driver;
        js.executeScript("window.scroll(0,-document.body.scrollHeight)");
    }


    //To change the color
    public static void changeColor(String color,WebElement element,WebDriver driver) {
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("arguments[0].style.backgroundColor='" + color + "'", element);

    }
    //To flash the element
    public static void flash(WebElement element,WebDriver driver){
        JavascriptExecutor js=(JavascriptExecutor) driver;
        String backgroundcol=element.getCssValue("background-color");
        for( int i=0; i<=50; i++){
            changeColor("#000000",element,driver);
            changeColor(backgroundcol,element,driver);
        }
    }

    public static void main(String[] args) throws IOException {
        javascripta js= new javascripta();
         js.highlightJS();
    }

}
