package ru.dsis.atms.auth.cookie;

import jakarta.servlet.http.Cookie;

public class CookieProvider {
    private int cookieAge;
    private boolean isHttpOnly;
    private boolean isSecure;

    public CookieProvider(int cookieAge, boolean isHttpOnly, boolean isSecure) {
        this.cookieAge = cookieAge;
        this.isHttpOnly = isHttpOnly;
        this.isSecure = isSecure;
    }

    public Cookie getCookie(String token) {
        var cookie = new Cookie("jwtToken", token);
        cookie.setMaxAge(cookieAge);
        cookie.setHttpOnly(isHttpOnly);
        cookie.setSecure(isSecure);
        cookie.setPath("/");

        return cookie;
    }
}
