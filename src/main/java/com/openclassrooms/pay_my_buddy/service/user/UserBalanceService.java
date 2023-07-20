package com.openclassrooms.pay_my_buddy.service.user;

import com.openclassrooms.pay_my_buddy.model.Transaction;
import com.openclassrooms.pay_my_buddy.model.User;
import org.springframework.stereotype.Service;

@Service
public class UserBalanceService {

    public void adjustUsersBalance(Transaction transaction) {
        User sender = transaction.getSender();
        User receiver = transaction.getReceiver();
        sender.setBalance(sender.getBalance() - transaction.getAmount() - transaction.getFee());
        receiver.setBalance(receiver.getBalance() + transaction.getAmount());
    }

}
