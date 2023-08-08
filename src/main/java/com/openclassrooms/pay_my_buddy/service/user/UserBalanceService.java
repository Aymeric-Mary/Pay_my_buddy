package com.openclassrooms.pay_my_buddy.service.user;

import com.openclassrooms.pay_my_buddy.model.Transaction;
import com.openclassrooms.pay_my_buddy.model.User;
import org.springframework.stereotype.Service;

@Service
public class UserBalanceService {

    public void adjustUsersBalance(Transaction transaction) throws IllegalArgumentException {
        User sender = transaction.getSender();
        User receiver = transaction.getReceiver();
        float newSenderBalance = sender.getBalance() - transaction.getAmount() - transaction.getFee();
        if(newSenderBalance < 0) {
            throw new IllegalArgumentException("You don't have enough money to do this operation");
        }
        sender.setBalance(sender.getBalance() - transaction.getAmount() - transaction.getFee());
        receiver.setBalance(receiver.getBalance() + transaction.getAmount());
    }

}
