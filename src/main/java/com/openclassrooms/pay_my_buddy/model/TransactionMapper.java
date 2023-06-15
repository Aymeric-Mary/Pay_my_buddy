package com.openclassrooms.pay_my_buddy.model;

import com.openclassrooms.pay_my_buddy.model.transaction.dto.TransactionRequestDto;
import com.openclassrooms.pay_my_buddy.model.transaction.dto.TransactionResponseDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper
public interface TransactionMapper {

    List<TransactionResponseDto> toDtos(List<Transaction> transactions);

    @Mapping(source = "receiver", target = "user")
    TransactionResponseDto toDto(Transaction transaction);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "sendDate", ignore = true)
    @Mapping(target = "description", source = "requestDto.description")
    @Mapping(target = "amount", source = "requestDto.amount")
    @Mapping(target = "sender", source = "userConnected")
    @Mapping(target = "receiver", source = "friend")
    Transaction toEntity(User userConnected, User friend, TransactionRequestDto requestDto);
}
