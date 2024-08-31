package com.corcentric.common;

import com.corcentric.driver.CapabilityFactory;
import com.corcentric.driver.OptionsManager;
import com.corcentric.runner.CucumberTestRunner;
import com.google.common.base.Function;
import io.github.bonigarcia.wdm.WebDriverManager;
import java.io.File;
import java.io.FileInputStream;
import java.net.URL;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.apache.log4j.Logger;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DateUtil;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.Wait;
import org.testng.Assert;

public class AppIndependentMethods extends CucumberTestRunner {
	public String browserName;
	private CapabilityFactory capabilityFactory = new CapabilityFactory();
	private static Logger logger = Logger.getLogger(AppIndependentMethods.class);

	/********************************************************
     * Method Name      : launchBrowser()
     * Purpose          : to launch the required browser (Chrome, Firefox, Edge)
     * Author           : Gudi
     * Parameters       : browserName
     * ReturnType       : WebDriver
     ********************************************************/
	public WebDriver launchBrowser(){
		logger.info("User navigate inside launch Browser method");
		try{
			browserName = System.getProperty("browser.name");
			if(browserName==null){
				browserName = appInd.getPropertyValueByKeyName("browserName");
			}
			logger.info("user point browser "+browserName);
        	switch(browserName.toLowerCase()) {
        		case "chrome":
					environment = System.getProperty("executionPlatform")!=null
							?System.getProperty("executionPlatform"):appInd.getPropertyValueByKeyName("executionPlatform");
					//WebDriverManager.chromedriver().setup();
					WebDriverManager.chromedriver().clearDriverCache().setup();
					tlDriver.set(new ChromeDriver(OptionsManager.getChromeOptions()));
					//tlDriver.set(new RemoteWebDriver(new URL("http://localhost:4444/wd/hub"), capabilityFactory.getCapabilities(browserName)));
					logger.info("Chrome browser is successfully launch");
        			break;
        		case "firefox":
					WebDriverManager.firefoxdriver().setup();
					tlDriver.set(new FirefoxDriver());
					logger.info("Firefox browser is successfully launch");
        			break;
        		case "edge":
					WebDriverManager.edgedriver().setup();
					tlDriver.set(new EdgeDriver());
					logger.info("Edge browser is successfully launch");
        			break;
        		default:
        			reports.writeResult(null, "Fail", "Invalid browser '"+browserName+"' was specified.");
					logger.error("given browser is not supported by framework");
        	}
        	
        	if(tlDriver.get()!=null) {
        		reports.writeResult(tlDriver.get(), "Pass", "The '"+browserName+"' browser was launched successful");
				tlDriver.get().manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        		return tlDriver.get();
        	}else {
				logger.error("Give browser object is null ");
        		reports.writeResult(null, "Fail", "Failed to launch the '"+browserName+"' browser");
				Assert.assertTrue(false, "Failed to launch the '"+browserName+"' browser");
        		return null;
        	}
        }catch(Exception e){
			e.printStackTrace();
        	reports.writeResult(null, "Exception", "Exception in 'launchBrowser()' method. " + e);
			Assert.assertTrue(false, "Failed to launch the '"+browserName+"' browser");
        }
        return null;
    }



    /********************************************************
     * Method Name      : getDateTime()
     * Purpose          : to get the current system date in required format
     * Author           : Gudi
     * Parameters       : strFormat
     * ReturnType       : String
     ********************************************************/
    public String getDateTime(String strFormat){
        String strDate = null;
        Date dt = null;
        SimpleDateFormat sdf = null;
        try{
            dt = new Date();
            sdf = new SimpleDateFormat(strFormat);
            strDate = sdf.format(dt);
        }catch(Exception e){
        	reports.writeResult(null, "Exception", "Exception in 'getDateTime() method. " + e);
			Assert.assertTrue(false, "Exception in 'getDateTime() method. "+e.getMessage());
            return strDate;
        }
        finally {dt = null; sdf = null;}
        return strDate;
    }
    
    
    
    
    /********************************************************
     * Method Name      : clickObject()
     * Purpose          : to click on the WebElement
     * Author           : Gudi
     * Parameters       : oBrowser, objBy
     * ReturnType       : boolean
     ********************************************************/
    public boolean clickObject(WebDriver oBrowser, By objBy) {
    	List<WebElement> oEles = null;
    	try {
			appDep.waitForTheElementState(oBrowser, objBy, "Clickable", "", minTimeOut);
    		oEles = oBrowser.findElements(objBy);;
			appDep.threadSleep(200);
    		if(oEles.size() > 0) {
				moveToElementByElement(oBrowser, oEles.get(0));
    			oEles.get(0).click();
    			reports.writeResult(oBrowser, "Pass", "The element '"+ objBy +"' was clicked successful");
    			return true;
    		}else {
    			reports.writeResult(oBrowser, "Fail", "The element '"+ objBy +"' was not found in the DOM");
				Assert.assertTrue(false,"The element '"+ objBy +"' was not found in the DOM");
    			return false;
    		}
    	}catch(Exception e){
        	reports.writeResult(null, "Exception", "Exception in 'clickObject() method. " + e);
            Assert.assertTrue(false,"The element '"+ objBy +"' was not found in the DOM");
			return false;
        }
    }

	public WebDriver switchToLastWindow(WebDriver oBrowser){
		Object[] allWin = oBrowser.getWindowHandles().toArray();
		return  oBrowser.switchTo().window((String) allWin[allWin.length - 1]);
	}

	public WebDriver switchBetweenTabsOrWindow(WebDriver oBrowser){
		return oBrowser.switchTo().window(oBrowser.getWindowHandles().stream()
				.filter( win -> !win.equals(oBrowser.getWindowHandle()))
				.findAny()
				.get());
	}

	public int getTotalOpenWindows(WebDriver oBrowser){ return oBrowser.getWindowHandles().size(); }
	public boolean isSwitchingRequired(WebDriver oBrowser, int currentOpenWin){
		return oBrowser.getWindowHandles().size()>currentOpenWin;
	}

	/********************************************************
	 * Method Name      : clickObject()
	 * Purpose          : to click on the WebElement
	 * Author           : Gudi
	 * Parameters       : oBrowser, objBy
	 * ReturnType       : boolean
	 ********************************************************/
	public boolean clickObject(WebDriver oBrowser, WebElement objEle) {
		try {
			appDep.waitForTheElementState(oBrowser, objEle, "Clickable", "", waitTimeOut);
			if(objEle.isDisplayed()) {
				highlightElement(oBrowser, objEle);
				objEle.click();
				reports.writeResult(oBrowser, "Pass", "The element '"+String.valueOf(objEle)+"' was clicked successful");
				return true;
			}else {
				reports.writeResult(oBrowser, "Fail", "The element '"+String.valueOf(objEle)+"' was not found in the DOM");
				Assert.assertTrue(false,"The element '"+ objEle +"' was not found in the DOM");
				return false;
			}
		}catch(Exception e){
			reports.writeResult(null, "Exception", "Exception in 'clickObject() method. " + e);
			Assert.assertTrue(false,"The element '"+ objEle +"' was not found in the DOM");
			return false;
		}
		finally {objEle = null;}
	}
    
    
    
