package utilities;

import org.openqa.selenium.SearchContext;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;

public class DriverManager {

    public static WebDriver driver;
    public EdgeOptions options;

   public static WebDriver getDriver(){

       if(driver==null)
       {
           System.setProperty("Webdriver.edge.driver",System.getProperty("user.dir")+"/src/main/resources/Drivers/msedgedriver.exe");
           EdgeOptions options=new EdgeOptions();
       }
       else {
           driver=getDriver();
       }

       return driver;
   }
}
