package com.openclassrooms.pay_my_buddy.controller;

import com.openclassrooms.pay_my_buddy.dto.TransactionRequestDto;
import com.openclassrooms.pay_my_buddy.dto.TransactionResponseDto;
import com.openclassrooms.pay_my_buddy.dto.UserDto;
import com.openclassrooms.pay_my_buddy.mapper.UserMapper;
import com.openclassrooms.pay_my_buddy.model.User;
import com.openclassrooms.pay_my_buddy.service.TransactionService;
import com.openclassrooms.pay_my_buddy.service.auth.AuthService;
import com.openclassrooms.pay_my_buddy.service.user.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class TransferController {

    private final UserService userService;
    private final UserMapper userMapper;
    private final TransactionService transactionService;
    private final AuthService authService;

    @GetMapping("/transfer")
    public ModelAndView transfer() {
        User connectedUser = authService.getConnectedUser();
        List<UserDto> users = userMapper.entitiesToUserDtos(userService.getConnectableUsers());
        List<UserDto> connections = userMapper.entitiesToUserDtos(userService.getConnections());
        List<TransactionResponseDto> transactions = transactionService.getTransactionResponseDtos();
        ModelAndView mv = new ModelAndView("transfer");
        mv.addObject("connectedUser", connectedUser);
        mv.addObject("users", users);
        mv.addObject("connections", connections);
        mv.addObject("transactions", transactions);
        return mv;
    }

    @PostMapping(value = "/transfer", consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    public RedirectView transfer(@ModelAttribute @Valid TransactionRequestDto requestDto, BindingResult result, RedirectAttributes redirectAttributes) {
        if (result.hasErrors()) {
            redirectAttributes.addFlashAttribute("errors", result.getFieldErrors());
            return new RedirectView("/transfer", true);
        }
        User connectedUser = authService.getConnectedUser();
        transactionService.createTransaction(connectedUser, requestDto);
        redirectAttributes.addFlashAttribute("success", true);
        return new RedirectView("/transfer", true);
    }

}
