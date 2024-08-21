package utilities;

import io.cucumber.core.backend.Options;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import org.apache.commons.io.FileUtils;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.*;
import org.openqa.selenium.winium.DesktopOptions;
import org.openqa.selenium.winium.WiniumDriver;
import org.openqa.selenium.winium.WiniumDriverService;
import org.openqa.selenium.winium.WiniumOptions;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;
import java.time.Duration;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

import static java.awt.SystemColor.desktop;
import static java.lang.String.valueOf;
import static stepDefinition.propertiesLoad.propertiesFile;
import static stepDefinition.propertiesLoad.uniqueEmailSubjectTitle;
//import static stepDefinition.propertiesLoad.uniqueEmailSubjectTitle;

public class desktopApplication {
    public static WiniumDriver desktopApplicationDriver = null;

    public static void main(String[] args) throws InterruptedException, IOException {
        DesiredCapabilities capabilities = new DesiredCapabilities();
        capabilities.setCapability("app", "C:\\Windows\\System32\\notepad.exe"); // Example for Notepad

        WiniumDriver driver = null;
        try {
            driver = new WiniumDriver(new URL("http://localhost:9999"), capabilities);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

    }
    @Given("^Open Desktop Application \"([^\"]*)\"$")
    public static void OpenDesktopApplication(String strDesktopApplication) {
        try {
            System.out.println("Step: openDesktopApplication");
            DesktopOptions desktop = new DesktopOptions();
            desktop.setApplicationPath(strDesktopApplication);

            Process process = Runtime.getRuntime().exec("TASKKILL /F /IM Winium.Desktop.Driver.exe");
            process.waitFor();
            if (process.isAlive()) {
                process.destroy();
            }

            File drivePath = new File("C:\\Program Files\\Winium.Desktop.Driver\\Winium.Desktop.Driver.exe"); //Set winium driver path
            WiniumDriverService service = new WiniumDriverService.Builder().usingDriverExecutable(drivePath).usingPort(9999).withVerbose(false).withSilent(false).buildDesktopService();
            service.start(); //Build and Start a Winium Driver service
            desktopApplicationDriver = new WiniumDriver(new URL("http://localhost:9999"), desktop);
            Thread.sleep(1000);
            report.reportPass("openDesktopApplication", strDesktopApplication, "", "StrScreenshot");
        } catch (Exception e) {
            System.out.println("FAILING Step: openDesktopApplication");
            report.reportFail("openDesktopApplication", strDesktopApplication, "", "StrScreenshot");
        }
    }
    @Given("^Close Application \"([^\"]*)\"$")
    public void closeApplication(String appName) throws InterruptedException, IOException {
        Process process = Runtime.getRuntime().exec("TASKKILL /F /IM "+appName);
        process.waitFor();
        if (process.isAlive()) {
            process.destroy();
        }
    }

    @And("^Close Desktop Application$")
    public void CloseDesktopApplication() {
        try {
            System.out.println("Step: closeDesktopApplication");
            desktopApplicationDriver.close();
            report.reportPass("closeDesktopApplication", "", "");
        } catch (Exception e) {
            System.out.println("FAILING Step: closeDesktopApplication");
            report.reportFail("closeDesktopApplication", "", "");
        }
    }

    @Then("^On Desktop Application, I click \"([^\"]*)\"$")
    public void onDesktopApplicationIClickElement(String StrElement) {
        try {
            System.out.println("Step: onDesktopApplicationIClickElement");
            WebElement element = extractingElement(StrElement);
            element.click();
            report.reportPass("onDesktopApplicationIClickElement", StrElement, "", "StrScreenshot");
        } catch (Exception e) {
            System.out.println("FAILING Step: onDesktopApplicationIClickElement");
            report.reportFail("onDesktopApplicationIClickElement", StrElement, "", "StrScreenshot");
        }
    }

    @Then("^On Desktop Application, I click \"([^\"]*)\" if \"([^\"]*)\" contains \"([^\"]*)\"$")
    public void onDesktopApplicationIClickElement(String StrElement, String StrCondition1, String StrCondition2) {
        try {
            System.out.println("Step: onDesktopApplicationIClickElementIfCondition");
            if (StrCondition1.contains(StrCondition2)) {
                WebElement element = extractingElement(StrElement);
                element.click();
                Thread.sleep(1000);
                report.reportPass("onDesktopApplicationIClickElementIfCondition", StrElement, "Condition exist", "StrScreenshot");
            }
            else {
                report.reportPass("onDesktopApplicationIClickElementIfCondition", StrElement, "Condition does not exist", "StrScreenshot");
            }
        } catch (Exception e) {
            System.out.println("FAILING Step: onDesktopApplicationIClickElementIfCondition");
            report.reportFail("onDesktopApplicationIClickElementIfCondition", StrElement, "", "StrScreenshot");
        }
    }

    @Then("^On Desktop Application, I doubleclick \"([^\"]*)\"$")
    public void onDesktopApplicationIDoubleClickElement(String StrElement) {
        try {
            System.out.println("Step: onDesktopApplicationIDoubleClickElement");
            Actions action = new Actions(desktopApplicationDriver);
            WebElement element = extractingElement(StrElement);
            action.doubleClick(element).perform();
            report.reportPass("onDesktopApplicationIDoubleClickElement", StrElement, "", "StrScreenshot");
        } catch (Exception e) {
            System.out.println("FAILING Step: onDesktopApplicationIDoubleClickElement");
            report.reportFail("onDesktopApplicationIDoubleClickElement", StrElement, "", "StrScreenshot");
        }
    }

    @Then("^On Desktop Application, I scroll \"([^\"]*)\"$")
    public void onDesktopApplicationIScrollElement(String StrElement) {
        try {
            System.out.println("Step: onDesktopApplicationIScrollElement");
            WebElement element = extractingElement(StrElement);
            ((JavascriptExecutor)desktopApplicationDriver).executeScript("arguments[0].scrollIntoView()",element);
            Thread.sleep(1000);
            report.reportPass("onDesktopApplicationIScrollElement", StrElement, "", "StrScreenshot");
        } catch (Exception e) {
            System.out.println("FAILING Step: onDesktopApplicationIScrollElement");
            report.reportFail("onDesktopApplicationIScrollElement", StrElement, "", "StrScreenshot");
        }
    }

    @Then("^On Desktop Application, I type \"([^\"]*)\" on \"([^\"]*)\" Textbox$")
    public void onDesktopApplicationITypeOnElement(String inputText, String StrElement) {
        try {
            System.out.println("Step: onDesktopApplicationITypeOnElement");
            switch (inputText) {
                case "Unique Email Subject Title":
                    WebElement element = extractingElement(StrElement);
                    element.sendKeys("Test Email_" + uniqueEmailSubjectTitle);
                    break;
                default:
                    element = extractingElement(StrElement);
                    element.sendKeys(inputText);
                    break;
            }
            report.reportPass("onDesktopApplicationITypeOnElement", StrElement + ": " + inputText.replaceAll("Genome12","xxx").replaceAll("passw0rd","yyy"), "", "StrScreenshot");
        } catch (Exception e) {
            System.out.println("FAILING Step: onDesktopApplicationITypeOnElement");
            report.reportFail("onDesktopApplicationITypeOnElement", StrElement + ": " + inputText.replaceAll("Genome12","xxx").replaceAll("passw0rd","zzz"), "", "StrScreenshot");
        }
    }

    @Then("^On Desktop Application, I type \"([^\"]*)\" on \"([^\"]*)\" Textbox if \"([^\"]*)\" contains \"([^\"]*)\"$")
    public void onDesktopApplicationITypeOnElement(String inputText, String StrElement, String StrCondition1, String StrCondition2) {
        try {
            System.out.println("Step: onDesktopApplicationITypeOnElement");
            if (StrCondition1.contains(StrCondition2)) {
                switch (inputText) {
                    case "Unique Email Subject Title":
                        WebElement element = extractingElement(StrElement);
                        element.sendKeys("Test Email_" + uniqueEmailSubjectTitle);
                        break;
                    default:
                        element = extractingElement(StrElement);
                        element.sendKeys(inputText);
                        break;
                }
                report.reportPass("onDesktopApplicationITypeOnElement", StrElement + ": " + inputText.replaceAll("91235129", "aaa").replaceAll("passw0rd", "bbb"), "Condition exist", "StrScreenshot");
            }
            else {
                report.reportPass("onDesktopApplicationITypeOnElement", StrElement + ": " + inputText.replaceAll("91235129", "ccc").replaceAll("passw0rd", "ddd"), "Condition does not exist", "StrScreenshot");
            }
        } catch (Exception e) {
            System.out.println("FAILING Step: onDesktopApplicationITypeOnElement");
            report.reportFail("onDesktopApplicationITypeOnElement", StrElement + ": " + inputText.replaceAll("91235129","eee").replaceAll("passw0rd","fff"), "", "StrScreenshot");
        }
    }

    @Then("^On Desktop Application, I key \"([^\"]*)\" on \"([^\"]*)\"$")
    public void onDesktopApplicationIKeyOnElement(String StrKey, String StrElement) {
        try {
            System.out.println("Step: onDesktopApplicationIKeyOnElement");
            WebElement element = extractingElement(StrElement);
            switch (StrKey) {
                case "ENTER":
                    element.sendKeys(Keys.RETURN);
                    report.reportPass("onDesktopApplicationIKeyOnElement", StrElement + ": " + StrKey, "", "StrScreenshot");
                    break;
                case "CLEAR":
                    element.clear();
                    report.reportPass("onDesktopApplicationIKeyOnElement", StrElement + ": " + StrKey, "", "StrScreenshot");
                    break;
                case "CTRL+A":
                    element.sendKeys(Keys.CONTROL,"a");
                    report.reportPass("onDesktopApplicationIKeyOnElement", StrElement + ": " + StrKey, "", "StrScreenshot");
                    break;
                case "RIGHT-CLICK":
                    Actions action = new Actions(desktopApplicationDriver);
                    action.contextClick(element).perform();
                    break;
                default:
                    System.out.println("No matching Key");
                    break;
            }
        } catch (Exception e) {
            System.out.println("FAILING Step: onDesktopApplicationIKeyOnElement");
            report.reportFail("onDesktopApplicationIKeyOnElement", StrElement + ": " + StrKey, "", "StrScreenshot");
        }
    }
    //robot.keypress() is used because there is no element that we are attributing the input to
    @Then("^On Desktop Application, I key \"([^\"]*)\"$")
    public void onDesktopApplicationIKeyOnElement(String StrKey) {
        try {
            System.out.println("Step: onDesktopApplicationIKey");
            Robot robot = new Robot();
            switch (StrKey) {
                case "ENTER":
                    robot.keyPress(KeyEvent.VK_ENTER);
                    robot.keyRelease(KeyEvent.VK_ENTER);
                    report.reportPass("onDesktopApplicationIKey", StrKey, "", "StrScreenshot");
                    break;
                case "UP":
                    robot.keyPress(KeyEvent.VK_UP);
                    robot.keyRelease(KeyEvent.VK_UP);
                    report.reportPass("onDesktopApplicationIKey", StrKey, "", "StrScreenshot");
                    break;
                case "DOWN":
                    robot.keyPress(KeyEvent.VK_DOWN);
                    robot.keyRelease(KeyEvent.VK_DOWN);
                    report.reportPass("onDesktopApplicationIKey", StrKey, "", "StrScreenshot");
                    break;
                case "RIGHT":
                    robot.keyPress(KeyEvent.VK_RIGHT);
                    robot.keyRelease(KeyEvent.VK_RIGHT);
                    report.reportPass("onDesktopApplicationIKey", StrKey, "", "StrScreenshot");
                    break;
                case "HOME":
                    robot.keyPress(KeyEvent.VK_HOME);
                    robot.keyRelease(KeyEvent.VK_HOME);
                    report.reportPass("onDesktopApplicationIKey", StrKey, "", "StrScreenshot");
                    break;
                case "END":
                    robot.keyPress(KeyEvent.VK_END);
                    robot.keyRelease(KeyEvent.VK_END);
                    report.reportPass("onDesktopApplicationIKey", StrKey, "", "StrScreenshot");
                    break;
                case "ALT+F4":
                    robot.keyPress(KeyEvent.VK_ALT);
                    robot.keyPress(KeyEvent.VK_F4);
                    robot.keyRelease(KeyEvent.VK_ALT);
                    robot.keyRelease(KeyEvent.VK_F4);
                    report.reportPass("onDesktopApplicationIKey", StrKey, "", "StrScreenshot");
                    break;
                case "SHIFT+LEFT":
                    robot.keyPress(KeyEvent.VK_SHIFT);
                    robot.keyPress(KeyEvent.VK_LEFT);
                    robot.keyRelease(KeyEvent.VK_SHIFT);
                    robot.keyRelease(KeyEvent.VK_LEFT);
                    report.reportPass("onDesktopApplicationIKey", StrKey, "", "StrScreenshot");
                    break;
                case "CTRL+A":
                    robot.keyPress(KeyEvent.VK_CONTROL);
                    robot.keyPress(KeyEvent.VK_A);
                    robot.keyRelease(KeyEvent.VK_CONTROL);
                    robot.keyRelease(KeyEvent.VK_A);
                    report.reportPass("onDesktopApplicationIKey", StrKey, "", "StrScreenshot");
                    break;
                case "CTRL+N": //Create new mail shortcut
                    robot.keyPress(KeyEvent.VK_CONTROL);
                    robot.keyPress(KeyEvent.VK_N);
                    robot.keyRelease(KeyEvent.VK_CONTROL);
                    robot.keyRelease(KeyEvent.VK_N);
                    report.reportPass("onDesktopApplicationIKey", StrKey, "", "StrScreenshot");
                    break;
                case "CRTL+DOWN":
                    robot.keyPress(KeyEvent.VK_CONTROL);
                    robot.keyPress(KeyEvent.VK_DOWN);
                    robot.keyRelease(KeyEvent.VK_CONTROL);
                    robot.keyRelease(KeyEvent.VK_DOWN);
                    report.reportPass("onDesktopApplicationIKey", StrKey, "", "StrScreenshot");
                    break;
                case "CTRL+F":
                    robot.keyPress(KeyEvent.VK_CONTROL);
                    robot.keyPress(KeyEvent.VK_F);
                    robot.keyRelease(KeyEvent.VK_CONTROL);
                    robot.keyRelease(KeyEvent.VK_F);
                    report.reportPass("onDesktopApplicationIKey", StrKey, "", "StrScreenshot");
                    break;
                case "CRTL+K":
                    robot.keyPress(KeyEvent.VK_CONTROL);
                    robot.keyPress(KeyEvent.VK_K);
                    robot.keyRelease(KeyEvent.VK_CONTROL);
                    robot.keyRelease(KeyEvent.VK_K);
                    report.reportPass("onDesktopApplicationIKey", StrKey, "", "StrScreenshot");
                    break;
                case "CRTL+S":
                    robot.keyPress(KeyEvent.VK_CONTROL);
                    robot.keyPress(KeyEvent.VK_S);
                    robot.keyRelease(KeyEvent.VK_CONTROL);
                    robot.keyRelease(KeyEvent.VK_S);
                    report.reportPass("onDesktopApplicationIKey", StrKey, "", "StrScreenshot");
                    break;
                case "CRTL+R":
                    robot.keyPress(KeyEvent.VK_CONTROL);
                    robot.keyPress(KeyEvent.VK_R);
                    robot.keyRelease(KeyEvent.VK_CONTROL);
                    robot.keyRelease(KeyEvent.VK_R);
                    report.reportPass("onDesktopApplicationIKey", StrKey, "", "StrScreenshot");
                    break;
                case "CTRL+SHIFT+R":
                    robot.keyPress(KeyEvent.VK_CONTROL);
                    robot.keyPress(KeyEvent.VK_SHIFT);
                    robot.keyPress(KeyEvent.VK_R);
                    robot.keyRelease(KeyEvent.VK_CONTROL);
                    robot.keyRelease(KeyEvent.VK_SHIFT);
                    robot.keyRelease(KeyEvent.VK_R);
                    report.reportPass("onDesktopApplicationIKey", StrKey, "", "StrScreenshot");
                    break;
                case "TAB":
                    robot.keyPress(KeyEvent.VK_TAB);
                    robot.keyRelease(KeyEvent.VK_TAB);
                    report.reportPass("onDesktopApplicationIKey", StrKey, "", "StrScreenshot");
                    break;
                case "BACKSPACE":
                    robot.keyPress(KeyEvent.VK_BACK_SPACE);
                    robot.keyRelease(KeyEvent.VK_BACK_SPACE);
                    report.reportPass("onDesktopApplicationIKey", StrKey, "", "StrScreenshot");
                    break;
                case "SPACE":
                    robot.keyPress(KeyEvent.VK_SPACE);
                    robot.keyRelease(KeyEvent.VK_SPACE);
                    report.reportPass("onDesktopApplicationIKey", StrKey, "", "StrScreenshot");
                    break;
                case "ALT+DOWN":
                    robot.keyPress(KeyEvent.VK_ALT);
                    robot.keyPress(KeyEvent.VK_DOWN);
                    robot.keyRelease(KeyEvent.VK_ALT);
                    robot.keyRelease(KeyEvent.VK_DOWN);
                    report.reportPass("onDesktopApplicationIKey", StrKey, "", "StrScreenshot");
                    break;
                case "ALT+ALT+DOWN":
                    robot.keyPress(KeyEvent.VK_ALT);
                    robot.keyRelease(KeyEvent.VK_ALT);
                    robot.keyPress(KeyEvent.VK_ALT);
                    robot.keyPress(KeyEvent.VK_DOWN);
                    robot.keyRelease(KeyEvent.VK_ALT);
                    robot.keyRelease(KeyEvent.VK_DOWN);
                    report.reportPass("onDesktopApplicationIKey", StrKey, "", "StrScreenshot");
                    break;
                case "HOME+TAB+END":
                    robot.keyPress(KeyEvent.VK_HOME);
                    robot.keyRelease(KeyEvent.VK_HOME);
                    robot.keyPress(KeyEvent.VK_TAB);
                    robot.keyRelease(KeyEvent.VK_TAB);
                    robot.keyPress(KeyEvent.VK_END);
                    robot.keyRelease(KeyEvent.VK_END);
                    report.reportPass("onDesktopApplicationIKey", StrKey, "", "StrScreenshot");
                    break;
                case "DA_NewEmail_Security_Dropdown":
                    robot.keyPress(KeyEvent.VK_ALT);
                    robot.keyPress(KeyEvent.VK_H);
                    robot.keyRelease(KeyEvent.VK_ALT);
                    robot.keyRelease(KeyEvent.VK_H);
                    robot.keyPress(KeyEvent.VK_CAPS_LOCK);
                    robot.keyRelease(KeyEvent.VK_CAPS_LOCK);
                    robot.keyPress(KeyEvent.VK_Y);
                    robot.keyPress(KeyEvent.VK_2);
                    robot.keyRelease(KeyEvent.VK_Y);
                    robot.keyRelease(KeyEvent.VK_2);
                    robot.keyPress(KeyEvent.VK_CAPS_LOCK);
                    robot.keyRelease(KeyEvent.VK_CAPS_LOCK);
                    report.reportPass("onDesktopApplicationIKey", StrKey, "", "StrScreenshot");
                    break;
                case "DA_EmailReceived_Message_Move":
                    robot.keyPress(KeyEvent.VK_ALT);
                    robot.keyPress(KeyEvent.VK_H);
                    robot.keyRelease(KeyEvent.VK_ALT);
                    robot.keyRelease(KeyEvent.VK_H);
                    robot.keyPress(KeyEvent.VK_CAPS_LOCK);
                    robot.keyRelease(KeyEvent.VK_CAPS_LOCK);
                    robot.keyPress(KeyEvent.VK_Y);
                    robot.keyPress(KeyEvent.VK_3);
                    robot.keyRelease(KeyEvent.VK_Y);
                    robot.keyRelease(KeyEvent.VK_3);
                    robot.keyPress(KeyEvent.VK_CAPS_LOCK);
                    robot.keyRelease(KeyEvent.VK_CAPS_LOCK);
                    report.reportPass("onDesktopApplicationIKey", StrKey, "", "StrScreenshot");
                    break;
                case "DA_NewEmail_Options_Permission":
                    robot.keyPress(KeyEvent.VK_ALT);
                    robot.keyPress(KeyEvent.VK_P);
                    robot.keyRelease(KeyEvent.VK_ALT);
                    robot.keyRelease(KeyEvent.VK_P);
                    robot.keyPress(KeyEvent.VK_CAPS_LOCK);
                    robot.keyRelease(KeyEvent.VK_CAPS_LOCK);
                    robot.keyPress(KeyEvent.VK_P);
                    robot.keyPress(KeyEvent.VK_M);
                    robot.keyRelease(KeyEvent.VK_P);
                    robot.keyRelease(KeyEvent.VK_M);
                    robot.keyPress(KeyEvent.VK_CAPS_LOCK);
                    robot.keyRelease(KeyEvent.VK_CAPS_LOCK);
                    report.reportPass("onDesktopApplicationIKey", StrKey, "", "StrScreenshot");
                    break;
                case "DA_EmailReceived_Message_Delete":
                    robot.keyPress(KeyEvent.VK_ALT);
                    robot.keyPress(KeyEvent.VK_H);
                    robot.keyRelease(KeyEvent.VK_ALT);
                    robot.keyRelease(KeyEvent.VK_H);
                    robot.keyPress(KeyEvent.VK_CAPS_LOCK);
                    robot.keyRelease(KeyEvent.VK_CAPS_LOCK);
                    robot.keyPress(KeyEvent.VK_D);
                    robot.keyRelease(KeyEvent.VK_D);
                    robot.keyPress(KeyEvent.VK_CAPS_LOCK);
                    robot.keyRelease(KeyEvent.VK_CAPS_LOCK);
                    report.reportPass("onDesktopApplicationIKey", StrKey, "", "StrScreenshot");
                    break;
                case "DA_searchBox_CloseSearch_Button":
                    robot.keyPress(KeyEvent.VK_CAPS_LOCK);
                    robot.keyRelease(KeyEvent.VK_CAPS_LOCK);
                    robot.keyPress(KeyEvent.VK_ALT);
                    robot.keyPress(KeyEvent.VK_J);
                    robot.keyPress(KeyEvent.VK_S);
                    robot.keyPress(KeyEvent.VK_2);
                    robot.keyRelease(KeyEvent.VK_ALT);
                    robot.keyRelease(KeyEvent.VK_J);
                    robot.keyRelease(KeyEvent.VK_S);
                    robot.keyRelease(KeyEvent.VK_2);
                    robot.keyPress(KeyEvent.VK_C);
                    robot.keyPress(KeyEvent.VK_S);
                    robot.keyRelease(KeyEvent.VK_C);
                    robot.keyRelease(KeyEvent.VK_S);
                    robot.keyPress(KeyEvent.VK_CAPS_LOCK);
                    robot.keyRelease(KeyEvent.VK_CAPS_LOCK);
                    report.reportPass("onDesktopApplicationIKey", StrKey, "", "StrScreenshot");
                    break;
                case "BSE_TABLE_SELECTION":
                    robot.keyPress(KeyEvent.VK_TAB);
                    robot.keyRelease(KeyEvent.VK_TAB);
                    robot.keyPress(KeyEvent.VK_TAB);
                    robot.keyPress(KeyEvent.VK_ENTER);
                    robot.keyRelease(KeyEvent.VK_TAB);
                    robot.keyRelease(KeyEvent.VK_ENTER);
                    report.reportPass("onDesktopApplicationIKey", StrKey, "", "StrScreenshot");
                    break;
                default:
                    for (char i: StrKey.toCharArray()) {
                        int keyCode =KeyEvent.getExtendedKeyCodeForChar(i);
                        if(keyCode==523)
                        {
                            robot.keyPress(KeyEvent.VK_SHIFT);
                            robot.keyPress(KeyEvent.VK_MINUS);
                            robot.keyRelease(KeyEvent.VK_SHIFT);
                            robot.keyRelease(KeyEvent.VK_MINUS);
                            Thread.sleep(100);
                        }
                        else {
                            robot.keyPress(keyCode);
                            robot.keyRelease(keyCode);
                            Thread.sleep(100);
                        }
                    }
                    report.reportPass("onDesktopApplicationIKey", StrKey, "", "StrScreenshot");
                    break;
            }
            Thread.sleep(1000);
        } catch (Exception e) {
            System.out.println("FAILING Step: onDesktopApplicationIKey");
            report.reportFail("onDesktopApplicationIKey", StrKey, "", "StrScreenshot");
        }

    }

    @Then("^On Desktop Application, I key \"([^\"]*)\" if \"([^\"]*)\" contains \"([^\"]*)\"$")
    public void onDesktopApplicationIKeyElement(String StrKey, String StrCondition1, String StrCondition2) {
        try {
            System.out.println("Step: onDesktopApplicationIKeyElementIfCondition");
            Robot robot = new Robot();
            if (StrCondition1.contains(StrCondition2)) {
                switch (StrKey) {
                    case "ALT+F4":
                        robot.keyPress(KeyEvent.VK_ALT);
                        robot.keyPress(KeyEvent.VK_F4);
                        robot.keyRelease(KeyEvent.VK_ALT);
                        robot.keyRelease(KeyEvent.VK_F4);
                        break;
                    case "DOWN":
                        robot.keyPress(KeyEvent.VK_DOWN);
                        robot.keyRelease(KeyEvent.VK_DOWN);
                        break;
                    case "UP":
                        robot.keyPress(KeyEvent.VK_UP);
                        robot.keyRelease(KeyEvent.VK_UP);
                        break;
                    default:
                        break;
                }
                report.reportPass("onDesktopApplicationIKeyElementIfCondition", StrKey, "Condition exist", "StrScreenshot");
            }
            else {
                report.reportPass("onDesktopApplicationIKeyElementIfCondition", StrKey, "Condition does not exist", "StrScreenshot");
            }
        } catch (Exception e) {
            System.out.println("FAILING Step: onDesktopApplicationIKeyElementIfCondition");
            report.reportFail("onDesktopApplicationIKeyElementIfCondition", StrKey, "", "StrScreenshot");
        }
    }

    @Then("^On Desktop Application, I Index Search \"([^\"]*)\" by \"([^\"]*)\"$")
    public void onDesktopApplicationIIndexSearch(String StrKey, int interval) {
        try {
            System.out.println("Step: onDesktopApplicationIIndexSearch");
            Robot robot = new Robot();
            for (char i: StrKey.toCharArray()) {
                int keyCode = KeyEvent.getExtendedKeyCodeForChar(i);
                robot.keyPress(keyCode);
                robot.keyRelease(keyCode);
                Thread.sleep(interval);
            }
            report.reportPass("onDesktopApplicationIIndexSearch", StrKey, "", "StrScreenshot");
        } catch (Exception e) {
            System.out.println("FAILING Step: onDesktopApplicationIIndexSearch");
            report.reportFail("onDesktopApplicationIIndexSearch", StrKey, "", "StrScreenshot");
        }
    }

    @Then("^On Desktop Application, I open file \"([^\"]*)\"$")
    public void onDesktopApplicationIOpenFile(String StrPath) {
        try {
            System.out.println("Step: onDesktopApplicationIOpenFile");
            File file = new File(StrPath);
            Desktop desktop = Desktop.getDesktop();
            if(file.exists()) {
                desktop.open(file);
            }
            report.reportPass("onDesktopApplicationIOpenFile", StrPath, "", "StrScreenshot");
        } catch (Exception e) {
            System.out.println("FAILING Step: onDesktopApplicationIOpenFile");
            report.reportFail("onDesktopApplicationIOpenFile", StrPath, "", "StrScreenshot");
        }
    }

    @Then("^On Desktop Application, I verify element$")
    public void onDesktopApplicationIVerifyElement(DataTable dt) {
        List<Map<String, String>> dtList = dt.asMaps(String.class, String.class);
        try {
            System.out.println("Step: onDesktopApplicationIVerifyElement");
            for (int i = 0; i < dtList.size(); i++) {
                String verificationMethod = dtList.get(i).get("method");
                String elementNote = dtList.get(i).get("element");
                switch (verificationMethod) {
                    case "verifyText":
                        WebElement element = extractingElement(dtList.get(i).get("element"));
                        String actualText = element.getText().replaceAll("\r\n","\n").replaceAll("\r","\n").replaceAll("\t","   ");
                        String expectedText = dtList.get(i).get("expected").replaceAll("//", "");

                        if (actualText.equals(expectedText)) {
                            System.out.println("Step: onDesktopApplicationIVerifyElement --- verifyText --- PASSED --- " + elementNote);
                            report.reportPass("onDesktopApplicationIVerifyElement \n--- verifyText", dtList.get(i).get("element"), "ACTUAL  : [" + actualText + "] \nEXPECTED: [" + expectedText + "]", "StrScreenshot");
                        } else {
                            System.out.println("Step: onDesktopApplicationIVerifyElement --- verifyText --- FAILED --- " + elementNote);
                            report.reportFail("onDesktopApplicationIVerifyElement \n--- verifyText", dtList.get(i).get("element"), "ACTUAL  : [" + actualText + "] \nEXPECTED: [" + expectedText + "]", "StrScreenshot");
                        }
                        break;
                    case "verifyTextContains":
                        element = extractingElement(dtList.get(i).get("element"));
                        actualText = element.getText().replaceAll("\r\n","\n").replaceAll("\r","\n");
                        expectedText = dtList.get(i).get("expected").replaceAll("//", "");
                        if (actualText.contains(expectedText)) {
                            System.out.println("Step: onDesktopApplicationIVerifyElement --- verifyTextContains --- PASSED --- " + elementNote);
                            report.reportPass("onDesktopApplicationIVerifyElement \n--- verifyTextContains", dtList.get(i).get("element"), "ACTUAL  : [" + actualText + "] \nEXPECTED: [" + expectedText + "]", "StrScreenshot");
                        } else {
                            System.out.println("Step: onDesktopApplicationIVerifyElement --- verifyTextContains --- FAILED --- " + elementNote);
                            report.reportFail("onDesktopApplicationIVerifyElement \n--- verifyTextContains", dtList.get(i).get("element"), "ACTUAL  : [" + actualText + "] \nEXPECTED: [" + expectedText + "]", "StrScreenshot");
                        }
                        break;
                    case "verifyName":
                        element = extractingElement(dtList.get(i).get("element"));
                        actualText = (element.getAttribute("Name")).replaceAll("\n\r","\n").replaceAll("\r\n","\n");;
                        expectedText = dtList.get(i).get("expected").replaceAll("//", "");
                        if (actualText.equals(expectedText)) {
                            System.out.println("Step: onDesktopApplicationIVerifyElement --- verifyName --- PASSED --- " + elementNote);
                            report.reportPass("onDesktopApplicationIVerifyElement \n--- verifyName", dtList.get(i).get("element"), "ACTUAL  : [" + actualText + "] \nEXPECTED: [" + expectedText + "]", "StrScreenshot");
                        } else {
                            System.out.println("Step: onDesktopApplicationIVerifyElement --- verifyName --- FAILED --- " + elementNote);
                            report.reportFail("onDesktopApplicationIVerifyElement \n--- verifyName", dtList.get(i).get("element"), "ACTUAL  : [" + actualText + "] \nEXPECTED: [" + expectedText + "]", "StrScreenshot");
                        }
                        break;
                    case "verifyNameContains":
                        element = extractingElement(dtList.get(i).get("element"));
                        actualText = (element.getAttribute("Name")).replaceAll("\r\n","\n");;
                        expectedText = dtList.get(i).get("expected");
                        if (actualText.contains(expectedText)) {
                            System.out.println("Step: onDesktopApplicationIVerifyElement --- verifyNameContains --- PASSED --- " + elementNote);
                            report.reportPass("onDesktopApplicationIVerifyElement \n--- verifyNameContains", dtList.get(i).get("element"), "ACTUAL  : [" + actualText + "] \nEXPECTED: [" + expectedText + "]", "StrScreenshot");
                        } else {
                            System.out.println("Step: onDesktopApplicationIVerifyElement --- verifyNameContains --- FAILED --- " + elementNote);
                            report.reportFail("onDesktopApplicationIVerifyElement \n--- verifyNameContains", dtList.get(i).get("element"), "ACTUAL  : [" + actualText + "] \nEXPECTED: [" + expectedText + "]", "StrScreenshot");
                        }
                        break;
                    case "verifyNameDoesNotContain":
                        element = extractingElement(dtList.get(i).get("element"));
                        actualText = (element.getAttribute("Name")).replaceAll("\r\n","\n");;
                        expectedText = dtList.get(i).get("expected");
                        if (!actualText.contains(expectedText)) {
                            System.out.println("Step: onDesktopApplicationIVerifyElement --- verifyNameDoesNotContain --- PASSED --- " + elementNote);
                            report.reportPass("onDesktopApplicationIVerifyElement \n--- verifyNameDoesNotContain", dtList.get(i).get("element"), "ACTUAL  : [" + actualText + "] \nEXPECTED: [" + expectedText + "]", "StrScreenshot");
                        } else {
                            System.out.println("Step: onDesktopApplicationIVerifyElement --- verifyNameDoesNotContain --- FAILED --- " + elementNote);
                            report.reportFail("onDesktopApplicationIVerifyElement \n--- verifyNameDoesNotContain", dtList.get(i).get("element"), "ACTUAL  : [" + actualText + "] \nEXPECTED: [" + expectedText + "]", "StrScreenshot");
                        }
                        break;
                    case "verifyPresence":
                        String presenceOfElement = "";
                        element = extractingElement(dtList.get(i).get("element"));
                        if (element == null) {
                            presenceOfElement = "False";
                        } else {
                            presenceOfElement = "True";
                        }
                        expectedText = dtList.get(i).get("expected");
                        if (presenceOfElement.equals(expectedText)) {
                            System.out.println("Step: onDesktopApplicationIVerifyElement --- verifyPresence --- PASSED --- " + elementNote);
                            report.reportPass("onDesktopApplicationIVerifyElement \n--- verifyPresence", dtList.get(i).get("element"), "ACTUAL  : [" + presenceOfElement + "] \nEXPECTED: [" + expectedText + "]", "StrScreenshot");
                        } else {
                            System.out.println("Step: onDesktopApplicationIVerifyElement --- verifyPresence --- FAILED --- " + elementNote);
                            report.reportFail("onDesktopApplicationIVerifyElement \n--- verifyPresence", dtList.get(i).get("element"), "ACTUAL  : [" + presenceOfElement + "] \nEXPECTED: [" + expectedText + "]", "StrScreenshot");
                        }
                        break;
                }
            }
        } catch (Exception e) {
            System.out.println("FAILING Step: onDesktopApplicationIVerifyElement");
            report.reportFail("onDesktopApplicationIVerifyElement", "", "");
        }
    }

    public void onDesktopApplicationIVerifyElement(String StrElement, String verificationMethod, String StrExpected) {
        try {
            System.out.println("Step: onDesktopApplicationIVerifyElement");
            switch (verificationMethod) {
                case "verifyText":
                    WebElement element = extractingElement(StrElement);
                    String actualText = element.getText().replaceAll("\r\n","\n").replaceAll("\r","\n");
                    String expectedText = StrExpected;
                    if (actualText.equals(expectedText)) {
                        System.out.println("Step: onDesktopApplicationIVerifyElement --- verifyText --- PASSED --- " + StrElement);
                        report.reportPass("onDesktopApplicationIVerifyElement \n--- verifyText", StrElement, "ACTUAL  : [" + actualText + "] \nEXPECTED: [" + expectedText + "]", "StrScreenshot");
                    } else {
                        System.out.println("Step: onDesktopApplicationIVerifyElement --- verifyText --- FAILED --- " + StrElement);
                        report.reportFail("onDesktopApplicationIVerifyElement \n--- verifyText", StrElement, "ACTUAL  : [" + actualText + "] \nEXPECTED: [" + expectedText + "]", "StrScreenshot");
                    }
                    break;
                case "verifyName":
                    element = extractingElement(StrElement);
                    actualText = (element.getAttribute("Name")).replaceAll("\r\n","\n");;
                    expectedText = StrExpected;
                    if (actualText.equals(expectedText)) {
                        System.out.println("Step: onDesktopApplicationIVerifyElement --- verifyName --- PASSED --- " + StrElement);
                        report.reportPass("onDesktopApplicationIVerifyElement \n--- verifyName", StrElement, "ACTUAL  : [" + actualText + "] \nEXPECTED: [" + expectedText + "]", "StrScreenshot");
                    } else {
                        System.out.println("Step: onDesktopApplicationIVerifyElement --- verifyName --- FAILED --- " + StrElement);
                        report.reportFail("onDesktopApplicationIVerifyElement \n--- verifyName", StrElement, "ACTUAL  : [" + actualText + "] \nEXPECTED: [" + expectedText + "]", "StrScreenshot");
                    }
                    break;
                case "verifyPresence":
                    String presenceOfElement = "";
                    element = extractingElement(StrElement);
                    if (element == null) {
                        presenceOfElement = "False";
                    } else {
                        presenceOfElement = "True";
                    }
                    expectedText = StrExpected;
                    if (presenceOfElement.equals(expectedText)) {
                        System.out.println("Step: onDesktopApplicationIVerifyElement --- verifyPresence --- PASSED --- " + StrElement);
                        report.reportPass("onDesktopApplicationIVerifyElement \n--- verifyPresence", StrElement, "ACTUAL  : [" + presenceOfElement + "] \nEXPECTED: [" + expectedText + "]", "StrScreenshot");
                    } else {
                        System.out.println("Step: onDesktopApplicationIVerifyElement --- verifyPresence --- FAILED --- " + StrElement);
                        report.reportFail("onDesktopApplicationIVerifyElement \n--- verifyPresence", StrElement, "ACTUAL  : [" + presenceOfElement + "] \nEXPECTED: [" + expectedText + "]", "StrScreenshot");
                    }
                    break;
            }
        } catch (Exception e) {
            System.out.println("FAILING Step: onDesktopApplicationIVerifyElement");
            report.reportFail("onDesktopApplicationIVerifyElement", "", "");
        }
    }
    //This method is mean to identify the different elements that are detected by uispy
//Returns WebElement
    public static WebElement extractingElement(String StrElement) {
        WebElement element = null;
        try {
            System.out.println("Step: extractingElement --- " + StrElement);
            String method = propertiesFile.getProperty(StrElement).split("(?<!\\\\),")[0];
            switch (method) {
                case "name-name":
                    String identity1 = propertiesFile.getProperty(StrElement).split(",")[1];
                    String identity2 = propertiesFile.getProperty(StrElement).split(",")[2];
                    element = desktopApplicationDriver.findElement(By.name(identity1)).findElement(By.name(identity2));
                    break;
                case "name-id":
                    identity1 = propertiesFile.getProperty(StrElement).split(",")[1];
                    identity2 = propertiesFile.getProperty(StrElement).split(",")[2];
                    element = desktopApplicationDriver.findElement(By.name(identity1)).findElement(By.id(identity2));
                    break;
                case "name-className":
                    identity1 = propertiesFile.getProperty(StrElement).split(",")[1];
                    identity2 = propertiesFile.getProperty(StrElement).split(",")[2];
                    element = desktopApplicationDriver.findElement(By.name(identity1)).findElement(By.className(identity2));
                    break;
                case "className-name":
                    identity1 = propertiesFile.getProperty(StrElement).split(",")[1];
                    identity2 = propertiesFile.getProperty(StrElement).split(",")[2];
                    element = desktopApplicationDriver.findElement(By.className(identity1)).findElement(By.name(identity2));
                    break;
                case "className-id":
                    identity1 = propertiesFile.getProperty(StrElement).split(",")[1];
                    identity2 = propertiesFile.getProperty(StrElement).split(",")[2];
                    element = desktopApplicationDriver.findElement(By.className(identity1)).findElement(By.id(identity2));
                    break;
                case "id-className":
                    identity1 = propertiesFile.getProperty(StrElement).split(",")[1];
                    identity2 = propertiesFile.getProperty(StrElement).split(",")[2];
                    element = desktopApplicationDriver.findElement(By.id(identity1)).findElement(By.className(identity2));
                    break;
                case "id-name":
                    identity1 = propertiesFile.getProperty(StrElement).split(",")[1];
                    identity2 = propertiesFile.getProperty(StrElement).split(",")[2];
                    element = desktopApplicationDriver.findElement(By.id(identity1)).findElement(By.name(identity2));
                    break;
                case "id-id":
                    identity1 = propertiesFile.getProperty(StrElement).split(",")[1];
                    identity2 = propertiesFile.getProperty(StrElement).split(",")[2];
                    element = desktopApplicationDriver.findElement(By.id(identity1)).findElement(By.id(identity2));
                    break;
                case "xpath":
                    identity1 = propertiesFile.getProperty(StrElement).split("xpath,")[1];
                    element = desktopApplicationDriver.findElement(By.xpath(identity1));
                    break;
                case "className-xpath":
                    identity1 = propertiesFile.getProperty(StrElement).split(",",3)[1];
                    identity2 = propertiesFile.getProperty(StrElement).split(",",3)[2];
                    element = desktopApplicationDriver.findElement(By.className(identity1)).findElement(By.xpath(identity2));
                    break;
                case "name-className-name":
                    identity1 = propertiesFile.getProperty(StrElement).split(",")[1];
                    identity2 = propertiesFile.getProperty(StrElement).split(",")[2];
                    String identity3 = propertiesFile.getProperty(StrElement).split(",")[3];
                    element = desktopApplicationDriver.findElement(By.name(identity1)).findElement(By.className(identity2)).findElement(By.name(identity3));
                    break;
                case "name-name-name":
                    identity1 = propertiesFile.getProperty(StrElement).split(",")[1];
                    identity2 = propertiesFile.getProperty(StrElement).split(",")[2];
                    identity3 = propertiesFile.getProperty(StrElement).split(",")[3];
                    element = desktopApplicationDriver.findElement(By.name(identity1)).findElement(By.name(identity2)).findElement(By.name(identity3));
                    break;
                case "className-name-name":
                    identity1 = propertiesFile.getProperty(StrElement).split(",")[1];
                    identity2 = propertiesFile.getProperty(StrElement).split(",")[2];
                    identity3 = propertiesFile.getProperty(StrElement).split(",")[3];
                    element = desktopApplicationDriver.findElement(By.className(identity1)).findElement(By.name(identity2)).findElement(By.name(identity3));
                    break;
                case "className-id-className":
                    identity1 = propertiesFile.getProperty(StrElement).split(",")[1];
                    identity2 = propertiesFile.getProperty(StrElement).split(",")[2];
                    identity3 = propertiesFile.getProperty(StrElement).split(",")[3];
                    element = desktopApplicationDriver.findElement(By.className(identity1)).findElement(By.id(identity2)).findElement(By.className(identity3));
                    break;
                default:
                    System.out.println("FAILING Step: extractingElement --- No matching method");
                    break;
            }
        } catch (Exception e) {
            System.out.println("Step: extractingElement --- NoSuchElementException --- " + StrElement);
        }
        System.out.println(element.toString());
        return element;
    }

    @Then("^On Desktop Application, I unzip \"([^\"]*)\" to \"([^\"]*)\"$")
    public void onDesktopApplicationIUnzipElement(String StrSource, String strDestination) {
        try {
            System.out.println("Step: onDesktopApplicationIUnzipElement");
            String source = StrSource;
            File destDir = new File(strDestination);
            if (!destDir.exists()) {
                destDir.mkdir();
            }
            ZipInputStream zipIn = new ZipInputStream(new FileInputStream(source));
            ZipEntry zipEntry = zipIn.getNextEntry();
            while (zipEntry != null) {
                String filePath = destDir + File.separator + zipEntry.getName();
                if (!zipEntry.isDirectory()) {
                    extractFile(zipIn, filePath);
                } else {
                    File dir = new File(filePath);
                    dir.mkdir();
                }
                zipIn.closeEntry();
                zipEntry = zipIn.getNextEntry();
            }
            zipIn.close();

            report.reportPass("onDesktopApplicationIUnzipElement", StrSource, "");
        } catch (Exception e) {
            System.out.println("FAILING Step: onDesktopApplicationIUnzipElement");
            report.reportFail("onDesktopApplicationIUnzipElement", StrSource, "");
        }
    }

    public void extractFile(ZipInputStream zipIn, String filePath) throws IOException {
        System.out.println("Step: extractFile");
        File child = new File(filePath);
        File parent = new File(child.getParent());
        if (!parent.exists()) {
            parent.mkdir();
        }
        BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(filePath));
        byte[] bytesIn = new byte[4096];
        int read = 0;
        while ((read = zipIn.read(bytesIn)) != -1) {
            bos.write(bytesIn, 0, read);
        }
        bos.close();
    }

