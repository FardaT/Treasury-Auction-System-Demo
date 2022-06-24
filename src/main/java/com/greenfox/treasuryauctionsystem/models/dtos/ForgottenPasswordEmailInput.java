package com.greenfox.treasuryauctionsystem.models.dtos;

public class ForgottenPasswordEmailInput {

  private String email;

  public ForgottenPasswordEmailInput() {
  }

  public ForgottenPasswordEmailInput(String email) {
    this.email = email;
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }
}
