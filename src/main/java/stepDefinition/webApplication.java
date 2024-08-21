package stepDefinition;

import io.cucumber.java.After;
import io.cucumber.java.AfterStep;
import io.cucumber.java.Scenario;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import org.openqa.selenium.*;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.interactions.Action;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import utilities.report;

import java.awt.*;
import java.awt.datatransfer.StringSelection;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.io.File;
import java.time.Duration;

import static java.awt.event.KeyEvent.VK_LEFT;
import static java.awt.event.KeyEvent.VK_RIGHT;
import static stepDefinition.propertiesLoad.propertiesFile;

public class webApplication {

    public static WebDriver driver;



    @Given("^Open Edge browser and navigate to url \"([^\"]*)\"$")
    public void openEdgeBrowserAndNavigateToURL(String url)
    {   try {
        System.out.println("Step: OpenEdgeBrowser&navigateToURL: "+ url);
        System.setProperty("Webdriver.edge.driver",System.getProperty("user.dir")+"/src/main/resources/Drivers/msedgedriver.exe");
        EdgeOptions options=new EdgeOptions();
        driver=new EdgeDriver();
        driver.get(url);
        driver.manage().window().maximize();
        Thread.sleep(200);
        } catch (InterruptedException e) {
        System.out.println("Step Failed: OpenEdgeBrowser&navigateToURL: "+ url);
        }
    }



    @And("^Close Browser Application$")
    public void closeBrowser()
    {
        System.out.println("Step: Browser Closed");
        driver.quit();
    }

    @Then("^On Webpage, I click \"([^\"]*)\"$")
    public void onWebpageIClickElement(String strElement)
    {
       try{
           WebElement element = extractingElement(strElement);
           element.click();
           Thread.sleep(1000);
       } catch(Exception e)
        {
            System.out.println("Failing Step: onWebPageIClick");
        }
    }

    public WebElement extractingElement(String strElement)
    {
        WebElement element = null;
        try{
            String method = propertiesFile.getProperty(strElement).split("(?<!\\\\),")[0];
            switch(method){
                case "xpath":
                    String identity1=propertiesFile.getProperty(strElement).split(",")[1];
                    element=driver.findElement(By.xpath(identity1));
                    break;
                case "CSS":
                    String identity2=propertiesFile.getProperty(strElement).split(",")[1];
                    element=driver.findElement(By.cssSelector(identity2));
                    break;
                case "Classname":
                    String identity3=propertiesFile.getProperty(strElement).split(",")[1];
                    element=driver.findElement(By.className(identity3));
                    break;
                default:
                    System.out.println("Failing Step: extractingElement --- No Matching method");
                    break;
            }
        } catch (Exception e){
            System.out.println("Failing Step: extractingElement --- No Such Element Exception" + strElement);}
        return element;
    }


    //DoubleClick on an Element
    @Then("^On Webpage, I double click \"([^\"]*)\"$")
    public void onWebpageIDoubleClickElement(String strElement)
    {
        try{
            WebElement element = extractingElement(strElement);
            Actions act= new Actions(driver);
            act.doubleClick(element).perform();
            Thread.sleep(1000);
            report.reportPass("On WebPage, I double click",strElement,"Element has been double clicked");
        } catch(Exception e)
        {
            System.out.println("Failing Step: onWebPageIClick");
        }



    }


    //Scroll down the webpage
    @Then("^On Webpage, I scroll down$")
    public void onWebpageIScrollDown() {
        try {
            System.out.println("Step: onWebpageIScrollDown");
            Robot robot = new Robot();
            robot.keyPress(KeyEvent.VK_PAGE_DOWN);
            robot.keyRelease(KeyEvent.VK_PAGE_DOWN);
            Thread.sleep(1000);
        } catch (Exception e) {
            System.out.println("FAILING Step: onWebpageIScrollDown");

        }
    }
    @Then("^On Webpage, I scroll down until \"([^\"]*)\"$")
    public void onWebpageIScrollDownUntil(String strElement) {
        try {

           /*JavascriptExecutor js = (JavascriptExecutor) driver;
            WebElement element=extractingElement(strElement);
            System.out.println("Step: onWebpageIScrollDownUntil: "+ strElement);
            js.executeScript("arguments[0].scrollIntoView(true);",element);
            Thread.sleep(1000);*/
            WebElement element=extractingElement(strElement);
            Actions action = new Actions(driver);
            action.moveToElement(element);
            action.perform();
            Thread.sleep(1000);
        } catch (Exception e) {
            System.out.println("FAILING Step: onWebpageIScrollDownUntil");

        }
    }


