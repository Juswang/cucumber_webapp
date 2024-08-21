package org.example;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;

import static stepDefinition.propertiesLoad.propertiesFile;
import static stepDefinition.webApplication.getScreenCoordinates;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {

    public static WebDriver driver;
    public static void main(String[] args) {

        getScreenCoordinates();
        }


    public static void clickElement(String css)
    {
        WebElement element = null;
        element=driver.findElement(By.cssSelector(css));
        element.click();

    }

    }