    /********************************************************
     * Method Name      : setObject()
     * Purpose          : to set the value in the element
     * Author           : Gudi
     * Parameters       : oBrowser, objBy, strData
     * ReturnType       : boolean
     ********************************************************/
    public boolean setObject(WebDriver oBrowser, By objBy, String strData){
    	List<WebElement> oEles = null;
    	try {
			appDep.waitForTheElementState(oBrowser, objBy, "Clickable", "", minTimeOut);
    		oEles = oBrowser.findElements(objBy);
    		if(oEles.size() > 0) {
				oEles.get(0).clear();
    			oEles.get(0).sendKeys(strData);
    			reports.writeResult(oBrowser, "Pass", "The value '"+strData+"' was entered in the element '"+String.valueOf(objBy)+"' successful");
    			return true;
    		}else {
    			reports.writeResult(oBrowser, "Fail", "The element '"+String.valueOf(objBy)+"' was not found in the DOM");
				Assert.assertTrue(false,"The element '"+ objBy +"' was not found in the DOM");
    			return false;
    		}
    	}catch(Exception e){
        	reports.writeResult(null, "Exception", "Exception in 'setObject() method. " + e);
			Assert.assertTrue(false,"The element '"+ objBy +"' was not found in the DOM");
            return false;
        }
    	finally {oEles = null;}
    }




	/********************************************************
	 * Method Name      : clearAndSetObject()
	 * Purpose          : to set the value in the element
	 * Author           : Gudi
	 * Parameters       : oBrowser, objBy, strData
	 * ReturnType       : boolean
	 ********************************************************/
	public boolean clearAndSetObject(WebDriver oBrowser, By objBy, String strData) {
		List<WebElement> oEles = null;
		try {
			appDep.waitForTheElementState(oBrowser, objBy, "Clickable", "", minTimeOut);
			oEles = oBrowser.findElements(objBy);
			if(oEles.size() > 0) {
				oEles.get(0).clear();
				oEles.get(0).sendKeys(strData);
				reports.writeResult(oBrowser, "Pass", "cleared & entered the value '"+strData+"' in the element '"+String.valueOf(objBy)+"' successful");
				return true;
			}else {
				reports.writeResult(oBrowser, "Fail", "The element '"+String.valueOf(objBy)+"' was not found in the DOM");
				Assert.assertTrue(false,"The element '"+ objBy +"' was not found in the DOM");
				return false;
			}
		}catch(Exception e){
			reports.writeResult(null, "Exception", "Exception in 'clearAndSetObject() method. " + e);
			Assert.assertTrue(false,"The element '"+ objBy +"' was not found in the DOM");
			return false;
		}
		finally {oEles = null;}
	}



	/********************************************************
	 * Method Name      : selectObject()
	 * Purpose          : to select the value in the select dropdown
	 * Author           : Gudi
	 * Parameters       : oBrowser, objBy, strData
	 * ReturnType       : boolean
	 ********************************************************/
	public boolean selectObject(WebDriver oBrowser, By objBy, String strData) {
		List<WebElement> oEles = null;
		Select oSelect = null;
		try {
			appDep.waitForTheElementState(oBrowser, objBy, "Clickable", "", minTimeOut);
			oEles = oBrowser.findElements(objBy);
			if(oEles.size() > 0) {
				oSelect = new Select(oEles.get(0));
				oSelect.selectByVisibleText(strData);
				reports.writeResult(oBrowser, "Pass", "The value '"+strData+"' was selected in the element '"+String.valueOf(objBy)+"' successful");
				return true;
			}else {
				reports.writeResult(oBrowser, "Fail", "The element '"+String.valueOf(objBy)+"' was not found in the DOM");
				Assert.assertTrue(false,"The element '"+ objBy +"' was not found in the DOM");
				return false;
			}
		}catch(Exception e){
			reports.writeResult(null, "Exception", "Exception in 'selectObject() method. " + e);
			Assert.assertTrue(false,"The element '"+ objBy +"' was not found in the DOM");
			return false;
		}
		finally {oSelect = null; oEles = null;}
	}




	/********************************************************
	 * Method Name      : selectObject()
	 * Purpose          : to select the value in the select dropdown
	 * Author           : Gudi
	 * Parameters       : oBrowser, objBy, index
	 * ReturnType       : boolean
	 ********************************************************/
	public boolean selectObject(WebDriver oBrowser, By objBy, int index) {
		List<WebElement> oEles = null;
		Select oSelect = null;
		try {
			appDep.waitForTheElementState(oBrowser, objBy, "Clickable", "", minTimeOut);
			oEles = oBrowser.findElements(objBy);
			if(oEles.size() > 0) {
				oSelect = new Select(oEles.get(0));
				oSelect.selectByIndex(index);
				reports.writeResult(oBrowser, "Pass", "The index '"+index+"' was selected in the element '"+String.valueOf(objBy)+"' successful");
				return true;
			}else {
				reports.writeResult(oBrowser, "Fail", "The element '"+String.valueOf(objBy)+"' was not found in the DOM");
				Assert.assertTrue(false,"The element '"+ objBy +"' was not found in the DOM");
				return false;
			}
		}catch(Exception e){
			reports.writeResult(null, "Exception", "Exception in 'selectObject() method. " + e);
			Assert.assertTrue(false,"The element '"+ objBy +"' was not found in the DOM");
			return false;
		}
		finally {oSelect = null; oEles = null;}
	}



	/********************************************************
	 * Method Name      : selectObjectByValue()
	 * Purpose          : to select the value in the select dropdown using value
	 * Author           : Gudi
	 * Parameters       : oBrowser, objBy, value
	 * ReturnType       : boolean
	 ********************************************************/
	public boolean selectObjectByValue(WebDriver oBrowser, By objBy, String value) {
		List<WebElement> oEles = null;
		Select oSelect = null;
		try {
			appDep.waitForTheElementState(oBrowser, objBy, "Clickable", "", minTimeOut);
			oEles = oBrowser.findElements(objBy);
			if(oEles.size() > 0) {
				oSelect = new Select(oEles.get(0));
				oSelect.selectByValue(value);
				reports.writeResult(oBrowser, "Pass", "The value '"+value+"' was selected in the element '"+String.valueOf(objBy)+"' successful");
				return true;
			}else {
				reports.writeResult(oBrowser, "Fail", "The element '"+String.valueOf(objBy)+"' was not found in the DOM");
				Assert.assertTrue(false,"The element '"+ objBy +"' was not found in the DOM");
				return false;
			}
		}catch(Exception e){
			reports.writeResult(null, "Exception", "Exception in 'selectObjectByValue() method. " + e);
			Assert.assertTrue(false,"The element '"+ objBy +"' was not found in the DOM");
			return false;
		}
		finally {oSelect = null; oEles = null;}
	}


    
    
    /********************************************************
     * Method Name      : compareValues()
     * Purpose          : to compare both actual and expected values
     * Author           : Gudi
     * Parameters       : oBrowser, actual, expected
     * ReturnType       : boolean
     ********************************************************/
    public boolean compareValues(WebDriver oBrowser, Object actual, Object expected) {
    	try {
    		if(actual.equals(expected)) {
    			reports.writeResult(oBrowser, "Pass", "The actual '"+actual+"' & expected '"+expected+"' values are matching");
    			return true;
    		}else {
    			reports.writeResult(oBrowser, "Fail", "Mis-match in the both actual '"+actual+"' & expected '"+expected+"' values");
    			return false;
    		}
    	}catch(Exception e){
        	reports.writeResult(null, "Exception", "Exception in 'compareValues() method. " + e);
			Assert.assertTrue(false,"Mis-match in the both actual '"+actual+"' & expected '"+expected+"' values");
            return false;
        }
    }
    
    
    
    
    
