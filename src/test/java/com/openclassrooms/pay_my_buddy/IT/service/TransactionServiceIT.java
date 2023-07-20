package com.openclassrooms.pay_my_buddy.IT.service;

import com.openclassrooms.pay_my_buddy.DataTools;
import com.openclassrooms.pay_my_buddy.dto.TransactionRequestDto;
import com.openclassrooms.pay_my_buddy.dto.TransactionResponseDto;
import com.openclassrooms.pay_my_buddy.dto.UserDto;
import com.openclassrooms.pay_my_buddy.model.Transaction;
import com.openclassrooms.pay_my_buddy.model.User;
import com.openclassrooms.pay_my_buddy.service.auth.AuthService;
import com.openclassrooms.pay_my_buddy.service.transaction.TransactionService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

public class TransactionServiceIT extends DataTools {

    @MockBean
    private AuthService authServiceMock;

    @Autowired
    private TransactionService transactionService;

    private User connectedUser;

    @BeforeEach
    public void setup() {
        this.connectedUser = createUser("user", "connected", "connected@gmail.com");
        when(authServiceMock.getConnectedUser()).thenReturn(this.connectedUser);
    }

    @Nested
    class CreateTransaction {
        @Test
        @Disabled
        public void testCreateTransaction() {
            // Given
            User friend = createUser("friend", "friend", "friend@gmail.com");
            connectedUser.setBalance(1000f);
            TransactionRequestDto dto = new TransactionRequestDto(friend.getId(), 200f, "description");
            // When
            Transaction transaction = transactionService.createTransaction(friend, dto);
            // Then
            assertThat(transaction.getAmount()).isEqualTo(200);
            assertThat(transaction.getDescription()).isEqualTo("description");
            assertThat(transaction.getReceiver()).isEqualTo(friend);
            assertThat(transaction.getSender()).isEqualTo(connectedUser);
            assertThat(connectedUser.getBalance()).isEqualTo(799);
        }
    }

    @Nested
    class GetTransactionsByUser {
        @Test
        @Disabled
        public void testGetTransactionResponseDtos() {
            // Given
            User friend = createUser("friend", "friend", "friend@gmail.com");
            User friend2 = createUser("jean", "dupond", "jean@gmail.com");
            Transaction transaction1 = createTransaction(connectedUser, friend, 200.0f, "description");
            Transaction transaction2 = createTransaction(friend2, connectedUser, 300.0f, "description2");
            // When
            List<TransactionResponseDto> transactions = transactionService.getTransactionResponseDtos();
            // Then
            List<TransactionResponseDto> expectedTransactionResponseDtos = List.of(
                    TransactionResponseDto.builder()
                            .id(transaction1.getId())
                            .user(UserDto.builder().id(friend.getId()).firstname("friend").lastname("friend").build())
                            .amount(-200.0f)
                            .description("description")
                            .build(),
                    TransactionResponseDto.builder()
                            .id(transaction2.getId())
                            .user(UserDto.builder().id(friend2.getId()).firstname("jean").lastname("dupond").build())
                            .amount(300.0f)
                            .description("description2")
                            .build()
            );
            assertThat(transactions)
                    .usingRecursiveFieldByFieldElementComparator()
                    .isEqualTo(expectedTransactionResponseDtos);
        }
    }

}
