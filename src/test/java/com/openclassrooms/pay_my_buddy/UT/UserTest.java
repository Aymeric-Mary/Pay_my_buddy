package com.openclassrooms.pay_my_buddy.UT;

import com.openclassrooms.pay_my_buddy.model.User;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.assertj.core.api.Assertions.assertThat;

public class UserTest {

    @ParameterizedTest
    @CsvSource({
            "100, 100.5",
            "150, 150.75",
            "400, 402.0"
    })
    public void testPay(float amount, float expectedMinusBalance) {
        // Given
        User user = User.builder().balance(1000f).build();
        // When
        user.pay(amount);
        // Then
        assertThat(user.getBalance()).isEqualTo(1000 - expectedMinusBalance);
    }
}
