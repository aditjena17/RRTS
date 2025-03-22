package com.RRTS.RRTS.classes;

import java.util.Optional;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {
	private UserRepository userRepository;
//    private BCryptPasswordEncoder passwordEncoder;

    public AuthService(UserRepository userRepository) {
        this.userRepository = userRepository;
//        this.passwordEncoder = new BCryptPasswordEncoder();
    }

    public String authenticateUser(String email, String rawPassword, String expectedRole) {
        User userOpt = userRepository.findByEmail(email);

        if (userOpt == null) {
            return "Email not found";
        }

        User user = userOpt;

//        if (!passwordEncoder.matches(rawPassword, user.getPassword())) {
//            return "Incorrect password";
//        }
        if(!rawPassword.equalsIgnoreCase(user.getPassword())) {
        	return "Incorrect password";
        }

        if (!user.getRole().equalsIgnoreCase(expectedRole)) {
            return "Incorrect role";
        }

        return "Login successful";
    }
}