    //enter text into textbox
    @Then("^On Webpage, I type \"([^\"]*)\" on \"([^\"]*)\" Textbox$")
    public void onWebPageITypeOnElement(String inputText,String strElement)
    {
        try
        {
            System.out.println("Step: OnWebPageITypeOnElement" + inputText);
            WebElement element=extractingElement(strElement);
            element.sendKeys(inputText);
            Thread.sleep(1000);
        }
        catch(Exception e)
        {
            System.out.println("Failing Step : OnWebPageITypeOnElement" + inputText);
        }
    }
    //validate and verify the findings

    //Drag and drop
    @Then("^I drag and drop \"([^\"]*)\" from file explorer folder path \"([^\"]*)\" to web \"([^\"]*)\"$")
    public void iDragandDrop(String selected,String actualFilePath,String target)
    {
        try
        {
            System.out.println("Step: iDragandDrop");
            Robot robot=new Robot();
            robot.keyPress(KeyEvent.VK_WINDOWS);
            robot.keyPress(VK_RIGHT);
            robot.keyRelease(KeyEvent.VK_WINDOWS);
            robot.keyRelease(KeyEvent.VK_RIGHT);
            Thread.sleep(500);
            //get element to drop file to

            //select file to be moved over to webapp
            System.out.println("Step: iDragandDrop");
            String filePath = actualFilePath;
            File file = new File(filePath);
            // Open the file explorer and navigate to the file location
            StringSelection stringSelection = new StringSelection(file.getAbsolutePath());
            Toolkit.getDefaultToolkit().getSystemClipboard().setContents(stringSelection, null);
            //Robot robot = new Robot();
            String[]targetCoord=target.split(",");

            int dropAreaX=Integer.parseInt(targetCoord[0]);
            int dropAreaY=Integer.parseInt(targetCoord[1]);

            // Open the file explorer using Windows + E
            robot.keyPress(KeyEvent.VK_WINDOWS);
            robot.keyPress(KeyEvent.VK_E);
            robot.keyRelease(KeyEvent.VK_E);
            robot.keyRelease(KeyEvent.VK_WINDOWS);
            robot.keyRelease(KeyEvent.VK_E);
            Thread.sleep(1000); // Adjust the sleep time if necessary
            robot.keyPress(KeyEvent.VK_WINDOWS);
            robot.keyPress(VK_LEFT);
            robot.keyRelease(KeyEvent.VK_WINDOWS);
            robot.keyRelease(KeyEvent.VK_LEFT);
            Thread.sleep(1000); // Adjust the sleep time if necessary
            robot.keyPress(KeyEvent.VK_TAB);
            robot.keyRelease(KeyEvent.VK_TAB);
            Thread.sleep(1000); // Adjust the sleep time if necessary
            robot.keyPress(KeyEvent.VK_TAB);
            robot.keyRelease(KeyEvent.VK_TAB);
            Thread.sleep(1000); // Adjust the sleep time if necessary
            robot.keyPress(KeyEvent.VK_TAB);
            robot.keyRelease(KeyEvent.VK_TAB);
            Thread.sleep(1000); // Adjust the sleep time if necessary
            robot.keyPress(KeyEvent.VK_TAB);
            robot.keyRelease(KeyEvent.VK_TAB);
            Thread.sleep(1000); // Adjust the sleep time if necessary
            robot.keyPress(KeyEvent.VK_TAB);
            robot.keyRelease(KeyEvent.VK_TAB);
            Thread.sleep(1000); // Adjust the sleep time if necessary
            // Paste the file path into the file explorer
            robot.keyPress(KeyEvent.VK_CONTROL);
            robot.keyPress(KeyEvent.VK_V);
            robot.keyRelease(KeyEvent.VK_V);
            robot.keyRelease(KeyEvent.VK_CONTROL);
            Thread.sleep(1000);

            // Press Enter to open the file location
            robot.keyPress(KeyEvent.VK_ENTER);
            robot.keyRelease(KeyEvent.VK_ENTER);
            Thread.sleep(1000); // Adjust the sleep time if necessary
            String[]SourceCoord=selected.split(",");
            // Find and select the file by name
            // Adjust the coordinates to match the file location in the file explorer
            int fileX =Integer.parseInt(SourceCoord[0]); // Example X coordinate of the file in file explorer
            int fileY = Integer.parseInt(SourceCoord[1]); // Example Y coordinate of the file in file explorer

            // Move to the file location
            robot.mouseMove(fileX, fileY);
            Thread.sleep(1000);

            // Press and hold the left mouse button
            robot.mousePress(InputEvent.BUTTON1_DOWN_MASK);
            Thread.sleep(2000);
            System.out.println("document selected");

            System.out.println("Step: DropArea Coordinates X:"+ dropAreaX+" Y:"+dropAreaY);
            // Drag the file to the drop area
            robot.mouseMove(dropAreaX, dropAreaY);
            Thread.sleep(2000);

            // Release the left mouse button
            robot.mouseRelease(InputEvent.BUTTON1_DOWN_MASK);
            Thread.sleep(1000);
        }
        catch(Exception e)
        {
            System.out.println("Failing Step : iDragandDrop");
        }
    }


