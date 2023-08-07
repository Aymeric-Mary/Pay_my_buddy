package com.openclassrooms.pay_my_buddy.service.bankAccount;

import com.openclassrooms.pay_my_buddy.dto.DepositRequestDto;
import com.openclassrooms.pay_my_buddy.dto.WithdrawRequestDto;
import com.openclassrooms.pay_my_buddy.model.User;
import com.openclassrooms.pay_my_buddy.service.auth.AuthService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Transactional
public class BankAccountService {

    private final AuthService authService;

    public boolean deposit(DepositRequestDto requestDto) {
        User user = getConnectedUser();
        float newBankAccountBalance = user.getBankAccount().getBalance() - requestDto.depositAmount();
        if (newBankAccountBalance < 0) {
            return false;
        }
        user.setBalance(user.getBalance() + requestDto.depositAmount());
        user.getBankAccount().setBalance(user.getBankAccount().getBalance() - requestDto.depositAmount());
        return true;
    }

    public boolean withdraw(WithdrawRequestDto requestDto) {
        User user = getConnectedUser();
        float newBalance = user.getBalance() - requestDto.withdrawAmount();
        if (newBalance < 0) {
            return false;
        }
        user.getBankAccount().setBalance(user.getBankAccount().getBalance() + requestDto.withdrawAmount());
        user.setBalance(user.getBalance() - requestDto.withdrawAmount());
        return true;
    }

    private User getConnectedUser() {
        return authService.getConnectedUser();
    }
}