    /********************************************************
     * Method Name      : verifyText()
     * Purpose          : to verify the presence of the text on the elements
     * Author           : Gudi
     * Parameters       : oBrowser, objBy, strObjectType, expectedValue
     * ReturnType       : boolean
     ********************************************************/
    public boolean verifyText(WebDriver oBrowser, By objBy, String strObjectType, String expectedValue) {
    	List<WebElement> oEles = null;
    	Select oSelect = null;
    	String actualValue = null;
    	try {
			appDep.waitForTheElementState(oBrowser, objBy, "Visibility", "", minTimeOut);
    		oEles = oBrowser.findElements(objBy);
			if(oEles.size() > 0){
				switch(strObjectType.toLowerCase()) {
					case "text":
						actualValue = oEles.get(0).getText().trim();
						break;
					case "value":
						actualValue = oEles.get(0).getAttribute("value").trim();
						break;
					case "placeholder":
						actualValue = oEles.get(0).getAttribute("placeholder").trim();
						break;
					case "dropdown":
						oSelect = new Select(oEles.get(0));
						actualValue = oSelect.getFirstSelectedOption().getText().trim();
						break;
					default:
						reports.writeResult(null, "Fail", "invalid object type '"+strObjectType+"' mentioned.");
						return false;
				}
				Assert.assertTrue(compareValues(oBrowser, actualValue.trim(), expectedValue.trim()), "Both actual '"+actualValue+"' & Expected '"+expectedValue+"' values are not matching");
			}else{
				reports.writeResult(oBrowser, "Fail", "The element '"+String.valueOf(objBy)+"' was not found in the DOM");
				Assert.assertTrue(false,"The element '"+ objBy +"' was not found in the DOM");
				return false;
			}
			return true;
    	}catch(Exception e){
        	reports.writeResult(null, "Exception", "Exception in 'verifyText() method. " + e);
			Assert.assertTrue(false,"The element '"+ objBy +"' was not found in the DOM");
            return false;
        }
    	finally {oSelect = null; oEles = null;}
    }
    
    
    
    /********************************************************
     * Method Name      : verifyElementPresent()
     * Purpose          : to verify the presence of the element in DOM
     * Author           : Gudi
     * Parameters       : oBrowser, objBy
     * ReturnType       : boolean
     ********************************************************/
    public boolean verifyElementPresent(WebDriver oBrowser, By objBy) {
    	List<WebElement> oEles = null;
    	try {
    		oEles = oBrowser.findElements(objBy);
    		if(oEles.size() > 0) {
				highlightElement(oBrowser, oEles.get(0));
    			reports.writeResult(oBrowser, "Pass", "The element '"+ objBy +"' was present in the DOM");
    			return true;
    		}else {
    			reports.writeResult(oBrowser, "warning", "The element '"+ objBy +"' was NOT present in the DOM");
    			return false;
    		}
    	}catch(Exception e){
        	reports.writeResult(null, "Exception", "Exception in 'verifyElementPresent() method. " + e);
			Assert.assertTrue(false,"The element '"+ objBy +"' was not found in the DOM");
            return false;
        }
    	finally {oEles = null;}
    }




	/********************************************************
	 * Method Name      : verifyElementPresent()
	 * Purpose          : to verify the presence of the element in DOM
	 * Author           : Gudi
	 * Parameters       : oBrowser, objEle
	 * ReturnType       : boolean
	 ********************************************************/
	public boolean verifyElementPresent(WebDriver oBrowser, WebElement objEle) {
		try {
			if(objEle.isDisplayed()) {
				highlightElement(oBrowser, objEle);
				reports.writeResult(oBrowser, "Pass", "The element '"+String.valueOf(objEle)+"' was present in the DOM");
				return true;
			}else {
				reports.writeResult(oBrowser, "Fail", "The element '"+String.valueOf(objEle)+"' was NOT present in the DOM");
				return false;
			}
		}catch(Exception e){
			reports.writeResult(null, "Exception", "Exception in 'verifyElementPresent() method. " + e);
			Assert.assertTrue(false,"The element '"+ objEle +"' was not found in the DOM");
			return false;
		}
	}



    
    
    /********************************************************
     * Method Name      : verifyElementNotPresent()
     * Purpose          : to verify the NON presence of the element in DOM
     * Author           : Gudi
     * Parameters       : oBrowser, objBy
     * ReturnType       : boolean
     ********************************************************/
    public boolean verifyElementNotPresent(WebDriver oBrowser, By objBy) {
    	try {
    		if(oBrowser.findElement(objBy).isDisplayed()) {
    			reports.writeResult(oBrowser, "Fail", "The element '"+ objBy +"' was STILL present in the DOM");
    		}
    	}catch(Exception e){
			reports.writeResult(oBrowser, "Pass", "The element '"+ objBy +"' was NOT present in the DOM");
			return true;
        }
		return false;
	}
    
    
    
    
    /********************************************************
     * Method Name      : verifyOptionalElementPresent()
     * Purpose          : to verify the NON presence of the element in DOM
     * Author           : Gudi
     * Parameters       : oBrowser, objBy
     * ReturnType       : boolean
     ********************************************************/
    public boolean verifyOptionalElementPresent(WebDriver oBrowser, By objBy) {
    	List<WebElement> oEles = null;
    	try {
    		oEles = oBrowser.findElements(objBy);
    		if(oEles.size() > 0) {
				highlightElement(oBrowser, oEles.get(0));
    			return true;
    		}else {
    			return false;
    		}
    	}catch(Exception e){
        	reports.writeResult(null, "Exception", "Exception in 'verifyOptionalElementPresent() method. " + e);
			Assert.assertTrue(false,"The element '"+ objBy +"' was not found in the DOM");
            return false;
        }
    	finally {oEles = null;}
    }
    

    
    
    /********************************************************
     * Method Name      : getPropertyValueByKeyName()
     * Purpose          : to launch the required browser (Chrome, Firefox, Edge)
     * Author           : Gudi
     * Parameters       : browserName
     * ReturnType       : WebDriver
     ********************************************************/
    public String getPropertyValueByKeyName(String strKey) {
    	FileInputStream fin = null;
    	Properties prop = null;
    	try {
    		fin = new FileInputStream(System.getProperty("user.dir") + "/src/main/resources/"+System.getProperty("environment")+".properties");
    		prop = new Properties();
    		prop.load(fin);
    		String strValue = prop.getProperty(strKey);
			return strValue;
    	}catch(Exception e){
        	reports.writeResult(null, "Exception", "Exception in 'getPropertyValueByKeyName() method. " + e);
            return null;
        }
    	finally
    	{
    		try {
        		fin.close(); fin = null; prop = null;
        	}catch(Exception e){
                return null;
            }
    	}
    }




	/********************************************************
	 * Method Name      : getTextOnElement()
	 * Purpose          : to read the text present on the element
	 * Author           : Gudi
	 * Parameters       : oBrowser, objBy
	 * ReturnType       : String
	 ********************************************************/
	public String getTextOnElement(WebDriver oBrowser, By objBy, String strObjectType){
		List<WebElement> oEles = null;
		String strText = null;
		Select oSelect = null;
		try{
			appDep.waitForTheElementState(oBrowser, objBy, "Clickable", "", minTimeOut);
			oEles = oBrowser.findElements(objBy);
			if(oEles.size() > 0){
				switch(strObjectType.toLowerCase()){
					case "text":
						strText = oEles.get(0).getText().trim();
						break;
					case "value":
						strText = oEles.get(0).getAttribute("value").trim();
						break;
					case "dropdown":
						oSelect = new Select(oEles.get(0));
						strText = oSelect.getFirstSelectedOption().getText().trim();
						break;
					default:
						reports.writeResult(oBrowser, "Fail", "Invalid object type '"+strObjectType+"' was mention. The supported values are 'Text', 'Value' & 'Dropdown'");
						Assert.fail("Invalid object type '"+strObjectType+"' was mention. The supported values are 'Text', 'Value' & 'Dropdown'");
				}

				reports.writeResult(oBrowser, "Pass", "The text '"+strText+"' was read from the element '"+String.valueOf(objBy)+"'");
				return strText;
			}else{
				reports.writeResult(oBrowser, "Fail", "The element '"+String.valueOf(objBy)+"' was NOT present in the DOM");
				Assert.fail("The element '"+String.valueOf(objBy)+"' was NOT present in the DOM");
				return null;
			}
		}catch(Exception e){
			reports.writeResult(oBrowser, "Exception", "Exception in 'getTextOnElement() method. " + e);
			return null;
		}
		finally{
			oSelect = null; strText = null; oEles = null;
		}
	}




