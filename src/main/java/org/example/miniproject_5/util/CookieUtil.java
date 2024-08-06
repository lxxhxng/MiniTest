package org.example.miniproject_5.util;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;

import java.util.Arrays;
import java.util.Map;

public class CookieUtil {

    public static Map<Integer, String> parseStr(HttpServletRequest req) {

        Map<Integer,String> answerMap = Arrays.stream(req.getCookies())
                .filter(cookie -> cookie.getName().equals("answer"))
                .findFirst()
                .map(cookie -> cookie.getValue())
                .map(value -> Arrays.stream(value.split("&"))
                        .map(str -> str.split(":"))
                        .collect(java.util.stream.Collectors.toMap(str -> Integer.parseInt(str[0]), str -> str[1])))
                .orElse(null);

        return answerMap;
    }

    public static Cookie getCookie(HttpServletRequest request, final String name) {

        Cookie[] cookies = request.getCookies();
        if(cookies == null || cookies.length == 0) {
            return null;
        }
        for(Cookie cookie : cookies) {
            if(cookie.getName().equals(name)) {
                return cookie;
            }
        }
        return null;
    }
}
}