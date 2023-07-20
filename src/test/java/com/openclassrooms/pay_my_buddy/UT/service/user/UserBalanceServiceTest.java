package com.openclassrooms.pay_my_buddy.UT.service.user;

import com.openclassrooms.pay_my_buddy.model.Transaction;
import com.openclassrooms.pay_my_buddy.model.User;
import com.openclassrooms.pay_my_buddy.service.user.UserBalanceService;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.assertj.core.api.Assertions.assertThat;

public class UserBalanceServiceTest {

    UserBalanceService userBalanceService = new UserBalanceService();

    @ParameterizedTest
    @CsvSource({
            "700,300,599.5,400",
            "550,900,449.5,1000",
            "200,100,99.5,200",
    })
    public void testAdjustUsersBalance(Float senderBalance, Float receiverBalance, Float expectedSenderBalance, Float expectedReceiverBalance) {
        // Given
        User sender = User.builder().balance(senderBalance).build();
        User receiver = User.builder().balance(receiverBalance).build();
        Transaction transaction = Transaction.builder()
                .sender(sender)
                .receiver(receiver)
                .amount(100f)
                .fee(0.5f)
                .build();
        // When
        userBalanceService.adjustUsersBalance(transaction);
        // Then
        assertThat(sender.getBalance()).isEqualTo(expectedSenderBalance);
        assertThat(receiver.getBalance()).isEqualTo(expectedReceiverBalance);
    }
}
