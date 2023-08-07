package com.openclassrooms.pay_my_buddy.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

public record DepositRequestDto(
        @NotNull(message = "The amount of deposit can not be null")
        @Min(value = 10, message = "The amount of deposit can not be under 10€")
        Float depositAmount
)
{
}
