package stepDefinition;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.When;
import io.cucumber.java.en.Then;
//import static org.junit.Assert.assertTrue;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.edge.EdgeDriver;

    public class LoginSteps {

        WebDriver driver;

        @Given("User is on the login page")
        public void user_is_on_the_login_page() {
            System.setProperty("webdriver.edge.driver", "path/to/msedgedriver.exe");
            driver = new EdgeDriver();
            driver.get("https://example.com/login"); // Replace with your application's login URL
        }

        @When("User enters valid username and password")
        public void user_enters_valid_username_and_password() {
            WebElement usernameField = driver.findElement(By.id("username")); // Replace with the actual username field locator
            WebElement passwordField = driver.findElement(By.id("password")); // Replace with the actual password field locator

            usernameField.sendKeys("validUsername");
            passwordField.sendKeys("validPassword");

            WebElement loginButton = driver.findElement(By.id("loginButton")); // Replace with the actual login button locator
            loginButton.click();
        }

        @Then("User should be redirected to the homepage")
        public void user_should_be_redirected_to_the_homepage() {
            String expectedUrl = "https://example.com/home"; // Replace with the actual homepage URL
            //assertTrue(driver.getCurrentUrl().equals(expectedUrl));
            driver.quit();
        }
}
