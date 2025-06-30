package com.palomino.confecontrol.other;


import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;

public class CookieUtil {

    public static void removeJwtCookie(HttpServletResponse response) {
        Cookie jwtCookie = new Cookie("JWT", null);
        jwtCookie.setHttpOnly(true);
        jwtCookie.setMaxAge(0); // elimina la cookie
        jwtCookie.setPath("/");
        response.addCookie(jwtCookie);
        System.out.println("Limpiando cookies en utilidad");
    }
}
