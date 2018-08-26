package com.taskagile.domain.common.security;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class PasswordEncryptorDelegator implements PasswordEncryptor {

  private PasswordEncoder passwordEncoder;

  public PasswordEncryptorDelegator(PasswordEncoder passwordEncoder) {
    this.passwordEncoder = passwordEncoder;
  }

  @Override
  public String encrypt(String rawPassword) {
    return passwordEncoder.encode(rawPassword);
  }
}
