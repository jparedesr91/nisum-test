package com.nisum.interviewtest.user;

import com.nisum.interviewtest.user.model.Phone;
import com.nisum.interviewtest.user.model.Role;
import com.nisum.interviewtest.user.model.User;
import com.nisum.interviewtest.user.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Component
public class DataLoader implements ApplicationRunner {

    private UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    @Autowired
    public DataLoader(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public void run(ApplicationArguments args) {
        Phone phone = Phone.builder()
                .cityCode("123")
                .countryCode("123")
                .number("123")
                .build();
        List<Phone> phonesList = new ArrayList<>();
        phonesList.add(phone);

        List<Role> rolesList = new ArrayList<>();
        rolesList.add(Role.ROLE_ADMIN);

        User user = User.builder()
                .active(true)
                .id(UUID.randomUUID())
                .name("Admin")
                .username("admin@admin.com")
                .password(passwordEncoder.encode("admin"))
                .phones(phonesList)
                .roles(rolesList)
                .build();

        userRepository.save(user);
    }
}