    @Then("^On Webpage, I check if  \"([^\"]*)\" is displayed$")
    public void CheckIfElementDisplayed(String strElement)
    {
        try{
            WebElement element=extractingElement(strElement);
            if(element.isDisplayed())
            {
                System.out.println("Step: element is displayed");
            }else
            {
                System.out.println("Step: element is not displayed");
            }
        }catch(Exception e){
            System.out.println(e.toString());
        }
    }
    @Then("^On Webpage, I check if  \"([^\"]*)\" is Checked$")
    public void checkIfElementChecked(String strElement)
    {
        try{
            WebElement element=extractingElement(strElement);
            if(element.isSelected())
            {
                System.out.println("Step: element is Selected");
            }else
            {
                System.out.println("Step: element is not Selected");
            }
        }catch(Exception e){
            System.out.println(e.toString());
        }
    }
    @Then("^On Webpage, I return to the previous page$")
    public void iReturnToPreviousPage()
    {
        driver.navigate().back();
    }

    @Then("^On Webpage, I hover mouse over \"([^\"]*)\"$")
    public void hoverOverElement(String strElement){
        try{
            WebElement element=extractingElement(strElement);
            if(element.isDisplayed())
            {
                Actions action= new Actions(driver);
                action.moveToElement(element).perform();
                Thread.sleep(1500);
            }
        }catch(Exception e){
            System.out.println(e.toString());
        }
    }


    public static void getScreenCoordinates()
    {
        System.out.println("Press Ctrl+C to exit");
        while (true) {
            java.awt.Point point = MouseInfo.getPointerInfo().getLocation();
            int x = (int) point.getX();
            int y = (int) point.getY();
            System.out.print("X: " + x + " Y: " + y + "\r");
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
    @Then("^On Webpage, I wait \"([^\"]*)\" seconds$")
    public void IWait(String time)
    {
        Long seconds=Long.parseLong(time);
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(seconds));
    }

    @Then("^On Webpage, I wait for \"([^\"]*)\" seconds until \"([^\"]*)\" is visible$")
    public void IWaitTillElementIsDisplayed(String time, String strElement){
        int dur =Integer.parseInt(time);
        WebDriverWait wait = new WebDriverWait(driver,Duration.ofSeconds(dur));
        WebElement element = wait.until(ExpectedConditions.visibilityOf(extractingElement(strElement)));
    }

    @AfterStep
    public static void takeScreenshot(Scenario scenario)
    {
        System.out.println("Capture Screenshot");
        final byte [] screenshot=((TakesScreenshot)driver).getScreenshotAs(OutputType.BYTES);
        scenario.attach(screenshot,"image/png","image");
    }


    @After
    public static void tearDown()
    {
        System.out.println("Closing Driver");
        driver.quit();
    }


    @Then("On WebPage, I key enter on {string}")
    public void onWebPageIKeyEnterOn(String strElement) {

        WebElement element = extractingElement(strElement);
        element.sendKeys(Keys.ENTER);
    }

    @Then("On WebPage, I key tab on {string}")
    public void onWebPageIKeyTabOn(String strElement) {

        WebElement element =extractingElement(strElement);
        element.sendKeys(Keys.TAB);
    }
}
