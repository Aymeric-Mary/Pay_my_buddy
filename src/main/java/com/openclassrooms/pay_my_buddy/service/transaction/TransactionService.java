package com.openclassrooms.pay_my_buddy.service.transaction;

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
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

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
    public Transaction createTransaction(User sender, TransactionRequestDto requestDto) throws IllegalArgumentException {
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

    public Page<TransactionResponseDto> getTransactionResponseDtos(Pageable pageable) {
        User connectedUser = authService.getConnectedUser();
        Page<Transaction> transactions = transactionRepository.findByUser(connectedUser, pageable);
        return transactions.map(transaction -> convertToDto(transaction, connectedUser));
    }

    private TransactionResponseDto convertToDto(Transaction transaction, User connectedUser) {
        if (transaction.getSender().equals(connectedUser)) {
            return transactionMapper.sendTransactionToDto(transaction);
        } else {
            return transactionMapper.receivedTransactionToDto(transaction);
        }
    }
}
