package ru.javawebinar.topjava.web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static ru.javawebinar.topjava.util.MealsUtil.DEFAULT_CALORIES_PER_DAY;

public class SecurityUtil {
    private static final Logger log = LoggerFactory.getLogger(SecurityUtil.class);

    private static int authId = 1;

    public static void setAuthId(int authId) {
        SecurityUtil.authId = authId;
    }

    public static int authUserId() {
        return authId;
    }

    public static int authUserCaloriesPerDay() {
        return DEFAULT_CALORIES_PER_DAY;
    }
}