package com.openclassrooms.pay_my_buddy.mapper;

import com.openclassrooms.pay_my_buddy.dto.TransactionRequestDto;
import com.openclassrooms.pay_my_buddy.dto.TransactionResponseDto;
import com.openclassrooms.pay_my_buddy.model.Transaction;
import com.openclassrooms.pay_my_buddy.model.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper
public interface TransactionMapper {

    @Mapping(source = "sender", target = "user")
    TransactionResponseDto receivedTransactionToDto(Transaction transaction);

    @Mapping(source = "receiver", target = "user")
    @Mapping(target = "amount", expression = "java(-transaction.getAmount())")
    TransactionResponseDto sendTransactionToDto(Transaction transaction);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "sendDate", ignore = true)
    @Mapping(target = "description", source = "requestDto.description")
    @Mapping(target = "amount", source = "requestDto.amount")
    @Mapping(target = "sender", source = "userConnected")
    @Mapping(target = "receiver", source = "friend")
    Transaction toEntity(User userConnected, User friend, TransactionRequestDto requestDto);
}
