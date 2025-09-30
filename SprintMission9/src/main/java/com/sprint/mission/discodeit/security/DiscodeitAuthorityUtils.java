package com.sprint.mission.discodeit.security;

import java.util.List;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.stereotype.Component;

@Component
public class DiscodeitAuthorityUtils {
  @Value("${mail.address.admin}")
  private String adminMailAddress;

  private final List<GrantedAuthority> ADMIN_ROLES = AuthorityUtils.createAuthorityList("ROLE_ADMIN", "ROLE_USER");
  private final List<GrantedAuthority> USER_ROLES = AuthorityUtils.createAuthorityList("ROLE_USER");

  public List<GrantedAuthority> createAuthorities(String email){
    if (email.equals(adminMailAddress)){
      return ADMIN_ROLES;
    }
    return USER_ROLES;
  }
}
