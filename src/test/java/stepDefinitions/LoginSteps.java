package stepDefinitions;

import io.cucumber.java.en.*;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.FluentWait;
import pages.LoginPage;
import org.testng.Assert;

import java.time.Duration;
import java.util.NoSuchElementException;

public class LoginSteps {

    WebDriver driver = Hooks.driver;  // use shared driver
    LoginPage lp = new LoginPage(driver);

    @Given("user is on login page")
    public void openLoginPage() {
        driver.get("https://the-internet.herokuapp.com/login");
    }

    @When("user enters username and password")
    public void enterValidCredentials() {
        lp.enterUsername("tomsmith");
        lp.enterPassword("SuperSecretPassword!");
    }

    @When("user enters invalid credentials")
    public void enterInvalidCredentials() {
        lp.enterUsername("wrong");
        lp.enterPassword("wrong");
    }

    @When("clicks login button")
    public void clickLogin() {
        lp.clickLogin();
    }

    @Then("user should see dashboard")
    public void verifyDashboard() {
        String flashText = getFlashText();
        Assert.assertTrue(flashText.contains("You logged into a secure area!"),
                "Expected success message not displayed! Actual: " + flashText);
    }

    @Then("user should see error message")
    public void verifyError() {
        String flashText = getFlashText();
        Assert.assertTrue(flashText.toLowerCase().contains("invalid"),
                "Expected error message not displayed! Actual: " + flashText);
    }

    // Helper method to wait for flash message using FluentWait
    private String getFlashText() {
        FluentWait<WebDriver> wait = new FluentWait<>(driver)
                .withTimeout(Duration.ofSeconds(20))
                .pollingEvery(Duration.ofMillis(500))
                .ignoring(NoSuchElementException.class);

        WebElement flash = wait.until(d -> {
            WebElement element = d.findElement(By.id("flash"));
            return element.isDisplayed() ? element : null;
        });

        return flash.getText().trim();
    }
}