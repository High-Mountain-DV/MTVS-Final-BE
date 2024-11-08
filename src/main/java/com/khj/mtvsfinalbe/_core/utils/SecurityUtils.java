package com.khj.mtvsfinalbe._core.utils;

import org.springframework.security.core.context.SecurityContextHolder;

public class SecurityUtils {

    public static Long getCurrentUserId() {

        String name = SecurityContextHolder.getContext().getAuthentication().getName();

        System.out.println("name = " + name);

        if(name.equals("anonymousUser")) {
            throw new RuntimeException("Anonymous User");
        }

        return Long.parseLong(name);
    }
}

