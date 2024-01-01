package linkedin.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.IOException;
import java.time.Duration;
import java.util.*;


import linkedin.core.Locators;
import linkedin.core.ReadpropertyFile;

public class GoogleHomePage extends BasePage {
    private Locators locators = new Locators();
    private WebDriverWait wait;
    private String googleUrl;

    // Constructor
    public GoogleHomePage(WebDriver driver) throws IOException {
        super(driver);
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10)); // Initialize wait with a timeout

        // Load Google URL from properties file
        ReadpropertyFile readPropertyFile = new ReadpropertyFile("C:\\Users\\dell\\TestAoutomation_workspace\\FinalTask\\src\\test\\resources\\configfiles\\config.propreties");
        this.googleUrl = readPropertyFile.getProperty("googleurl");
    }
    
    // Method to open the Google page
    public GoogleHomePage openPage() {
        driver.get(googleUrl); // Use URL from properties file
        return this;
    }
    // Method to search for a keyword
    public GoogleHomePage searchFor(String keyword) {
        WebElement searchBox = driver.findElement(locators.searchBar); // Assuming you have a locator for Google's search box
        searchBox.sendKeys(keyword);
        searchBox.sendKeys(Keys.ENTER);
        return this;
    }
    
    // Method to extract the href link of the first 8 results
    public List<String> extractLinks() {
        List<String> links = new ArrayList<>();

        // Wait for the search results to be visible
        wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(locators.googleSearchResults));

        // Find individual search result elements
        List<WebElement> results = driver.findElements(locators.googleSearchResults);
//        System.out.println("Number of results found: " ); // Debugging line

        for (int i = 0; i < Math.min(results.size(), 8); i++) {
            WebElement result = results.get(i);
            try {
                // Get the href attribute of the link within the search result
                String href = result.findElement(locators.getLink).getAttribute("href");
//                System.out.println("Extracted link: " + href); // Debugging line
                links.add(href);
            } catch (Exception e) {
                System.out.println("Error extracting link from result " + i + ": " + e.getMessage());
            }
        }
        return links;
    }



}
