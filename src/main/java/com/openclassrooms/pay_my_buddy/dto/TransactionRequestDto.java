package com.openclassrooms.pay_my_buddy.dto;

public record TransactionRequestDto(
        Long connectionId,
        Float amount,
        String description
){}
