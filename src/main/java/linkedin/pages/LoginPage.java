package linkedin.pages;

import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.support.ui.ExpectedConditions;
import java.time.Duration;
import linkedin.core.Locators;

public class LoginPage extends BasePage {
    private Locators locators = new Locators();
    private String email;
    private String password;

    public LoginPage(WebDriver driver, String email, String password) {
        super(driver);
        this.email = email;
        this.password = password;
    }

    public LoginPage enterEmail() {
        driver.findElement(locators.email).sendKeys(email);
        return this;
    }

    public LoginPage enterPassword() {
        driver.findElement(locators.password).sendKeys(password);
        return this;
    }

    public LinkedinHomePage clickLogin() {
        driver.findElement(locators.loginButton).click();
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        wait.until(ExpectedConditions.urlContains("linkedin.com/feed"));
        return new LinkedinHomePage(driver);
    }

    public boolean isLoginSuccessful() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        try {
            wait.until(ExpectedConditions.urlContains("linkedin.com/feed"));
            return true;
        } catch (TimeoutException e) {
            return false;
        }
    }
}
