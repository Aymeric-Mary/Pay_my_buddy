package com.openclassrooms.pay_my_buddy;

import lombok.experimental.UtilityClass;

import java.time.Instant;

@UtilityClass
public class Utils {

    public static Instant now() {
        return Instant.now();
    }
}
