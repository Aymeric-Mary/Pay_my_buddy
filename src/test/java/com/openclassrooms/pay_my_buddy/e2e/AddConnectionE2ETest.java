package com.openclassrooms.pay_my_buddy.e2e;

import com.openclassrooms.pay_my_buddy.controller.UserController;
import com.openclassrooms.pay_my_buddy.dto.AddConnectionDto;
import com.openclassrooms.pay_my_buddy.model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.springframework.boot.test.mock.mockito.SpyBean;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;

public class AddConnectionE2ETest extends WithoutSecurity {

    @SpyBean
    private UserController userControllerSpy;

    @BeforeEach
    public void mockConnectableUsers() {
        User user1 = User.builder().id(1L).firstname("Paul").lastname("Durand").build();
        User user2 = User.builder().id(2L).firstname("Jean").lastname("Dupont").build();
        mockConnectableUsers(List.of(user1, user2));
    }

    @Test
    public void shouldShowConnectableUsers() {
        // Given
        driver.navigate().to(baseUrl + "/transfer");
        WebElement btnAddConnection = driver.findElement(By.id("btn-add-connection"));
        // When
        btnAddConnection.click();
        // Then
        WebElement modalAddConnection = driver.findElement(By.id("add-connection"));
        assertThat(modalAddConnection.isDisplayed()).isTrue();
        List<WebElement> users = driver.findElement(By.id("connections")).findElement(By.tagName("tbody")).findElements(By.tagName("tr"));
        assertThat(users.get(0).getAttribute("id")).isEqualTo("1");
        assertThat(users.get(0).findElements(By.tagName("td")).get(0).getText()).isEqualTo("Paul");
        assertThat(users.get(0).findElements(By.tagName("td")).get(1).getText()).isEqualTo("Durand");
        assertThat(users.get(1).getAttribute("id")).isEqualTo("2");
        assertThat(users.get(1).findElements(By.tagName("td")).get(0).getText()).isEqualTo("Jean");
        assertThat(users.get(1).findElements(By.tagName("td")).get(1).getText()).isEqualTo("Dupont");
    }

    @Test
    public void shouldAddConnectionWhenClickOnOk() throws InterruptedException {
        // Given
        driver.navigate().to(baseUrl + "/transfer");
        WebElement btnAddConnection = driver.findElement(By.id("btn-add-connection"));
        btnAddConnection.click();
        List<WebElement> users = driver.findElement(By.id("connections")).findElement(By.tagName("tbody")).findElements(By.tagName("tr"));;
        // When
        users.get(0).click();
        // Then
        Alert alert = driver.switchTo().alert();
        assertThat(alert.getText()).isEqualTo("Are you sure you want to add Paul Durand as a connection?");
        // When
        alert.accept();
        // Then
        Thread.sleep(1000);
        verify(userControllerSpy).addConnection(new AddConnectionDto(1L));
    }

    @Test
    public void shouldNotAddConnectionWhenClickOnCancel() throws InterruptedException {
        // Given
        driver.navigate().to(baseUrl + "/transfer");
        WebElement btnAddConnection = driver.findElement(By.id("btn-add-connection"));
        btnAddConnection.click();
        List<WebElement> users = driver.findElement(By.id("connections")).findElement(By.tagName("tbody")).findElements(By.tagName("tr"));;
        // When
        users.get(0).click();
        // Then
        Alert alert = driver.switchTo().alert();
        assertThat(alert.getText()).isEqualTo("Are you sure you want to add Paul Durand as a connection?");
        // When
        alert.dismiss();
        // Then
        Thread.sleep(1000);
        verifyNoInteractions(userControllerSpy);
    }
}
