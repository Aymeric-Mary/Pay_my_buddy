package com.openclassrooms.pay_my_buddy.e2e;

import com.openclassrooms.pay_my_buddy.DataTools;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public abstract class AbstractE2E extends DataTools {
    @LocalServerPort
    private int port;
    protected WebDriver driver;
    protected String baseUrl;

    @BeforeAll
    static void setupAll() {
        WebDriverManager.chromedriver().setup();
    }

    @BeforeEach
    public void setUp() {
        driver = new ChromeDriver();
        baseUrl = "http://localhost:" + port;
        userRepository.deleteAll();
    }

    @AfterEach
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }

    protected void login(){
        login(false);
    }

    protected void login(Boolean rememberMe){
        driver.get(baseUrl + "/login");
        WebElement inputEmail = driver.findElement(By.id("email"));
        WebElement inputPassword = driver.findElement(By.id("password"));
        WebElement submitButton = driver.findElement(By.cssSelector("button[type=submit]"));
        WebElement rememberMeCheckbox = driver.findElement(By.id("remember-me"));

        inputEmail.sendKeys("test@gmail.com");
        inputPassword.sendKeys("test");
        if (rememberMe) rememberMeCheckbox.click();
        submitButton.click();
    }

}
