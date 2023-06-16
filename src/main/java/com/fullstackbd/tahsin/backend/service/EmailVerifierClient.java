package com.fullstackbd.tahsin.backend.service;

import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.service.annotation.GetExchange;
import org.springframework.web.service.annotation.HttpExchange;

@HttpExchange
public interface EmailVerifierClient {
    @GetExchange("/")
    Boolean verifyEmail(@RequestParam("email") String email);
}
