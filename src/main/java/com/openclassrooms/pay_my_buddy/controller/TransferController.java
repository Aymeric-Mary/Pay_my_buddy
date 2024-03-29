package com.openclassrooms.pay_my_buddy.controller;

import com.openclassrooms.pay_my_buddy.dto.TransactionRequestDto;
import com.openclassrooms.pay_my_buddy.dto.TransactionResponseDto;
import com.openclassrooms.pay_my_buddy.dto.UserDto;
import com.openclassrooms.pay_my_buddy.mapper.UserMapper;
import com.openclassrooms.pay_my_buddy.model.User;
import com.openclassrooms.pay_my_buddy.service.auth.AuthService;
import com.openclassrooms.pay_my_buddy.service.transaction.TransactionService;
import com.openclassrooms.pay_my_buddy.service.user.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;

import java.util.List;
import java.util.Map;

@Controller
@RequiredArgsConstructor
public class TransferController {

    private final UserService userService;
    private final UserMapper userMapper;
    private final TransactionService transactionService;
    private final AuthService authService;

    private final int PAGE_SIZE = 5;

    @GetMapping("/transfer")
    public ModelAndView transfer(@RequestParam(defaultValue = "1") int page) {
        List<UserDto> users = userMapper.entitiesToUserDtos(userService.getConnectableUsers());
        List<UserDto> connections = userMapper.entitiesToUserDtos(userService.getConnections());
        Page<TransactionResponseDto> transactions = transactionService.getTransactionResponseDtos(PageRequest.of(page - 1, PAGE_SIZE));
        ModelAndView mv = new ModelAndView("transfer");
        return mv.addAllObjects(Map.of(
                "users", users,
                "connections", connections,
                "transactions", transactions
        ));
    }

    @PostMapping(value = "/transfer", consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    public RedirectView transfer(@ModelAttribute @Valid TransactionRequestDto requestDto, BindingResult result, RedirectAttributes redirectAttributes) {
        if (result.hasErrors()) {
            redirectAttributes.addFlashAttribute("errors", result.getFieldErrors());
            return new RedirectView("/transfer", true);
        }
        User connectedUser = authService.getConnectedUser();
        try {
            transactionService.createTransaction(connectedUser, requestDto);
            redirectAttributes.addFlashAttribute("success", true);
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errors", List.of(
                    new ObjectError("amount", e.getMessage())
            ));
        }
        return new RedirectView("/transfer", true);
    }
}
