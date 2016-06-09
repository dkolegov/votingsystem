package com.voting.system.utils;

import java.util.ArrayList;
import java.util.List;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

public class EncryptPassword{
	
	private static List<String> passwords = new ArrayList<String>(){{
		add("qwerty");
		add("123456");
		add("qwerty123");
	}};

	public static void main(String args[]) throws Exception {
		PasswordEncoder passwordEncoder = new BCryptPasswordEncoder(11);
		for (String password : passwords) {
			System.out.println(" "+password+": " + passwordEncoder.encode(password));
		}
	}
}