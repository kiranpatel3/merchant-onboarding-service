package com.kp.merchantonboardingservice.controller;

import com.kp.merchantonboardingservice.MerchantOnboardingServiceApplication;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.web.WebAppConfiguration;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = MerchantOnboardingServiceApplication.class)
@WebAppConfiguration(value = "")
public class BaseControllerTest {
}
