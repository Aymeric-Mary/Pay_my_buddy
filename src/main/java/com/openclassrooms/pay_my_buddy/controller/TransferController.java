package com.openclassrooms.pay_my_buddy.controller;

import com.openclassrooms.pay_my_buddy.model.transaction.dto.TransactionRequestDto;
import com.openclassrooms.pay_my_buddy.model.transaction.dto.TransactionResponseDto;
import com.openclassrooms.pay_my_buddy.model.user.dto.UserDto;
import com.openclassrooms.pay_my_buddy.service.TransactionService;
import com.openclassrooms.pay_my_buddy.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class TransferController {

    private final UserService userService;
    private final TransactionService transactionService;

    @GetMapping("/transfer")
    public ModelAndView transfer() {
        List<UserDto> users = userService.getAllNotConnectedUsers();
        List<UserDto> connections = userService.getConnections();
        List<TransactionResponseDto> transactions = transactionService.getTransactions();
        ModelAndView mv = new ModelAndView("transfer");
        mv.addObject("users", users);
        mv.addObject("connections", connections);
        mv.addObject("transactions", transactions);
        mv.addObject("transactions", transactions);
        return mv;
    }

    @PostMapping(value = "/transfer", consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    public ModelAndView transfer(@ModelAttribute TransactionRequestDto requestDto) {
        transactionService.createTransaction(requestDto);
        return new ModelAndView("redirect:/transfer", "success", true);
    }

}
