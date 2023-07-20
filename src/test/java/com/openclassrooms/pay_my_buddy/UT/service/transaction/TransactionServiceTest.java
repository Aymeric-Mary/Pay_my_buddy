package com.openclassrooms.pay_my_buddy.UT.service.transaction;

import com.openclassrooms.pay_my_buddy.dto.TransactionRequestDto;
import com.openclassrooms.pay_my_buddy.dto.TransactionResponseDto;
import com.openclassrooms.pay_my_buddy.mapper.TransactionMapper;
import com.openclassrooms.pay_my_buddy.model.Transaction;
import com.openclassrooms.pay_my_buddy.model.User;
import com.openclassrooms.pay_my_buddy.repository.TransactionRepository;
import com.openclassrooms.pay_my_buddy.service.auth.AuthService;
import com.openclassrooms.pay_my_buddy.service.transaction.TransactionService;
import com.openclassrooms.pay_my_buddy.service.user.UserBalanceService;
import com.openclassrooms.pay_my_buddy.service.user.UserService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class TransactionServiceTest {

    @Mock
    private AuthService authServiceMock;

    @Mock
    private UserService userServiceMock;

    @Mock
    private TransactionMapper transactionMapperMock;

    @Mock
    private UserBalanceService userBalanceServiceMock;

    @Mock
    private TransactionRepository transactionRepositoryMock;

    @InjectMocks
    private TransactionService transactionService;

    @Test
    void shouldCreateTransaction() {
        // Given
        User sender = User.builder().id(1L).build();
        User receiver = User.builder().id(2L).build();
        TransactionRequestDto requestDto = new TransactionRequestDto(2L, 100f, "description");

        Transaction expectedTransaction = Transaction.builder().build();
        when(userServiceMock.getUserById(requestDto.connectionId())).thenReturn(receiver);
        when(transactionMapperMock.toEntity(sender, receiver, requestDto)).thenReturn(expectedTransaction);
        when(transactionRepositoryMock.save(expectedTransaction)).thenReturn(expectedTransaction);
        //When
        Transaction transaction = transactionService.createTransaction(sender, requestDto);
        // Then
        verify(userBalanceServiceMock).adjustUsersBalance(expectedTransaction);
        assertThat(expectedTransaction).isEqualTo(transaction);
    }

    @Test
    void shouldGetTransactionResponseDtosWhenConnectedUserIsSender() {
        // Given
        User connectedUser = new User();
        User connection = new User();
        Transaction transaction1 = Transaction.builder().sender(connectedUser).receiver(connection).build();
        Transaction transaction2 = Transaction.builder().sender(connectedUser).receiver(connection).build();
        Transaction transaction3 = Transaction.builder().sender(connection).receiver(connectedUser).build();
        List<Transaction> transactions = List.of(transaction1, transaction2, transaction3);
        TransactionResponseDto dto1 = TransactionResponseDto.builder().build();
        TransactionResponseDto dto2 = TransactionResponseDto.builder().build();
        TransactionResponseDto dto3 = TransactionResponseDto.builder().build();

        when(authServiceMock.getConnectedUser()).thenReturn(connectedUser);
        when(transactionRepositoryMock.findByUser(connectedUser)).thenReturn(transactions);
        when(transactionMapperMock.sendTransactionToDto(transaction1)).thenReturn(dto1);
        when(transactionMapperMock.sendTransactionToDto(transaction2)).thenReturn(dto2);
        when(transactionMapperMock.receivedTransactionToDto(transaction3)).thenReturn(dto3);
        // When
        List<TransactionResponseDto> result = transactionService.getTransactionResponseDtos();
        // Then
        verify(authServiceMock, times(1)).getConnectedUser();
        verify(transactionRepositoryMock, times(1)).findByUser(connectedUser);
        verify(transactionMapperMock, times(2)).sendTransactionToDto(any(Transaction.class));
        verify(transactionMapperMock, times(1)).receivedTransactionToDto(any(Transaction.class));
        assertThat(result).containsExactly(dto1, dto2, dto3);
    }
}