    @Then("^On Desktop Application, I verify size of files and folders in \"([^\"]*)\" is not empty$")
    public void onDesktopApplicationIVerifySizeOfFilesAndFoldersIsNotEmpty(String filePath) {
        try {
            System.out.println("Step: onDesktopApplicationIVerifySizeOfFilesAndFoldersIsNotEmpty");
            File folder = new File(filePath);
            File[] listOfFiles = folder.listFiles();

            for(File file : listOfFiles) {
                String isDirectory = valueOf(file.isDirectory());
                switch (isDirectory) {
                    case "true":
                        if ((file.list().length)!=0) {
                            System.out.println("PASSED");
                            report.reportPass("onDesktopApplicationIVerifySizeOfFilesAndFoldersIsNotEmpty", file.toString(), "Directory is not empty");
                        } else {
                            System.out.println("FAILED");
                            report.reportFail("onDesktopApplicationIVerifySizeOfFilesAndFoldersIsNotEmpty", file.toString(), "Directory is empty");
                        }
                        break;
                    case "false":
                        if (file.length()!=0) {
                            System.out.println("PASSED");
                            report.reportPass("onDesktopApplicationIVerifySizeOfFilesAndFoldersIsNotEmpty", file.toString(), "File is more than 0 KB");
                        } else {
                            System.out.println("FAILED");
                            report.reportFail("onDesktopApplicationIVerifySizeOfFilesAndFoldersIsNotEmpty", file.toString(), "Directory is 0 KB");
                        }
                        break;
                }
            }

        } catch (Exception e) {
            System.out.println("FAILING Step: onDesktopApplicationIVerifySizeOfFilesAndFoldersIsNotEmpty");
            report.reportFail("onDesktopApplicationIVerifySizeOfFilesAndFoldersIsNotEmpty", "", "", "StrScreenshot");
        }
    }

