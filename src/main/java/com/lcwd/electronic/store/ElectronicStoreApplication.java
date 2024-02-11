package com.lcwd.electronic.store;

import com.lcwd.electronic.store.entities.Role;
import com.lcwd.electronic.store.entities.User;
import com.lcwd.electronic.store.repositories.RoleRepository;
import com.lcwd.electronic.store.repositories.UserRepository;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import java.util.Arrays;
import java.util.Set;
import java.util.UUID;

@SpringBootApplication
@EnableWebMvc
public class ElectronicStoreApplication implements CommandLineRunner {
    public static void main(String[] args) {
        SpringApplication.run(ElectronicStoreApplication.class, args);
    }
    @Autowired
    private PasswordEncoder passwordEncoder;


    @Autowired
    private RoleRepository repository;

    @Autowired
    private UserRepository userRepository;


    @Value("${normal.role.id}")
    private String role_normal_id;

    @Value("${admin.role.id}")
    private String role_admin_id;

    @Override
    public void run(String... args) throws Exception {
        System.out.println(passwordEncoder.encode("abcd"));

        try{
            //adding the admin User
            Role role_admin = Role.builder().roleId(role_admin_id).roleName("ROLE_ADMIN").build();
            User user=User.builder()
                    .userId(UUID.randomUUID().toString())
                    .name("Pranay Mate")
                    .gender("Male")
                    .email("pranaymate0706@gmail.com")
                    .password(passwordEncoder.encode("pranay123"))
                    .about("Hi I am the admin User")
                    .imageName("pranay.jpg")
                    .roles(Set.of(role_admin))
                    .build();

                    User SavedUser=userRepository.save(user);
        }catch (Exception e)
        {
            e.printStackTrace();
        }

        try {
            Role role_admin = Role.builder().roleId(role_admin_id).roleName("ROLE_ADMIN").build();
            Role role_normal = Role.builder().roleId(role_normal_id).roleName("ROLE_NORMAL").build();
            repository.save(role_admin);
            repository.save(role_normal);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}


