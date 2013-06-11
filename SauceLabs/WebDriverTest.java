package com.example.tests;


import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TestName;
import org.openqa.selenium.Platform;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;

import java.net.URL;

import static junit.framework.Assert.assertEquals;

public class WebDriverTest {

    private WebDriver driver;

    @Before
	public void setUp() throws Exception {

        DesiredCapabilities capabillities = DesiredCapabilities.iphone();
        capabillities.setCapability("version", "5.0");
        capabillities.setCapability("platform", Platform.MAC);
        capabillities.setCapability("name", "PaulWasHere");
        this.driver = new RemoteWebDriver(
					  new URL("http://polarqa:d609b648-22e3-44bb-a38e-c28931df837d@ondemand.saucelabs.com:80/wd/hub"),
					  capabillities);
    }

    @Test
	public void basic() throws Exception {
        driver.get("http://www.amazon.com/");
        assertEquals("Amazon.com: Online Shopping for Electronics, Apparel, Computers, Books, DVDs & more", driver.getTitle());
    }

    @After
	public void tearDown() throws Exception {
        driver.quit();
    }

}