	/********************************************************
	 * Method Name      : createAndGetWebElement()
	 * Purpose          : to create and return the WebElement for the selected object
	 * Author           : Gudi
	 * Parameters       : oBrowser, objBy
	 * ReturnType       : WebElement
	 ********************************************************/
	public WebElement createAndGetWebElement(WebDriver oBrowser, By objBy){
		WebElement oEle = null;
		try{
			oEle = waitForElement(objBy);
			if(oEle.isDisplayed()){
				reports.writeResult(oBrowser, "Pass", "The webElement was created and returned for the object '"+String.valueOf(objBy)+"'");
				return oEle;
			}else{
				return null;
			}
		}catch(Exception e){
			reports.writeResult(oBrowser, "Exception", "Exception in 'createAndGetWebElement() method. " + e);
			return null;
		}
	}


	/********************************************************
	 * Method Name      : scrollTo()
	 * Purpose          : to scroll to the particular element
	 * Author           : Gudi
	 * Parameters       : oBrowser, horizontal scroll, vertical scroll
	 * ReturnType       : boolean
	 ********************************************************/
	public void scrollTo(WebDriver oBrowser, int horizontal, int vertical){
		JavascriptExecutor js = (JavascriptExecutor) oBrowser;
		js.executeScript("window.scrollBy("+horizontal+", "+vertical+")", "");
	}


	/********************************************************
	 * Method Name      : scrollToElement()
	 * Purpose          : to scroll to the perticular element
	 * Author           : Gudi
	 * Parameters       : oBrowser, objBy
	 * ReturnType       : boolean
	 ********************************************************/
	public boolean scrollToElement(WebDriver oBrowser, By objBy){
		WebElement oEle = null;
		JavascriptExecutor js = null;
		try{
			js = (JavascriptExecutor) oBrowser;
			oEle = waitForElement(objBy);
			if(oEle.isDisplayed()){
				highlightElement(oBrowser, oEle);
				js.executeScript("arguments[0].scrollIntoView();", oEle);
				reports.writeResult(oBrowser, "Pass", "The scrollToElement was dome for the element '"+String.valueOf(objBy)+"'");
				return true;
			}else{
				return false;
			}
		}catch(Exception e){
			reports.writeResult(oBrowser, "Exception", "Exception in 'scrollToElement() method. " + e);
			return false;
		}
	}


	/********************************************************
	 * Method Name      : scrollToThePage()
	 * Purpose          : to scroll to the Top/bottom of the page using Actions class
	 * Author           : Gudi
	 * Parameters       : oBrowser, scrollUpTo
	 * ReturnType       : boolean
	 ********************************************************/
	public boolean scrollToThePage(WebDriver oBrowser, String scrollUpTo){
		Actions oAction = null;
		try{
			oAction = new Actions(oBrowser);
			if(scrollUpTo.equalsIgnoreCase("Top")){
				oAction.sendKeys(Keys.HOME).build().perform();
			}else{
				oAction.sendKeys(Keys.END).build().perform();
			}
			return true;
		}catch(Exception e){
			reports.writeResult(oBrowser, "Exception", "Exception in 'scrollToThePage() method. " + e);
			return false;
		}finally{oAction = null;}
	}



	/********************************************************
	 * Method Name      : chooseAnotherValueOtherThanTheCurrent()
	 * Purpose          : to choose the another input value other than the existing one in the element among 2 different values
	 * Author           : Gudi
	 * Parameters       : oBrowser, objBy, objectType, strInputDatas
	 * ReturnType       : String
	 ********************************************************/
	public String chooseAnotherValueOtherThanTheCurrent(WebDriver oBrowser, By objBy, String objectType, String strInputDatas){
		String actualValue = null;
		List<WebElement> oEles = null;
		Select oSelect = null;
		String strArrValues[];
		try{
			waitForElement(objBy);
			oEles = oBrowser.findElements(objBy);
			if(oEles.size() > 0){
				switch(objectType.toLowerCase()){
					case "text":
						actualValue = oEles.get(0).getText();
						break;
					case "value":
						actualValue = oEles.get(0).getAttribute("value");
						break;
					case "list":
						oSelect = new Select(oEles.get(0));
						actualValue = oSelect.getFirstSelectedOption().getText();
						break;
					default:
						reports.writeResult(oBrowser, "Fail", "Invalid object type '"+objectType+"' was specified. Supported values are 'text', 'value' & 'list'");
						return null;
				}
				strArrValues = strInputDatas.trim().split("#");
				if(actualValue.trim().equalsIgnoreCase(strArrValues[0]) || actualValue.contains(strArrValues[0])){
					return strArrValues[1];
				}else{
					return strArrValues[0];
				}
			}else{
				reports.writeResult(oBrowser, "Fail", "The element '"+String.valueOf(objBy)+"' was NOT present in the DOM");
				return null;
			}
		}catch(Exception e){
			reports.writeResult(oBrowser, "Exception", "Exception in 'chooseAnotherValueOtherThanTheCurrent() method. " + e);
			return null;
		}finally {
			actualValue = null; oEles = null; oSelect = null; strArrValues = null;
		}
	}



	/********************************************************
	 * Method Name      : javaScriptClickObject()
	 * Purpose          : to click on the WebElement using javascript
	 * Author           : Gudi
	 * Parameters       : oBrowser, objBy
	 * ReturnType       : boolean
	 ********************************************************/
	public boolean javaScriptClickObject(WebDriver oBrowser, By objBy) {
		List<WebElement> oEles = null;
		JavascriptExecutor js = null;
		try {
			oEles = oBrowser.findElements(objBy);
			if(oEles.size() > 0) {
				highlightElement(oBrowser, oEles.get(0));
				js = (JavascriptExecutor) oBrowser;
				js.executeScript("arguments[0].click();", oEles.get(0));
				reports.writeResult(oBrowser, "Pass", "The element '"+String.valueOf(objBy)+"' was clicked successful");
				return true;
			}else {
				reports.writeResult(oBrowser, "Fail", "The element '"+String.valueOf(objBy)+"' was not found in the DOM");
				return false;
			}
		}catch(Exception e){
			reports.writeResult(null, "Exception", "Exception in 'javaScriptClickObject() method. " + e);
			return false;
		}
		finally {oEles = null;}
	}




	/********************************************************
	 * Method Name      : javaScriptClickObject()
	 * Purpose          : to click on the WebElement using javascript
	 * Author           : Gudi
	 * Parameters       : oBrowser, objBy
	 * ReturnType       : boolean
	 ********************************************************/
	public boolean javaScriptClickObject(WebDriver oBrowser, WebElement objEle) {
		JavascriptExecutor js = null;
		try {
			if(objEle.isDisplayed()) {
				highlightElement(oBrowser, objEle);
				js = (JavascriptExecutor) oBrowser;
				js.executeScript("arguments[0].click();", objEle);
				reports.writeResult(oBrowser, "Pass", "The element '"+String.valueOf(objEle)+"' was clicked successful");
				return true;
			}else {
				reports.writeResult(oBrowser, "Fail", "The element '"+String.valueOf(objEle)+"' was not found in the DOM");
				return false;
			}
		}catch(Exception e){
			reports.writeResult(null, "Exception", "Exception in 'javaScriptClickObject() method. " + e);
			return false;
		}
		finally {objEle = null;}
	}



