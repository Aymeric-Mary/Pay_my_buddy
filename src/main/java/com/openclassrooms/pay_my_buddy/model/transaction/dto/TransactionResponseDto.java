package com.openclassrooms.pay_my_buddy.model.transaction.dto;

import com.openclassrooms.pay_my_buddy.model.user.dto.UserDto;
import lombok.Data;

@Data
public class TransactionResponseDto {
    private Integer id;
    private UserDto user;
    private Integer amount;
    private String description;
}
