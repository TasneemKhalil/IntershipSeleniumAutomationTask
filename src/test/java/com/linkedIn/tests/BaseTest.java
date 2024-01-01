package com.linkedIn.tests;

import java.io.IOException;
import java.time.Duration;
import java.util.Properties;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;

import io.github.bonigarcia.wdm.WebDriverManager;
import linkedin.pages.LinkedinHomePage;
import linkedin.pages.LoginPage;
import linkedin.core.ReadpropertyFile;

public abstract class BaseTest {
    protected WebDriver driver;
    protected LinkedinHomePage linkedInHomePage;
    protected Properties prop;
    protected WebDriverWait wait;

    @BeforeTest
    public void setUp() throws IOException {
        System.out.println("Setting up WebDriver and properties");

        // Load properties using ReadpropertyFile
        ReadpropertyFile readPropertyFile = new ReadpropertyFile("C:\\Users\\dell\\TestAoutomation_workspace\\FinalTask\\src\\test\\resources\\configfiles\\config.propreties");
        String browserType = readPropertyFile.getProperty("browser");
        prop = new Properties();
        prop.setProperty("linkedinurl", readPropertyFile.getProperty("linkedinurl"));
        prop.setProperty("email", readPropertyFile.getProperty("email"));
        prop.setProperty("password", readPropertyFile.getProperty("password"));
        

        // Set up WebDriver based on the browser type
        if ("firefox".equalsIgnoreCase(browserType)) {
            WebDriverManager.firefoxdriver().setup();
            driver = new FirefoxDriver();
        } else if ("chrome".equalsIgnoreCase(browserType)) {
            WebDriverManager.chromedriver().setup();
            driver = new ChromeDriver();
        } else {
            throw new IllegalArgumentException("Browser type not supported: " + browserType);
        }

       
       
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10)); // Implicit wait

        // Navigate to LinkedIn and login
        System.out.println("Opening LinkedIn URL: " + prop.getProperty("linkedinurl"));
        driver.get(prop.getProperty("linkedinurl"));
        LoginPage loginPage = new LoginPage(driver, prop.getProperty("email"), prop.getProperty("password"));
        System.out.println("Performing login");
        loginPage.enterEmail().enterPassword().clickLogin();

        // Add explicit wait to ensure login is successful
        wait = new WebDriverWait(driver, Duration.ofSeconds(20)); // Explicit wait      

        linkedInHomePage = new LinkedinHomePage(driver);
        System.out.println("Setup complete");
    }

    @AfterTest
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }
}
