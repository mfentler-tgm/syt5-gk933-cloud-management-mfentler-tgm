package com.hellokoding.auth.service;

public interface SecurityService {
    String findLoggedInEmail();

    void autologin(String email, String password);
}
