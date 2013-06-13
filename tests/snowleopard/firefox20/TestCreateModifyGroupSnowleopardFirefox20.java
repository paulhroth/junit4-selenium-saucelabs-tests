package com.example.tests;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.*;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.net.URL;
import java.util.Random;

import static junit.framework.Assert.assertEquals;

import com.saucelabs.saucerest.SauceREST;

public class TestCreateModifyGroupSnowleopardFirefox20 {

    //setup strings: feel free to modify these as required
    private final String sauce_user = "polarqa";
    private final String sauce_pass = "4testing";
    private final String sauce_access = "d609b648-22e3-44bb-a38e-c28931df837d";
    private final String baseUrl = "https://control-staging.mediaeverywhere.com";
    private final String initialOwnerGroup = "AcmePublisher";
    private final String finalOwnerGroup = "AcmeAdvertising";
    
    //these are required for the test to work
    private WebDriver driver;
    private SauceREST client;
    private String jobID;
    private boolean passed;

    //generates a random string in order to allow everything created by this test to be unique
    public String generateString() {
        StringBuilder sb = new StringBuilder();
        Random random = new Random();
        char[] chars = "abcdefghijklmnopqrstuvwxyz".toCharArray();
        for (int i = 0; i < 20; i++) {
            char c = chars[random.nextInt(chars.length)];
            sb.append(c);
        }
        return(sb.toString());
    }

    @Before
    public void setUp() throws Exception {

        DesiredCapabilities capabillities = DesiredCapabilities.firefox();
        capabillities.setCapability("version", "20.0");
        capabillities.setCapability("platform", "OS X 10.6");
        capabillities.setCapability("name", "TestCreateModifyGroupMacFirefox");
        client = new SauceREST(sauce_user, sauce_access);
        this.driver = new RemoteWebDriver(
                new URL("http://" + sauce_user + ":d609b648-22e3-44bb-a38e-c28931df837d@ondemand.saucelabs.com:80/wd/hub"),
                capabillities);
        jobID = ((RemoteWebDriver)driver).getSessionId().toString();
        passed = false;
    }

    @Test
    public void testCreateModifyGroup() throws Exception {

        //loads site
        driver.get(baseUrl + "/login");

        //logs in
        driver.findElement(By.id("id_username")).clear();
        driver.findElement(By.id("id_username")).sendKeys(sauce_user);
        driver.findElement(By.id("id_password")).clear();
        driver.findElement(By.id("id_password")).sendKeys(sauce_pass);
        driver.findElement(By.cssSelector("button.btn.btn-primary")).click();

        //navigates to the Native Ad Creator
        driver.findElement(By.linkText("Native Ad Creator")).click();

        //creates a New Group with the name "test_<random 20 character long string>"
        final String groupName = generateString();
        (new WebDriverWait(driver, 10)).until(new ExpectedCondition<Boolean>() {
            public Boolean apply(WebDriver d) {
                return d.findElements(By.linkText("+ New Group")).size()>0;
            }
        });
        driver.findElement(By.linkText("+ New Group")).click();
        driver.findElement(By.id("id_name")).clear();
        driver.findElement(By.id("id_name")).sendKeys("test_" + groupName);
        new Select(driver.findElement(By.id("id_ownergroup"))).selectByVisibleText(initialOwnerGroup);
        driver.findElement(By.cssSelector("input.btn.btn-success")).click();

        //modifies the newly created group
        (new WebDriverWait(driver, 10)).until(new ExpectedCondition<Boolean>() {
            public Boolean apply(WebDriver d) {
                return d.findElements(By.xpath(".//*[@id='main']/div/table/tbody/tr/td/h2[contains(text(),'" + groupName + "')]/a")).size()>0;
            }
        });
        driver.findElement(By.xpath(".//*[@id='main']/div/table/tbody/tr/td/h2[contains(text(),'" + groupName + "')]/a")).click();
        new Select(driver.findElement(By.id("id_ownergroup"))).selectByVisibleText(finalOwnerGroup);
        driver.findElement(By.cssSelector("input.btn.btn-success")).click();
        
        //passes the test
        passed = true;
    }

    @After
    public void tearDown() throws Exception {
        if (passed) {
            client.jobPassed(jobID);
        } else {
            client.jobFailed(jobID);
        }
        driver.quit();
    }
}