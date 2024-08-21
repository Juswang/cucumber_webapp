package utilities;

import io.cucumber.java.en.Then;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Date;

public class report {
    public static File file;
    private static int number = 0;
    private static int stepNumber = 0;

    public static void main (String args[]) {
        createExecutionReport();
        createHTMLReport();
        reportPass("Step 1", "","Actual: HELLO Expected: HELLO");
        reportFail("Step 2", "","Actual: HELLO Expected: HJLLO");
    }

    @Then("^I create execution report$")
    public static void createExecutionReport() {
        try {
            System.out.println("Step: createExecutionReport");
            report.createHTMLReport();
            File screenshotFolders=new File("src\\result\\screenshots");
            FileUtils.cleanDirectory(screenshotFolders);
            /*File fromPleaseClick=new File("src\\main\\repository\\testData\\pleaseClick.jpg");
            File toPleaseClick= new File("src\\result\\screenshots\\pleaseClick.jpg");
            FileUtils.copyFile(fromPleaseClick, toPleaseClick);
            File fromGovtech=new File("src\\main\\repository\\testData\\govtech.jpg");
            File toGovtech= new File("src\\result\\screenshots\\govtech.jpg");
            FileUtils.copyFile(fromGovtech, toGovtech);*/
        } catch (Exception e) {
            System.out.println("FAILING Step: createExecutionReport");
        }
    }

    @Then("^I create execution report header \"([^\"]*)\"$")
    public static void createExecutionReportHeader(String strTestcase) {
        try {
            System.out.println("Step: createExecutionReportHeader");
            createHTMLHeader(strTestcase);
        } catch (Exception e) {
            System.out.println("FAILING Step: createExecutionReportHeader");
        }
    }

    public static void createHTMLReport() {
        try {
            String currentDate = new SimpleDateFormat("ddMMMyyyy_HHmmss").format(new Date());
            String reportPath =  "src\\result\\ExecutionReport_" + currentDate + ".html";
            file = new File(reportPath);
            FileWriter fr = new FileWriter(file, true);
            BufferedWriter br = new BufferedWriter(fr);
            PrintWriter pr = new PrintWriter(br);
            pr.println(
                    "<html>\n" +
                            "<head>\n" +
                            "<meta http-equiv=\"X-UA-Compatible\" content=\"IE=10\">" +
                            "<style> table {width:100%;} table, th { border:1px solid black; border-collapse:collapse; white-space:pre-line; text-align:left; font-family:Arial; font-size:12px; word-break:break-all;}" +
                            "tr#reportHeader {background-color:#ffffe0; text-align:left; padding:30px;}" +
                            "tr#timeHeader {background-color:#66FF99; text-align:left; padding:30px;}" +
                            "tr#reportPass {background-color:#CFF7A6; text-align:left;}" +
                            "tr#reportFail {background-color:#FE8986; text-align:left;}" +
                            "</style>\n" +
                            "<script>function myFunction(inputName){\n" +
                            "var x = document.getElementsByName(inputName).length;\n" +
                            "var i;\n" +
                            "for (i=0; i<x; i++) {\n" +
                            "var y = document.getElementsByName(inputName)[i];\n" +
                            "if (y.style.display===\"table-row\") {y.style.display=\"none\";}\n" +
                            "else if (y.style.display===\"none\") {y.style.display=\"table-row\";}\n" +
                            "}\n" +
                            "} </script>\n" +
                            "<title>SIPO Test Report</title>" +
                            "</head>\n" +
                            "<body>\n" +
                            "<h2>GovTech Secured Infrastructure Programme Office Test Report <img src=\"screenshots/govtech.jpg\" align=\"right\" /> </h2>\n" +
                            "<h6 align=\"right\">CopyRight of GovTech </h6>\n" +
                            "<table>\n" +
                            "<tr>\n" +
                            "<th padding: 5px; width=\"2%\">No</th>\n" +
                            "<th padding: 5px; width=\"4%\">Time</th>\n" +
                            "<th padding: 5px; width=\"20%\">Step</th>\n" +
                            "<th padding: 5px; width=\"50%\">Element</th>\n" +
                            "<th padding: 5px; width=\"20%\">Remark</th>\n" +
                            "<th padding: 5px; width=\"4%\">Result</th>\n" +
                            "</tr>"
            );
            pr.close();
            br.close();
            fr.close();
        } catch (Exception e) {
            System.out.println("Error when createHTMLReport");
        }
    }

    public static void createHTMLHeader(String strTestcase) {
        try {
            String currentDate = new SimpleDateFormat("EEEEE MMMMM yyyy HH:mm:ss").format(new Date());
            FileWriter fr = new FileWriter(file, true);
            BufferedWriter br = new BufferedWriter(fr);
            PrintWriter pr = new PrintWriter(br);
            number = number + 1;
            stepNumber = 0;
            pr.println(
                    "<tr id=\"reportHeader\">\n" +
                            "<th colspan=\"6\" height=\"80\">Test Case: " + strTestcase + "\n" +
                            "Execution Time: " + currentDate +
                            "<p align=\"right\"> <button onclick=\"myFunction(" + number + ")\"/> Show / Hide Testcase Steps </button> </p>" +
                            "</th>\n" +
                            "</tr>"
            );
            pr.close();
            br.close();
            fr.close();

        } catch (Exception e) {
            System.out.println("Error when createHTMLHeader:\n" + e.toString());
        }
    }

