package com.example;

import java.io.FileInputStream;

import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;



public class AppTest {
    private WebDriver driver;
    private XSSFWorkbook wb;
    public ExtentReports report;
    private Actions action;

    @BeforeTest
    public void beforeTestSetup()throws Exception {
        driver = new ChromeDriver();

        //action
        action =new Actions(driver);
        //excel
        String xlpath = "C:\\Users\\madhu\\OneDrive\\Pictures\\Desktop\\selenium\\selenium_cc2\\src\\excel\\excel_cc.xlsx";
        FileInputStream file = new FileInputStream(xlpath);
        wb = new XSSFWorkbook(file);

        //report
        report = new ExtentReports();
        ExtentSparkReporter spark = new ExtentSparkReporter("C:\\Users\\madhu\\OneDrive\\Pictures\\Desktop\\selenium\\selenium_cc2\\src\\report\\Report.html");
        report.attachReporter(spark);
    }

    @Test
    public void bookSearch() {

        String searchText = wb.getSheetAt(0).getRow(0).getCell(0).getStringCellValue();
        ExtentTest test = report.createTest("TestCase 1");
        driver.get("https://www.barnesandnoble.com/");

        
        driver.findElement(By.xpath("//*[@id=\"rhf_header_element\"]/nav/div/div[3]/form/div/div[1]/a")).click();
        
        driver.findElement(By.linkText("Books")).click();
        
        driver.findElement(By.xpath("//*[@id=\"rhf_header_element\"]/nav/div/div[3]/form/div/div[2]/div/input[1]"))
        .sendKeys(searchText);
        driver.findElement(By.xpath("//*[@id=\"rhf_header_element\"]/nav/div/div[3]/form/div/span/button")).click();
        
        String ExptName = "chetan bhagat";
        String OriginalName = driver.findElement(By.xpath("//*[@id=\"searchGrid\"]/div/section[1]/section[1]/div/div[1]/div[1]/h1/span")).getText();
        
        if(ExptName.equals(OriginalName)){
            System.out.println("String Matches");
            test.log(Status.PASS, "Match Passed");
        }
        else{
            test.log(Status.FAIL, "Match Failed");
        }
        
    }
    @Test(dependsOnMethods="bookSearch")
    public void addToCart()throws Exception{
        
        
        driver.get("https://www.barnesandnoble.com/");
        
        action.moveToElement(driver.findElement(By.xpath("//*[@id=\"rhfCategoryFlyout_eBooks\"]")))
        .perform();
        
        driver.findElement(By.xpath("//*[@id=\"navbarSupportedContent\"]/div/ul/li[4]/div/div/div[1]/div/div[2]/div[1]/dd/a[1]")).click();
        driver.findElement(By.xpath("//*[@id=\"addToBagForm_9780345490933\"]/input[11]")).click();

    }
    @AfterTest
    public void closeSetup(){
        report.flush();
    }
}
