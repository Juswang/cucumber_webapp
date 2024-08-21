package testRunner;

import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(
        tags="@test or @SE3",
        features="src/main/java/features",
        glue={"stepDefinition","hooks","utilities"},
        plugin={"pretty","html:target/cucumber-html-report","json:cucumber.json",
        "com.aventstack.extentreports.cucumber.adapter.ExtentCucumberAdapter:"}
)
public class testRunner {
}
