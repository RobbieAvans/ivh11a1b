package edu.avans.hartigehap.web.it;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.concurrent.TimeUnit;

import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class SubsystemLoginIT {

	/**
	 * The index url should be redirected to the login page
	 */
    public static String URL = "http://localhost:8080/hh/static/index.html";

    @Test
    public void login() {
        WebDriver driver = BrowserUtils.getWebDriver();
        driver.get(URL);

        // Waiting time to load javascripts and angular scripts
        log.info("Timeout of 10 seconds");
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);

        String source = driver.getPageSource();
        log.info("HTML Source of webpage:" + source);
        
        // Check if we are on the login in page -> the following elements should exists
        WebElement emailInput = driver.findElement(By.cssSelector("input[type='email']"));
        assertNotNull(emailInput);
        
        WebElement passwordInput = driver.findElement(By.cssSelector("input[type='password']"));
        assertNotNull(passwordInput);
        
        WebElement submitButton = driver.findElement(By.cssSelector("input[type='submit'][value='inloggen']"));
        assertNotNull(submitButton);
        
        // Set the input values
        emailInput.sendKeys("manager@hh.nl");
        passwordInput.sendKeys("manager");
        
        // Click on button
        submitButton.click();
        
        // After the login we should be on the hallReservation page
        assertEquals("http://localhost:8080/hh/static/index.html#/hallreservation/", driver.getCurrentUrl());
        log.info("HTML Source of the new webpage:" + driver.getPageSource());
        
        // Timeout to make sure everything is loaded
        log.info("Timeout of 10 seconds");
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        
        // On this page we expect a datatable
        WebElement datatable = driver.findElement(By.id("DataTables_Table_0"));
        assertNotNull(datatable);
        
        // A search input
        WebElement searchInput = driver.findElement(By.cssSelector("input[type='search']"));
        assertNotNull(searchInput);
        
        // A Link to create a new reservation
        WebElement createReservationLink = driver.findElement(By.linkText("Nieuwe reservering aanmaken"));
        assertEquals("http://localhost:8080/hh/static/index.html#/hallreservation/", createReservationLink.getAttribute("href"));
    }
}