    @Then("^On Desktop Application, I delete file \"([^\"]*)\"$")
    public void onDesktopApplicationIDeleteFile(String filePath) {
        try {
            System.out.println("Step: onDesktopApplicationIDeleteFile");
            File filePathString=new File(filePath);
            filePathString.delete();
        } catch (Exception e) {
            System.out.println("FAILING Step: onDesktopApplicationIDeleteFile");
            report.reportFail("onDesktopApplicationIDeleteFile", filePath, "");
        }
    }

    @Then("^On Desktop Application, I delete directory \"([^\"]*)\"$")
    public void onDesktopApplicationIDeleteDirectory(String filePath) {
        try {
            System.out.println("Step: onDesktopApplicationIDeleteDirectory");
            File folder = new File(filePath);
            File[] listOfFiles = folder.listFiles();
            for(File file : listOfFiles) {
                file.delete();
            }
        } catch (Exception e) {
            System.out.println("FAILING Step: onDesktopApplicationIDeleteDirectory");
            report.reportFail("onDesktopApplicationIDeleteDirectory", filePath, "");
        }
    }

    @Then("^On Desktop Application, I copy file \"([^\"]*)\" to \"([^\"]*)\"$")
    public void onDesktopApplicationICopyFileTo(String filePath, String copyPath) {
        try {
            System.out.println("Step: onDesktopApplicationICopyFileTo");
            File fromPleaseClick=new File(filePath);
            File toPleaseClick= new File(copyPath);
            FileUtils.copyFile(fromPleaseClick, toPleaseClick);
        } catch (Exception e) {
            System.out.println("FAILING Step: onDesktopApplicationICopyFileTo");
            report.reportFail("onDesktopApplicationICopyFileTo", filePath + " copy to " + copyPath, "");
        }
    }

