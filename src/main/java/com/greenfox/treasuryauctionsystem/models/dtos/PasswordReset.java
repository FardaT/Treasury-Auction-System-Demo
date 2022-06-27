package com.greenfox.treasuryauctionsystem.models.dtos;

public class PasswordReset {

  private String password;
  private String confirm;
  private String token;

  public PasswordReset() {
  }

  public PasswordReset(String password, String confirm, String token) {
    this.password = password;
    this.confirm = confirm;
    this.token = token;
  }

  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  public String getConfirm() {
    return confirm;
  }

  public void setConfirm(String confirm) {
    this.confirm = confirm;
  }

  public String getToken() {
    return token;
  }

  public void setToken(String token) {
    this.token = token;
  }
}
