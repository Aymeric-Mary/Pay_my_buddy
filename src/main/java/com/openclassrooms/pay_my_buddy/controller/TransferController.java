package com.openclassrooms.pay_my_buddy.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class TransferController {

    @GetMapping("/transfer")
    public String transfer() {
        return "transfer";
    }

}
