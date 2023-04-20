package com.openclassrooms.pay_my_buddy.e2e;

import com.openclassrooms.pay_my_buddy.config.AppConfig;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.Instant;
import java.util.Date;

import static org.assertj.core.api.Assertions.assertThat;

public class LoginE2ETest extends AbstractE2E {

    @Autowired
    private AppConfig appConfig;

    @Test
    public void testLogin_whenCredentialsAreGood_andRememberMeIsFalse() {
        // Given
        createUser("test@gmail.com", "test");
        // When
        filledAndSubmitLoginForm(false);

        // Then
        assertThat(driver.getCurrentUrl()).isEqualTo(baseUrl + "/transfer");
        assertThat(driver.manage().getCookies()).noneMatch(cookie -> cookie.getName().equals("remember-me"));
    }

    @Test
    public void testLogin_whenCredentialsAreWrong() {
        // Given
        // When
        filledAndSubmitLoginForm(false);

        // Then
        assertThat(driver.getCurrentUrl()).isEqualTo(baseUrl + "/login?error");
        WebElement errorMessage = driver.findElement(By.id("wrong-credentials"));
        assertThat(errorMessage.isDisplayed()).isEqualTo(true);
    }

    @Test
    public void testLogin_whenCredentialsAreGood_andRememberMeIsTrue() {
        // Given
        createUser("test@gmail.com", "test");
        // When
        filledAndSubmitLoginForm(true);
        // Then
        assertThat(driver.getCurrentUrl()).isEqualTo(baseUrl + "/transfer");
        assertThat(driver.manage().getCookies()).anyMatch(cookie -> cookie.getName().equals("remember-me"));
        assertThat(driver.manage().getCookieNamed("remember-me").getExpiry())
                .isInSameSecondAs(Date.from(Instant.now().plusSeconds(appConfig.getNbDaysRememberMe() * 24 * 60 * 60)));
    }

    private void filledAndSubmitLoginForm(Boolean rememberMe) {
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
