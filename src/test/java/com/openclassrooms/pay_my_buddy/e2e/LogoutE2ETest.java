package com.openclassrooms.pay_my_buddy.e2e;

import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import static org.assertj.core.api.Assertions.assertThat;

public class LogoutE2ETest extends AbstractE2E {

    @Test
    public void testLogout() {
        // Given
        mockUserRepository("test@gmail.com", "test");
        login("test@gmail.com", "test");
        // When
        WebElement logoutLink = driver.findElement(By.id("logout"));

        logoutLink.click();

        // Then
        assertThat(driver.getCurrentUrl()).isEqualTo(baseUrl + "/login?logout");
    }
}
