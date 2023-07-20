package com.openclassrooms.pay_my_buddy.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

public record TransactionRequestDto(
        @NotNull(message = "The connection can not be null")
        Long connectionId,
        @NotNull(message = "The amount of transaction can not be null")
        @Min(value = 2, message = "The amount of transaction can not be under 2€")
        Float amount,
        String description
) {
}