	/********************************************************
	 * Method Name      : isAlertPresent()
	 * Purpose          : to verify that the alert is present in the DOM
	 * Author           : Gudi
	 * Parameters       : oBrowser
	 * ReturnType       : boolean
	 ********************************************************/
	public boolean isAlertPresent(WebDriver oBrowser){
		try{
			oBrowser.switchTo().alert();
			return true;
		}catch(Exception e) {
			return false;
		}
	}


	public void clickObject(WebElement objEle) {
		try {
			if(objEle.isDisplayed()) {
				highlightElement(oBrowser, objEle);
				objEle.click();
				reports.writeResult(oBrowser, "Pass", "The element '"+String.valueOf(objEle)+"' was clicked successful");
				//return true;
			}else {
				reports.writeResult(oBrowser, "Fail", "The element '"+String.valueOf(objEle)+"' was not found in the DOM");
				//return false;
			}
		}catch(Exception e){
			reports.writeResult(null, "Exception", "Exception in 'clickObject() method. " + e);
			//return false;
		}
		finally {objEle = null;}
	}


	public void clickObjectUsingActions(WebDriver oBrowser, WebElement objEle){
		Actions oAction = null;
		try{
			oAction = new Actions(oBrowser);
			oAction.moveToElement(objEle).perform();
			oAction.click(objEle).click().perform();
		}catch(Exception e){
			reports.writeResult(null, "Exception", "Exception in 'clickObject() method. " + e);
			//return false;
		}
		finally {objEle = null; oAction = null;}
	}



	/********************************************************
	 * Method Name      : dateAddAndReturn()
	 * Purpose          : to add numbers of days to the current system date and returns in the required format
	 * Author           : Gudi
	 * Parameters       : dateFormat, numberofDaysToAdd
	 * ReturnType       : boolean
	 ********************************************************/
	public String dateAddAndReturn(String dateFormat, int numberOfDaysToAdd) {
		Date dt = null;
		SimpleDateFormat sdf = null;
		Calendar cal = null;
		try {
			dt = new Date();
			sdf = new SimpleDateFormat(dateFormat);
			cal = Calendar.getInstance();
			cal.setTime(sdf.parse(sdf.format(dt)));
			cal.add(Calendar.DAY_OF_MONTH, numberOfDaysToAdd);
			return sdf.format(cal.getTime());
		}catch(Exception e) {
			reports.writeResult(null, "Exception", "Exception in 'dateAddAndReturn() method. " + e);
			return null;
		}
		finally {dt = null; sdf = null; cal = null;}
	}

	/********************************************************
	 * Method Name      : setOptionalObject()
	 * Purpose          : to set the value in the Optional element
	 * Author           : Gudi
	 * Parameters       : oBrowser, objBy, strData
	 * ReturnType       : boolean
	 ********************************************************/
	public boolean setOptionalObject(WebDriver oBrowser, By objBy, String strData) {
		List<WebElement> oEles = null;
		try {
			if(strData != null){
				waitForElement(objBy);
				oEles = oBrowser.findElements(objBy);
				if(oEles.size() > 0) {
					oEles.get(0).clear();
					oEles.get(0).sendKeys(strData);
					reports.writeResult(oBrowser, "Pass", "The value '"+strData+"' was entered in the element '"+ objBy +"' successful");
					return true;
				}else {
					reports.writeResult(oBrowser, "Fail", "The element '"+ objBy +"' was not found in the DOM");
					return false;
				}
			}
			return true;
		}catch(Exception e){
			reports.writeResult(null, "Exception", "Exception in 'setOptionalObject() method. " + e);
			return false;
		}
		finally {oEles = null;}
	}



	/********************************************************
	 * Method Name      : getMonthNumber()
	 * Purpose          : to get the month number based on the month name
	 * Author           : Gudi
	 * Parameters       : monthName
	 * ReturnType       : int
	 ********************************************************/
	public int getMonthNumber(String monthName){
		Date date = null;
		Calendar cal = null;
		try{
			date = new SimpleDateFormat("MMMM", Locale.ENGLISH).parse(monthName);
			cal = Calendar.getInstance();
			cal.setTime(date);
			return (cal.get(Calendar.MONTH) + 1);
		}catch(Exception e){
			reports.writeResult(null, "Exception", "Exception while executing getMonthNumber() method. "+ e);
			return -1;
		}finally{date = null; cal = null;}
	}





	/********************************************************
	 * Method Name      : setMultipleValues()
	 * Purpose          : to enter multiple values in the edit field
	 * Author           : Gudi
	 * Parameters       : oBrowser, objBy1, objBy2, values
	 * ReturnType       : int
	 ********************************************************/
	public boolean setMultipleValues(WebDriver oBrowser, By objBy1, String objBy2, String values){
		List<WebElement> oEles = null;
		String arrValues[];
		try {
			waitForElement(objBy1);
			oEles = oBrowser.findElements(objBy1);
			if(oEles.size() > 0) {
				arrValues = values.split("#");
				for(int i=0; i<arrValues.length; i++){
					oEles.get(0).click();
					oEles.get(0).sendKeys(arrValues[i]);
					oBrowser.findElement(By.xpath(objBy2 + "[text()='"+arrValues[i]+"']")).click();
					appDep.threadSleep(1000);
				}
				reports.writeResult(oBrowser, "Pass", "The multiple values '"+values.replace("#", ", ")+"' were entered in the element '"+String.valueOf(objBy1)+"' successful");
				return true;
			}else {
				reports.writeResult(oBrowser, "Fail", "The element '"+ objBy1 +"' was not found in the DOM");
				return false;
			}
		}catch(Exception e){
			reports.writeResult(null, "Exception", "Exception in 'setMultipleValues() method. " + e);
			return false;
		}
		finally {oEles = null; arrValues = null;}
	}




	/********************************************************
	 * Method Name      : getDateTimeByTimezone()
	 * Purpose          : to get the current date based on the timezone
	 * Author           : Gudi
	 * Parameters       : dateFormat, zone
	 * ReturnType       : String
	 ********************************************************/
	public String getDateTimeByTimezone(String dateFormat, String zone){
		Calendar cal = null;
		DateFormat df = null;
		TimeZone timeZone = null;
		try{
			cal = Calendar.getInstance();
			df = new SimpleDateFormat(dateFormat);
			timeZone = TimeZone.getTimeZone(zone);
			df.setTimeZone(timeZone);
			return df.format(cal.getTime());
		}catch(Exception e){
			reports.writeResult(null, "Exception", "Exception in 'getDateTimeByTimezone() method. " + e);
			return null;
		}finally{cal = null; df = null; timeZone = null;}
	}




	/********************************************************
	 * Method Name      : formatDateFromOnetoAnother()
	 * Purpose          : to get the given date format to required date format
	 * Author           : Gudi
	 * Parameters       : date, givenformat, resultformat
	 * ReturnType       : String
	 ********************************************************/
	public String formatDateFromOnetoAnother(String date, String givenformat, String resultformat) {
		String result = "";
		SimpleDateFormat sdf;
		SimpleDateFormat sdf1;
		try {
			sdf = new SimpleDateFormat(givenformat);
			sdf1 = new SimpleDateFormat(resultformat);
			result = sdf1.format(sdf.parse(date));
		}catch (Exception e) {
			e.printStackTrace();
			return "";
		}finally {sdf = null; sdf1 = null;}
		return result;
	}



