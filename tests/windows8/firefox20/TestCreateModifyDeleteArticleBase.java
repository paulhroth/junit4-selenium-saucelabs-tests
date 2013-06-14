// This template tests the following:
// 1. Creating an article with all required fields filled in, no CTA, a special character is used for the Rich Text Editor.
// 2. Going back to the Ads Dashboard, then back to the creative.
// 3. Verifying all required fields' values and verifying the Publication Date.
// 4. Editing all required fields and checking if an error is displayed when saved with a cleared field.
// 4. Deleting the creative while in the creative.

import java.net.URL;
import java.util.List;
import org.junit.*;

import static org.junit.Assert.*;

import org.openqa.selenium.*;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.WebDriverWait;

public class TestCreateModifyDeleteArticleBase {
  private WebDriver driver;
  private String baseUrl;
  private int waitTimeInSeconds = 10;
  
  public void waitForElementByLinkText(final String linkText) {
		(new WebDriverWait(driver, waitTimeInSeconds)).until(new ExpectedCondition<Boolean>() {
      	public Boolean apply(WebDriver d) {
          	return d.findElements(By.linkText(linkText)).size() > 0;
      	}
  	});
	}
	
	public void waitForElementByXPath(final String xpath) {
		(new WebDriverWait(driver, waitTimeInSeconds)).until(new ExpectedCondition<Boolean>() {
			public Boolean apply(WebDriver d) {
				return d.findElements(By.xpath(xpath)).size() > 0;
			}
		});
	}
		
	public void waitForElementById(final String id) {
		(new WebDriverWait(driver, waitTimeInSeconds)).until(new ExpectedCondition<Boolean>() {
			public Boolean apply(WebDriver d) {
				return d.findElements(By.id(id)).size() > 0;
			}
		});
	}
	
	public void waitForElementByCssSelector(final String cssSelector) {
		(new WebDriverWait(driver, waitTimeInSeconds)).until(new ExpectedCondition<Boolean>() {
			public Boolean apply(WebDriver d) {
				return d.findElements(By.cssSelector(cssSelector)).size() > 0;
			}
		});
	}
	
	public void waitForElementByClassName(final String className) {
		(new WebDriverWait(driver, waitTimeInSeconds)).until(new ExpectedCondition<Boolean>() {
			public Boolean apply(WebDriver d) {
				return d.findElements(By.className(className)).size() > 0;
			}
		});
	}
	
	public void waitForElementBodyContains(final String text) {
		(new WebDriverWait(driver, waitTimeInSeconds)).until(new ExpectedCondition<Boolean>() {
			public Boolean apply(WebDriver d) {
				return d.findElement(By.tagName("body")).getText().contains(text);
			}
		});
	}
  
  @Before
  public void setUp() throws Exception {

      DesiredCapabilities capabillities = DesiredCapabilities.firefox();
      capabillities.setCapability("version", "20.0");
      capabillities.setCapability("platform", Platform.WIN8);
      capabillities.setCapability("name", "TestCreateModifyDeleteArticleBase");
      this.driver = new RemoteWebDriver(
					  new URL("http://coreywu:d7770fcb-6478-4188-bf7a-3fbd8fd237b6@ondemand.saucelabs.com:80/wd/hub"),
					  capabillities);
      baseUrl = "https://control-staging.mediaeverywhere.com/";
  }
  
