package com.openclassrooms.pay_my_buddy.controller;

import com.openclassrooms.pay_my_buddy.model.user.dto.UserDto;
import com.openclassrooms.pay_my_buddy.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class TransferController {

    private final UserService userService;

    @GetMapping("/transfer")
    public ModelAndView transfer() {
        List<UserDto> users = userService.getAllNotConnectedUsers();
        List<UserDto> connections = userService.getConnections();
        ModelAndView mv = new ModelAndView("transfer");
        mv.addObject("users", users);
        mv.addObject("connections", connections);
        return mv;
    }

}