	/********************************************************
	 * Method Name      : addDaysToDate()
	 * Purpose          : to add/minus the days to the todays/given date
	 * Author           : Gudi
	 * Parameters       : date, dateFormat, intervals
	 * ReturnType       : String
	 ********************************************************/
	public static String addDaysToDate(String date, String dateFormat, int intervals)
	{
		SimpleDateFormat sdf = null;
		Calendar cal = null;
		String newDate = null;
		try{
			sdf = new SimpleDateFormat(dateFormat);
			cal = Calendar.getInstance();

			if(date.trim().isEmpty()) {
				cal = Calendar.getInstance();
				date = (cal.get(Calendar.DAY_OF_MONTH) + "-" + (cal.get(Calendar.MONTH)+1) + "-" + cal.get(Calendar.YEAR));
			}
			cal.setTime(sdf.parse(date));
			cal.add(Calendar.DAY_OF_MONTH, intervals);
			newDate = sdf.format(cal.getTime());
			return newDate;
		}catch(ParseException e){
			e.printStackTrace();
			return null;
		}finally {sdf = null; cal = null; newDate = null;}
	}



	/********************************************************
	 * Method Name      : readFromPdfFile()
	 * Purpose          : to read the content from the .pdf file
	 * Author           : Gudi
	 * Parameters       : pdfFilePath
	 * ReturnType       : String
	 ********************************************************/
	public String readFromPdfFile(String pdfFilePath){
		PDDocument objDoc = null;
		String strText = null;
		try{
			objDoc = PDDocument.load(new File(pdfFilePath));
			strText = new PDFTextStripper().getText(objDoc);
			return strText;
		}catch(Exception e){
			e.printStackTrace();
			return null;
		}finally {objDoc = null; strText = null;}
	}




	/********************************************************
	 * Method Name      : getExcelCellData()
	 * Purpose          : to read the cell content
	 * Author           : Gudi
	 * Parameters       : objCell
	 * ReturnType       : String
	 ********************************************************/
	public String getExcelCellData(Cell objCell){
		String cellData = null;
		double dt = 0;
		Calendar cal = null;
		try{
			switch(objCell.getCellType()){
				case BLANK:
					cellData = "";
					break;
				case BOOLEAN:
					cellData = String.valueOf(objCell.getBooleanCellValue());
					break;
				case STRING:
					cellData = objCell.getStringCellValue();
					break;
				case NUMERIC:
					if(DateUtil.isCellDateFormatted(objCell)){
						dt = objCell.getNumericCellValue();
						cal = Calendar.getInstance();
						cal.setTime(DateUtil.getJavaDate(dt));
						cellData = String.valueOf(cal.get(Calendar.MONTH)+1) +"/"+ cal.get(Calendar.DAY_OF_MONTH) +"/"+ cal.get(Calendar.YEAR);
					}else{
						String data = String.valueOf(objCell.getNumericCellValue());
						String arrData[] = data.split("\\.");
						if(Integer.parseInt(arrData[1]) > 0){
							cellData = String.valueOf(objCell.getNumericCellValue());
						}else{
							cellData = String.valueOf(arrData[0]);
						}
					}
					break;
				default:
					reports.writeResult(null, "Fail", "Invalid CellType() which is not handled in 'getExcelCellData()'");
			}

			return cellData;
		}catch(Exception e){
			reports.writeResult(null, "Exception", "Exceptoin while executing 'getExcelCellData()' method. " + e);
			return null;
		}finally {
			cellData = null; cal = null;
		}
	}




	/********************************************************
	 * Method Name      : clickCheckboxBasedOnPreviousValue()
	 * Purpose          : click the checkbox based on the previous value
	 * Author           : Gudi
	 * Parameters       : oBrowser, strValue
	 * ReturnType       : String
	 ********************************************************/
	public String clickCheckboxBasedOnPreviousValue(WebDriver oBrowser, By objBy, String strValue){
		WebElement objChkBox = null;
		String strStatus = null;
		String arrChkBox[];
		try{
			if(!strValue.isEmpty())
				arrChkBox = strValue.split("#");
			objChkBox = appInd.createAndGetWebElement(oBrowser, objBy);
			if(objChkBox.isSelected() || objChkBox.getAttribute("value").equalsIgnoreCase("true")){
				if(!strValue.contains("#") && !strValue.equalsIgnoreCase("True")){
					Assert.assertTrue(appInd.clickObject(oBrowser, objChkBox));
					strStatus = "False";
				}else{
					Assert.assertTrue(appInd.clickObject(oBrowser, objChkBox));
					strStatus = "False";
				}
			}else{
				Assert.assertTrue(appInd.clickObject(oBrowser, objChkBox));
				strStatus = "True";
			}
			return strStatus;
		}catch(Exception e) {
			reports.writeResult(oBrowser, "Exception", "Exception in 'clickCheckboxBasedOnPreviousValue()' method. " + e);
			return null;
		}catch(AssertionError e) {
			reports.writeResult(oBrowser, "Exception", "AssertionError in 'clickCheckboxBasedOnPreviousValue()' method. " + e);
			return null;
		}finally {
			objChkBox = null; strStatus = null; arrChkBox = null;
		}
	}



	/********************************************************
	 * Method Name      : formatDoubleValues()
	 * Purpose          : when we add the big double numbers the output includes scientific calculations (means E and . will be added)
	 * 					  This method will format the output in the required format
	 * Author           : Gudi
	 * Parameters       : sum
	 * ReturnType       : String
	 ********************************************************/
	public String formatDoubleValues(double sum) {
		String dblValue = null;
		StringBuilder str = null;
		try {
			dblValue = String.valueOf(sum);
			if(dblValue.contains("E")) {
				dblValue = dblValue.substring(0, dblValue.length()-2);
				str = new StringBuilder(dblValue);
				return new String(str.insert(str.length()-2, ".")).replaceFirst("\\.", "");
			}else {
				return dblValue;
			}
		}catch(Exception e) {
			return null;
		}finally {dblValue = null; str = null;}
	}





	/********************************************************
	 * Method Name      : stringInitCap()
	 * Purpose          : when we add the big double numbers the output includes scientific calculations (means E and . will be added)
	 * 					  This method will format the output in the required format
	 * Author           : Gudi
	 * Parameters       : sum
	 * ReturnType       : String
	 ********************************************************/
	public String stringInitCap(String strValue, String demileter){
		String arrValue[];
		String strData = "";
		try{
			arrValue = strValue.toLowerCase().split(demileter);
			for(int i=0; i<arrValue.length; i++){
				for(int j=0; j<arrValue[i].length(); j++){
					if(j==0){
						strData+=String.valueOf(arrValue[i].charAt(0)).toUpperCase();
					}else{
						strData+=String.valueOf(arrValue[i].charAt(j));
					}
				}
				strData+=" ";
			}
			return strData.trim();
		}catch(Exception e) {
			return null;
		}finally {arrValue = null;}
	}




	/********************************************************
	 * Method Name      : validateAndSelectTheCheckbox()
	 * Purpose          : validate and select the checkbox based on the choice
	 * Author           : Gudi
	 * Parameters       : oBrowser, objBy, isSelectChkBox
	 * ReturnType       : boolean
	 ********************************************************/
	public boolean validateAndSelectTheCheckbox(WebDriver oBrowser, By objBy, String isSelectChkBox){
		List<WebElement> objEles = null;
		try{
			waitForElement(objBy);
			objEles = oBrowser.findElements(objBy);
			if(objEles.size() > 0){
				if(objEles.get(0).isSelected() && isSelectChkBox.equalsIgnoreCase("False")){
					objEles.get(0).click();
				}else if(!objEles.get(0).isSelected() && isSelectChkBox.equalsIgnoreCase("True")){
					objEles.get(0).click();
				}
			}else{
				reports.writeResult(oBrowser, "Fail", "The element '"+String.valueOf(objBy)+"' was not found in the DOM");
				return false;
			}
			return true;
		}catch(Exception e) {
			reports.writeResult(oBrowser, "Exception", "Exception in 'validateAndSelectTheCheckbox()' method. " + e);
			return false;
		}catch(AssertionError e) {
			reports.writeResult(oBrowser, "Exception", "AssertionError in 'validateAndSelectTheCheckbox()' method. " + e);
			return false;
		}finally {objEles = null;}
	}

