package com.group.taskmanager.tests;

import com.codeborne.selenide.WebDriverRunner;
import com.group.taskmanager.ConfigReader;
import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;

import java.net.MalformedURLException;
import java.net.URL;

import static com.codeborne.selenide.Selenide.*;

public class BaseTest {
    private static ConfigReader configReader;

    @BeforeClass
    public static void setUpAll() throws MalformedURLException {
        configReader = new ConfigReader();
        if (configReader.getProperties().getProperty("saucelab.test").equals("true")) {
            String sauceUsername = configReader.getProperties().getProperty("saucelab.username");
            String sauceAccesskey = configReader.getProperties().getProperty("saucelab.accesskey");
            String url = "https://" + sauceUsername + ":" + sauceAccesskey + "@ondemand.saucelabs.com:443/wd/hub";
            DesiredCapabilities caps = DesiredCapabilities.chrome();
            caps.setCapability("platform", "Windows XP");
            caps.setCapability("version", "57.0");
            WebDriver driver = new RemoteWebDriver(new URL(url), caps);
            WebDriverRunner.setWebDriver(driver);
        }
    }

    @Before
    public void setUp() {
        System.setProperty("webdriver.chrome.driver", configReader.getProperties().getProperty("webdriver.chrome.driver"));
        String webappUrl = configReader.getProperties().getProperty("webapp.url");
        open(webappUrl);
    }

    @After
    public void tearDown() {
        clearBrowserCookies();
        clearBrowserLocalStorage();
    }

}
