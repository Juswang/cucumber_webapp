package stepDefinition;

import io.cucumber.java.en.Then;

import java.io.FileInputStream;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Properties;

public class propertiesLoad {

    public static Properties propertiesFile=new Properties();
    public static String uniqueEmailSubjectTitle;

    @Then("^I load Properties File \"([^\"]*)\"$")
    public void iLoadPropertiesFile(String propertiesFileName)
    {
        try{
            System.out.println("Step: I load properties");
            InputStream input =new FileInputStream("src\\main\\repository\\" + propertiesFileName);
            propertiesFile.load(input);

     ;   } catch(Exception e)
        {
            System.out.println("Failing Step: I load properties");
        }

    }


    @Then("^I generate Unique Email Subject Title$")
    public void igenerateUniqueEmailSubjectTitle()
    {
        try {
            System.out.println("Step: igenerateUniqueEmailSubject");
            uniqueEmailSubjectTitle = null;
            uniqueEmailSubjectTitle = new SimpleDateFormat("ddMMMyyyy_HHmmss").format(new Date());
        } catch (Exception e){
            System.out.println("FAILING Step: igenerateUniqueEmailSubject");
        }
    }


    @Then("^Close System$")
    public void closeSystem()
    {
        System.out.println("Step: close System");
        System.exit(0);
    }

}