    @Then("^On Desktop Application, I create file \"([^\"]*)\" with \"([^\"]*)\"$")
    public void onDesktopApplicationICreateFile(String filePath, Long length) {
        try {
            System.out.println("Step: onDesktopApplicationICreateFile");
            RandomAccessFile f = new RandomAccessFile(filePath,"rw");
            if (length!= 0) {
                f.writeChars("Writing " + length + " lines\n");
                int lengthInt = length.intValue();
                for (int i = 0; i < lengthInt; i++) {
                    f.writeChars("This file is " + length + " Byte\n");
                }
            }
            f.setLength(length);
            report.reportPass("onDesktopApplicationICreateFile", filePath, "", "StrScreenshot");
        } catch (Exception e) {
            System.out.println("FAILING Step: onDesktopApplicationICreateFile");
            report.reportFail("onDesktopApplicationICreateFile", filePath, "", "StrScreenshot");
        }
    }

    @And("^On Desktop Application, I wait via condition \"([^\"]*)\", expecting \"([^\"]*)\" in element \"([^\"]*)\" for max \"([^\"]*)\" seconds$")
    public void onDesktopApplicationIWaitViaCondition(String condition, String expectedText, String StrElement, long maxSeconds) {
        try {
            System.out.println("Step: onDesktopApplicationIWaitViaCondition");
            switch (condition) {
                case "textToBePresent":
                    WebDriverWait wait = (WebDriverWait) desktopApplicationDriver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
                    wait.until(ExpectedConditions.textToBePresentInElement(extractingElement(StrElement),expectedText));
                    break;
                case "nameToBePresent":
                    wait = (WebDriverWait) desktopApplicationDriver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
                    wait.until(ExpectedConditions.attributeToBe(extractingElement(StrElement),"Name",expectedText));
                    break;
                case "visibility":
                    wait = (WebDriverWait) desktopApplicationDriver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
                    wait.until(new expectedConditionPresenceOfElement(StrElement));
                    //System.out.println("visibility"+extractingElement(StrElement).isDisplayed());
                    break;
                case "invisibility":
                    wait = (WebDriverWait) desktopApplicationDriver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
                    wait.until(new expectedConditionAbsenceOfElement(StrElement));
                    break;
                default:
                    System.out.println("onDesktopApplicationIWaitViaCondition: NO METHOD FOUND");
                    break;
            }
            report.reportPass("onDesktopApplicationIWaitViaCondition", StrElement + ": " + expectedText, "", "StrScreenshot");
        } catch (Exception e) {
            System.out.println("FAILING Step: onDesktopApplicationIWaitViaCondition");
            report.reportFail("onDesktopApplicationIWaitViaCondition", StrElement + ": " + expectedText, "", "StrScreenshot");
        }
    }
    @And("^On Desktop Application, I expect \"([^\"]*)\" in element \"([^\"]*)\"$")
    public void expectedTextInElement(String expectedText,String strElement)
    {
        try {
            System.out.println("Step: OnDesktopApplicationIExpect");
            WebDriverWait wait = (WebDriverWait) desktopApplicationDriver.manage().timeouts().implicitlyWait(Duration.ofSeconds(3));
            wait.until(ExpectedConditions.attributeToBe(extractingElement(strElement),"Name",expectedText));
            WebElement element=extractingElement(strElement);
            System.out.println("element value" + extractingElement(strElement).getText());
            report.reportPass("OnDesktopApplicationIExpect", strElement + ": " + expectedText, "", "StrScreenshot");
        } catch (Exception e) {
            System.out.println("FAILING Step: onDesktopApplicationIExpect");
            report.reportFail("onDesktopApplicationIExpect", strElement + ": " + expectedText, "", "StrScreenshot");
        }

    }