	public List<WebElement> getWebElements(WebDriver oBrowser, By by){
		waitForElement(by);
		return oBrowser.findElements(by);
	}

	public String getAttribute(WebDriver oBrowser, By by, String value){
		WebElement element = waitForElement(by);
		highlightElement(oBrowser, element);
		return element.getAttribute(value).trim();
	}

	/********************************************************
	 * Method Name      : getQueryByKeyName()
	 * Purpose          : get the query from SQL.xml file based on the query keyName
	 * Author           : Gudi
	 * Parameters       : queryKey
	 * ReturnType       : WebDriver
	 ********************************************************/
	public String getQueryByKeyName(String queryKey){
		FileInputStream fin = null;
		Properties prop = null;
		Object sql = null;
		try{
			fin = new FileInputStream(System.getProperty("user.dir").replace("\\", "/")+ "/src/main/resources/SQL.xml");
			prop = new Properties();
			prop.loadFromXML(fin);
			sql = prop.get(queryKey);
			return sql.toString().trim();
		}catch(Exception e){
			reports.writeResult(oBrowser, "Exception", "Exception in 'getQueryByKeyName()' method. " + e);
			return null;
		}finally{
			try{fin.close(); fin = null; prop = null;
			}catch(Exception e){}
		}
	}





	/********************************************************
	 * Method Name      : createAndGetWebElementByjQuery()
	 * Purpose          : find and return the WebElement using JQuery
	 * Author           : Gudi
	 * Parameters       : oBrowser, jQuery, milliseconds
	 * ReturnType       : WebDriver
	 ********************************************************/
	public WebElement createAndGetWebElementByjQuery(WebDriver oBrowser, String jQuery, int... milliseconds) {
		JavascriptExecutor js = null;
		ArrayList<WebElement> objEles;
		WebElement objEle = null;
		try{
			int ms200 = 200; // number of milliseconds to wait before retrying
			int numberOfRetries = 10; // default number of retries
			if (milliseconds.length > 0) {
				numberOfRetries = milliseconds[0] / ms200;
				if (numberOfRetries <= 0) {
					numberOfRetries = 1;
				}
			}
			js = (JavascriptExecutor) oBrowser;
			for (int i = 0; i < numberOfRetries; i++) {
				objEles = (ArrayList<WebElement>) js.executeScript("return $(\"" + jQuery + "\").get()");
				if (objEles.size() > 0) {
					objEle = objEles.get(0);
					return objEle;
				} else {
					appDep.threadSleep(2000);
				}
			}
			return objEle;
		}catch(Exception e){
			reports.writeResult(oBrowser, "Exception", "Exception in 'createAndGetWebElementByjQuery()' method. " + e);
			return null;
		}finally {
			js = null; objEles = null; objEle = null;
		}
	}




	/********************************************************
	 * Method Name      : clearAndSetObjectByJavaScript()
	 * Purpose          : to set the value in the element using JavaScriptExecutor
	 * Author           : Gudi
	 * Parameters       : oBrowser, objBy, strData
	 * ReturnType       : boolean
	 ********************************************************/
	public boolean clearAndSetObjectByJavaScript(WebDriver oBrowser, By objBy, String strData) {
		List<WebElement> oEles = null;
		JavascriptExecutor js = null;
		try {
			waitForElement(objBy);
			oEles = oBrowser.findElements(objBy);
			if(oEles.size() > 0) {
				js = (JavascriptExecutor) oBrowser;
				js.executeScript("arguments[0].value='';", oEles.get(0));
				js.executeScript("arguments[0].value='"+strData+"';", oEles.get(0));
				highlightElement(oBrowser, oEles.get(0));
				reports.writeResult(oBrowser, "Pass", "cleared & entered the value '"+strData+"' in the element '"+ objBy +"' successful");
				return true;
			}else {
				reports.writeResult(oBrowser, "Fail", "The element '"+ objBy +"' was not found in the DOM");
				Assert.assertTrue(false,"The element '"+ objBy +"' was not found in the DOM");
				return false;
			}
		}catch(Exception | AssertionError e){
			reports.writeResult(null, "Exception", "Exception in 'clearAndSetObjectByJavaScript() method. " + e);
			Assert.assertTrue(false,"The element '"+ objBy +"' was not found in the DOM");
			return false;
		}
		finally {oEles = null; js = null;}
	}



	/********************************************************
	 * Method Name      : formatDate
	 * Purpose          : If day or month is less than 10, then it will return single digit number (removes zero which is prefixed)
	 * Author           : Gudi
	 * Parameters       : strDate, givenformat, resultformat
	 * ReturnType       : String
	 ********************************************************/
	public String formatDate(String strDate, String givenformat, String resultformat){
		String strFormatDate = null;
		String arrDate[];
		Pattern patt = null;
		Matcher match = null;
		String delimeter = null;
		String dtDate = "";
		try{
			strFormatDate = appInd.formatDateFromOnetoAnother((strDate.split(" "))[0].trim(), givenformat, resultformat);
			patt = Pattern.compile("[^0-9]");
			match = patt.matcher(strFormatDate);

			while(match.find()) {
				delimeter = match.group();
				break;
			}
			arrDate = strFormatDate.split(delimeter);
			for(int i=0; i<arrDate.length; i++){
				if(i<arrDate.length-1)
					dtDate+=String.valueOf(Integer.parseInt(arrDate[i]))+delimeter;
				else
					dtDate+=String.valueOf(Integer.parseInt(arrDate[i]));
			}
			return dtDate;
		}catch(Exception e){
			reports.writeResult(null, "Exception", "Exception in 'formatDate() method. " + e);
			Assert.assertTrue(false, "Exception in 'formatDate() method. "+e.getMessage());
			return strDate;
		}
	}

	public int getRandomNumber(int range){
		int num = 1;
		for(int i=1; i<range; i++){
			num=num*10;
		}
		Random rnd = new Random();
		return num + rnd.nextInt(9*num);
	}

	public String getRandomString(int targetStringLength){
		int leftLimit = 97; // letter 'a'
		int rightLimit = 122; // letter 'z'
		Random random = new Random();
		StringBuilder buffer = new StringBuilder(targetStringLength);
		for (int i = 0; i < targetStringLength; i++) {
			int randomLimitedInt = leftLimit + (int)
					(random.nextFloat() * (rightLimit - leftLimit + 1));
			buffer.append((char) randomLimitedInt);
		}
		return buffer.toString();
	}

	/********************************************************
	 * Method Name      : generateRandomNumbers
	 * Purpose          : returns number of random numbers from the given range
	 * Author           : Gudi
	 * Parameters       : start, maxNum, numberOfRandomNumbers
	 * ReturnType       : String
	 ********************************************************/
	public String generateRandomNumbers(int start, int maxNum, int numberOfRandomNumbers) {
		String randomNum = "";
		String number = "";
		int minNum = start;
		int index = 0;
		try {
			Random rand = new Random();
			for(int i=start; i<=maxNum; i++) {
				number = String.valueOf(rand.nextInt((maxNum - minNum) + 1) + minNum);
				if(!randomNum.contains(number)) {
					randomNum+= number+"#";
					index++;
					if(index == numberOfRandomNumbers) break;
				}
			}
			return randomNum.substring(0, randomNum.length()-1);
		}catch(Exception e) {
			reports.writeResult(null, "Exception", "Exception in 'generateRandomNumbers()' method. " + e.getMessage());
			return null;
		}
	}

	public void moveToElement(WebDriver driver, By by){
		new Actions(driver).moveToElement(waitForElement(by)).build().perform();;
	}

