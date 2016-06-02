package com.voting.system.core;

import java.time.LocalTime;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

public class User {


	public static String getId() {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
	    return auth.getName();
	}

	public static boolean isChangedHisMind() {
		LocalTime time = LocalTime.now();
		return time.isBefore(LocalTime.of(11, 0));
	}
}
