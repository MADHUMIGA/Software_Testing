package com.skcet;

import java.io.FileInputStream;
import java.io.IOException;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

public class AppTest 
{
    
    WebDriver driver;
    WebDriverWait wait;
    WebElement element;
    String text,text2,username,password;
    XSSFWorkbook workbook;
    XSSFSheet sheet;
    List<Details> detailsList;

    Details details;

    class Details {

        String uname,pass;

        Details(Row row){

            this.uname = row.getCell(0).getStringCellValue();
            this.pass = row.getCell(1).getStringCellValue();
        }

        @Override
        public String toString(){
            return "Username   :"+uname+"\n" +
                   "Password   :"+pass+"\n";
        }
    }

    @BeforeTest
    public void setUpServer(){
        driver = new ChromeDriver();
        wait = new WebDriverWait(driver,Duration.ofSeconds(30));
        driver.get("https://www.demoblaze.com/");
    }

    @BeforeTest
    public void setUpExcel() throws IOException{
        
        String path = "C:\\Users\\Lenovo\\Desktop\\IamNeo\\Software Testing\\Testing Framework-day8\\classexercise2\\src\\Excel\\Data.xlsx";
        FileInputStream fs = new FileInputStream(path);
        workbook = new XSSFWorkbook(fs);
        sheet = workbook.getSheetAt(0);

        int rowCount = sheet.getLastRowNum();
        detailsList = new ArrayList<>();

        for (int i = 1; i <= rowCount; i++) {
            Row row = sheet.getRow(i);
            if (row != null) {
            detailsList.add(new Details(row));
            }
        }

  workbook.close();

  // Now you can access the list of Details objects
  for (Details details : detailsList) {
    System.out.println(details); // Use toString() for formatted output
    // Use details.uname and details.pass for login or other purposes
  }
        workbook.close();

        // username = sheet.getRow(1).getCell(0).getStringCellValue();
        // password = sheet.getRow(1).getCell(1).getStringCellValue();
    }

    /**
     * TestCase::1
     */
    @Test(priority = 1)
    public void testDemoBlaze(){

        //Step 1 -- clicking laptops
        WebElement laptop = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("/html/body/div[5]/div/div[1]/div/a[3]")));
        laptop.click();

        //Step 2 -- selecting mac book air
        WebElement macbook = wait.until(ExpectedConditions.visibilityOfElementLocated(By.linkText("MacBook air")));
        macbook.click();

        // Saving the product name in a file
        WebElement name = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@id='tbodyid']/h2")));
        text = name.getText();

        //Step 3 -- click Add to cart
        WebElement cart = wait.until(ExpectedConditions.visibilityOfElementLocated(By.linkText("Add to cart")));
        cart.click();

        //Step 4 -- accept Alert
        Alert alert = wait.until(ExpectedConditions.alertIsPresent());
        alert.accept();

        //Step 5 -- click cart
        WebElement clickcart = wait.until(ExpectedConditions.visibilityOfElementLocated(By.linkText("Cart")));
        clickcart.click();

        //Step 6 -- check the selected product and the product in the cart matched
        WebElement producElement = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@id='tbodyid']/tr/td[2]")));
        text2 = producElement.getText();
        if(text.equals(text2))
        {
            System.out.println("Selected product and the product in the cart are matched");
        }
        else{
            System.out.println("Selected product and the product in the cart are not matched");
        }

    }

    /** 
     * TestCase::2
    */
    @Test(priority = 2)
    public void test_login(){

        driver.get("https://www.demoblaze.com/");


        for (Details details : detailsList){

        //Step 1 click Log In
        WebElement logIn = wait.until(ExpectedConditions.visibilityOfElementLocated(By.linkText("Log in")));
        logIn.click();

        //Step 2 send username
        WebElement uname = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("loginusername")));
        uname.sendKeys(details.uname);
        
        //Step 3 send password
        WebElement pass = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("loginpassword")));
        pass.sendKeys(details.pass);

        //Step 4 click login button
        WebElement log = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@id='logInModal']/div/div/div[3]/button[2]")));
        log.click();

        //Step 5 check logged in
        WebElement navbar = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("nameofuser")));
        if(navbar.getText().equals("Welcome Testalpha"))
        {
            System.out.println("Logged in successfully");
        }
        else{
            System.out.println("Log in failed");
        }
    }
        
    }

    @AfterTest
    public void afterAll() throws InterruptedException{

        Thread.sleep(3000);
        driver.quit();
    }
}
