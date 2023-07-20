package com.openclassrooms.pay_my_buddy.e2e;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class TransferE2ETest extends AbstractE2E {

    @Test
    @Disabled
    public void shouldRedirectToLogin_whenNoAuthenticated() {
        // Given
        // When
        driver.get(baseUrl + "/transfer");
        // Then
       assertThat(driver.getCurrentUrl()).isEqualTo(baseUrl + "/login");
    }
}
