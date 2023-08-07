package com.openclassrooms.pay_my_buddy.advice;

import com.openclassrooms.pay_my_buddy.service.auth.AuthService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

@ControllerAdvice
@Component
@RequiredArgsConstructor
public class GlobalModelAttributeAdvice {

    private final AuthService authService;

    @ModelAttribute
    public void addConnectedUser(Model model) {
        authService.getOptionalConnectedUser()
                .ifPresent(user -> model.addAttribute("connectedUser", user));
    }

    @ModelAttribute
    public void addCurrentUri(HttpServletRequest request){
        request.setAttribute("currentUri", request.getRequestURI());
    }
}
