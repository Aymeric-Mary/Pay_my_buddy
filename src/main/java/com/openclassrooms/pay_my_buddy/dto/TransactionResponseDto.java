package com.openclassrooms.pay_my_buddy.dto;

import lombok.Builder;

@Builder
public record TransactionResponseDto (
    Long id,
    UserDto user,
    Float amount,
    Float fee,
    String description
){}
