package io.xstar.auth;

import org.springframework.security.crypto.bcrypt.BCrypt;

public class AuthApplicationTests {

  public void contextLoads() {
    String rawPwd="Pass1234";
    System.out.println(BCrypt.hashpw(rawPwd, BCrypt.gensalt()));
  }
}
