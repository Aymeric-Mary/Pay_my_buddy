package com.openclassrooms.pay_my_buddy.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

public record WithdrawRequestDto(
        @NotNull(message = "The amount of withdraw can not be null")
        @Min(value = 10, message = "The amount of withdraw can not be under 10€")
        Float withdrawAmount
)
{
}
