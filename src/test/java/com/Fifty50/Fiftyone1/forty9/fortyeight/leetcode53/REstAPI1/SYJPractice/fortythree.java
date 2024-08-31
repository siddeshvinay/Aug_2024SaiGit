package com.Fifty50.Fiftyone1.forty9.fortyeight.leetcode53.REstAPI1.SYJPractice;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;

import java.io.File;
import java.time.Duration;
import java.util.*;

public class fortythree {

    public WebDriver driver;


    public void launch(){
        ChromeOptions options= new ChromeOptions();
       // options.addArguments("--headless=new--");
        //options.setAcceptInsecureCerts(true);
        //options.setExperimentalOption("exculdeSwitches", new String[]{"enable-automation"});
       // options.addArguments("--incognito");
        File selectorhub= new File("E:\\API\\CRXExte\\SelectorsHub-XPath-Helper-Chrome-Web-Store.crx");
        options.addExtensions(selectorhub);
        File addblocker= new File("E:\\API\\CRXExte\\uBlock-Origin-Chrome-Web-Store.crx");
        options.addExtensions(addblocker);

        WebDriverManager.chromedriver().setup();
        driver= new ChromeDriver(options);
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));
    }



    public void Textedit(String admin, String email, String newtext, String permentaddre){
        launch();
        driver.get("https://demoqa.com/text-box");
        driver.findElement(By.xpath("//input[@id='userName']")).sendKeys(admin);
        driver.findElement(By.xpath("//input[@id='userEmail']")).sendKeys(email);
        driver.findElement(By.xpath(" //textarea[@id='currentAddress']")).sendKeys(newtext);
        driver.findElement(By.xpath(" //textarea[@id='permanentAddress']")).sendKeys(permentaddre);
        driver.findElement(By.xpath(" //button[@id='submit']")).click();
    }

    public void validateTextArea(){
        String admin="admin";
        String email="validate@email.com";
        String newtext="Vlaidate text area";
        String permentaddre="Validate permaddress";
        Textedit(admin,email,newtext,permentaddre);
       List<WebElement> getarea= driver.findElements(By.xpath("//div[@id='output']/div/p"));
        Map<String, String> map = new TreeMap<>(String.CASE_INSENSITIVE_ORDER);
          for(int i=0; i<getarea.size(); i++) {
              String output[] = getarea.get(i).getText().split("\n");
              for(String lie: output){
                  String[] parts=lie.split(":");
                  String key=parts[0].trim();
                  String value=parts[1].trim();
                  map.put(key,value);
              }
          }
        System.out.println("The name is matching"+map.get("Name").equals(admin));
        Assert.assertEquals(map.get("Name"),admin, "Name is matching");
        Assert.assertEquals(map.get("Email"),email, "email is matching");
        Assert.assertEquals(map.get("Current Address"),newtext, "current address is matching");
        Assert.assertEquals(map.get("Permananet Address"),permentaddre, "parment address is matching");
        driver.quit();
    }


    ///Validate select dropdowns
    public void selectdropdw(){
        launch();
        driver.get("https://demoqa.com/select-menu");
       WebElement sea=  driver.findElement(By.xpath("//select[@id='oldSelectMenu']"));
        Select sele= new Select(sea);
        ArrayList<String> stored= new ArrayList<>();
        List<WebElement> slopt=sele.getOptions();
        for(WebElement as: slopt){
            stored.add(as.getText());
        }
        Collections.sort(stored);
        for(String getas: stored){
            System.out.println(getas);
        }
        System.out.println("======================");
        Collections.sort(stored, Collections.reverseOrder());
        for(String getas: stored){
            System.out.println(getas);
        }

    }

    //Multiselect dropdown
    public void multiselect() {
        launch();
        driver.get("https://demoqa.com/select-menu");
        driver.findElement(By.xpath("//div[contains(text(),'Select...')]")).click();
        driver.findElement(By.xpath("//div[@id='react-select-4-option-0']")).click();
        driver.findElement(By.xpath("//div[@id='react-select-4-option-3']")).click();
    }

    //Window handlew
    public void windowhandle(){
        launch();
        driver.get("https://demoqa.com/browser-windows");
        driver.findElement(By.xpath("//button[@id='tabButton']")).click();
        Set<String> windows= driver.getWindowHandles();
           Iterator<String> ite= windows.iterator();
           while (ite.hasNext()){
              String childwind= ite.next();
              driver.switchTo().window(childwind);
               WebElement text = driver.findElement(By.xpath("//h1[@id='sampleHeading']"));
               System.out.println("Heading of child window is " + text.getText());
               driver.close();
               System.out.println("Child window closed");
           }
    }


    //Frames
    public void framesa(){
        launch();
        driver.get("https://demoqa.com/frames");

        JavascriptExecutor js = (JavascriptExecutor)driver;
        int count=Integer.parseInt(js.executeScript("return window.length").toString());
        System.out.println(count+"of the Frames using javascript");

        List<WebElement> frame=driver.findElements(By.tagName("iframe"));
        for(WebElement e: frame){
        }
    /*    driver.switchTo().frame("frame1");
        String test=driver.findElement(By.xpath("//h1[@id='sampleHeading']")).getText();
        System.out.println(test);
        driver.switchTo().parentFrame();
        driver.switchTo().alert();*/
    }

    //javascript



    public static void main(String[] args) {
        fortythree f43 = new fortythree();
        //f43.validateTextArea();
        //f43.selectdropdw();
        f43.framesa();
    }

}
