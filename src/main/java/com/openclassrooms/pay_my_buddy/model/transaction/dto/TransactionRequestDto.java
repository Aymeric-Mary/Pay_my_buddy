package com.openclassrooms.pay_my_buddy.model.transaction.dto;

import lombok.Data;

@Data
public class TransactionRequestDto {
    private Long connectionId;
    private Integer amount;
    private String description;
}
