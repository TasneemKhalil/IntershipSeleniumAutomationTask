package linkedin.pages;

import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import linkedin.core.Locators;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

public class LinkedinHomePage extends BasePage {
    private Locators locators = new Locators();
    private WebDriverWait wait;

    public LinkedinHomePage(WebDriver driver) {
        super(driver);
        wait = new WebDriverWait(driver, Duration.ofSeconds(15));
    }

    public LinkedinHomePage searchFor(String keyword) {
        WebElement searchBox = wait.until(ExpectedConditions.visibilityOfElementLocated(locators.searchBox));
        searchBox.sendKeys(keyword);
        searchBox.sendKeys(Keys.ENTER);
        return this;
    }

    public LinkedinHomePage choosePeople() {
        try {
            WebElement peopleFilterButton = wait.until(ExpectedConditions.elementToBeClickable(locators.peopleFilterButton));
            peopleFilterButton.click();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return this;
    }


    public List<String[]> extractResults() {
        wait.until(ExpectedConditions.visibilityOfElementLocated(locators.profiles));
        List<String[]> results = new ArrayList<>();
        List<WebElement> profiles = driver.findElements(locators.profiles);

        for (int i = 0; i < Math.min(profiles.size(), 8); i++) {
            WebElement profile = profiles.get(i);
            String name = profile.findElement(locators.name).getText();
            String href = profile.findElement(locators.href).getAttribute("href");
            String position = profile.findElement(locators.position).getText();
            String location = profile.findElement(locators.location).getText();
            results.add(new String[]{name, href, position, location});
        }
        return results;
    }

}
