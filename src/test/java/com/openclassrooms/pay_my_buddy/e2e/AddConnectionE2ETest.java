package com.openclassrooms.pay_my_buddy.e2e;

import com.openclassrooms.pay_my_buddy.model.User;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class AddConnectionE2ETest extends AbstractE2E {

    @Test
    public void shouldAddConnection() {
        // Given
        User connectedUser = mockUserRepository("test@gmail.com", "test");
        login("test@gmail.com", "test");
        User user1 = createUser("Jean", "Dupont", "jean@gmail.com");
        User user2 = createUser("Paul", "Dupont", "paul@gmail.com");
        addConnection(connectedUser, user1);
        driver.navigate().to(baseUrl + "/transfer");
        WebElement btnAddConnection = driver.findElement(By.id("btn-add-connection"));
        // When
        btnAddConnection.click();
        // Then
        WebElement modalAddConnection = driver.findElement(By.id("add-connection"));
        assertThat(modalAddConnection.isDisplayed()).isTrue();
        List<WebElement> users = driver.findElement(By.id("connections")).findElement(By.tagName("tbody")).findElements(By.tagName("tr"));
        assertThat(users.size()).isEqualTo(1);
        assertThat(users.get(0).getAttribute("id")).isEqualTo(user2.getId().toString());
        assertThat(users.get(0).findElements(By.tagName("td")).get(0).getText()).isEqualTo("Paul");
        assertThat(users.get(0).findElements(By.tagName("td")).get(1).getText()).isEqualTo("Dupont");

    }
}
