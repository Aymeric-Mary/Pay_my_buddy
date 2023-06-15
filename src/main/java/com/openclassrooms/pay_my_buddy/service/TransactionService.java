package com.openclassrooms.pay_my_buddy.service;

import com.openclassrooms.pay_my_buddy.model.Transaction;
import com.openclassrooms.pay_my_buddy.model.TransactionMapper;
import com.openclassrooms.pay_my_buddy.model.User;
import com.openclassrooms.pay_my_buddy.model.transaction.dto.TransactionRequestDto;
import com.openclassrooms.pay_my_buddy.model.transaction.dto.TransactionResponseDto;
import com.openclassrooms.pay_my_buddy.repository.TransactionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TransactionService {

    private final UserService userService;
    private final TransactionRepository transactionRepository;
    private final TransactionMapper transactionMapper;

    public Transaction createTransaction(TransactionRequestDto requestDto) {
        User userConnected = userService.getUserConnected();
        User friend = userService.getUser(requestDto.getConnectionId());
        Transaction transaction = transactionMapper.toEntity(userConnected, friend, requestDto);
        return transactionRepository.save(transaction);
    }

    public List<TransactionResponseDto> getTransactions() {
        User user = userService.getUserConnected();
        List<Transaction> transactions = transactionRepository.findByUser(user);
        return transactionMapper.toDtos(transactions);
    }
}
