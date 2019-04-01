package io.xstar.auth.config.custom

import io.xstar.auth.repository.UserRepository
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.stereotype.Service

/**
 * @author Li Zhengyue
 * @date 2019/3/29
 * @since JDK1.8
 */
@Service
class CustomUserDetailsService(private val userRepository: UserRepository) : UserDetailsService {

  @Throws(UsernameNotFoundException::class)
  override fun loadUserByUsername(username: String): UserDetails {
    return userRepository.findByUsr(username).orElseThrow {
      UsernameNotFoundException(String.format("user %s does not exist!", username))
    }
  }
}