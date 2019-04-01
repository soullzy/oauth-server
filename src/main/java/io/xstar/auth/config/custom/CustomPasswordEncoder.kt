package io.xstar.auth.config.custom

import org.springframework.context.annotation.Bean
import org.springframework.security.crypto.bcrypt.BCrypt
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Component

/**
 * @author Li Zhengyue
 * @date 2019/3/29
 * @since JDK1.8
 */
@Component
class CustomPasswordEncoder : PasswordEncoder {
  /**
   * 一、明文存储，用于测试
   *  已过期
   *  NoOpPasswordEncoder.getInstance();
   *
   * 二、用 BCrypt 对密码编码
   *  BCryptPasswordEncoder();
   *
   * 三、支持多种编码，通过密码的前缀区分编码方式
   *  PasswordEncoderFactories.createDelegatingPasswordEncoder();
   *
   */

  /**
   * Encode the password.
   *
   * @param rawPassword raw password
   * @return encoded password
   */
  override fun encode(rawPassword: CharSequence): String {
    val rawPwd = rawPassword as String
    return BCrypt.hashpw(rawPwd, BCrypt.gensalt())
  }

  /**
   * Matches raw password and encoded password.
   *
   * @param rawPassword     raw password
   * @param encodedPassword encoded password
   * @return match or not
   */
  override fun matches(rawPassword: CharSequence, encodedPassword: String): Boolean {
    val rawPwd = rawPassword as String
    return BCrypt.checkpw(rawPwd, encodedPassword)
  }

  /*@Bean
  fun passwordEncoder(): PasswordEncoder {
    return BCryptPasswordEncoder()
  }*/
}