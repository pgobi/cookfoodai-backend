package com.pgobi.cookfood.ai.responses;

public class LoginResponse {
    private String accessToken;

    private long expiresIn;

    public String getaAcessToken() {
        return accessToken;
    }

    public LoginResponse setAccessToken(String token) {
        this.accessToken = token;
        return this;
    }

    public long getExpiresIn() {
        return expiresIn;
    }

    public LoginResponse setExpiresIn(long expiresIn) {
        this.expiresIn = expiresIn;
        return this;
    }

    @Override
    public String toString() {
        return "LoginResponse{" +
                "token='" + accessToken + '\'' +
                ", expiresIn=" + expiresIn +
                '}';
    }
}