    public static void reportPass(String strStep, String strElement, String strRemark) {
        try {
            String currentDate = new SimpleDateFormat("HH:mm:ss").format(new Date());
            FileWriter fr = new FileWriter(file, true);
            BufferedWriter br = new BufferedWriter(fr);
            PrintWriter pr = new PrintWriter(br);
            stepNumber = stepNumber + 1;
            pr.println(
                    "<tr style=\"display:none\" id=\"reportPass\" name=\"" + number + "\">\n" +
                            "<th>" + stepNumber + "</th>\n" +
                            "<th>" + currentDate + "</th>\n" +
                            "<th>" + strStep + "</th>\n" +
                            "<th>" + strElement + "</th>\n" +
                            "<th>" + strRemark + "</th>\n" +
                            "<th align=\"center\">PASS</th>\n" +
                            "</tr>"
            );
            pr.close();
            br.close();
            fr.close();

        } catch (Exception e) {
            System.out.println("Error when writing report file:\n" + e.toString());
        }
    }

    public static void reportPass(String strStep, String strElement,String strRemark, String strScreenshot) {
        try {
            String currentDate = new SimpleDateFormat("HH:mm:ss").format(new Date());
            String strScreenshotDir = takesScreenshot();
            FileWriter fr = new FileWriter(file, true);
            BufferedWriter br = new BufferedWriter(fr);
            PrintWriter pr = new PrintWriter(br);
            stepNumber = stepNumber + 1;
            pr.println(
                    "<tr style=\"display:none\" id=\"reportPass\" name=\"" + number + "\">\n" +
                            "<th>" + stepNumber + "</th>\n" +
                            "<th>" + currentDate + "</th>\n" +
                            "<th>" + strStep + "</th>\n" +
                            "<th>" + strElement + "</th>\n" +
                            "<th>" + strRemark + " <a target=\"popup\" onclick=\"window.open('" + strScreenshotDir + "','jbnWindow','width=1000,height=800')\"> <img src=\"screenshots/pleaseClick.jpg\"</a> </th>\n" +
                            "<th align=\"center\">PASS</th>\n" +
                            "</tr>"
            );
            pr.close();
            br.close();
            fr.close();

        } catch (Exception e) {
            System.out.println("Error when writing report file:\n" + e.toString());
        }
    }

    public static void reportFail(String strStep, String strElement, String strRemark) {
        try {
            String currentDate = new SimpleDateFormat("HH:mm:ss").format(new Date());
            FileWriter fr = new FileWriter(file, true);
            BufferedWriter br = new BufferedWriter(fr);
            PrintWriter pr = new PrintWriter(br);
            stepNumber = stepNumber + 1;
            pr.println(
                    "<tr id=\"reportFail\">\n" +
                            "<th>" + stepNumber + "</th>\n" +
                            "<th>" + currentDate + "</th>\n" +
                            "<th>" + strStep + "</th>\n" +
                            "<th>" + strElement + "</th>\n" +
                            "<th>" + strRemark + "</th>\n" +
                            "<th align=\"center\">FAIL</th>\n" +
                            "</tr>"
            );
            pr.close();
            br.close();
            fr.close();

        } catch (Exception e) {
            System.out.println("Error when writing report file:\n" + e.toString());
        }
    }

    public static void reportFail(String strStep, String strElement, String strRemark, String strScreenshot) {
        try {
            String currentDate = new SimpleDateFormat("HH:mm:ss").format(new Date());
            String strScreenshotDir = takesScreenshot();
            FileWriter fr = new FileWriter(file, true);
            BufferedWriter br = new BufferedWriter(fr);
            PrintWriter pr = new PrintWriter(br);
            stepNumber = stepNumber + 1;
            pr.println(
                    "<tr id=\"reportFail\">\n" +
                            "<th>" + stepNumber + "</th>\n" +
                            "<th>" + currentDate + "</th>\n" +
                            "<th>" + strStep + "</th>\n" +
                            "<th>" + strElement + "</th>\n" +
                            "<th>" + strRemark + " <a target=\"popup\" onclick=\"window.open('" + strScreenshotDir + "','jbnWindow','width=1000,height=800')\"> <img src=\"screenshots/pleaseClick.jpg\"</a> </th>\n" +
                            "<th align=\"center\">FAIL</th>\n" +
                            "</tr>"
            );
            pr.close();
            br.close();
            fr.close();

        } catch (Exception e) {
            System.out.println("Error when writing report file:\n" + e.toString());
        }
    }

    public static String takesScreenshot() {
        String strScreenshot = "screenshots/screenshot.jpg";
        try {
            System.out.println("Step: takesScreenshot");
            String currentDate = new SimpleDateFormat("ddMMMyyyy_HHmmss").format(new Date());
            TakesScreenshot scrShot = desktopApplication.desktopApplicationDriver;
            File DestFile=new File("src\\result\\screenshots\\screenshot_" + currentDate + ".jpg");
            File SrcFile=scrShot.getScreenshotAs(OutputType.FILE);
            FileUtils.copyFile(SrcFile, DestFile);
            strScreenshot = "screenshots/screenshot_" + currentDate + ".jpg";

        } catch (Exception e) {
            System.out.println("FAILING Step: takesScreenshot");
        }
        return strScreenshot;
    }
}


