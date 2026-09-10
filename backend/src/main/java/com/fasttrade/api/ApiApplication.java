package com.fasttrade.api;

import com.fasttrade.api.user.User;
import com.fasttrade.api.user.UserRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootApplication
public class ApiApplication {

    public static void main(String[] args) {
        SpringApplication.run(ApiApplication.class, args);
    }

    /** Seeds/reset the admin so login always works with the configured password. */
    @Bean
    CommandLineRunner seedAdmin(UserRepository users, PasswordEncoder encoder,
                                @Value("${app.admin.email}") String email,
                                @Value("${app.admin.password}") String password) {
        return args -> {
            User admin = users.findByEmail(email).orElseGet(User::new);
            admin.setName("Admin");
            admin.setEmail(email);
            admin.setPassword(encoder.encode(password));
            admin.setRole("ADMIN");
            users.save(admin);
        };
    }
}
