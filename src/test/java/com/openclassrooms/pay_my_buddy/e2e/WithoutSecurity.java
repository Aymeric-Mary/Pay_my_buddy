package com.openclassrooms.pay_my_buddy.e2e;

import com.openclassrooms.pay_my_buddy.config.SecurityTestConfig;
import org.springframework.test.context.ContextConfiguration;

@ContextConfiguration(classes = SecurityTestConfig.class)
public abstract class WithoutSecurity extends AbstractE2E {}
