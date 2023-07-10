package com.openclassrooms.pay_my_buddy.controller;

import com.openclassrooms.pay_my_buddy.model.user.dto.AddConnectionDto;
import com.openclassrooms.pay_my_buddy.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping(value = "/users/mine/connections", consumes = "application/json")
    public ResponseEntity<Void> addConnection(
            @RequestBody AddConnectionDto addConnectionDto
            ) {
        userService.addConnection(addConnectionDto);
        return ResponseEntity.noContent().build();
    }
}
