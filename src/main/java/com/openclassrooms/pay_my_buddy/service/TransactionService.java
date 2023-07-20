package com.openclassrooms.pay_my_buddy.service;

import com.openclassrooms.pay_my_buddy.dto.TransactionRequestDto;
import com.openclassrooms.pay_my_buddy.dto.TransactionResponseDto;
import com.openclassrooms.pay_my_buddy.mapper.TransactionMapper;
import com.openclassrooms.pay_my_buddy.model.Transaction;
import com.openclassrooms.pay_my_buddy.model.User;
import com.openclassrooms.pay_my_buddy.repository.TransactionRepository;
import com.openclassrooms.pay_my_buddy.service.auth.AuthService;
import com.openclassrooms.pay_my_buddy.service.user.UserBalanceService;
import com.openclassrooms.pay_my_buddy.service.user.UserService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class TransactionService {

    private final AuthService authService;
    private final UserService userService;
    private final UserBalanceService userBalanceService;
    private final TransactionRepository transactionRepository;
    private final TransactionMapper transactionMapper;

    /**
     * Create a transaction
     *
     * @param requestDto the transaction request dto
     * @return the transaction
     */
    public Transaction createTransaction(User sender, TransactionRequestDto requestDto) {
        User receiver = userService.getUserById(requestDto.connectionId());
        Transaction transaction = transactionMapper.toEntity(sender, receiver, requestDto);
        userBalanceService.adjustUsersBalance(transaction);
        return transactionRepository.save(transaction);
    }

    /**
     * Get all transactions of the user connected
     *
     * @return the list of transactions
     */

    public List<TransactionResponseDto> getTransactionResponseDtos() {
        User connectedUser = authService.getConnectedUser();
        List<Transaction> transactions = getTransactionsByUser(connectedUser);
        return transactions.stream()
                .map(transaction -> convertToDto(transaction, connectedUser))
                .toList();
    }

    private List<Transaction> getTransactionsByUser(User user) {
        return transactionRepository.findByUser(user);
    }

    private TransactionResponseDto convertToDto(Transaction transaction, User connectedUser) {
        if (transaction.getSender().equals(connectedUser)) {
            return transactionMapper.sendTransactionToDto(transaction);
        } else {
            return transactionMapper.receivedTransactionToDto(transaction);
        }
    }
}