	private void moveToElementByElement(WebDriver driver, WebElement element){
		new Actions(driver).moveToElement(element).build().perform();;
	}

	public WebElement waitForElement(final By ele) {
		WebElement element=null;
		try
		{
			Wait<WebDriver> wait = new FluentWait<>(oBrowser)
					.withTimeout(Duration.ofSeconds(15))
					.pollingEvery(Duration.ofMillis(1000))
					.ignoring(Exception.class);
			element = wait.until((Function<WebDriver, WebElement>) driver -> driver.findElement(ele));
			// Element is now available for further use.
			highlightElement(oBrowser, element);
		}
		catch (Exception e)
		{
			logger.info(e.getMessage());
		}
		return element;
	}

	public void highlightElement(WebDriver oBrowser, WebElement element){
		((JavascriptExecutor) oBrowser).executeScript("arguments[0].style.border='2px solid red'", element);
	}



	/********************************************************
	 * Method Name      : getRandomNumberFromTheListSpecified()
	 * Purpose          : to get the random number form the list specified
	 * Author           : Gudi
	 * Parameters       : arrListValues[]
	 * ReturnType       : int
	 ********************************************************/
	public int getRandomNumberFromTheListSpecified(Integer arrListValues[]){
		List<Integer> givenList = null;
		Random random = null;
		int randomNum = 0;
		try{
			givenList = Arrays.asList(arrListValues);
			random = new Random();
			randomNum = givenList.get(random.nextInt(givenList.size()));
			return randomNum;
		}catch(Exception e){
			reports.writeResult(null, "Exception", "Exception in 'getRandomNumberFromTheListSpecified()' method. " + e.getMessage());
			return -1;
		}finally {
			givenList = null;
			random = null;
		}
	}


	/********************************************************
	 * Method Name      : getCurrentLineNumber()
	 * Purpose          : to get the current Line number of the code
	 * Author           : Gudi
	 * Parameters       : NA
	 * ReturnType       : int
	 ********************************************************/
	public int getCurrentLineNumber(){
		return Thread.currentThread().getStackTrace()[2].getLineNumber();
	}


	/********************************************************
	 * Method Name      : generatePhoneNumber()
	 * Purpose          : to get the 10 digits phone number
	 * Author           : Gudi
	 * Parameters       : prefix, format
	 * ReturnType       : String
	 ********************************************************/
	public String generatePhoneNumber(String prefix, String format){
		String phoneNum = null;
		try{
			String num = prefix + appInd.getDateTime(format);
			if(num.length()==10){
				phoneNum = num;
			}else{
				if(num.length() == 11){
					phoneNum = num.substring(0, num.length()-1);
				}else if(num.length()==9){
					phoneNum = num+"8";
				}else if(num.length()==8){
					phoneNum = num+"81";
				}
			}
			return phoneNum;
		}catch(Exception e){
			reports.writeResult(null, "Exception", "Exception in 'generatePhoneNumber()' method. " + e.getMessage());
			return null;
		}
	}



	/********************************************************
	 * Method Name      : generateTaxID()
	 * Purpose          : to get the >=9 digits taxID number
	 * Author           : Gudi
	 * Parameters       : format
	 * ReturnType       : String
	 ********************************************************/
	public String generateTaxID(String format){
		String taxID = null;
		try{
			String tax = appInd.getDateTime("Shhmmss");
			if(tax.length()<=9){
				taxID = tax;
			}else if(tax.length()==8){
				taxID = tax+"1";
			}else if(tax.length()==7){
				taxID = tax+"26";
			}
			return taxID;
		}catch(Exception e){
			reports.writeResult(null, "Exception", "Exception in 'generateTaxID()' method. " + e.getMessage());
			return null;
		}
	}




	/********************************************************
	 * Method Name      : dateDifferences()
	 * Purpose          : to get the difference between 2 dates in the form of Days/Months/Years. Date format can be either dd/MM/yyyy OR MM/dd/yyyy
	 * Author           : Gudi
	 * Parameters       : date1, date2, intervals, dateFormat
	 * ReturnType       : long
	 ********************************************************/
	public long dateDifferences(String date1, String date2, String intervals, String dateFormat){
		SimpleDateFormat sdf = null;
		long differenceInMilliseconds = 0;
		long differenceInMonths = 0;
		long differenceInYears = 0;
		long difference = 0;
		try{
			sdf = new SimpleDateFormat(dateFormat);
			Date dtDate1 = sdf.parse(date1);
			Date dtDate2 = sdf.parse(date2);

			switch(intervals.toLowerCase()){
				case "day":
					differenceInMilliseconds = dtDate1.getTime() - dtDate2.getTime();
					difference = TimeUnit.DAYS.convert(differenceInMilliseconds, TimeUnit.MILLISECONDS);
					break;
				case "month":
					differenceInMilliseconds = dtDate1.getTime() - dtDate2.getTime();
					differenceInMonths = TimeUnit.MILLISECONDS.toDays(differenceInMilliseconds) / 30;
					difference = Math.abs(differenceInMonths);
					break;
				case "year":
					differenceInMilliseconds = dtDate1.getTime() - dtDate2.getTime();
					differenceInYears = TimeUnit.MILLISECONDS.toDays(differenceInMilliseconds) / 365;
					difference = Math.abs(differenceInYears);
					break;
				default:
					System.out.println("Invalid interval for finding the date differences");
			}
			return difference;
		}catch(Exception e){
			reports.writeResult(null, "Exception", "Exception in 'dateDifferences()' method. " + e.getMessage());
			return -1;
		}finally {sdf = null;}
	}



	/********************************************************
	 * Method Name      : verifyContainsValues()
	 * Purpose          : This method helps to verify whether actual value contains expected value
	 * Author           : Shidd
	 * Parameters       : oBrowser, actual, expected
	 * ReturnType       : boolean
	 ********************************************************/
	public boolean verifyContainsValues(String actual, String expected) {
		try {
			if(actual.contains(expected)) {
				reports.writeResult(oBrowser, "Pass", "The actual '"+actual+"' & expected '"+expected+"' values are matching");
				return true;
			}else {
				reports.writeResult(oBrowser, "Fail", "Mis-match in the both actual '"+actual+"' & expected '"+expected+"' values");
				return false;
			}
		}catch(Exception e){
			reports.writeResult(null, "Exception", "Exception in 'verifyContainsValues() method. " + e);
            Assert.fail("Mis-match in the both actual '" + actual + "' & expected '" + expected + "' values");
			return false;
		}
	}

	/********************************************************
	 * Method Name      : switchToggleButton()
	 * Purpose          : This method helps to toggle button based on requirement
	 * Author           : Shidd
	 * Parameters       : oBrowser, element, requirement
	 * ReturnType       : boolean
	 ********************************************************/
	public boolean switchToggleButton(WebDriver oBrowser, By element, String requirement) {
		boolean status;
		try {
			status = oBrowser.findElement(element).isSelected();
			if (requirement.equalsIgnoreCase("true")) {
				if (!status) {
					Assert.assertTrue(javaScriptClickObject(oBrowser, element));
				}
			} else if (requirement.equalsIgnoreCase("false")) {
				if (status) {
					Assert.assertTrue(javaScriptClickObject(oBrowser, element));
				}
			}
			return true;
		} catch(Exception e){
			reports.writeResult(null, "Exception", "Exception in 'switchToggleButton() method. " + e);
			Assert.fail("Unable to toggle button based on requirement");
			return false;
		}
	}

/*	public static void main(String[] args) {
		AppIndependentMethods appind= new AppIndependentMethods();
		String sx=appind.getPropertyValueByKeyName("browserName");
		System.out.println(sx);
	}*/

}
