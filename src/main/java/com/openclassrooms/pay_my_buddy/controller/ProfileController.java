package com.openclassrooms.pay_my_buddy.controller;

import com.openclassrooms.pay_my_buddy.dto.DepositRequestDto;
import com.openclassrooms.pay_my_buddy.dto.WithdrawRequestDto;
import com.openclassrooms.pay_my_buddy.service.bankAccount.BankAccountService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class ProfileController {

    private final BankAccountService bankAccountService;

    @GetMapping("/profile")
    public String profile() {
        return "profile";
    }

    @PostMapping(value = "/deposit", consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    public RedirectView deposit(@ModelAttribute @Valid DepositRequestDto requestDto, BindingResult result, RedirectAttributes redirectAttributes) {
        if (result.hasErrors()) {
            redirectAttributes.addFlashAttribute("depositErrors", result.getFieldErrors());
        } else {
            if (bankAccountService.deposit(requestDto)) {
                redirectAttributes.addFlashAttribute("depositSuccess", true);
            } else {
                redirectAttributes.addFlashAttribute("depositErrors", List.of(
                        new ObjectError("balance", "You don't have enough money to do this operation")
                ));
            }
        }
        return new RedirectView("/profile", true);
    }

    @PostMapping(value = "/withdraw", consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    public RedirectView withdraw(@ModelAttribute @Valid WithdrawRequestDto requestDto, BindingResult result, RedirectAttributes redirectAttributes) {
        if (result.hasErrors()) {
            redirectAttributes.addFlashAttribute("withdrawErrors", result.getFieldErrors());
        } else {
            if (bankAccountService.withdraw(requestDto)) {
                redirectAttributes.addFlashAttribute("withdrawSuccess", true);
            } else {
                redirectAttributes.addFlashAttribute("withdrawErrors", List.of(
                        new ObjectError("balance", "You don't have enough money to do this operation")
                ));
            }
        }
        return new RedirectView("/profile", true);
    }
}
