package com.sprint.mission.discodeit.config;

import com.sprint.mission.discodeit.entity.User;
import com.sprint.mission.discodeit.entity.UserStatus;
import com.sprint.mission.discodeit.repository.UserRepository;
import com.sprint.mission.discodeit.repository.UserStatusRepository;
import com.sprint.mission.discodeit.security.Role;
import java.time.Instant;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class AdminInitializer implements CommandLineRunner {

  private final UserRepository userRepository;
  private final UserStatusRepository userStatusRepository;
  private final PasswordEncoder passwordEncoder;

  @Override
  public void run(String... args) {
    String adminEmail = "admin@email.com";

    if (!userRepository.existsByEmail(adminEmail)) {
      User admin = new User(
          "admin",
          adminEmail,
          passwordEncoder.encode("1111!"),
          null
      );
      admin.updateRole(Role.ADMIN);
      UserStatus adminStatus = new UserStatus(admin, Instant.now());
      userRepository.save(admin);
      userStatusRepository.save(adminStatus);
      System.out.println("Admin account has been created");
    }
  }
}
