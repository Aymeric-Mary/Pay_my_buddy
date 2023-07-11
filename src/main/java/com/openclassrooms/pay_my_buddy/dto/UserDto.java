package com.openclassrooms.pay_my_buddy.dto;

import lombok.Builder;

@Builder
public record UserDto (
    Long id,
    String firstname,
    String lastname
){}
