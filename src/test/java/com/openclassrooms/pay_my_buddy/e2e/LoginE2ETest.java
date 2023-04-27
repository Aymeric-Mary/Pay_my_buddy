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
        mockUserRepository("test@gmail.com", "test");
        // When
        login("test@gmail.com", "test");
        // Then
        assertThat(driver.getCurrentUrl()).isEqualTo(baseUrl + "/transfer");
        assertThat(driver.manage().getCookies()).noneMatch(cookie -> cookie.getName().equals("remember-me"));
    }

    @Test
    public void testLogin_whenCredentialsAreWrong() {
        // Given
        mockUserRepository("test@gmail.com", "testtest");
        // When
        login("test@gmail.com", "test");
        // Then
        assertThat(driver.getCurrentUrl()).isEqualTo(baseUrl + "/login?error");
        WebElement errorMessage = driver.findElement(By.id("wrong-credentials"));
        assertThat(errorMessage.isDisplayed()).isEqualTo(true);
    }

    @Test
    public void testLogin_whenCredentialsAreGood_andRememberMeIsTrue() {
        // Given
        mockUserRepository("test@gmail.com", "test");
        // When
        login("test@gmail.com", "test", true);
        // Then
        assertThat(driver.getCurrentUrl()).isEqualTo(baseUrl + "/transfer");
        assertThat(driver.manage().getCookies()).anyMatch(cookie -> cookie.getName().equals("remember-me"));
        assertThat(driver.manage().getCookieNamed("remember-me").getExpiry())
                .isInSameSecondAs(Date.from(Instant.now().plusSeconds(appConfig.getNbDaysRememberMe() * 24 * 60 * 60)));
    }
}
