//import com.saucelabs.common.SauceOnDemandAuthentication; 
//import com.saucelabs.common.SauceOnDemandSessionIdProvider; 
import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TestName;
import org.openqa.selenium.By;
import org.openqa.selenium.Platform;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.Select;

import java.net.URL;

import static junit.framework.Assert.assertEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * Simple {@link RemoteWebDriver} test that demonstrates how to run your Selenium tests with <a href="http://ondemand.saucelabs.com/ondemand">Sauce OnDemand</a>.
 * *
 * @author Ross Rowe
 */
public class TestSauceLabs {

    private WebDriver driver;
    private String baseUrl;
    
    @Before
	public void setUp() throws Exception {

        DesiredCapabilities capabillities = DesiredCapabilities.firefox();
        capabillities.setCapability("version", "20.0");
        capabillities.setCapability("platform", Platform.WIN8);
        this.driver = new RemoteWebDriver(
					  new URL("http://coreywu:d7770fcb-6478-4188-bf7a-3fbd8fd237b6@ondemand.saucelabs.com:80/wd/hub"),
					  capabillities);
        baseUrl = "https://control-staging.mediaeverywhere.com/";
    }

    @Test
	public void basic() throws Exception {
        driver.get("http://www.amazon.com/");
        assertEquals("Amazon.com: Online Shopping for Electronics, Apparel, Computers, Books, DVDs & more", driver.getTitle());
    }
    
    @Test
    public void testLogin() throws Exception {
      driver.get(baseUrl + "/");
      driver.findElement(By.id("id_username")).sendKeys("polarqa");
      driver.findElement(By.id("id_password")).sendKeys("4testing");
      driver.findElement(By.tagName("button")).click();
      assertTrue(driver.findElements(By.linkText("Native Ad Creator")).size() > 0);
    }
    
    @Test
    public void testCreateCreative() throws Exception {
  	  driver.get(baseUrl + "/");
  	    driver.findElement(By.id("id_username")).sendKeys("corey.wu@polarmobile.com");
  	    driver.findElement(By.id("id_password")).sendKeys("27131018699753UoW@");
  	    driver.findElement(By.tagName("button")).click();
  	    driver.findElement(By.linkText("Native Ad Creator")).click();
  	    driver.findElements(By.className("btn")).get(0).click();
  	    new Select(driver.findElement(By.id("id_extype-experience"))).selectByVisibleText("Article");
  	    new Select(driver.findElement(By.id("id_extype-experience"))).selectByVisibleText("Outbound");
  	    driver.findElement(By.cssSelector("option[value=\"outbound\"]")).click();
  	    driver.findElement(By.id("id_creative-name")).sendKeys("Selenium Test1");
  	    driver.findElement(By.id("id_creative-advertiser_name")).sendKeys("Selenium Test2");
  	    driver.findElement(By.id("id_article-title")).sendKeys("Selenium Test3");
  	    driver.findElement(By.id("id_article-description")).sendKeys("Selenium Test4");
  	    driver.findElement(By.cssSelector("div.cta_actions > button.btn.btn-primary")).click();
  	    driver.findElement(By.id("id_name")).clear();
  	    driver.findElement(By.id("id_name")).sendKeys("Selenium Test5");
  	    driver.findElement(By.id("id_text")).clear();
  	    driver.findElement(By.id("id_text")).sendKeys("Selenium Test6");
  	    driver.findElement(By.id("id_dest_url")).clear();
  	    driver.findElement(By.id("id_dest_url")).sendKeys("https://www.google.ca/");
  	    driver.findElement(By.cssSelector("button.btn.btn-primary")).click();
  	    driver.findElement(By.cssSelector("input.btn.btn-success")).click();
  	    //driver.findElement(By.cssSelector("div.cta_actions > button.btn.btn-primary")).click();
  	    //driver.findElement(By.id("id_article-body_ifr")).sendKeys("Selenium Test5");
  	    //assertEquals("Selenium Test5", driver.findElement(By.tagName("p")).getText());
  	    //assertTrue(driver.findElements(By.linkText("Native Ad Creator")).size() > 0);
  	 
    }
    
    @Test
    public void testDelete() throws Exception {
      driver.get(baseUrl + "/");
      driver.findElement(By.id("id_username")).sendKeys("corey.wu@polarmobile.com");
      driver.findElement(By.id("id_password")).sendKeys("27131018699753UoW@");
      driver.findElement(By.tagName("button")).click();
      driver.findElement(By.linkText("Native Ad Creator")).click();
      String link = driver.findElement(By.linkText("Selenium Test1")).getAttribute("href");
      String[] linkIds = link.split("/");
      int id = Integer.parseInt(linkIds[linkIds.length-1]);
      //assertEquals("https://control-staging.mediaeverywhere.com/native_ads/creative/38", driver.findElement(By.linkText("Selenium Test1")).getAttribute("href"));
      driver.findElement(By.xpath("//button[@onclick='$(\"#deletemodalcontainer\").load(\"/native_ads/creative/delete/" + id + "\");']")).click();
      assertEquals(" ", driver.findElements(By.tagName("button")).get(1).getAttribute("onclick"));
      synchronized (driver)
      {
          driver.wait(30000);
      }
    }

    @After
	public void tearDown() throws Exception {
        driver.quit();
    }

}