  @Test
  public void test() throws Exception {
	  driver.get(baseUrl + "/");
      driver.findElement(By.id("id_username")).sendKeys("polarqa");
      driver.findElement(By.id("id_password")).sendKeys("4testing");
      driver.findElement(By.tagName("button")).click();
      assertTrue("There is no 'Native Ad Creator' on the page.", driver.findElements(By.linkText("Native Ad Creator")).size() > 0);
      driver.findElement(By.linkText("Native Ad Creator")).click();
      
      waitForElementByClassName("title");
	  synchronized (driver) { driver.wait(1000); }
      
	  int countInitial = driver.findElements(By.linkText("Selenium Test1")).size();
	  
	  // Create Creative
	  driver.findElements(By.className("btn")).get(0).click();
	  
	  waitForElementBodyContains("Experience");
	  synchronized (driver) { driver.wait(2000); }
	  
	  driver.findElement(By.id("id_creative-name")).sendKeys("Selenium Test1");
	  driver.findElement(By.id("id_creative-advertiser_name")).sendKeys("Selenium Test2");
	  driver.findElement(By.id("id_article-title")).sendKeys("Selenium Test3");
	  driver.findElement(By.id("id_article-description")).sendKeys("Selenium Test4");
	  driver.findElement(By.cssSelector("#mce_26 > button[type=\"button\"]")).click();
	  driver.findElement(By.id("g209")).click();
	  driver.findElement(By.cssSelector("#mce_44 > button[type=\"button\"]")).click();
	  driver.findElement(By.cssSelector("input.btn.btn-success")).click();
	  
	  assertFalse("'This field is required' was displayed; a field was not inputted correctly."
			  , driver.findElement(By.tagName("body")).getText().contains(
			  "This field is required."));
	  assertTrue("'Changes saved!' was not displayed.", driver.findElement(By.tagName("body")).getText().contains("Changes saved!"));
	  String date = driver.findElement(By.id("id_article-pub_date")).getAttribute("value");
	  
	  driver.findElement(By.xpath("/html/body/div[2]/header/div/h6/a[2]")).click(); // Click on 'Ads'
	  int countFinal = driver.findElements(By.linkText("Selenium Test1")).size();
	  
	  waitForElementByLinkText("Selenium Test1");
	  synchronized (driver) { driver.wait(2000); }
	  
	  assertEquals("The number of instances of 'Selenium Test1' after creation is not one more than the number of instances initially."
			  , countInitial + 1, countFinal);
	  List<WebElement> creativeList = driver.findElements(By.linkText("Selenium Test1"));
	  creativeList.get(creativeList.size() - 1).click();
	  
	  // Checking Creative Fields
	  assertTrue("The 'Warning!...' was not displayed.", driver.findElement(By.tagName("body")).getText().contains(
			  "Warning! You are currently editing an existing creative. Any changes you make may affect live ads."));
	  assertEquals("The 'Experience' selector was not disabled.", "true", driver.findElement(By.id("id_extype-faux_experience")).getAttribute("disabled"));
	  assertEquals("The 'Experience' was not set to 'Article'.", "article", driver.findElement(By.id("id_extype-experience")).getAttribute("value"));
	  assertEquals("The 'Experience' was not hidden'.", "hidden", driver.findElement(By.id("id_extype-experience")).getAttribute("type"));
	  assertEquals("The 'Creative Name' is incorrect", "Selenium Test1", driver.findElement(By.id("id_creative-name")).getAttribute("value"));
	  assertEquals("The 'Advertiser Name' is incorrect", "Selenium Test2", driver.findElement(By.id("id_creative-advertiser_name")).getAttribute("value"));
	  assertEquals("The 'Title' is incorrect", "Selenium Test3", driver.findElement(By.id("id_article-title")).getAttribute("value"));
	  assertEquals("The 'Summary' is incorrect", "Selenium Test4", driver.findElement(By.id("id_article-description")).getAttribute("value"));
	  //assertTrue("'℘' was not displayed.", driver.findElement(By.tagName("body")).getText().contains("℘"));
	  assertEquals("'The 'Published Date' is incorrect. (or another problem occurred)", date, driver.findElement(By.id("id_article-pub_date")).getAttribute("value"));
	  
	  // Modifying Fields
	  driver.findElement(By.id("id_creative-name")).clear();
	  driver.findElement(By.cssSelector("input.btn.btn-success")).click();
	  assertTrue("'This field is required' was not displayed; the field 'Creative Name' was not cleared correctly."
			  , driver.findElement(By.tagName("body")).getText().contains(
			  "This field is required."));
	  driver.findElement(By.id("id_creative-name")).sendKeys("Selenium Test12");
	  
	  driver.findElement(By.id("id_creative-advertiser_name")).clear();
	  driver.findElement(By.cssSelector("input.btn.btn-success")).click();
	  assertTrue("'This field is required' was not displayed; the field 'Advertiser Name' was not cleared correctly."
			  , driver.findElement(By.tagName("body")).getText().contains(
			  "This field is required."));
	  driver.findElement(By.id("id_creative-advertiser_name")).sendKeys("Selenium Test22");
	  
	  driver.findElement(By.id("id_article-title")).clear();
	  driver.findElement(By.cssSelector("input.btn.btn-success")).click();
	  assertTrue("'This field is required' was not displayed; the field 'Title' was not cleared correctly."
			  , driver.findElement(By.tagName("body")).getText().contains(
			  "This field is required."));
	  driver.findElement(By.id("id_article-title")).sendKeys("Selenium Test32");
	  
	  driver.findElement(By.id("id_article-description")).clear();
	  driver.findElement(By.cssSelector("input.btn.btn-success")).click();
	  assertTrue("'This field is required' was not displayed; the field 'Summary' was not cleared correctly."
			  , driver.findElement(By.tagName("body")).getText().contains(
			  "This field is required."));
	  driver.findElement(By.id("id_article-description")).sendKeys("Selenium Test42");
	  driver.findElement(By.cssSelector("#mce_26 > button[type=\"button\"]")).click();
	  
	  waitForElementById("g209");
	  synchronized (driver) { driver.wait(1000); }
	  
	  driver.findElement(By.id("g209")).click();
	  driver.findElement(By.cssSelector("#mce_44 > button[type=\"button\"]")).click();
	  driver.findElement(By.cssSelector("input.btn.btn-success")).click();
	  assertFalse("'This field is required' was displayed; a field was not inputted correctly."
			  , driver.findElement(By.tagName("body")).getText().contains(
			  "This field is required."));
	  
	  waitForElementByCssSelector("input.btn.btn-danger");
	  
	  // Deleting Fields
	  driver.findElement(By.cssSelector("input.btn.btn-danger")).click();
	  
	  waitForElementByCssSelector("button.btn.btn-danger");
	  synchronized (driver) { driver.wait(1000); }
	  
	  driver.findElement(By.cssSelector("button.btn.btn-danger")).click();
	  countFinal = driver.findElements(By.linkText("Selenium Test1")).size();
	  assertEquals("The number of instances of 'Selenium Test1' after creation is not equal to the number of instances initially."
			  , countInitial, countFinal);
	  
	  synchronized (driver) { driver.wait(10000); }
  }
  
  @After
  public void tearDown() throws Exception {
    driver.quit();
  }
}