package com.example.selinium_day5;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class SeliniumDay5Application {

	public static void main(String[] args) throws InterruptedException {
		SpringApplication.run(SeliniumDay5Application.class, args);
		WebDriver driverchrome=new ChromeDriver();
		driverchrome.get("https://www.demoblaze.com");
		driverchrome.findElement(By.linkText("Laptops")).click();
		Thread.sleep((1000));
		driverchrome.findElement(By.linkText("MacBook air")).click();
		Thread.sleep((1000));
		driverchrome.findElement(By.linkText("Add to cart")).click();
		Thread.sleep((1000));
		driverchrome.switchTo().alert().accept();
		Thread.sleep((10000));
		driverchrome.findElement(By.linkText("Cart")).click();
	}

}
