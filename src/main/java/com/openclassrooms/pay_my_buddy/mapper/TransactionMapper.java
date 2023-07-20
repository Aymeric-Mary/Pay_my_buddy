package com.openclassrooms.pay_my_buddy.mapper;

import com.openclassrooms.pay_my_buddy.dto.TransactionRequestDto;
import com.openclassrooms.pay_my_buddy.dto.TransactionResponseDto;
import com.openclassrooms.pay_my_buddy.model.Transaction;
import com.openclassrooms.pay_my_buddy.model.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

@Mapper
public interface TransactionMapper {

    @Mapping(target = "user", source = "sender")
    @Mapping(target = "fee", constant = "0f")
    TransactionResponseDto receivedTransactionToDto(Transaction transaction);

    @Mapping(target = "user", source = "receiver")
    @Mapping(target = "amount", source = "transaction", qualifiedByName = "calculateSendAmount")
    TransactionResponseDto sendTransactionToDto(Transaction transaction);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "sendDate", ignore = true)
    @Mapping(target = "description", source = "requestDto.description")
    @Mapping(target = "amount", source = "requestDto.amount")
    @Mapping(target = "fee", source = "requestDto.amount", qualifiedByName = "calculateFee")
    @Mapping(target = "sender", source = "userConnected")
    @Mapping(target = "receiver", source = "friend")
    Transaction toEntity(User userConnected, User friend, TransactionRequestDto requestDto);

    @Named("calculateFee")
    default Float calculateFee(Float amount) {
        return amount * 0.005f;
    }

    @Named("calculateSendAmount")
    default Float calculateSendAmount(Transaction transaction) {
        return -(transaction.getAmount() + transaction.getFee());
    }
}