    @Then("^If outlook is open, Close it$")
    public void ifOutlookIsOpenCloseIt() {

        boolean element= desktopApplicationDriver.findElement(By.name("PST Splitter")).findElement(By.id("20")).isDisplayed();
        if(element)
        {
            desktopApplicationDriver.findElement(By.name("PST Splitter")).findElement(By.id("1")).click();
        }
        return;
    }

    @Given("Open Desktop Application from {string} path")
    public void openDesktopApplicationFromPath(String application) throws IOException, InterruptedException {

        DesktopOptions desktop = new DesktopOptions();
        desktop.setApplicationPath(application);
        File drivePath = new File("C:\\Program Files\\Winium.Desktop.Driver\\Winium.Desktop.Driver.exe"); //Set winium driver path
        WiniumDriverService service = new WiniumDriverService.Builder().usingDriverExecutable(drivePath).usingPort(9999).withVerbose(false).withSilent(false).buildDesktopService();
        service.start(); //Build and Start a Winium Driver service
        desktopApplicationDriver = new WiniumDriver(new URL("http://localhost:9999"), desktop);
        Thread.sleep(1000);

    }

    public static class expectedConditionPresenceOfElement implements ExpectedCondition {
        String element;
        public expectedConditionPresenceOfElement(String StrElement) {
            this.element = StrElement;
        }
        //@NullableDecl
        @Override
        public Boolean apply( Object o) {
            extractingElement(element);
            Boolean result = true;
            return result;
        }
    }

    public static class expectedConditionAbsenceOfElement implements ExpectedCondition {
        String element;
        public expectedConditionAbsenceOfElement(String StrElement) {
            this.element = StrElement;
        }
        //@NullableDecl
        @Override
        public Boolean apply(Object o) {
            extractingElement(element);
            Boolean result = false;
            return result;
        }
    }






}
