package linkedin.core;

import org.openqa.selenium.By;

// storing the locators of the web elements
public class Locators {
    public final By successMessage = null;
	// Locators for Login page
    public By email = By.id("session_key");
    public By password = By.id("session_password");
    public By loginButton = By.className("sign-in-form__submit-btn--full-width");
    public By loginSuccess = By.id("success");
    public By loginError = By.id("error");

    // Locators for Linkedin home page
    
    public By searchBox = By.cssSelector("input[aria-label='Search']");
    public By peopleFilterButton = By.xpath("//button[contains(@class, 'artdeco-pill') and .//text()='People']");
    public By profiles =  By.className("reusable-search__result-container");
    public By name = By.className("entity-result__title-text"); 
    public By href = By.cssSelector("span.entity-result__title-text a.app-aware-link");
    public By position = By.className("entity-result__primary-subtitle");
    public By location = By.className("entity-result__secondary-subtitle");

    // Locators for Google home page
    public By searchBar= By.name("q");
    public By googleSearchResults = By.cssSelector("div#search div.g");
    public By getLink= By.tagName("a");
    
   
}
